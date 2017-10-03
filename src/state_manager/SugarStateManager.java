package state_manager;

import java.util.ArrayList;

import cell.SugarCell;
import grid.Grid;
import grid.Location;
import simulation_objects.SugarAgent;

public class SugarStateManager extends StateManager{

	public static final String SIMULATION_NAME = "Sugar";
	

	public SugarStateManager(Grid initialState) {
		super(initialState);
	}

	
	@Override
	public void handleCell(Location loc) {
		SugarCell currentCell = (SugarCell)currentGrid.get(loc);
		SugarAgent currentAgent = currentCell.getSugarAgent();
		if (currentAgent != null) {
			Location toMove = neighborWithMostSugar(loc, currentAgent);
			if (toMove != null) {
				((SugarCell) tempGrid.get(toMove)).moveInAgent(currentAgent);
				((SugarCell) tempGrid.get(loc)).removeAgent();
			}		
		}
		else {
			currentCell.regenSugar();
		}
	}

	private Location neighborWithMostSugar(Location loc, SugarAgent currentAgent) {
		ArrayList<Location> neighborsInVisionLocations = currentGrid.getNeighborsInVision(loc, currentAgent.getVision());
		int maxSugar = -1;
		Location locationToMove = null;
		for (Location l : neighborsInVisionLocations) {
			if (((SugarCell) currentGrid.get(l)).getSugar() > maxSugar && ((SugarCell)currentGrid.get(l)).getSugarAgent() == null) {
				maxSugar = ((SugarCell) currentGrid.get(l)).getSugar();
				locationToMove = l;
			}	
		}
		return locationToMove;
	}
	
	
}
