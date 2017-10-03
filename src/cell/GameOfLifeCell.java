package cell;

public class GameOfLifeCell extends Cell {

	public static final int DEAD = 0;
	public static final int ALIVE = 1;
	
	public GameOfLifeCell(int initialState) {
		super(initialState);
		MAX = ALIVE;
	}
	
	public void updateColor(){
		if (getState() == ALIVE)
			setColor(colormap[ALIVE]);
		else if(getState() == DEAD)
			setColor(colormap[DEAD]);
	}
	
	/**
	 * Create a copy of GameOfLifeCell
	 * @return GameOfLifeCell which is identical to current cell 
	 */
    public GameOfLifeCell copy() {
    		return new GameOfLifeCell(getState());
    }

}
