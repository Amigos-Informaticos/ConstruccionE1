package View;

import Configuration.Configuracion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tools.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController extends Application {
	private static Stage stage;
	private static String nombre;
	private static String titulo = null;
	private static final HashMap<String, String> ventanas = new HashMap<>();
	private static final HashMap<Sizes, double[]> tamanios = new HashMap<>();
	private static Sizes tamanioActual = null;
	private static final Map<String, Object> memoriaControlador = new HashMap<>();
	
	public enum Sizes {
		SMALL,
		MID,
		LARGE
	}
	
	public static void save(String varName, Object data) {
		if (memoriaControlador.containsKey(varName)) {
			memoriaControlador.replace(varName, data);
		} else {
			memoriaControlador.put(varName, data);
		}
	}
	
	public static Object get(String varName) {
		return memoriaControlador.get(varName);
	}
	
	public static void clearMemory() {
		for (Map.Entry<String, Object> entry: memoriaControlador.entrySet()) {
			if (!entry.getKey().equals("user")) {
				memoriaControlador.remove(entry.getValue());
			}
		}
	}
	
	public static boolean has(String varName) {
		return memoriaControlador.containsKey(varName);
	}
	
	public static String getStageName() {
		return nombre;
	}
	
	public static void activate(String name, String title, Sizes size) {
		MainController.tamanioActual = size;
		MainController.activate(name, title);
	}
	
	public static void activate(String name, String title) {
		MainController.titulo = title;
		MainController.activate(name);
	}
	
	public static void activate(String name) {
		try {
			MainController.nombre = name;
			Scene newScene = new Scene(
				FXMLLoader.load(MainController.class.getResource(ventanas.get(name))));
			MainController.stage.setScene(newScene);
			if (MainController.tamanioActual != null) {
				MainController.stage.setWidth(tamanios.get(tamanioActual)[0]);
				MainController.stage.setHeight(tamanios.get(tamanioActual)[1]);
			} else {
				MainController.stage.setWidth(newScene.getWidth());
				MainController.stage.setHeight(newScene.getHeight());
			}
			if (MainController.titulo != null) {
				MainController.stage.setTitle(MainController.titulo);
			} else {
				MainController.stage.setTitle(name);
			}
			MainController.tamanioActual = null;
		} catch (IOException e) {
			new Logger().log(e);
		}
	}
	
	public static void load() {
		Configuracion.loadScreens("src/View/").forEach((name, location) ->
			MainController.ventanas.put(
				name,
				location.substring(9)
			));
	}
	
	public static void loadSizes() {
		tamanios.put(Sizes.SMALL, new double[] {310.0, 450.0});
		tamanios.put(Sizes.MID, new double[] {700.0, 450.0});
		tamanios.put(Sizes.LARGE, new double[] {700.0, 710.0});
	}
	
	public static boolean alert(Alert.AlertType type, String header, String message) {
		Alert alert = new Alert(type);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
		return alert.getResult() == ButtonType.OK;
	}
	
	public static File fileExplorer() {
		MainController.stage.setTitle("Seleccione un archivo");
		FileChooser fileChooser = new FileChooser();
		return fileChooser.showOpenDialog(MainController.stage);
	}
	
	public static void hit(String name, String title, Sizes size) {
		MainController.tamanioActual = size;
		MainController.hit(name, title);
	}
	
	public static void hit(String name, String title) {
		MainController.titulo = title;
		MainController.hit(name);
	}
	
	public static void hit(String name) {
		MainController.nombre = name;
		Application.launch();
	}
	
	@Override
	public void start(Stage stage) {
		MainController.stage = stage;
		MainController.stage.setResizable(false);
		MainController.load();
		MainController.loadSizes();
		MainController.activate(MainController.nombre);
		MainController.stage.show();
	}
}