package cell;

import java.util.ArrayList;
import java.util.List;

import grid.Grid;
import grid.Location;
import simulation_objects.Ant;

public class AntCell extends Cell {

	public static final int EMPTY = 0;
	public static final int ANT = 1;
	public static final int NEST = 2;
	public static final int FOOD = 3;
	public static final int OBSTACLE = 4;
	
	public static final int MAX_PHEROMONE_FOOD = 10;
	public static final int MAX_PHEROMONE_HOME = 10;

	private int foodPheromoneLevel;
	private int homePheromoneLevel;
	
	
	private ArrayList<Ant> occupyingAnts = new ArrayList<Ant>();
	
	public AntCell(int initialState, int numOfAnts) {
		super(initialState);
		MAX = OBSTACLE;
		if(initialState == NEST) {
			homePheromoneLevel = MAX_PHEROMONE_HOME;
		}
		if (initialState == ANT) {
			for (int i = 0; i < numOfAnts; i++) {
				Ant tempAnt = new Ant();
				occupyingAnts.add(tempAnt);
			}
		}
		
	}
	
	public AntCell(int state, ArrayList<Ant> currentAnts) {
		this(state,currentAnts.size());
		occupyingAnts.clear();
		for (Ant a : currentAnts) {
			occupyingAnts.add( a.clone() );
		}
	}

	// Resets the ants currently occupying the cell
	public void clearAnts() {
		occupyingAnts.clear();
		if (getState() == ANT) {
			this.setState(EMPTY);
		}
	}
	
	public void addAnt(Ant antToAdd) {
		occupyingAnts.add(antToAdd);
		if (getState() == EMPTY) {
			this.setState(ANT);
		}
	}
	
	public void addNewAnt(Location location) {
		occupyingAnts.add(new Ant());
	}

	public ArrayList<Ant> getAnts() {
		return occupyingAnts;	
	}

	@Override
	public void updateColor() {
		if (getState() == NEST)
			setColor(colormap[NEST]);
		else if (getState() == FOOD)
			setColor(colormap[FOOD]);
		else if (getState() == ANT)
			setColor(colormap[ANT]);
		else if (getState() == OBSTACLE)
			setColor(colormap[OBSTACLE]);
		else
			setColor(colormap[EMPTY]);
	}
	
	public void topOffFoodLevel() {
		topOffFoodLevel(MAX_PHEROMONE_FOOD);
	}
	
	public void topOffFoodLevel(int foodLevel) {
		if (foodLevel > 0) {
			foodPheromoneLevel = foodLevel;
		}
	}
	
	public void topOffHomeLevel(int homeLevel) {
		if (homeLevel > 0) {
			homePheromoneLevel = homeLevel;
		}
	}

	@Override
	public Cell copy() {
		return new AntCell(getState(),occupyingAnts);
	}

	public int getHomePheromoneAmount() {
		return homePheromoneLevel;
	}

	public int getFoodPheromoneAmount() {
		return foodPheromoneLevel;
	}
}
