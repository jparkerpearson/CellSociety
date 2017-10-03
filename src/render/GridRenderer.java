package render;

import grid.Grid; 
import grid.Location;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.shape.Shape;

/**Creates a grid of cells that are equally sized
 * based off of input array size and window width/height
 * @author Lasia Lo
 *
 */

public abstract class GridRenderer {
	protected final int GAP = 2;
	
	protected Group gridImage;
	protected Grid currentGrid;
	private int rows;
	private int columns;
	protected Shape[][] cellImages;
	
	protected double width;
	protected double height;
	protected double imgWidth;
	protected double imgHeight;
		
	public GridRenderer(Grid cellGrid) {
		currentGrid = cellGrid;
		gridImage = new Group();
		rows = cellGrid.getRows();
		columns = cellGrid.getColumns();
		cellImages = new Shape[rows][columns];
	}

	public void setImageSize(double width, double height) {
		this.width = width;
		this.height = height;
		imgHeight = (height - GAP*(rows-1))/rows;
		imgWidth = (width - GAP*(columns-1))/columns;
		renderGrid(true);
	}
	
	protected abstract void addImage(int x, int y);
	
	protected void addToGroup(int x, int y, Shape newImage) {
		newImage.setOnMouseClicked(e->changeCellState(x,y));
		gridImage.getChildren().add(newImage);
		cellImages[x][y] = newImage;
	}
	
	protected void changeCellState(int x, int y) {
		currentGrid.get(new Location(x,y)).iterateState();
		renderGrid(false);
	}

	public void updateGrid(Grid newCellGrid) {
		currentGrid = newCellGrid;
		renderGrid(false);
	}
	
	private void renderGrid(boolean create) {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				if (create)
					addImage(x, y);
				cellImages[x][y].setFill(currentGrid.get(new Location(x, y)).getColor());
			}
		}
	}
	
	public Parent getGrid() {
		return gridImage;
	}
}
