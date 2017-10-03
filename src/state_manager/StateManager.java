package state_manager;

import grid.Grid;
import grid.Location;

/**
 * Super class for different state managers which 
 * will direct each cell what to do in current generation
 * @author Parker Pearson
 */
public abstract class StateManager {
	protected Grid currentGrid;
	protected Grid tempGrid;
	
    public StateManager(Grid initialState) {
	    	currentGrid = initialState;	
	    	tempGrid = currentGrid.copy();
    }
	
	/**
     * Iterates through the current 2d array of cells and calls the handleCell on each cell
     */
    public void updateGrid() {
	    	for (Location loc : currentGrid.locationSet()) {
	    		handleCell(loc);
	    	}
	    	currentGrid = tempGrid.copy();
	}

	protected abstract void handleCell(Location loc);
	
	protected void setGrid(Grid newGrid) {
		currentGrid = newGrid.copy();
	}

	public Grid getGrid(){
		return currentGrid;
	}
	
}
