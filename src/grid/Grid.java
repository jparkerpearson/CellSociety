package grid;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

import cell.Cell;
import simulation_objects.Orientation;

public abstract class Grid {
	
	protected HashMap<Location, Cell> grid;
	protected HashMap<Integer,Integer> stats;
	protected GridType type;
	protected int rows;
	protected int columns;
	
	private static HashMap<Orientation,int[]> forwardOffsets;

	public Grid(int numRow, int numCol) {
		this(numRow, numCol, GridType.FINITE);
	}
	
	public Grid(int numRow, int numCol, GridType gt) {
		grid = new HashMap<Location, Cell>();
		stats = new HashMap<Integer,Integer>();
		type = gt;
		rows = numRow;
		columns = numCol;
		forwardOffsets = new HashMap<Orientation,int[]>();
		initializeForwardDirectionOffsets();
	}
	
	public Cell get(Location loc) {
		if (grid.containsKey(loc)) {
			return grid.get(loc);
		}
		return null;
	}
	
	public void put(Location loc, Cell cell) {
		grid.put(loc, cell);
	}
	
	public boolean containsLocation(Location loc) {
		return grid.containsKey(loc);
	}
	
	public Set<Location> locationSet() {
		return grid.keySet();
	}	
	
	public abstract Grid copy();
	
	public ArrayList<Cell> getNeighbors(Location currentLoc, boolean immediate) {
		Cell cell = get(currentLoc);
		if (cell == null)
			return null;
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for (Location loc : getNeighborLocations(currentLoc, immediate)) {
			neighbors.add(get(loc));
		}
		return neighbors;
	}
	
	public abstract ArrayList<Location> getNeighborLocations(Location currentLoc, boolean immediate);
	
	protected ArrayList<Location> filterNeighbors(List<Location> locs) {
		ArrayList<Location> neighbors = new ArrayList<Location>();
		for (Location loc : locs) {
			if (grid.containsKey(loc)) {
				neighbors.add(loc);
			} else if (type == GridType.TOROIDAL) {
				neighbors.add(modularLocation(loc));
			}
		}
		return neighbors;
	}
	

	protected Location modularLocation(Location loc) {
		return new Location((loc.x + rows) % rows, (loc.y + columns) % columns);
	}
	
	public HashMap<Integer,Integer> getStats(){
		for (Cell cell:grid.values()) {
			int state = cell.getState();
			if (stats.containsKey(state))
				stats.put(state, stats.get(state)+1);
			else
				stats.put(state, 1);
		}
		return stats;
	}
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	
	public void printGrid() {
		for (Location loc : locationSet()) {
			System.out.println(loc.toString() + " " + get(loc).getState());
		}
	}
	
	public HashMap<Location, Cell> getGrid() {
		return grid;
	}

	private void initializeForwardDirectionOffsets() {
		int[] neArray = {1, 0, 1, 0, 1, 1};
		forwardOffsets.put(Orientation.NORTHEAST, neArray);
		
		int[] nArray = {1, 0, 1, 1, 1, 1};
		forwardOffsets.put(Orientation.NORTH, nArray);
		
		int[] nwArray = {-1, 0, -1, 0, -1, -1};
		forwardOffsets.put(Orientation.NORTHWEST, nwArray);

		int[] eArray = {1, 1, 1, -1, 0, 1};
		forwardOffsets.put(Orientation.EAST, eArray);

		int[] wArray = {-1, -1, -1, -1, 0, 1};
		forwardOffsets.put(Orientation.WEST, wArray);
		
		int[] seArray = {0, 1, 1, -1, -1, 0};
		forwardOffsets.put(Orientation.SOUTHEAST, seArray);
		
		int[] swArray = {0, -1, -1, -1, -1, 0};
		forwardOffsets.put(Orientation.SOUTHWEST, swArray);
		
		int[] sArray = {-1, 0, 1, -1, -1, -1};
		forwardOffsets.put(Orientation.SOUTH, sArray);
	}
	
	public ArrayList<Location> getForwardNeighborLocations(Location location, Orientation orientation) {
		ArrayList<Location> forwardNeighbors = new ArrayList<Location>();
		int[] offsets = forwardOffsets.get(orientation);
		for (int i=0; i < (offsets.length /2) ; i++) {
			forwardNeighbors.add(new Location(location.x + offsets[i],location.y +offsets[i+3] ));
		}		
		return forwardNeighbors;
	}

	public ArrayList<Location> getNeighborsInVision(Location loc, int vision) {
		ArrayList<Location> neighborsToReturn = new ArrayList<Location>();
		for (int i = -vision; i <= vision; i++) {
			for (int j = -vision; j <= vision; j++) {
				if ((j != 0 || i!= 0) && (j ==0 || i == 0)) {
					Location tempLocation = new Location(loc.x + i,loc.y + j);
					if(grid.containsKey(tempLocation)) {
						neighborsToReturn.add(tempLocation);
					}
				}
			}
		}
		return neighborsToReturn;
	}
}
