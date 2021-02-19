package Configuration;

import Connection.DBConnection;
import Connection.FTPConnection;
import tools.Dir;
import tools.File;

import java.util.HashMap;

public class Configuration {
	
	public static File getDBConnectionFile() {
		return new File("src/Configuration/connection.config");
	}
	
	public static File getDefaultDBConnectionFile() {
		return new File("src/Configuration/defaultDBConnection.config");
	}
	
	public static File getFTPConnectionFile() {
		return new File("src/Configuration/FTPConnection.config");
	}
	
	public static File getDefaultFTPConnectionFile() {
		return new File("src/Configuration/defaultFTPConnection.config");
	}
	
	public static void loadDBConnection(DBConnection connection) {
		File connectionFile = getDBConnectionFile();
		if (!connectionFile.exists()) {
			connectionFile = getDefaultDBConnectionFile();
		}
		if (connection == null) {
			connection = new DBConnection();
		}
		connection.setDriver(connectionFile.readLine());
		connection.setUrl(connectionFile.readLine());
		connection.setUser(connectionFile.readLine());
		connection.setPassword(connectionFile.readLine());
	}
	
	public static void loadFTPConnection(FTPConnection connection) {
		File connectionFile = getFTPConnectionFile();
		if (!connectionFile.exists()) {
			connectionFile = getDefaultFTPConnectionFile();
		}
		if (connection == null) {
			connection = new FTPConnection();
		}
		connection.setUrl(connectionFile.readLine());
		connection.setUser(connectionFile.readLine());
		connection.setPassword(connectionFile.readLine());
		connection.setDefaultRemoteDirectory(connectionFile.readLine());
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
