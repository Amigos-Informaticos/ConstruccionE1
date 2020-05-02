package Configuration;

import Connection.DBConnection;
import tools.Dir;
import tools.File;
import tools.Logger;

import java.util.HashMap;

public class Configuration {
	private static File configFile = new File("src/Configuration/settings.config");
	private static Logger logger = new Logger();
	
	public static File getConnectionConfigFile() {
		return new File("src/Configuration/connection.config");
	}
	
	public static File getDefaultConnectionConfigFile() {
		return new File("src/Configuration/defaultConnection.config");
	}
	
	public static boolean saveConnection(DBConnection connection) {
		boolean isSaved = false;
		if (connection != null) {
			File connectionFile = getConnectionConfigFile();
			if (connectionFile.exists()) {
				connectionFile.delete();
			}
			connectionFile.write(connection.getDriver());
			connectionFile.newLine();
			connectionFile.write(connection.getUrl());
			connectionFile.newLine();
			connectionFile.write(connection.getUser());
			connectionFile.newLine();
			connectionFile.write(connection.getPassword());
			isSaved = true;
		}
		return isSaved;
	}
	
	public static boolean saveToFile(DBConnection connection, String path) {
		boolean isSaved = false;
		File file;
		if (connection != null && connection.isReady() && path != null) {
			file = new File(path);
			if (file.exists()) {
				file.delete();
			}
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
		HashMap<String, String> screens = new HashMap<String, String>();
		Dir view = new Dir("src/View");
		for (File file: view.ls()) {
			if (Dir.isDir("src/View/" + file.getName())) {
				Dir auxiliarDir = new Dir("src/View/" + file.getName());
				for (File fxml: auxiliarDir.ls()) {
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
