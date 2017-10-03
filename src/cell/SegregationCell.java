package cell;

public class SegregationCell extends Cell {
	
	public static final int O = 2;
	public static final int X = 1;
	public static final int EMPTY = 0;
	
	public SegregationCell(int initialState) {
		super(initialState);
		MAX = O;
	}
	
	public void updateColor() {
		if (getState() == O)
			setColor(colormap[O]); 
		else if (getState() == X)
			setColor(colormap[X]);
		else if (getState() == EMPTY)
			setColor(colormap[EMPTY]);
	}
	
	/**
	 * Create a copy of SegregationCell
	 * @return SegregationCell which is identical to current cell 
	 */
    public SegregationCell copy() {
    		return new SegregationCell(getState());
    }

}
