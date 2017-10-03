package cell;
import javafx.scene.paint.Color;
import simulation_objects.SugarAgent;

public class SugarCell extends Cell {		
		public static final Color AGENT_COLOR = Color.BLACK;
		
		public static final int AGENT = 0;
		public static final int SUGAR = 1;

		public static final int MAX_COLOR = 255;
		
		private int sugarGrowBackRate;
		private SugarAgent currentAgent = null;
		private int maxSugar;
		private int currentSugar;
		private int colorMultiplier  ;

		
		public SugarCell(int initialState,int newSugarMax, int growBackRate) {
			setState(initialState);
			if (initialState == AGENT) {
				currentAgent = new SugarAgent();
			}
			else if (newSugarMax < SugarAgent.MIN_SUGAR_LEVEL) {
				newSugarMax = SugarAgent.MIN_SUGAR_LEVEL;
			}
		
			colorMultiplier = MAX_COLOR / newSugarMax;
			currentSugar = newSugarMax;
			sugarGrowBackRate = growBackRate;
			maxSugar = newSugarMax;
			MAX = SUGAR;
			updateColor();
		}
		
		public SugarCell(int state, int maxS, int sugarGrowBack, int remainingSugar) {
			this(state,maxS,sugarGrowBack);
			currentSugar = remainingSugar;
		}

		public void updateColor() {
			if (getState() == AGENT) {
				setColor(AGENT_COLOR);
			}
			else {
				Color c = Color.rgb(MAX_COLOR ,MAX_COLOR - (colorMultiplier * currentSugar), 0, 1);
				setColor(c);
			}	
		}
				
		/**
		 * Create a copy of SugarCell
		 * @return SugarCell which is identical to current cell 
		 * 
		 */
		@Override
	    public SugarCell copy() {
	    	return new SugarCell(getState(), maxSugar, sugarGrowBackRate, currentSugar);
	    }
		
		/**
		 * Increase sugar in the cell by the grow back rate
		 * 
		 */
		public void regenSugar() {
			if (currentSugar + sugarGrowBackRate < maxSugar) {
				currentSugar += sugarGrowBackRate;
			} 
			else {
				currentSugar = maxSugar;
			}
			updateColor();
		}
		
		
		private void consumeSugar() {
			currentSugar = 0;
		}
		
		public void removeAgent() {
			currentAgent = null;
			setState(SUGAR);
		}
		
		public void moveInAgent(SugarAgent newAgent) {
			if (newAgent != null && !newAgent.isDead()) {
				currentAgent = newAgent;
				currentAgent.consumeSugar(currentSugar);
				consumeSugar();
				setState(AGENT);
			}
		}
		
		public SugarAgent getSugarAgent() {
			if (currentAgent != null) {
				return new SugarAgent(currentAgent);
			}
			else {
				return null;
			}
		}

		public int getSugar() {
			return currentSugar;
		}
}
