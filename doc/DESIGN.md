DESIGN
===================

Introduction
-------------

The aim of this project is to create a flexible cellular automata (CA) simulator in JavaFX. The simulator must be able to handle different set of rules determined by any type of CA simulation. 

* Primary design goal
 - The simulation contains a manager that is able to access and manipulate the contents of the grid (cells and their states)
 - Each manager will know the set of rules for a specific simulation it represents
* Primary architecture of the design
 - Simulator has access to a parser, a grid manager, and a renderer
 - Grid manager is abstract so that it must be extended to implement each type of CA simulator with different rule set
 - Grid manager alone has the ability to change the grid and its content
 - Renderer has read-only access to the grid (passed by grid manager) to display the current generation

### Overview

![Image](https://coursework.cs.duke.edu/CompSci308_2017Fall/cellsociety_team13/raw/master/data/Overview.jpg)
On the top level our architecture plan consists of a simulator. The simulator can be created with different Parser’s, Grid Managers, and Renders. By utilizing interchangeable components we hope to allow for greater flexibility in adding new features as well as maintaining the current ones. 

The Parser component will have a super Parser class. Each of the different simulations will use an extended Parser class in order to do the necessary parsing that is specific to that type of simulation. The various parser classes will be used to convert the starting configurations and pass them along to the Grid Manager. 

Additionally, the simulator will be able to operate using different subclasses of the GridManager class. Each subclass of GridManager will contain the logic necessary for updating the grid under the current simulation rules. By having a super GridManager class it will be easy to extend add add new managers and thus new simulations. 

The grid manager subclasses will update the extended cell classes. We plan to have a super Cell class which is extended to have a subclass for each of the different simulations. The current state of the cell will be controllable by a public method and contain the state in an Enum which is reflective of the possible cell states given the current simulation. 

The grid of cells which is maintained by the state managers will be passed to a Renderer class. 

### User Interface

The interface is going to consist of a sidebar and a grid. Before starting the simulation, the individual grid cells can be turned on or off by being clicked on. The sidebar will consists of a drop menu to specify what simulation will be run and a button to run/reset. A parameter entry (slider or a value box) will be included in the sidebar to allow the user to change the speed of simulation. 

Choosing a certain type of simulation will create more parameter entry boxes, such as for ProbCatch in fire-spreading simulation.

### Design Details

The Parser class will use the built-in Java classes in order to read in the XML file. If the file is in an incorrect format, it will throw an exception error. If not, then it will take specific variables specified in the file, such as cell positions, and save these variables into itself. These variables will then be used by the Grid Manager. 

All Grid Managers contain one grid of cells, and they are able to access the public methods of each cell to manipulate them (change their states or move them to another position in the grid). They will all contain an updateState() method that will go through each cell in the grid and remember the action to take on each cell. At the end of the loop, it will start another loop to update the states of every cell in the grid depending on the conditions, which will differ for each extension of Grid Manager. Also, they will contain a getGrid() function, which is public and accessible by the Simulator class to provide grid information to the renderer whenever requested. 

Depending on which type of CA simulation is chosen by the user, the simulator class will create a specific grid manager object that contains the correct set of rules. Using the initial conditions provided by the XML file, which will be stored in the form of a 2D matrix, a new grid will be constructed and saved into the grid manager. 

A cell is very simple in nature, in that each cell contains one instance variable to keep track of its state. It has a public setter method that changes its state, so that the grid manager can easily change its state to another. The cell does not need to keep track of its location since the location is dependent on the grid, and the grid manager can simply move the cell to a different location in matrix.

Lastly, the renderer will read what is on the grid and show it on the computer screen. The simulator will get the grid from Grid Manager after all the cells have been updated and feed it to the Renderer object’s render() method. The renderer class will have access to JavaFX’s packages so that it can draw the rectangles to represent each cell and display them on the screen. Another approach would be to make Renderer an interface, so that GridRenderer and GUIRenderer classes can implement it. This will allow the Grid Renderer to do its job, which is not reliant on the type of simulation, while also allowing GUI renderer to be flexible enough to change the required parameters for different types of simulations.


### Design Considerations

One of the first design decisions our group made was whether we wanted to either give the cell class logic in order for it to decide its next position or to have an overall cell manager to oversee the cells. While the cell having its own logic seemed to be a greater step towards object orientation, it would make things harder as it would have to take/give information from other cells. We wanted to avoid this interdependency in order to make the code more clear. As such, we decided to run with the cell manager, as it allowed for more distinct class roles. 

Another decision that we came across was on the role of the Grid Manager. At first, the Grid Manager carried many functions: taking in the info from the parser, figuring out which logic to use for the simulation, running the simulation, updating it and finally displaying it. We decided to split the later roles, displaying into a separate class, Renderer. The benefits of a separate Renderer class is that no matter the logic changes or overall changes that are made to the system, the general means to displaying the grid will nonetheless be the same.

### Team Responsibilities

Our current plan is to allocate work as following. Lasia will focus on developing the front-end, which will include the GUI and grid rendering. Raphael and Parker will split up the work on creating different types of grid state managers. This division of work assumes that a parser is publicly available, and that minimal work will be required for this job. Most importantly, our team will be flexible; every member will collaborate in a case that a problem is encountered so that no single job is delayed for too long.

