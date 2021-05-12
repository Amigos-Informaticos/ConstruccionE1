package Configuration;

import Connection.ConexionBD;
import Connection.ConexionFTP;
import tools.Dir;
import tools.File;

import java.util.HashMap;

public class Configuracion {
	
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
	
	public static void loadDBConnection(ConexionBD connection) {
		File connectionFile = getDBConnectionFile();
		if (!connectionFile.exists()) {
			connectionFile = getDefaultDBConnectionFile();
		}
		if (connection != null) {
			connection.setDriver(connectionFile.readLine());
			connection.setUrl(connectionFile.readLine());
			connection.setUser(connectionFile.readLine());
			connection.setPassword(connectionFile.readLine());
		}
	}
	
	public static void cargarConexionFTP(ConexionFTP connection) {
		File connectionFile = getFTPConnectionFile();
		if (!connectionFile.exists()) {
			connectionFile = getDefaultFTPConnectionFile();
		}
		if (connection != null) {
			connection.setUrl(connectionFile.readLine());
			connection.setUsuario(connectionFile.readLine());
			connection.setContrasena(connectionFile.readLine());
			connection.setDirectorioRemoto(connectionFile.readLine());
		}
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
	
	public static HashMap<String, String> loadScreenss(String path) {
		HashMap<String, String> screens = new HashMap<>();
		java.io.File rutaActual = new java.io.File(path);
		if (rutaActual.isDirectory()) {
			for (java.io.File archivo: rutaActual.listFiles()) {
				if (archivo.isDirectory()) {
					loadScreens(archivo.getPath()).forEach(screens::put);
				} else {
					String extension =
						archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1);
					if (extension.equals("fxml")) {
						String nombreSinExtension =
							archivo.getName().replaceFirst("[.][^.]+$", "");
						screens.put(nombreSinExtension, archivo.getPath());
					}
				}
			}
		}
		return screens;
	}
}
