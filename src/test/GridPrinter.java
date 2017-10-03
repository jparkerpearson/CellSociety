package test;

import cell.Cell;

/**
 * GridPrinter used to print current contents
 * of a grid of cells. Used to test backend.
 * 
 * @author Parker Pearson
 */
public class GridPrinter {

	public static void printGrid(Cell[][] gridToPrint) {
		for (int i= 0; i < gridToPrint[0].length; i++) {
			for (int j =0; j < gridToPrint.length; j++) {
				System.out.print(gridToPrint[i][j].getState() + " | ");
			}
			System.out.println();
			for (int x = 0; x < gridToPrint[0].length; x++) {
				System.out.print("----");
			}
			System.out.println();
		}
		System.out.println();
	}
}
