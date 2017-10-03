package state_manager;

import cell.Cell;
import cell.FireCell;
import state_manager.StateManager;
import grid.Grid;
import grid.Location;

/**
 * Implements the rules for Segregation State Manager:
 * @author Parker Pearson
 */
public class FireStateManager extends StateManager {
	
	public static final String SIMULATION_NAME = "Fire";
	
	private double fireSpreadProbability;

	public FireStateManager(Grid initialState, double probability) {
		super(initialState);
		fireSpreadProbability = probability;
	}

	@Override
	public void handleCell(Location loc) {
		if (currentGrid.get(loc).getState() == FireCell.TREE) {
			for (Cell neighboringCell : currentGrid.getNeighbors(loc, true)) {
				if (neighboringCell.getState() == FireCell.BURNING && catchesFire()) {
					tempGrid.get(loc).setState(FireCell.BURNING);
				}
			}
		}
		else if (currentGrid.get(loc).getState() == FireCell.BURNING) {
			tempGrid.get(loc).setState(FireCell.EMPTY);
		}
	}
	
	public void updateThreshold(double newThreshold) {
		fireSpreadProbability = newThreshold;
	}
	
	private boolean catchesFire() {
		return Math.random() < fireSpreadProbability;
	}

}
