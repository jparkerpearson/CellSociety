

### Overview

- What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

We are hiding rules of the simulation from the individual cells. The renderer also does not have access to the rules.

- What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

We intend to have a superclass for parser, state manager,  grid maker and cell with subclasses for each simulation.

- What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

The cell class is open with public function to set the current state. The manager is closed because the rules of the simulation do not change.


- Why do you think your design is good (also define what your measure of good is)?

We think our design is good because it is flexible and easy to expand on. Also, it is quite readable and hierarchy design is intuitive. 

- How is your area linked to/dependent on other areas of the project?
Are these dependencies based on the other class's behavior or implementation?

The parser will generate the initial grid of cells and pass it to the backend by way of the state manager. The state manager will update the grid depending on the current simulation and pass it on to the renderer which will display it on the front end.


- How can you minimize these dependencies?

We can minimize these dependencies by having the front end, back end, and parser working nearly independently and only interacting with each other to pass essential information. 

- Go over one pair of super/sub classes in detail to see if there is room for improvement. 

The super class of cell is currently not abstract and returns a default color. Upon reflection we realized a cell will always be extended and thus should be abstract. One specific subclass is the game of life cell which allows for the state of alive or dead. 

- Come up with at least five use cases for your part (most likely these will be useful for both teams).
1. Move to random location if surrounded by 5 cells.
2.  Get promoted to different state if any neighbors are lower than you.
3.  Swap state of one random adjacent cell.
4.  Increase grid size.
5.  See history of simulation.

- What feature/design problem are you most excited to work on?

Making different types of simulations work by utilizing efficient hierarchies.

- What feature/design problem are you most worried about working on?

Implementing the xml parser.
