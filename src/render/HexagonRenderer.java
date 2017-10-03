package render;

import grid.Grid;
import javafx.scene.shape.Polygon;

public class HexagonRenderer extends GridRenderer {
	
	public HexagonRenderer(Grid  cellGrid) {
		super(cellGrid);
	}
	
	@Override
	protected void addImage(int x, int y) {
		Polygon newImage = makeShape(); 
		double modify = 0;
		if (x % 2 ==1)
			modify = width(imgWidth)/2;
		newImage.setLayoutX(y*(width(imgWidth) + GAP) + modify);
		newImage.setLayoutY(x*(height(imgHeight) + GAP));
		
		addToGroup(x, y, newImage);
	}
	
	private double width(double imgWidth) {
		return Math.sqrt(3)*imgWidth/1.82;
	}
	
	private double height(double imgHeight) {
		return 1.5*imgHeight/1.82;
	}
	
	private Polygon makeShape() {
		Polygon poly = new Polygon();
		double[] xPoints = new double[6];
		double[] yPoints = new double[6];
		Double[] points = new Double[12];
		for (int k = 0; k<6;k++) { 
			float i = (float) k; 
			xPoints[k] = Math.sin(i/6 * 2 * Math.PI) * imgWidth/1.82; 
			yPoints[k] = Math.cos(i/6 * 2 * Math.PI) * imgHeight/1.82; 
		}
		for (int k = 0; k< 12;k++) { 
		    if (k%2 == 0) { 
		      points[k] = xPoints[k/2]; 
		    } 
		    else { 
		      points[k] = yPoints[(k-1)/2]; 
		    } 
		}		
		poly.getPoints().addAll(points);
		return poly;
	} 
}
