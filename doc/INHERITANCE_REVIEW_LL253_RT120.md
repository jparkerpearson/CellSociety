Author: Lasia Lo ll253
### Overview

- What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?


I am hiding rules of the GUI from the rest of the back end of the program. Likewise, in the GUI, the Grid itself is encapsulating the process of creating the grid, keeping the process away from the Simulator Gui.

- What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

I don't plan on having any inheritance in my area.

- What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

The GridRenderer class is open with a getGrid function. The Simulator, however, is completely closed.


- Why do you think your design is good (also define what your measure of good is)?

We think our design is good because it is flexible and easy to expand on. Also, it is quite readable and hierarchy design is intuitive. 

- How is your area linked to/dependent on other areas of the project?
Are these dependencies based on the other class's behavior or implementation?

My area is front-end, comprised of the Simulator and GridRender classes. My area depends on the backend to feed it information and displays this information. 

- How can you minimize these dependencies?

We can minimize these dependencies by having the front end, back end, and parser working nearly independently and only interacting with each other to pass essential information. 

- Go over one pair of super/sub classes in detail to see if there is room for improvement. 

The super class of cell is currently not abstract and returns a default color. Upon reflection we realized a cell will always be extended and thus should be abstract. One specific subclass is the game of life cell which allows for the state of alive or dead. 

- Come up with at least five use cases for your part (most likely these will be useful for both teams).
1. Stop Simulation
2.  Change simulation speed
3.  Step through simulation
4.  Increase grid size.
5.  See history of simulation.

- What feature/design problem are you most excited to work on?

Making a super pretty GUI. Featuring a zoomable grid. If time permits and I an feeling ambitious enough, a sound IO.

- What feature/design problem are you most worried about working on?

Implementing  and handling the xml parser.
