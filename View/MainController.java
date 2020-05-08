package View;

import Configuration.Configuration;
import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tools.Logger;
import tools.P;

import java.io.IOException;
import java.util.HashMap;

public class MainController extends Application {
	private static Stage stage;
	private static String name;
	private static String title = null;
	private static User user;
	private static String type;
	private static final HashMap<String, String> screens = new HashMap<>();
	private static final HashMap<Sizes, double[]> size = new HashMap<>();
	private static Sizes currentSize = null;
	
	public enum Sizes {
		SMALL,
		MID,
		LARGE
	}
	
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
	
	public static void activate(String name, String title, Sizes size) {
		MainController.currentSize = size;
		MainController.activate(name, title);
	}
	
	public static void activate(String name, String title) {
		MainController.title = title;
		MainController.activate(name);
	}
	
	public static void activate(String name) {
		try {
			Scene newScene = new Scene(
				FXMLLoader.load(MainController.class.getResource(screens.get(name))));
			MainController.stage.setScene(newScene);
			if (MainController.currentSize != null) {
				MainController.stage.setWidth(size.get(currentSize)[0]);
				MainController.stage.setHeight(size.get(currentSize)[1]);
			} else {
				P.pln(newScene.getHeight());
				MainController.stage.setWidth(newScene.getWidth());
				MainController.stage.setHeight(newScene.getHeight());
			}
			if (MainController.title != null) {
				MainController.stage.setTitle(MainController.title);
			} else {
				MainController.stage.setTitle(name);
			}
			MainController.currentSize = null;
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public static void load() {
		Configuration.loadScreens("src/View/").forEach((name, location) ->
			MainController.screens.put(
				name,
				location.substring(9)
			));
	}
	
	public static void loadSizes() {
		size.put(Sizes.SMALL, new double[]{310.0, 450.0});
		size.put(Sizes.MID, new double[]{700.0, 450.0});
		size.put(Sizes.LARGE, new double[]{700.0, 710.0});
	}
	
	public static void alert(Alert.AlertType type, String header, String message) {
		Alert alert = new Alert(type);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.show();
	}
	
	public static void hit(String name, String title, Sizes size) {
		MainController.currentSize = size;
		MainController.hit(name, title);
	}
	
	public static void hit(String name, String title) {
		MainController.title = title;
		MainController.hit(name);
	}
	
	public static void hit(String name) {
		MainController.name = name;
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		MainController.load();
		MainController.loadSizes();
		MainController.activate(MainController.name);
		MainController.stage.show();
	}
}