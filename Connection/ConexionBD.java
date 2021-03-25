package Connection;

import Configuration.Configuracion;
import tools.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	private Connection connection;
	
	public ConexionBD() {
		Configuracion.loadDBConnection(this);
	}
	
	public ConexionBD(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void loadConnection() {
		Configuracion.loadDBConnection(this);
	}
	
	public boolean isReady() {
		if (this.driver == null || this.url == null || this.user == null || this.password == null) {
			this.loadConnection();
		}
		return true;
	}
	
	public boolean openConnection() {
		boolean isOpen = false;
		if (this.isReady()) {
			try {
				Class.forName(this.driver);
				this.connection = DriverManager.getConnection(this.url, this.user, this.password);
				isOpen = true;
			} catch (ClassNotFoundException | SQLException e) {
				Logger.staticLog(e, true);
			}
		}
		return isOpen;
	}
	
	public void closeConnection() {
		try {
			while (!this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (SQLException e) {
			Logger.staticLog(e, true);
		}
	}
	
	public boolean ejecutar(String query, String[] valores) {
		boolean queryExecuted = false;
		try {
			this.openConnection();
			PreparedStatement statement = this.connection.prepareStatement(query);
			if (valores != null) {
				for (int i = 0; i < valores.length; i++) {
					statement.setString(i + 1, valores[i]);
				}
			}
			queryExecuted = statement.executeUpdate() > 0;
			this.closeConnection();
		} catch (SQLException e) {
			Logger.staticLog(e, true);
		}
		return queryExecuted;
	}
	
	public String[][] seleccionar(String query, String[] values, String[] names) throws SQLException  {
		int tableSize;
		String[][] responses = new String[0][0];
		try {
			if (this.openConnection()) {
				PreparedStatement preparedStatement = this.connection.prepareStatement(query);
				ResultSet queryResults;
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						preparedStatement.setString(i + 1, values[i]);
					}
				}
				queryResults = preparedStatement.executeQuery();
				this.closeConnection();
				queryResults.last();
				tableSize = queryResults.getRow();
				responses = new String[tableSize][names.length];
				queryResults.beforeFirst();
				for (int i = 0; i < tableSize; i++) {
					queryResults.next();
					for (int j = 0; j < names.length; j++) {
						responses[i][j] = queryResults.getString(names[j]);
					}
				}
			}
		} catch (SQLException e) {
			Logger.staticLog(e, true);
			System.out.println("SQL EXCEPTION");
			throw new SQLException(e.getMessage());

		}
		return responses.length > 0 ? responses : null;
	}
}
