package grid_maker;

import cell.Cell;
import cell.AntCell;

public class AntGridMaker extends GridMaker {
	
	protected Cell makeNewCell(int initialState) {
		 return new AntCell(initialState,1);
	}
	
	public int getRandomStartingState() {
		double r = Math.random();

		if (r < .8) {
			return AntCell.EMPTY;
		}
		else if (r < .85) {
			return AntCell.ANT;
		}
		else if (r < .9) {
			return AntCell.NEST;
		}
		else if (r < .95) {
			return AntCell.FOOD;
		}
		else {
			return AntCell.OBSTACLE;
		}
	}
	
   @Override
   protected boolean isValidState(int state) {
	   return state == AntCell.EMPTY || state == AntCell.ANT || state == AntCell.NEST || state == AntCell.FOOD || state == AntCell.OBSTACLE;
   }

}
