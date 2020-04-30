package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.Logger;

import java.io.IOException;

public class MainController extends Application {
	private static Stage stage;
	private static String location;
	private static String title;
	
	public void activate(String name, String location) {
		try {
			stage.setScene(
				new Scene(
					FXMLLoader.load(getClass().getResource(location))
				)
			);
		} catch (IOException e) {
			new Logger().log(e,false);
		}
	}
	
	public void hit(String name, String location) {
		MainController.title = name;
		MainController.location = location;
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		try {
			MainController.stage.setScene(
				new Scene(
					FXMLLoader.load(getClass().getResource(location))
				)
			);
			MainController.stage.show();
		} catch (IOException e) {
			new Logger().log(e,false);
		}
	}
}