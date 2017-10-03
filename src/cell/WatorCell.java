package cell;

public class WatorCell extends Cell {

	public static final int OCEAN = 0;
	public static final int PREY = 1;
	public static final int PREDATOR = 2;
	
	private int age;
	private int energy;
	
	public WatorCell(int initialState) {
		this(initialState, 0, 0);
	}
	
	public WatorCell(WatorCell cell) {
		this(cell.getState(), cell.getAge(), cell.getEnergy());
	}
	
	public WatorCell(int state, int age, int energy) {
		super(state);
		this.age = age;
		this.energy = energy;
		MAX = PREDATOR;
	}
	
	public void updateColor() {
		if (getState() == OCEAN)
			setColor(colormap[OCEAN]);
		else if (getState() == PREY)
			setColor(colormap[PREY]);
		else if (getState() == PREDATOR)
			setColor(colormap[PREDATOR]);
	}

	/**
	 * @return true if cell is a prey
	 */
	public boolean isPrey() {
		return getState() == PREY;
	}
	
	/**
	 * @return true if cell is a predator
	 */
	public boolean isPredator() {
		return getState() == PREDATOR;
	}
	
	/**
	 * Returns age of prey; returns -1 if not applicable
	 * @return age of prey 
	 */
	public int getAge() {
		if (isPredator()) {
			return -1;
		}
		else {
			return age;
		}
	}
	
	/**
	 * returns energy level of predator or -1 if not applicable 
	 * @return energy level of predator
	 */
	public int getEnergy() {
		if (isPrey()) {
			return -1;
		}
		else {
			return energy;
		}
	}
	
	/**
	 * Increment age
	 * @return true if age successfully incremented
	 */
	public boolean gainAge() {
		if (isPrey()) {
			age++;
		}
		return isPrey();
	}
	
	/**
	 * Decrement energy
	 * @return true if age successfully decremented
	 */
	public boolean loseEnergy() {
		if (isPredator()) {
			energy--;
		}
		return isPredator();
	}
	
	/**
	 * Gain 2 energy if cell contains predator
	 * @return true if energy increase is successful
	 */
	public boolean gainEnergy(int gain) {
		if (isPredator()) {
			energy += gain;
		}
		return isPredator();
	}
	
	/**
	 * Become descendent of self
	 */
	public void breed() {
		age = 0;
		energy /= 2;
	}

	/**
	 * Create a copy of WatorCell
	 * @return WatorCell which is identical to current cell 
	 */
    public WatorCell copy() {
	    	WatorCell cell = new WatorCell(getState(), age, energy);
	    	return cell;
    }

}