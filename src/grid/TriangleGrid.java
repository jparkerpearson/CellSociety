package grid;

import java.util.ArrayList;

public class TriangleGrid extends Grid {

	public TriangleGrid(int numRow, int numCol) {
		this(numRow, numCol, GridType.FINITE);
	}
	
	public TriangleGrid(int numRow, int numCol, GridType gt) {
		super(numRow, numCol, gt);
	}
	
	public TriangleGrid copy() {
		TriangleGrid newGrid = new TriangleGrid(rows, columns, type);
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
		if (isOddLocation(currentLoc))
			locs.add(new Location(currentLoc.x + 1, currentLoc.y));
		else
			locs.add(new Location(currentLoc.x - 1, currentLoc.y));
		if (!immediate) {
			locs.add(new Location(currentLoc.x, currentLoc.y + 2));
			locs.add(new Location(currentLoc.x, currentLoc.y - 2));
			locs.add(new Location(currentLoc.x + 1, currentLoc.y - 1));
			locs.add(new Location(currentLoc.x - 1, currentLoc.y + 1));
			locs.add(new Location(currentLoc.x - 1, currentLoc.y - 1));
			locs.add(new Location(currentLoc.x + 1, currentLoc.y + 1));
			if (isOddLocation(currentLoc)) {
				locs.add(new Location(currentLoc.x - 1, currentLoc.y));
				locs.add(new Location(currentLoc.x + 1, currentLoc.y + 2));
				locs.add(new Location(currentLoc.x + 1, currentLoc.y - 2));
			}
			else {
				locs.add(new Location(currentLoc.x + 1, currentLoc.y));
				locs.add(new Location(currentLoc.x - 1, currentLoc.y + 2));
				locs.add(new Location(currentLoc.x - 1, currentLoc.y - 2));
			}
		}
		ArrayList<Location> neighbors = filterNeighbors(locs);
		return neighbors;
	}	

	private boolean isOddLocation(Location loc) {
		return ((loc.x + loc.y) % 2) == 1;
	}
}
