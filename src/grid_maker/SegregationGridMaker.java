package grid_maker;

import cell.Cell;
import cell.SegregationCell;

public class SegregationGridMaker extends GridMaker{

	protected Cell makeNewCell(int initialState) {
		return new SegregationCell(initialState);
	}
	
	public int getRandomStartingState() {
		double r = Math.random();
		if (r > .6) {
			return SegregationCell.O;
		}
		else if (r > .2) {
			return SegregationCell.X;
		}
		else {
			return SegregationCell.EMPTY;
		}
	}
    
    @Override
    protected boolean isValidState(int state) {
    	return state == SegregationCell.EMPTY || state == SegregationCell.X|| state == SegregationCell.O;
    }
}

