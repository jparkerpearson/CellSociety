# cellsociety

CompSci 308 Cell Society Project
Raphael Kim (ck174@duke.edu)
Lasia Lo (ll253@duke.edu)
Parker Pearson (jpp20@duke.edu)



## XML Files

The specifications for each simulation must be detailed in the XML file that is loaded in the beginning.
Each XML file will consist of a single "simulation" element with an attribute called "name".
The name of the simulation is what determines which specific simulation manager will be loaded to the simulation.
The simulation element contains several children that each specify the necessary information regarding the simulation.
"title" and "author" are both for crediting the creator of the simulation. "parameter" contains all of the parameters that
a simulation manager requires when being constructed. "colormap" contains a dictionary of colors from which the color of each cell
will be determined. The first mentioned color will be assigned to state '0', the next will be assigned to state '1', and so forth.
"grid_type" determines the edge type of the grid, and the "grid_shape" determines the shape.
"initial_grid" may contain the initial state of grid, and it must contain a valid configuration. 
A valid configuration is a string with at least as many characters as there are cells in the grid, consisting of 
numbers that represent a valid state for the cells of that simulation. If a valid configuration is NOT specified, 
then the simulator will simply generate one at random that fits the correct dimension mentioned in "grid_dimension" and use that instead.
Lastly, XML parsing takes place inside a try-catch statement that allows us to check for a general error that would show that the XML file 
doesn't follow the format (incorrect parameter name, incorrect specification of parameter, etc).

### Allowed options for each parameter

* simulation name
 - GameOfLife
 - Fire
 - Segregation
 - Wator
 - RPS
 - Ant
 - Sugar
* color
 - Any of the color values in javafx.scene.paint.Color
* grid_type
 - finite
 - toroidal
* grid_shape
 - square
 - hexagon
 - triangle


### Sample XML File (from Fire.xml)

```xml
<simulation name="Fire">
	<title>Fire Spreading Simulator</title>
	<author>Parker Pearson</author>
	<parameter>
		<prob_catch>0.6</prob_catch>
	</parameter>
	<colormap>
		<color>white</color>
		<color>green</color>
		<color>red</color>
	</colormap>
	<grid_type>finite</grid_type>
	<grid_shape>square</grid_shape>
	<initial_grid>
		00220111111
		12221111101
		10001112111
		11121120111
		11101102001
		11111111111
		11111002111
		10111121111
		11111111111
		12111111101
		20211010111
	</initial_grid>
	<grid_dimension>
		<num_row>11</num_row>
		<num_col>11</num_col>
	</grid_dimension>
</simulation>
```

## Grid structure

Cellular Automata simulations are based on grid, which made grid object an integral part of the project. The original implementation of a 
grid made use of a 2d array of cells, which we found to be less flexible than what it could have been. The Grid was re-implemented as an abstract class
that makes extensive use of a HashMap of another class called Location. Each location keeps track of two coordinates, and this was designed this way
so that a coordinate can be tracked as a single object. Also, if a 3d grid is implemented, an extension of Location will prove useful.

The Grid allows for boundary checking by simply checking if the HashMap contains Location key in question. If an Infinite edge grid was to be implemented
(which we chose not to in order to make more time to implement other features), then the grid can be expanded by simply adding more Location keys to the
hashmap. 
