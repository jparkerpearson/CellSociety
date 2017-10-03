package simulator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import render.GridRenderer;
import state_manager.StateManager;

public class GridWindow{
	private StateManager manager;
	private GridRenderer gridRender;
	private Graph graph;
	private Group group;
	private HBox hbox;
	
	private Button myStopButton;
	private Button myStartButton;
	private Button myStepButton;
	private Timeline animation = new Timeline();
	private boolean playable = true;
	private double gridSize;
	private Button myGraphButton;
	private boolean showingGraph;
	private BorderPane layout;
	
	public GridWindow(StateManager manager, GridRenderer gridRender) {
		this.manager = manager;
		this.gridRender = gridRender;
		HBox bar = createHBox();
		layout = new BorderPane();
		group = new Group();
		group.getChildren().add(gridRender.getGrid());
		layout.setCenter(group);
		layout.setTop(bar);
	}
	
	public HBox createHBox() {
		HBox sidebar = new HBox();
		myStartButton = makeButton("Play",e->start());
		myStopButton = makeButton("Stop",e ->pause());
		myStepButton = makeButton("Step",e->step());
		myGraphButton = makeButton("Graph", e->showGraph());
		
		sidebar.getChildren().add(myStartButton);
		sidebar.getChildren().add(myStopButton);
		sidebar.getChildren().add(myStepButton);
		sidebar.getChildren().add(myGraphButton);
		return sidebar;
	}
	

	public void setPlayable(boolean play) {
		playable = play;
	}
	
	private void showGraph() {
		Node g = graph.getLineGraph();
		if (!showingGraph) {
			group.getChildren().add(g);
			showingGraph = true;
		}
		else {
			group.getChildren().remove(g);
			showingGraph = false;
		}
	}
	
	public void stop() {
		animation.stop();
	}
	private void pause() {
		animation.stop();
		playable = (!playable);
		if (!playable) {
			Rectangle pauseScreen = new Rectangle(gridSize,gridSize);
			group.getChildren().add(pauseScreen);
		}
		else {
			group.getChildren().remove(1);
		}
			
	}

	private void start() {
		setPlayable(true);
		animate();
		animation.play();
	}

	private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(property);
        result.setOnAction(handler);
        return result;
	}
	
	public void updateSize(double gridSize) {
		this.gridSize = gridSize;
		gridRender.setImageSize(gridSize,gridSize);
		graph = new Graph(gridSize/2);
		
	}
	
	public void updateWindow() {
		if (manager!=null && gridRender != null && playable ) {
			manager.updateGrid();
			gridRender.updateGrid(manager.getGrid());
			graph.updateGraph(manager.getGrid());
		}
	}
	
	public void test() {
		System.out.println("tet");
	}
	
	private void animate() {
		KeyFrame frame = new KeyFrame(Duration.seconds(.5),
				e -> step());
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	private void step(){
//		update
		updateWindow();
	}
	
	public Node getWindow() {
		return layout;
	}
	
}

