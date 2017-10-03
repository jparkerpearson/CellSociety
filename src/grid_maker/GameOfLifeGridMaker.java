package grid_maker;

import cell.Cell;
import cell.GameOfLifeCell;

public class GameOfLifeGridMaker extends GridMaker {
		
	protected Cell makeNewCell(int initialState) {
		return new GameOfLifeCell(initialState);
	}
	
	public int getRandomStartingState() {
		if (Math.random() > .8) {
			return GameOfLifeCell.ALIVE;
		}
		else {
			return GameOfLifeCell.DEAD;
		}
	}
    
    @Override
    protected boolean isValidState(int state) {
    	return state == GameOfLifeCell.DEAD || state == GameOfLifeCell.ALIVE;
    }
}
