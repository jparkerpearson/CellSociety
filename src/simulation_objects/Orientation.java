package simulation_objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * 
 * Code based off class at https://stackoverflow.com/questions/1972392/java-pick-a-random-value-from-an-enum 
 *
 */
public enum Orientation {
	NORTHEAST,
	NORTH,
	NORTHWEST,
	WEST,
	EAST,
	SOUTHWEST,
	SOUTH,
	SOUTHEAST;
	
	private static final List<Orientation> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Orientation randomOrientation()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
