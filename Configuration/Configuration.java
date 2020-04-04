package Configuration;

import Connection.DBConnection;
import tools.Arch;
import tools.Logger;

public class Configuration {
	private static Arch configFile = new Arch("src/Configuration/settings.config");
	private static Logger logger = new Logger();

	public static Arch getConnectionConfigFile() {
		return new Arch("src/Configuration/connection.config");
	}

	public static Arch getDefaultConnectionConfigFile() {
		return new Arch("src/Configuration/defaultConnection.config");
	}

	public static boolean saveConnection(DBConnection connection) {
		boolean isSaved = false;
		if (connection != null) {
			Arch connectionFile = getConnectionConfigFile();
			if (connectionFile.existe()) {
				connectionFile.delArchivo();
			}
			connectionFile.escribir(connection.getDriver());
			connectionFile.newLine();
			connectionFile.escribir(connection.getUrl());
			connectionFile.newLine();
			connectionFile.escribir(connection.getUser());
			connectionFile.newLine();
			connectionFile.escribir(connection.getPassword());
			isSaved = true;
		}
		return isSaved;
	}

	public static boolean saveToFile(DBConnection connection, String path) {
		boolean isSaved = false;
		Arch file;
		if (connection != null && connection.isReady() && path != null) {
			file = new Arch(path);
			if (file.existe()) {
				file.delArchivo();
			}
			file.escribir(connection.getDriver());
			file.newLine();
			file.escribir(connection.getUrl());
			file.newLine();
			file.escribir(connection.getUser());
			file.newLine();
			file.escribir(connection.getPassword());
			isSaved = true;
		}
		return isSaved;
	}

	public static void loadConnection(DBConnection connection) {
		Arch connectionFile = getConnectionConfigFile();
		if (!connectionFile.existe()) {
			connectionFile = getDefaultConnectionConfigFile();
		}
		if (connection == null) {
			connection = new DBConnection();
		}
		connection.setDriver(connectionFile.leerLinea());
		connection.setUrl(connectionFile.leerLinea());
		connection.setUser(connectionFile.leerLinea());
		connection.setPassword(connectionFile.leerLinea());
	}

	public static boolean loadFromFile(DBConnection connection, String path) {
		boolean loaded = false;
		Arch file = new Arch(path);
		if (path != null && Arch.existe(path) && file.getSizeInLines() == 4) {
			if (connection == null) {
				connection = new DBConnection();
			}
			connection.setDriver(file.leerLinea());
			connection.setUrl(file.leerLinea());
			connection.setUser(file.leerLinea());
			connection.setPassword(file.leerLinea());
			loaded = true;
		}
		return loaded;
	}
}
