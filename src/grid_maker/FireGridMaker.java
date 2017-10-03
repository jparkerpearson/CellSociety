package grid_maker;

import cell.Cell;
import cell.FireCell;

public class FireGridMaker extends GridMaker{

	protected Cell makeNewCell(int initialState) {
		 return new FireCell(initialState);
	}
	
	public int getRandomStartingState() {
		double r = Math.random();
		if (r > .9) {
			return FireCell.EMPTY;
		}
		else if (r > .1) {
			return FireCell.TREE;
		}
		else {
			return FireCell.BURNING;
		}
	}
	
    @Override
    protected boolean isValidState(int state) {
    	return state == FireCell.EMPTY || state == FireCell.TREE || state == FireCell.BURNING;
    }
}

