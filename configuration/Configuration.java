package configuration;

import connection.DBConnection;
import tools.Dir;
import tools.File;

import java.util.HashMap;

public class Configuration {
	
	public static File getConnectionConfigFile() {
		return new File("src/configuration/connection.config");
	}
	
	public static File getDefaultConnectionConfigFile() {
		return new File("src/configuration/defaultConnection.config");
	}
	
	public static boolean saveConnection(DBConnection connection) {
		return saveToFile(connection, getConnectionConfigFile().getStringPath());
	}
	
	public static boolean saveToFile(DBConnection connection, String path) {
		boolean isSaved = false;
		if (connection != null && connection.isReady() && path != null) {
			File file = new File(path);
			file.delete();
			file.write(connection.getDriver());
			file.newLine();
			file.write(connection.getUrl());
			file.newLine();
			file.write(connection.getUser());
			file.newLine();
			file.write(connection.getPassword());
			isSaved = true;
		}
		return isSaved;
	}
	
	public static void loadConnection(DBConnection connection) {
		File connectionFile = getConnectionConfigFile();
		if (!connectionFile.exists()) {
			connectionFile = getDefaultConnectionConfigFile();
		}
		if (connection == null) {
			connection = new DBConnection();
		}
		connection.setDriver(connectionFile.readLine());
		connection.setUrl(connectionFile.readLine());
		connection.setUser(connectionFile.readLine());
		connection.setPassword(connectionFile.readLine());
	}
	
	public static boolean loadFromFile(DBConnection connection, String path) {
		boolean loaded = false;
		File file = new File(path);
		if (path != null && File.exists(path) && file.getSizeInLines() == 4) {
			if (connection == null) {
				connection = new DBConnection();
			}
			connection.setDriver(file.readLine());
			connection.setUrl(file.readLine());
			connection.setUser(file.readLine());
			connection.setPassword(file.readLine());
			loaded = true;
		}
		return loaded;
	}
	
	public static HashMap<String, String> loadScreens() {
		HashMap<String, String> screens = new HashMap<>();
		Dir view = new Dir("src/view/");
		for (File file: view.ls()) {
			if (Dir.isDir("src/view/" + file.getName())) {
				Dir auxiliaryDir = new Dir("src/view/" + file.getName());
				for (File fxml: auxiliaryDir.ls()) {
					if (fxml.getExt().equals("fxml")) {
						screens.put(
							fxml.getNameNoExt(),
							file.getName() + "/" + fxml.getName()
						);
					}
				}
			} else {
				if ("fxml".equals(file.getExt())) {
					screens.put(
						file.getNameNoExt(),
						file.getName()
					);
				}
			}
		}
		return screens;
	}
}
