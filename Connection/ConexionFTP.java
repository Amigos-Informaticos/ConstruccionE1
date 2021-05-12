
package Connection;

import Configuration.Configuracion;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import tools.File;
import tools.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConexionFTP {
	private String url;
	private String usuario;
	private String contrasena;
	private String directorioRemoto;
	private final FTPClient clienteFTP;
	
	public ConexionFTP() {
		Configuracion.cargarConexionFTP(this);
		this.clienteFTP = new FTPClient();
	}
	
	public ConexionFTP(String url, String usuario, String contrasena, String directorioRemoto) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.directorioRemoto = directorioRemoto;
		this.clienteFTP = new FTPClient();
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getDirectorioRemoto() {
		return directorioRemoto;
	}
	
	public void setDirectorioRemoto(String directorioRemoto) {
		this.directorioRemoto = directorioRemoto;
	}
	
	public boolean conectar() {
		boolean conectado = false;
		try {
			this.clienteFTP.connect(this.url);
			if (this.clienteFTP.login(this.usuario, this.contrasena)) {
				conectado = true;
			}
		} catch (IOException e) {
			Logger.staticLog(e, true);
		}
		return conectado;
	}
	
	public void cerrar() {
		try {
			if (this.clienteFTP.isConnected()) {
				this.clienteFTP.logout();
				this.clienteFTP.disconnect();
			}
		} catch (IOException e) {
			Logger.staticLog(e, true);
		}
	}
	
	public boolean enviarArchivo(String ruta, String directorioRemoto) {
		boolean enviado = false;
		File file = new File(ruta);
		if (this.conectar() && file.exists()) {
			try {
				InputStream stream = new FileInputStream(ruta);
				this.clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
				this.clienteFTP.storeFile(
					this.directorioRemoto + directorioRemoto + file.getName(),
					stream);
				stream.close();
				enviado = true;
			} catch (IOException e) {
				Logger.staticLog(e, true);
			} finally {
				this.cerrar();
			}
		}
		return enviado;
	}
}

