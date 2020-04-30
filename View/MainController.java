package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.Logger;

import java.io.IOException;

public class MainController extends Application {
	private static Stage stage;
	
	public void activate(String name, String location) {
		try {
			stage.setScene(
				new Scene(
					FXMLLoader.load(getClass().getResource(location))
				)
			);
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public void hit() {
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
	}
}