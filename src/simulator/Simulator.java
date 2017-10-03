package simulator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import parser.XMLParser;
import render.GridRenderer;
import state_manager.StateManager;
import grid.Grid;

/**Takes initial state taken from parser and starts simulation
 * Allows user to control simulation speed.
 * @author Lasia Lo
 *
 */
public class Simulator{
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private final int MAX_SPEED = 2;
	private final double MIN_SPEED = .04;
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	private int SIDE_WIDTH = 100;
	private int GRID_SIZE = WIDTH-SIDE_WIDTH;
	
	private Scene myScene;
	private Timeline animation = new Timeline(); 
	private BorderPane layout;
	
	private List<GridWindow> gridWindows;
	private FileChooser fileChooser;
	private ResourceBundle myResources;
	
	private VBox sidebar;
	private Button myStopButton;
	private Button myStartButton;
	private Button myStepButton;
	private Button myChooseFile;
	private Button myResetButton;
	private Slider mySlider;
	
	private File selectedFile;
	
	private GridPane gridPane;
	private int numberOfGrids=0;
	
	public Simulator() {
	
	}
	public Simulator(String language) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +language);
		myScene = createGUI();
		gridPane= new GridPane();
		gridWindows = new ArrayList<GridWindow>();
	}
	public Scene getScene() {
		return myScene;
	}
	
	private Scene createGUI() {
		layout = new BorderPane();
		createBar();
		Scene scene = new Scene(layout,WIDTH,HEIGHT);
		scene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + "stylesheet.css");
		return scene;
	}
	private void createBar() {
		sidebar = new VBox();
		sidebar.setAlignment(Pos.CENTER);
		myStartButton = makeButton("Play",e->start());
		myStopButton = makeButton("Stop",e ->pause(false));
		myStepButton = makeButton("Step",e->step());
		myChooseFile = makeButton("AddGrid",e->addFile());
		myResetButton = makeButton("Restart",e->restart());
		mySlider = makeSlider();
		
		sidebar.getChildren().add(myChooseFile);
		sidebar.getChildren().add(myStopButton);
		sidebar.getChildren().add(myStartButton);
		sidebar.getChildren().add(myStepButton);
		sidebar.getChildren().add(myResetButton);
		sidebar.getChildren().add(mySlider);
		sidebar.setPrefWidth(SIDE_WIDTH);
		
		layout.setLeft(sidebar);
	}
	
	private void addFile() {
		pause(false);
			Stage stage = new Stage();
			
			fileChooser = new FileChooser();
			fileChooser.setTitle("Open XML File");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
			
			selectedFile = fileChooser.showOpenDialog(stage);
			try {
				createGridWindow(new XMLParser(selectedFile));
			}
			catch (Exception e) {
				Stage error = new Stage();
				
				Text text = new Text();
				text.setText("File is incompatible.");
				text.setFont(Font.font ("New Courier", 20));
				text.setLayoutY(20);
				Group group = new Group(text);
				Scene scene = new Scene(group,200,50);
				error.setTitle("File Incompatible");
				error.setScene(scene);
				error.show();
			}
	}
	private void createGridWindow(XMLParser parser) {
		double gridSize = GRID_SIZE /2.4;
		GridWindow gridWindow = new GridWindow(parser.getStateManager(),parser.getRenderer());
		gridWindow.updateSize(gridSize);
		gridWindows.add(gridWindow);
		addToGridPane(gridWindow.getWindow());
		layout.setCenter(gridPane);
	}
	private void addToGridPane(Node grid) {
		if (numberOfGrids>=4)
			gridPane.getChildren().remove(0);
		if (numberOfGrids%4==0)
			gridPane.add(grid,0,0);
		if (numberOfGrids%4==1)
			gridPane.add(grid,1,0);
		if (numberOfGrids%4==2)
			gridPane.add(grid,0,1);
		if (numberOfGrids%4==3)
			gridPane.add(grid,1,1);
		numberOfGrids++;
	}

	private void pause(boolean play) {
		animation.stop();
		for (GridWindow g : gridWindows) {
			g.stop();
		}
	}
	private void start() {
		animation.stop();
		animate();
		animation.play();
	}
	
	private void updateSpeed() {
		double delay =  mySlider.getValue();
		animation.setRate(delay);
	}

	private void restart() {
		gridPane.getChildren().clear();
	}
	
	protected Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        final String IMAGEFILE_SUFFIXES = 
        		String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        
        String label = myResources.getString(property);
        if (label.matches(IMAGEFILE_SUFFIXES)){
        	result.setGraphic(new ImageView(
        			new Image(DEFAULT_RESOURCE_PACKAGE+label)));
        }
        else {
        	result.setText(label);
        }
        result.setOnAction(handler);
        return result;
	}
	protected Slider makeSlider() {
		Slider slider = new Slider(MIN_SPEED,MAX_SPEED,1);
		slider.setBlockIncrement(10);
		return slider;
	}
	
	protected void animate() {
		KeyFrame frame = new KeyFrame(Duration.seconds(.5),
				e -> step());
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	private void step(){
		updateSpeed();
		for (GridWindow g : gridWindows) {
			g.updateWindow();
		}
	}
}
