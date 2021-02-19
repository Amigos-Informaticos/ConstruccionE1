package Connection;

import Configuration.Configuration;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import tools.File;
import tools.Logger;
import tools.P;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPConnection {
	private String url;
	private String user;
	private String password;
	private String defaultRemoteDirectory;
	private final FTPClient ftp;
	
	public FTPConnection() {
		Configuration.loadFTPConnection(this);
		this.ftp = new FTPClient();
	}
	
	public FTPConnection(String url, String user, String password, String remoteDirectory) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.defaultRemoteDirectory = remoteDirectory;
		this.ftp = new FTPClient();
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDefaultRemoteDirectory() {
		return defaultRemoteDirectory;
	}
	
	public void setDefaultRemoteDirectory(String defaultRemoteDirectory) {
		this.defaultRemoteDirectory = defaultRemoteDirectory;
	}
	
	public boolean connect() {
		boolean connected = false;
		try {
			this.ftp.connect(this.url);
			if (this.ftp.login(this.user, this.password)) {
				connected = true;
			}
		} catch (IOException e) {
			Logger.staticLog(e, true);
		}
		return connected;
	}
	
	public void close() {
		try {
			this.ftp.logout();
			this.ftp.disconnect();
		} catch (IOException e) {
			Logger.staticLog(e, true);
		}
	}
	
	public boolean sendFile(String filePath, String remoteDirectory) {
		boolean sent = false;
		File file = new File(filePath);
		if (this.connect() && file.exists()) {
			try {
				InputStream stream = new FileInputStream(filePath);
				this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
				this.ftp.storeFile(remoteDirectory + file.getName(), stream);
				stream.close();
				sent = true;
			} catch (IOException e) {
				Logger.staticLog(e, true);
			} finally {
				this.close();
			}
		}
		return sent;
	}
}