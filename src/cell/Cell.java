package cell;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Cell maintains the current state of individual
 * cell in a grid. 
 */
public abstract class Cell {
	
	public static final int DEFAULT_STATE = 0;
	
	protected static Color[] colormap;
	public static final Color DEFAULT_COLOR = Color.WHITE;
	
	protected int MAX;
	private Paint currentColor;
	private int currentState;
	

	public Cell() {
		this(DEFAULT_STATE);
	}
	
	/**
	 * 
	 * @param initialState of the cell
	 */
	public Cell(int initialState) {
		currentState = initialState;
		currentColor = DEFAULT_COLOR;
		updateColor();
	}
	
 	public abstract void updateColor();
	
	public static void setColormap(Color[] colors) {
		colormap = colors;
	}
 	
	/**
	 * @return current color of the cell
 	 */
    public Paint getColor() {
		return currentColor;    
    }
    
	/**
	 * @return current state of the cell
	 */
    public int getState() {
    	return currentState; 
    }
    
    /**
	 * 
	 * @param new color of the cell
     * @return 
	 */
	protected void setColor(Color newColor) {
		if (newColor != null) {
			currentColor = newColor;
		}
	}
    
    /**
     * @param new state of the cell
     */
    public void setState(int newState) {
    		currentState = newState;
    }
    
	public void iterateState() {
		if (getState() != MAX)
			setState(getState()+1);
		else
			setState(0);
		updateColor();
	}
    
    public abstract Cell copy();

}
