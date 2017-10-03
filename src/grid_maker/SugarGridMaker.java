package grid_maker;

import cell.Cell;
import cell.SugarCell;

public class SugarGridMaker extends GridMaker {

	private static final int AGENT_SPAWN_PERCENTAGE = 50;
	private static final int MAX_SUGAR_AMOUNT = 25;
	private static final int MIN_SUGAR_AMOUNT = 10;
	private static final int NUM_OF_STATES = 1;
	public static final int DEFAULT_GROWBACK_RATE = 1;

	protected Cell makeNewCell(int initialState) {
		int sugarAmount = (int) (Math.random( )* MAX_SUGAR_AMOUNT);
		if (sugarAmount < MIN_SUGAR_AMOUNT){
			sugarAmount = MIN_SUGAR_AMOUNT;
		}
		return new SugarCell(initialState,sugarAmount,DEFAULT_GROWBACK_RATE);
	}
	
	public int getRandomStartingState() {
		int random = (int) (Math.random() * AGENT_SPAWN_PERCENTAGE);
		if (random < 1) {
			return SugarCell.AGENT;
		}
		else {
			return SugarCell.SUGAR;
		}
	}
	
    @Override
    protected boolean isValidState(int state) {
    		return state <= NUM_OF_STATES && state >= 0;
    }
}
