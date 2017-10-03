package state_manager;

import java.util.LinkedList;

import cell.Cell;
import cell.SegregationCell;
import state_manager.StateManager;
import grid.Grid;
import grid.Location;

/**
 * Implements the rules for Segregation State Manager:
 * @author Parker Pearson
 */
public class SegregationStateManager extends StateManager{
	
	public static final String SIMULATION_NAME = "Segregation";
	
	private double currentThreshold;
	private LinkedList<SegregationCell> removedCells = new LinkedList<SegregationCell>();
	private LinkedList<Location> removeCoordinateQ = new LinkedList<Location>();

	
	public SegregationStateManager(Grid initialState, double threshold) {
		super(initialState);
		currentThreshold = threshold;
	}

	@Override
	public void handleCell(Location loc) {
		if (getAverageSimilarity(loc) < currentThreshold) {
			addCellToRemoveList(loc);
		}
	}
	
	public void updateThreshold(double newThreshold) {
		currentThreshold = newThreshold;
	}

	@Override 
	public void updateGrid() {
		tempGrid = currentGrid.copy();
		
		// find cells that should be removed, will probably refactor to remove and search in same loop by looking at current grid 
		// and removing from temp grid, wouldn't need to have a q of coordinates if i do it that way
		for (Location loc : currentGrid.locationSet()) {
			handleCell(loc);
		}
		
		// remove cell that should be removed
		while (!removeCoordinateQ.isEmpty()) {
			Location coordToRemove = removeCoordinateQ.poll();			
			tempGrid.get(coordToRemove).setState(SegregationCell.EMPTY);
		}
		fillEmptyCells();
		currentGrid = tempGrid.copy();
	}
	
	private double getAverageSimilarity(Location loc) {
		double similiarNeighbors = 0;
		int totalNeighbors = 0;
		for (Cell neighboringCell : currentGrid.getNeighbors(loc, true)) {
			totalNeighbors++;
			if (neighboringCell.getState() == currentGrid.get(loc).getState()) {
				similiarNeighbors++;
			}
		}	
		return similiarNeighbors / totalNeighbors;
	}
	
	private void fillEmptyCells() {
		for (Location loc : tempGrid.locationSet()) {
			if(tempGrid.get(loc).getState() == SegregationCell.EMPTY && removedCells.size() > 0 ) {
				int randomCellSelector = (int) (Math.random() * removedCells.size());
				tempGrid.get(loc).setState(removedCells.remove(randomCellSelector).getState());
			}
		}
	}

	private void addCellToRemoveList(Location loc) {
		removedCells.add((SegregationCell) currentGrid.get(loc));
		removeCoordinateQ.add(loc);
	}
}
