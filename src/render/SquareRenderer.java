package render;

import grid.Grid;
import javafx.scene.shape.Rectangle;

public class SquareRenderer extends GridRenderer {
	
	public SquareRenderer(Grid cellGrid) {
		super(cellGrid);
	}
	
	@Override
	protected void addImage(int x, int y) {
		Rectangle newImage = new Rectangle(imgWidth,imgHeight);
		newImage.setX(y*(imgWidth + GAP));
		newImage.setY(x*(imgHeight + GAP));
		
		addToGroup(x, y, newImage);
	}
}
