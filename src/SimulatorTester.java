import simulator.Simulator;
import javafx.application.Application;
import javafx.stage.Stage;

/**Tests and runs Simulator
 * @author Lasia Lo
 *
 */
public class SimulatorTester extends Application {
	
	@Override
	public void start(Stage stage) {
		
		Simulator test = new Simulator("Icons");
		stage.setTitle("Simulator");
		stage.setScene(test.getScene());
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
