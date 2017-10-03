package grid_maker;

import cell.Cell;
import cell.RPSCell;

public class RPSGridMaker extends GridMaker{

	protected Cell makeNewCell(int initialState) {
		 return new RPSCell(initialState);
	}
	
	public int getRandomStartingState() {
		double r = Math.random();
		if (r < 0.3333) {
			return RPSCell.ROCK;
		}
		else if (r < 0.6666) {
			return RPSCell.PAPER;
		}
		else {
			return RPSCell.SCISSORS;
		}
	}
	
    @Override
    protected boolean isValidState(int state) {
    	return state == RPSCell.EMPTY || state == RPSCell.ROCK || state == RPSCell.PAPER || state == RPSCell.SCISSORS;
    }
}
