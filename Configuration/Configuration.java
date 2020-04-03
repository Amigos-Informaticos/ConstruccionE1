package Configuration;

import Connection.DBConnection;
import tools.Arch;

public class Configuration {
	private static Arch configFile;

	public static void setConfigFile() {
		if (Configuration.configFile == null) {
			Configuration.configFile = new Arch("src/Configuration/settings.config");
		}
	}

	public static Arch getConnectionConfigFile() {
		return new Arch("src/Configuration/connection.config");
	}

	public static Arch getDefaultConnectionConfigFile() {
		return new Arch("src/Configuration/defaultConnection.config");
	}

	public static boolean saveConnection(DBConnection connection) {
		boolean isSaved = false;
		if (connection != null) {
			Arch connectionFile = new Arch("src/Configuration/connection.config");
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
}
