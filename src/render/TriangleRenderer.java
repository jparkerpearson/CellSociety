package render;

import grid.Grid;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleRenderer extends GridRenderer {
	
	public TriangleRenderer(Grid cellGrid) {
		super(cellGrid);
	}
	
	@Override
	protected void addImage(int x, int y) {
		double newX = Math.cos(Math.toRadians(60))*imgHeight;
		double newY = Math.sin(Math.toRadians(60))*imgWidth;
		double newGap = Math.sin(Math.toRadians(60))*GAP;
		Shape newImage = new Polygon(0.0,0.0,
									imgWidth,0.0,
									newX, newY);
		if ((x + y)%2 == 1)
			newImage.setRotate(180);
		newImage.setLayoutX(y*(imgWidth/2 + newGap));
		newImage.setLayoutY(x*(newY + GAP));
		
		addToGroup(x, y, newImage);
	}
}
