package state_manager;

import java.util.ArrayList;

import cell.RPSCell;
import grid.Grid;
import grid.Location;

public class RPSStateManager extends StateManager{
	public static final String SIMULATION_NAME = "RPS";
	
	public RPSStateManager(Grid initialState, double probability) {
		super(initialState);
	}

	@Override
	public void handleCell(Location loc) {
		RPSCell currentCell = (RPSCell) currentGrid.get(loc);
		Location randomNeighborLocation = getRandomLocation(currentGrid.getNeighborLocations(loc, false));	
		currentCell.battleCell((RPSCell) currentGrid.get(randomNeighborLocation), (RPSCell) tempGrid.get(randomNeighborLocation), (RPSCell) tempGrid.get(loc));		
	}

	// returns a random location from a list of locations
	// TODO: replace array list with list once dependency is updated
	private Location getRandomLocation(ArrayList<Location> neighborLocations) {
		int randomIndex = (int) (Math.random() * neighborLocations.size());
		return neighborLocations.get(randomIndex);
	}
}
