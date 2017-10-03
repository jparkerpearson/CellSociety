package parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import state_manager.*;
import grid_maker.*;
import javafx.scene.paint.Color;
import render.*;
import grid.*;
import cell.Cell;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import java.io.File;

public class XMLParser {
	
	private File xml;
	private Element node;
	private String title;
	private String author;
	private StateManager manager;
	private GridMaker maker;
	private GridRenderer renderer;
	private Grid grid;
	private Color[] colormap;
	
	public XMLParser(String filePath) {
		this(new File(filePath));
	}
	
	public XMLParser(File file) {
		try {
			xml = file;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xml);
			doc.getDocumentElement().normalize();
			
			node = (Element) doc.getElementsByTagName("simulation").item(0);
			
			String name = node.getAttribute("name");
			title = getStringByTagName(node, "title");
			author = getStringByTagName(node, "author");
			Element colors = (Element) node.getElementsByTagName("colormap").item(0);
			Element parameters = (Element) node.getElementsByTagName("parameter").item(0);
			Element gridSize = (Element) node.getElementsByTagName("grid_dimension").item(0);
			int gridNumRow = Integer.parseInt(getStringByTagName(gridSize, "num_row"));
			int gridNumCol = Integer.parseInt(getStringByTagName(gridSize, "num_col"));
			String gridConfiguration = getStringByTagName(node, "initial_grid");
			gridConfiguration = gridConfiguration.replaceAll("\\s+", "");
			GridShape shape = GridShape.valueOf(getStringByTagName(node, "grid_shape").toUpperCase());
			GridType type = GridType.valueOf(getStringByTagName(node, "grid_type").toUpperCase());
			
			initializeColormap(colors);
			Cell.setColormap(colormap);

			initializeGridMaker(name);
			initializeGrid(gridNumRow, gridNumCol, shape, type, gridConfiguration);
			initializeStateManager(name, parameters);
			initializeRenderer(shape);
		}
		catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	private void initializeColormap(Element colors) {
		int nColors = colors.getElementsByTagName("color").getLength();
	    colormap = new Color[nColors];
	    for (int i = 0; i < nColors; i++) {
	    	String c = colors.getElementsByTagName("color").item(i).getTextContent();
	        colormap[i] = Color.valueOf(c.toUpperCase());
	    }
	}
	
	private void initializeGrid(int nRow, int nCol, GridShape shape, GridType type, String config) {
		if (shape == GridShape.SQUARE) {
			grid = new SquareGrid(nRow, nCol, type);
		}
		else if (shape == GridShape.HEXAGON) {
			grid = new HexagonGrid(nRow, nCol, type);
		}
		else if (shape == GridShape.TRIANGLE) {
			grid = new TriangleGrid(nRow, nCol, type);
		}
		maker.initialize(grid, config);
	}
	
	private void initializeGridMaker(String name) {
		if (name.equals(GameOfLifeStateManager.SIMULATION_NAME)) {
			maker = new GameOfLifeGridMaker();
		}
		else if (name.equals(WatorStateManager.SIMULATION_NAME)) {
			maker = new WatorGridMaker();
		}
		else if (name.equals(FireStateManager.SIMULATION_NAME)) {
			maker = new FireGridMaker();
		}
		else if (name.equals(SegregationStateManager.SIMULATION_NAME)) {
			maker = new SegregationGridMaker();
		}
		else if (name.equals(RPSStateManager.SIMULATION_NAME)) {
			maker = new RPSGridMaker();
		}
		else if (name.equals(AntStateManager.SIMULATION_NAME)) {
			maker = new AntGridMaker();
		}
		else if (name.equals(SugarStateManager.SIMULATION_NAME)) {
			maker = new SugarGridMaker();
		}
	}
	
	private void initializeRenderer(GridShape shape) {
		if (shape == GridShape.SQUARE) {
			renderer = new SquareRenderer(grid);
		}
		else if (shape == GridShape.HEXAGON) {
			renderer = new HexagonRenderer(grid);
		}
		else if (shape == GridShape.TRIANGLE) {
			renderer = new TriangleRenderer(grid);
		}
	}
	
	private void initializeStateManager(String name, Element param) {
		if (name.equals(GameOfLifeStateManager.SIMULATION_NAME)) {
			manager = new GameOfLifeStateManager(grid);
		}
		else if (name.equals(WatorStateManager.SIMULATION_NAME)) {
			int breedingAge = Integer.parseInt(getStringByTagName(param, "breeding_age"));
			int breedingEnergy = Integer.parseInt(getStringByTagName(param, "breeding_energy"));
			int energyPerFish = Integer.parseInt(getStringByTagName(param, "energy_per_fish"));
			manager = new WatorStateManager(grid, breedingAge, breedingEnergy, energyPerFish);
		}
		else if (name.equals(FireStateManager.SIMULATION_NAME)) {
			double probCatch = Double.parseDouble(getStringByTagName(param, "prob_catch"));
			manager = new FireStateManager(grid, probCatch);
		}
		else if (name.equals(SegregationStateManager.SIMULATION_NAME)) {
			double similarity = Double.parseDouble(getStringByTagName(param, "similarity_threshold"));
			manager = new SegregationStateManager(grid, similarity);
		}
		else if (name.equals(RPSStateManager.SIMULATION_NAME)) {
			double probability = Double.parseDouble(getStringByTagName(param, "probability"));
			manager = new RPSStateManager(grid, probability);
		}
		else if (name.equals(AntStateManager.SIMULATION_NAME)) {
			manager = new AntStateManager(grid);
		}
		else if(name.equals(SugarStateManager.SIMULATION_NAME)) {
			manager = new SugarStateManager(grid);
		}
	}
	
	private String getStringByTagName(Element e, String tag) {
		return e.getElementsByTagName(tag).item(0).getTextContent();
	}
	
	public Element getRoot() {
		return node;
	}

	public String getTitle() {	
		return title;
	}
	
	public String getAuthor() {
		return author;
	}	
	
	public StateManager getStateManager() {
		return manager;
	}
	
	public GridRenderer getRenderer() {
		return renderer;
	}
}
