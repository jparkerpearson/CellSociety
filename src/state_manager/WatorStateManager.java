package state_manager;

import cell.WatorCell;
import state_manager.StateManager;
import grid.Grid;
import grid.Location;

import java.util.ArrayList;

/**
 * Implements the rules for Game of life state manager:
 * @author Raphael Kim
 */
public class WatorStateManager extends StateManager {

	public static final String SIMULATION_NAME = "Wator";
	
	private int breedingAge;
	private int breedingEnergy;
	private int energyPerFish;
	
	public WatorStateManager(Grid initialState, int breedingAge, int breedingEnergy, int energyPerFish) {
		super(initialState);
		this.breedingAge = breedingAge;
		this.breedingEnergy = breedingEnergy;
		this.energyPerFish = energyPerFish;
	}

	@Override
    public void updateGrid() {
		for (Location loc : currentGrid.locationSet()) {
			if (((WatorCell) currentGrid.get(loc)).isPredator()) {
				handleCell(loc);
			}
		}
		currentGrid = tempGrid.copy();
		for (Location loc : currentGrid.locationSet()) {
			if (((WatorCell) currentGrid.get(loc)).isPrey()) {
				handleCell(loc);
			}
		}
		currentGrid = tempGrid.copy();
	}
    
	@Override
	public void handleCell(Location loc) {
		WatorCell cell = (WatorCell) currentGrid.get(loc);
		ArrayList<Location> availableCoordinates = new ArrayList<Location>();
		if (cell.isPrey()) {
			cell.gainAge();	
			availableCoordinates = getNeighboringCoordinatesWithState(loc, WatorCell.OCEAN);
		}
		else if (cell.isPredator()) {
			cell.loseEnergy();
			if (isSpawned(cell)) {
				cell.gainEnergy(energyPerFish);
			}
			else if (isStarved(cell)) {
				cell = new WatorCell(WatorCell.OCEAN);
			}
			availableCoordinates = getNeighboringCoordinatesWithState(loc, WatorCell.PREY);
			if (availableCoordinates.size() > 0) {
				cell.gainEnergy(energyPerFish);
			}
			else {
				availableCoordinates = getNeighboringCoordinatesWithState(loc, WatorCell.OCEAN);
			}
		}
		if (availableCoordinates.size() > 0) {
			Location newLoc = availableCoordinates.get((int)(Math.random() * availableCoordinates.size()));
			if (isBreedable(cell)) {
				cell.breed();		
				tempGrid.put(loc, new WatorCell(cell));
			}
			else {			
				tempGrid.put(loc, new WatorCell(WatorCell.OCEAN));
			}
			tempGrid.put(newLoc,  new WatorCell(cell));	
		}
	}
	
	// return true if cell satisfies breeding requirements
	private boolean isBreedable(WatorCell cell) {
		return cell.getAge() >= breedingAge || cell.getEnergy() >= breedingEnergy;
	}
	
	// return true if predator died from starvation
	private boolean isStarved(WatorCell cell) {
		return cell.isPredator() && cell.getEnergy() == 0;
	}
	
	// return true if predator was just spawned
	private boolean isSpawned(WatorCell cell) {
		return cell.isPredator() && cell.getEnergy() < 0;
	}
	
	// return an ArrayList of immediately neighboring coordinates that match given state
	private ArrayList<Location> getNeighboringCoordinatesWithState(Location currentLoc, int state) {
		ArrayList<Location> locs = new ArrayList<Location>();
		for (Location loc : tempGrid.getNeighborLocations(currentLoc, true)) {
			if (tempGrid.get(loc).getState() == state) {
				locs.add(loc);
			}
		}
		return locs;
	}
		
}



	