package state_manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cell.AntCell;
import cell.Cell;
import grid.Grid;
import grid.Location;
import simulation_objects.Ant;
import simulation_objects.Orientation;

public class AntStateManager extends StateManager {
public static final String SIMULATION_NAME = "Ant";
public static final int MAX_ANTS = 10;
	
	public AntStateManager(Grid initialState) {
		super(initialState);
	}
	
	@Override
	public void updateGrid() {
	
		clearAnts(tempGrid);
	    super.updateGrid();   
	
	}

	private void clearAnts(Grid antGrid) {
		HashMap<Location, Cell> tempGrid = antGrid.getGrid();
		for (Cell c : tempGrid.values()) {
			((AntCell) c).clearAnts();
		}
	}

	@Override
	public void handleCell(Location loc) {
		AntCell currentCell = (AntCell) currentGrid.get(loc);
		ArrayList<Ant> listOfAnts = currentCell.getAnts();
		for (Ant a : listOfAnts) {
			moveAnt(a,loc);
		}	
	}

	private void moveAnt(Ant a, Location loc) {
		if (a.hasFood()) {
			returnToNest(a,loc);
		}
		else {
			findFoodSource(a,loc);
		}
	}
	
	private void findFoodSource(Ant a, Location loc) {
		// if located at nest move to location with max food pheromones
		if (a != null) {
			Location locationToMove = null;
			
			
			if (currentGrid.get(loc).getState() == AntCell.NEST) {
				a.setOrientation(getMaxPheromoneDirection(a,true,loc));
			}
			
			ArrayList<Location> possibleLocations = currentGrid.getForwardNeighborLocations(loc,a.getOrientation());

			
			
			if (! possibleLocations.isEmpty()) {
				locationToMove = selectLocation(possibleLocations);
			}

			if (locationToMove != null) {
				dropHomePheromones(a,loc);
				changeAntPosition(a,locationToMove,loc);
			}
			else {
				possibleLocations = currentGrid.getNeighborLocations(loc, false);
				locationToMove = null;

				if (! possibleLocations.isEmpty()) {
					locationToMove = selectLocation(possibleLocations);
				}
				if (locationToMove != null) {
					dropHomePheromones(a,loc);
					changeAntPosition(a,locationToMove,loc);
				}
				else {
					stayInPlace(a, loc);

				}
			}
		}
	}

	private void stayInPlace(Ant a,Location loc) {
		if (a!= null) {
			AntCell sameLocation = (AntCell) tempGrid.get(loc);
			sameLocation.addAnt(a);
		}
		
	}
	
	private void returnToNest(Ant a, Location loc) {
		if (a != null) {
			// if located at nest move to location with max food pheromones
			Location locationToMove = null;
			if (currentGrid.get(loc).getState() == AntCell.FOOD) {
				a.setOrientation(getMaxPheromoneDirection(a,false,loc));
			}
					
			ArrayList<Location> possibleLocations = currentGrid.getForwardNeighborLocations(loc,a.getOrientation());
						
			if (! possibleLocations.isEmpty()) {
				locationToMove = maxHomePheromones(possibleLocations);
			}
			
			if (locationToMove != null) {
				dropFoodPheromones(a,loc);
				changeAntPosition(a,locationToMove,loc);
			}
			else {
				possibleLocations = currentGrid.getNeighborLocations(loc, false);
				locationToMove = null;
				if (! possibleLocations.isEmpty()) {
					locationToMove = maxHomePheromones(possibleLocations);
				}
				if (locationToMove != null) {
					dropFoodPheromones(a,loc);
					changeAntPosition(a,locationToMove,loc);
				}	
				else {
					stayInPlace(a,loc);

				}
			}
		}
	}
	
	private Location maxHomePheromones(ArrayList<Location> possibleLocations) {
		Location maxHomePheromoneLocation = null;
		int maxHomePheromoneAmount = 0;
		for(Location l:possibleLocations) {
			if (currentGrid.get(l)!= null) {
				if (((AntCell)currentGrid.get(l)).getHomePheromoneAmount() > maxHomePheromoneAmount) {
					maxHomePheromoneAmount = ((AntCell)currentGrid.get(l)).getHomePheromoneAmount();
					maxHomePheromoneLocation = l;
				}
			}
			
		}
		if (maxHomePheromoneLocation == null) {
			return possibleLocations.get((int) (Math.random()*possibleLocations.size()));
		}
		else {
			return maxHomePheromoneLocation;
		}
	}
	
	private Location maxFoodPheromones(ArrayList<Location> possibleLocations) {
		Location maxFoodPheromoneLocation = null;
		int maxFoodPheromoneAmount = 0;
		for(Location l:possibleLocations) {
			if (((AntCell)currentGrid.get(l)).getFoodPheromoneAmount() > maxFoodPheromoneAmount) {
				maxFoodPheromoneAmount = ((AntCell)currentGrid.get(l)).getFoodPheromoneAmount();
				maxFoodPheromoneLocation = l;
			}
		}
		
		if (maxFoodPheromoneLocation == null) {
			return possibleLocations.get((int) (Math.random()*possibleLocations.size()));
		}
		return maxFoodPheromoneLocation;
	}

