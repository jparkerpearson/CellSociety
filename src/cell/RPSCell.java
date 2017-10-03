package cell;

public class RPSCell extends Cell {
	
	public static final int EMPTY = 0;
	public static final int PAPER = 1;
	public static final int ROCK = 2;
	public static final int SCISSORS = 3;
	
	
	private int gradientLevel;
	
	public RPSCell(int initialState) {
		this(initialState, 0);
	}	
	
	public RPSCell(int initialState, int colorLevel) {
		super(initialState);
		gradientLevel = colorLevel;
		MAX = SCISSORS;
	}
	
	public void upgradeCell() {
		if (gradientLevel > 0) {
			gradientLevel --;
		}
		
	}
	
	public void downgradeCell(){
		if (gradientLevel < 10) {
			gradientLevel++;
		}
	}
	
	// takes in a cell to battle as well as two cells to store the results of the battle
	public void battleCell(RPSCell cellToBattle, RPSCell cellToBattleCopy, RPSCell currentCellCopy) {
		
		if(getState() == EMPTY) {
			cellToBattleCopy.occupyEmptyCell(currentCellCopy);
		}
		
		else if (cellToBattle.getState() == EMPTY){
			occupyEmptyCell(cellToBattleCopy);
		}
		else if (shouldEat(this, cellToBattle)) {
			eatCell(currentCellCopy, cellToBattleCopy);
		}
		// currently don't account for if other cell should eat current cell as its not clear
		
	}

	// consume another cell
	private void eatCell(RPSCell currentCellCopy, RPSCell cellToBattleCopy) {
		// TODO Auto-generated method stub
		cellToBattleCopy.setState(getState());
		cellToBattleCopy.downgradeCell();
		currentCellCopy.upgradeCell();
		
	}

	// neighbor is empty, move current state into the empty cell
	private void occupyEmptyCell(RPSCell curretlyEmptyCell) {
		if (gradientLevel < 9) {
			curretlyEmptyCell.setState(getState());
			curretlyEmptyCell.setGradientLevel(gradientLevel+1);
		}	
	}

	// returns true if cell 1 should eat cell 2, false if empty or shouldn' eat
	private boolean shouldEat(RPSCell cell1, RPSCell cell2) {
		int cellState1 = cell1.getState();
		int cellState2 = cell2.getState();
		if (cellState1 == ROCK) {
			if (cellState2 == SCISSORS) {
				return true;
			}
		}
		else if (cellState1 == PAPER) {
			if (cellState2 == ROCK) {
				return true;
			}
		}
		else if(cellState1 == SCISSORS) {
			if (cellState2 == PAPER) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public void setGradientLevel(int newGradientLevel) {
		if (newGradientLevel >= 0 && newGradientLevel<= 10) {
			gradientLevel = newGradientLevel;
		}
		else {
			System.out.println("attempting to change graident to a level lthat doesnt make sense");
		}
	}

	@Override
	public void updateColor() {
		if (getState() == ROCK)
			setColor(colormap[ROCK]);
		else if (getState() == PAPER)
			setColor(colormap[PAPER]);
		else if (getState() == SCISSORS)
			setColor(colormap[SCISSORS]);
		else
			setColor(colormap[EMPTY]);		
	}

	@Override
	public Cell copy() {
		return new RPSCell(getState());
	}
	
}
