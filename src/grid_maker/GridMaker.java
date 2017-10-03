package grid_maker;

import cell.Cell;
import grid.Grid;
import grid.Location;

/**
 * GridPrinter used to print current contents
 * of a grid of cells. Used to test backend.
 * 
 * @author Parker Pearson
 */
public abstract class GridMaker {
	
	public void initialize(Grid grid, String config) {
		if (!isValidConfiguration(grid, config)) {
			config = generateNewConfiguration(grid);
		}
		int i = 0;
		for (int x = 0; x < grid.getRows(); x++) {
			for (int y = 0; y < grid.getColumns(); y++) {
				int initialState = Character.getNumericValue(config.charAt(i++));
				Cell cell = makeNewCell(initialState);
				grid.put(new Location(x, y),cell);
			}
		}
	}
	
	protected abstract Cell makeNewCell(int initialState);
	
	public void initializeRandomly(Grid grid) {
		String config = generateNewConfiguration(grid);
		initialize(grid, config);
	}
	
	public abstract int getRandomStartingState();
	
	protected abstract boolean isValidState(int state);
	
	public boolean isValidConfiguration(Grid grid, String config) {
		int r = grid.getRows();
		int c = grid.getColumns();
		if (r * c > config.length()) {
			return false;
		}
		for (int i = 0; i < config.length(); i++) {
			if (!isValidState(Character.getNumericValue(config.charAt(i)))) {
				return false; 
			}
		}
		return true;
	}
	
	public String generateNewConfiguration(Grid grid) {
		String config = "";
		for (int i = 0; i < grid.getRows() * grid.getColumns(); i++) {
			config = config + getRandomStartingState();
		}
		return config;
	}
}
