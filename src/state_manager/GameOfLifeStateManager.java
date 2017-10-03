package state_manager;

import cell.Cell;
import cell.GameOfLifeCell;
import state_manager.StateManager;
import grid.Grid;
import grid.Location;

/**
 * Implements the rules for Game of life state manager:
 * @author Parker Pearson
 */
public class GameOfLifeStateManager extends StateManager {	
	
	public static final String SIMULATION_NAME = "GameOfLife";
	
	public static final int MAX_NEIGHBORS = 3;
	public static final int MIN_NEIGHBORS = 2;

	public GameOfLifeStateManager(Grid initialState) {
		super(initialState);
	}

	@Override
	public void handleCell(Location loc) {
		boolean beAlive = false;
		if (currentGrid.get(loc).getState() == GameOfLifeCell.ALIVE) { 
			beAlive = shouldLive(numAliveNeighbors(loc));
		}
		else {
			beAlive = shouldBeBorn(numAliveNeighbors(loc));
		}
		int newState = beAlive ? GameOfLifeCell.ALIVE : GameOfLifeCell.DEAD;
		//tempGrid.put(loc, new GameOfLifeCell(newState));
		tempGrid.get(loc).setState(newState);
	}

	// returns whether a dead cell should be revived given its current amount of alive neighbors 
	private boolean shouldBeBorn(int numAliveNeighbors) {
		return numAliveNeighbors == MAX_NEIGHBORS;
	}

	// returns whether a living cell should continue to live given its amount of neighbors
	private boolean shouldLive(int numAliveNeighbors) {
		return numAliveNeighbors >= MIN_NEIGHBORS && numAliveNeighbors <= MAX_NEIGHBORS;
	}

	// return the amount of neighbors of a cell that are alive
	private int numAliveNeighbors(Location loc) {
		int aliveNeighbors = 0;
		for (Cell neighbor : currentGrid.getNeighbors(loc, false)) {
			if (neighbor.getState() == GameOfLifeCell.ALIVE)
				aliveNeighbors++;
		}
		return aliveNeighbors;
	}
	
}
