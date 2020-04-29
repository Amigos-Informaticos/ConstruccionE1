package View;

import Exceptions.CustomException;
import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.Logger;

import java.io.IOException;
import java.util.HashMap;

public class MainController extends Application {
	private static HashMap<String, String> screenMap = new HashMap<String, String>();
	private static Stage stage;
	private static Scene main;
	private static User user;
	private static String activeScreen;
	
	public void addScreen(String name, String resource) {
		screenMap.put(
			name,
			resource
		);
		activeScreen = name;
	}
	
	public void removeScreen(String name) {
		screenMap.remove(name);
	}
	
	public void setUser(User user) {
		MainController.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void activate(String name) throws CustomException {
		if (screenMap.containsKey(name)) {
			try {
				stage.setTitle(name);
				main =
					new Scene(FXMLLoader.load(getClass().getResource(screenMap.get(name))));
				stage.setScene(main);
				stage.show();
			} catch (IOException e) {
				new Logger().log(e);
			}
		} else {
			throw new CustomException("Key not stored = " + name + " : activate()", "KeyNotStored");
		}
	}
	
	public void hit() {
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		try {
			this.activate(activeScreen);
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
}