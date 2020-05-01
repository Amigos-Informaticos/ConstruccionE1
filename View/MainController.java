package View;

import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tools.Arch;
import tools.Logger;

import java.io.IOException;
import java.util.HashMap;

public class MainController extends Application {
	private static Stage stage;
	private static String name;
	private static User user;
	private static String type;
	private static HashMap<String, String> screens = new HashMap<String, String>();
	
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
	
	public static void activate(String name) {
		try {
			Scene newScene = new Scene(
				FXMLLoader.load(MainController.class.getResource(
					MainController.screens.get(name)
				))
			);
			MainController.stage.setScene(newScene);
			MainController.stage.setWidth(newScene.getWidth());
			MainController.stage.setHeight(newScene.getHeight());
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public static void load() {
		Arch screensFile = new Arch("src/Configuration/scenes");
		String[] lines = screensFile.getLineasArchivo();
		for (String line: lines) {
			MainController.screens.put(
				line.split(":")[0],
				line.split(":")[1]);
		}
	}
	
	public static void hit(String name) {
		MainController.name = name;
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		try {
			MainController.stage.setScene(
				new Scene(
					FXMLLoader.load(getClass().getResource(
						MainController.screens.get(name)
					))
				)
			);
			MainController.stage.show();
		} catch (IOException e) {
			new Logger().log(e, false);
		}
	}
	
	public static void alert(Alert.AlertType type, String header, String message) {
		Alert alert = new Alert(type);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.show();
	}
}