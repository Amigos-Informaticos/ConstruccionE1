package Configuration;

import Connection.DBConnection;
import tools.Dir;
import tools.File;

import java.util.HashMap;

public class Configuration {
	
	public static File getConnectionConfigFile() {
		return new File("src/Configuration/connection.config");
	}
	
	public static File getDefaultConnectionConfigFile() {
		return new File("src/Configuration/defaultConnection.config");
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
	
	public static HashMap<String, String> loadScreens(String path) {
		HashMap<String, String> screens = new HashMap<>();
		if (Dir.isDir(path)) {
			for (File file: Dir.ls(path)) {
				if (Dir.isDir(file)) {
					loadScreens(file.getStringPath()).forEach(screens::put);
				} else if (file.getExt().equals("fxml")) {
					screens.put(file.getNameNoExt(), file.getStringPath());
				}
			}
		}
		return screens;
	}
}
