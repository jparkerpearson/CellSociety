package simulation_objects;

import grid.Location;

public class SugarAgent {
	
	public static final int DEFAULT_VISION = 1;
	public static final int DEFAULT_METABOLISM = 4;
	public static final int DEFAULT_STARTING_SUGAR = 25;
	public static final int MAX_VISION_LEVEL = 5;
	public static final int MIN_VISION_LEVEL = 1;
	public static final int MAX_META_LEVEL = 5;
	public static final int MAX_SUGAR_LEVEL = 20;
	public static final int MIN_SUGAR_LEVEL = 0;
	public static final int MIN_META_LEVEL = 1;

	private int currentSugar;
	private int sugarMetabolism;
	private int vision;
	
	private Location antLocation;

	public SugarAgent() {
		this(DEFAULT_VISION, DEFAULT_METABOLISM, DEFAULT_STARTING_SUGAR );
	}
	
	public SugarAgent(int agentVision, int agentMetabolism, int startingSugarLevel) {
		vision = (agentVision <= MAX_VISION_LEVEL && agentVision >= MIN_VISION_LEVEL) ? agentVision : DEFAULT_VISION;
		sugarMetabolism = (agentMetabolism <= MAX_META_LEVEL && agentMetabolism >= MIN_META_LEVEL) ? agentMetabolism : DEFAULT_METABOLISM;
		currentSugar = (currentSugar <= MAX_SUGAR_LEVEL && currentSugar >= MIN_SUGAR_LEVEL) ? currentSugar : DEFAULT_STARTING_SUGAR;
	}
	
	public SugarAgent(SugarAgent currentAgent) {
		if (currentAgent != null) {
			vision = currentAgent.vision;
			sugarMetabolism = currentAgent.sugarMetabolism;
			currentSugar = currentAgent.currentSugar;
		}		
	}

	public Location getLocation() {
		return antLocation;
	}
	
	public int getVision() {
		return vision;
	}
	
	public boolean isDead() {
		return currentSugar > 0;
	}
	
	public void consumeSugar(int sugarAmount) {
		currentSugar += sugarAmount;
		currentSugar -= sugarMetabolism;
	}
}
