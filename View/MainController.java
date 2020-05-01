package View;

import Models.User;
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
	private static User user;
	private static String type;
	
	public static User getUser() {
		return user;
	}
	
	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static String getType() {
		return type;
	}
	
	public static void setType(String type) {
		MainController.type = type;
	}
	
	public static void activate(String name, String location) {
		try {
			stage.setScene(
				new Scene(
					FXMLLoader.load(MainController.class.getResource(location))
				)
			);
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public static void hit(String name, String location) {
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
			new Logger().log(e, false);
		}
	}
}