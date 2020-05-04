package view;

import configuration.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.User;
import tools.Logger;

import java.io.IOException;
import java.util.HashMap;

public class MainController extends Application {
	private static Stage stage;
	private static String name;
	private static String title = null;
	private static User user;
	private static String type;
	private static HashMap<String, String> screens = new HashMap<>();
	
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
				FXMLLoader.load(MainController.class.getResource(screens.get(name))));
			MainController.stage.setScene(newScene);
			MainController.stage.setWidth(newScene.getWidth());
			MainController.stage.setHeight(newScene.getHeight());
			MainController.stage.setTitle(name);
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public static void activate(String name, String title) {
		MainController.stage.setTitle(title);
		MainController.activate(name);
	}
	
	public static void load() {
		MainController.screens = Configuration.loadScreens();
	}
	
	public static void alert(Alert.AlertType type, String header, String message) {
		Alert alert = new Alert(type);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.show();
	}
	
	public static void hit(String name) {
		MainController.name = name;
		MainController.title = name;
		Application.launch();
	}
	
	public static void hit(String name, String title) {
		MainController.name = name;
		MainController.title = title;
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		load();
		try {
			MainController.stage.setScene(
				new Scene(
					FXMLLoader.load(getClass().getResource(
						MainController.screens.get(name)
					))
				)
			);
			if (MainController.title != null) {
				MainController.stage.setTitle(MainController.title);
			}
			MainController.stage.show();
		} catch (IOException e) {
			new Logger().log(e, false);
		}
	}
}