	private void changeAntPosition(Ant a, Location locationToMove, Location locationMovingFrom) {
		if (locationToMove != null) {
			a.setOrientation(getOrientation(locationMovingFrom,locationToMove));
			
			AntCell newLocation = (AntCell) tempGrid.get(locationToMove);
			if (newLocation!= null) {
				if ((newLocation.getState() == AntCell.FOOD && a.hasFood() == false) || (newLocation.getState() == AntCell.NEST && a.hasFood() == true)) {
					a.toggleHasFood();
				}
			
				newLocation.addAnt(a);
			}
		}
		
	}

	// returns the orientation pointing from first paramater cell to the second paramater cell OR null if in the same position
	private Orientation getOrientation(Location start, Location end) {
		if (start.x > end.x) { //WEST
			if( start.y > end.y) {
				return Orientation.SOUTHWEST;
			}
			else if( start.y < end.y) {
				return Orientation.NORTHWEST;
			}
			else {
				return Orientation.WEST;
			}
		}
		else if (start.x < end.x){ // EAST
			if( start.y > end.y) {
				return Orientation.SOUTHEAST;
			}
			else if( start.y < end.y) {
				return Orientation.NORTHEAST;
			}
			else {
				return Orientation.EAST;
			}
		}
		else {
			if( start.y > end.y) {
				return Orientation.SOUTH;

			}
			else if( start.y < end.y) {
				return Orientation.NORTH;
			}
		}
		return null;
	}

	private Orientation getMaxPheromoneDirection(Ant a, boolean homePheromones,Location loc) {
		if (homePheromones) {
			return getOrientation(loc,maxHomePheromones(currentGrid.getNeighborLocations(loc, false)));
		}
		else {
			return getOrientation(loc,maxFoodPheromones(currentGrid.getNeighborLocations(loc, false)));
		}
	}



	private Location selectLocation(List<Location> locationList) {
		ArrayList <Location> locationsToRemove = new ArrayList<Location>();
		List<Location> remainingNeighbors = locationList;
		Iterator<Location> itr = remainingNeighbors.iterator();
		while(itr.hasNext()) {
			Location thisLocation = (Location) itr.next();
			if (currentGrid.get( thisLocation) == null) {
				itr.remove();
			}
			else if (currentGrid.get(thisLocation).getState() == AntCell.OBSTACLE || ((AntCell)(currentGrid.get(thisLocation))).getAnts().size() >= MAX_ANTS) {
				itr.remove();	
			}
		}
		if (remainingNeighbors.isEmpty()) {

			return null;
		}
		else {
			return getRandomWeighted(remainingNeighbors);			
		}
	}
	
	private Location getRandomWeighted(List<Location> remainingNeighbors) {
		int totalN= 0;
		for( Location l : remainingNeighbors) {
			AntCell currentCell = (AntCell) currentGrid.get(l);
			totalN+= currentCell.getFoodPheromoneAmount();
		}
		int randomNum = (int) Math.random()*totalN;
		for (Location l:remainingNeighbors) {
			totalN-=randomNum;
			if (totalN <=0) {
				return l;
			}
		}
		
		return remainingNeighbors.get(remainingNeighbors.size()-1);
	}

	private void dropHomePheromones(Ant a, Location loc) {
		AntCell currentCell = (AntCell) currentGrid.get(loc);
		if( currentCell.getState() != AntCell.NEST) {
			Location maxPherLoc = maxHomePheromones(tempGrid.getNeighborLocations(loc,false));
			int maxPherAmount = ((AntCell)currentGrid.get(maxPherLoc)).getHomePheromoneAmount();
			if (maxPherAmount > currentCell.getHomePheromoneAmount()) {
				((AntCell) tempGrid.get(loc)).topOffHomeLevel(maxPherAmount);
			}
		}		
	}
	
	private void dropFoodPheromones(Ant a, Location loc) {
		currentGrid.get(loc);
		AntCell currentCell = (AntCell) currentGrid.get(loc);
		if( currentCell.getState() == AntCell.FOOD) {
			currentCell.topOffFoodLevel();
		}
		else {
			Location maxPherLoc = maxHomePheromones(tempGrid.getNeighborLocations(loc,false));
			int maxPherAmount = ((AntCell)currentGrid.get(maxPherLoc)).getHomePheromoneAmount();
			if (maxPherAmount > currentCell.getHomePheromoneAmount()) {
				((AntCell) tempGrid.get(loc)).topOffHomeLevel(maxPherAmount);
			}
		}		
	}
	
}
