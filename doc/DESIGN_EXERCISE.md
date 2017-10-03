```java
	/**
     * Super class for different parsers. Used to get 
     * information from the XML file
     */
	public abstract XmlParser() {}
	
	public void loadFile(File fileName) {}

	public List<String> getSimulation() {}

	
```

```java
	/**
     * The simulator gets info from parser,initializes the 
     * state, and calls the manager and renderer to update the 
     * state
     */
	public Simulator(StateManager stateMananager, Renderer frontEndRenderer, int updateTime) {}
	
	public void simulate() {}

	public void updateAnimationSpeed(int updateTime)
```

```java
	/**
     * The renderer is passed in a grid containing cells
     * and updates the screen to display current state
     */
	public Renderer(Cell[][] cellGrid) {}
	
	public void update() {}	
```

```java
	/**
     * A super class which will be extended for
     *  different cell types for different simulations
     * 
     */
	public abstract Cell() {}
	
	public void updateState(CellState newState) {}	

	public Color getColor()
```

```java
	/**
     * Super class for different state managers which 
     * will direct each cell what to do in current generation
     */
	public abstract StateManager(Cell[][] initialState) {}
	
	public void updateState() {}	
```