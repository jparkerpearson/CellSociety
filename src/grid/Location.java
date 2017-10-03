package grid;

import java.util.Objects;

public class Location {
	public final int x;
	public final int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location loc = (Location) obj;
		return loc.x == x && loc.y == y;
	}
	
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}