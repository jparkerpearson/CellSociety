package grid;

import java.util.ArrayList;

public class SquareGrid extends Grid {
	
	public SquareGrid(int numRow, int numCol) {
		this(numRow, numCol, GridType.FINITE);
	}
	
	public SquareGrid(int numRow, int numCol, GridType gt) {
		super(numRow, numCol, gt);
	}
	
	public SquareGrid copy() {
		SquareGrid newGrid = new SquareGrid(rows, columns, type);
		for (Location loc : locationSet()) {
			newGrid.put(loc, get(loc).copy());
		}
		return newGrid;
	}
	
	public ArrayList<Location> getNeighborLocations(Location currentLoc, boolean immediate) {
		if (!grid.containsKey(currentLoc))
			return null;
		ArrayList<Location> locs = new ArrayList<Location>();
		locs.add(new Location(currentLoc.x, currentLoc.y + 1));
		locs.add(new Location(currentLoc.x, currentLoc.y - 1));
		locs.add(new Location(currentLoc.x + 1, currentLoc.y));
		locs.add(new Location(currentLoc.x - 1, currentLoc.y));
		if (!immediate) {
			locs.add(new Location(currentLoc.x + 1, currentLoc.y - 1));
			locs.add(new Location(currentLoc.x - 1, currentLoc.y + 1));
			locs.add(new Location(currentLoc.x - 1, currentLoc.y - 1));
			locs.add(new Location(currentLoc.x + 1, currentLoc.y + 1));
		}
		ArrayList<Location> neighbors = filterNeighbors(locs);
		return neighbors;
	}	
	
}
