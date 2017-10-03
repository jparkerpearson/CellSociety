package cell;

public class FireCell extends Cell {
	
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	
	public FireCell(int initialState) {
		super(initialState);
		MAX = BURNING;
	}
	
	public void updateColor() {
		if (getState() == TREE)
			setColor(colormap[TREE]);
		else if (getState() == BURNING)
			setColor(colormap[BURNING]);
		else if (getState() == EMPTY)
			setColor(colormap[EMPTY]);
	}
	
	/**
	 * Create a copy of FireCell
	 * @return FireCell which is identical to current cell 
	 * 
	 */
    public FireCell copy() {
    	return new FireCell(getState());
    }

}
