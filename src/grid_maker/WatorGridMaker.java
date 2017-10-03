package grid_maker;

import cell.Cell;
import cell.WatorCell;

public class WatorGridMaker extends GridMaker {

	public Cell makeNewCell(int initialState) {
		return new WatorCell(initialState);
	}
	
	public int getRandomStartingState() {
		double r = Math.random();
		if (r > .7) {
			return WatorCell.PREY;
		}
		else if (r > .5) {
			return WatorCell.PREDATOR;
		}
		else {
			return WatorCell.OCEAN;
		}
	}
    
    @Override
    protected boolean isValidState(int state) {
    	return state == WatorCell.OCEAN || state == WatorCell.PREY || state == WatorCell.PREDATOR;
    }
}
