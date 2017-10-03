package grid;

import java.util.ArrayList;

public class HexagonGrid extends Grid {

	public HexagonGrid(int numRow, int numCol) {
		this(numRow, numCol, GridType.FINITE);
	}
	
	public HexagonGrid(int numRow, int numCol, GridType gt) {
		super(numRow, numCol, gt);
	}
	
	public HexagonGrid copy() {
		HexagonGrid newGrid = new HexagonGrid(rows, columns, type);
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
		locs.add(new Location(currentLoc.x - 1, currentLoc.y));
		locs.add(new Location(currentLoc.x + 1, currentLoc.y));
		if (isOddRow(currentLoc)) {
			locs.add(new Location(currentLoc.x - 1, currentLoc.y + 1));
			locs.add(new Location(currentLoc.x + 1, currentLoc.y + 1));
		}
		else {
			locs.add(new Location(currentLoc.x - 1, currentLoc.y - 1));
			locs.add(new Location(currentLoc.x + 1, currentLoc.y - 1));
		}
		ArrayList<Location> neighbors = filterNeighbors(locs);
		return neighbors;
	}	
	
	private boolean isOddRow(Location loc) {
		return loc.x % 2 == 1;
	}
}
