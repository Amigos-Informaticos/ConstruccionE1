package Configuration;

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
}
