package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tools.Arch;
import tools.Logger;

public class DBConnection {
	private String driver;
	private String url;
	private String user;
	private String password;
	private Connection connection;
	private Arch configurationFile;
	private Logger logger = new Logger();

	public DBConnection() {
	}

	public DBConnection(String driver, String url, String user, String password) {
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

	/**
	 * Loas connection configuration from a file
	 *
	 * @param path The configuration file's path as a String
	 * @return true => loaded | false => couldn't load
	 */
	public boolean loadFromFile(String path) {
		boolean loaded = false;
		this.configurationFile = new Arch(path);
		if (this.configurationFile.existe()) {
			this.configurationFile.initLineReader();
			this.driver = this.configurationFile.leerLinea();
			this.url = this.configurationFile.leerLinea();
			this.user = this.configurationFile.leerLinea();
			this.password = this.configurationFile.leerLinea();
			loaded = true;
		}
		return loaded;
	}

	/**
	 * Saves the current configuration to a file
	 *
	 * @param path The configuration file's path as a string
	 * @return true => saved | false => couldn't save
	 */
	public boolean saveToFile(String path) {
		boolean saved = false;
		if (this.driver != null && this.url != null && this.user != null && this.password != null) {
			this.configurationFile = new Arch(path);
			if (!this.configurationFile.existe()) {
				this.configurationFile.escribir(this.driver);
				this.configurationFile.newLine();
				this.configurationFile.escribir(this.url);
				this.configurationFile.newLine();
				this.configurationFile.escribir(this.user);
				this.configurationFile.newLine();
				this.configurationFile.escribir(this.password);
				saved = true;
			}
		}
		return saved;
	}

	/**
	 * Check if the connection is ready to open
	 *
	 * @return true => is Ready | false => isn't ready
	 */
	public boolean isReady() {
		boolean isReady = false;
		if (this.driver != null && this.url != null && this.user != null && this.password != null) {
			if (this.openConnection()) {
				this.closeConnection();
				isReady = true;
			}
		}
		return isReady;
	}

	/**
	 * Open the connection to the DB
	 *
	 * @return true => The connection is open | false => is not open
	 */
	public boolean openConnection() {
		boolean isOpen = false;
		try {
			Class.forName(this.driver);
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			isOpen = true;
		} catch (ClassNotFoundException | SQLException e) {
			this.logger.log(e, true);
		}
		return isOpen;
	}

	/**
	 * Close the connection, no matter if its open or not
	 */
	public void closeConnection() {
		try {
			while (!this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (SQLException e) {
			this.logger.log(e, true);
		}
	}

	/**
	 * Send a prepared query to the DB
	 *
	 * @param query  The query to send
	 * @param values The values to insert into the query
	 * @return true => The query is sent | false => it couldn't send
	 */
	public boolean preparedQuery(String query, String[] values) {
		boolean queryExecuted = false;
		if (this.isReady()) {
			try {
				this.openConnection();
				PreparedStatement statement = this.connection.prepareStatement(query);
				for (int i = 0; i < values.length; i++) {
					statement.setString(i + 1, values[i]);
				}
				queryExecuted = statement.executeUpdate() > 0 && (queryExecuted = true);
				this.closeConnection();
			} catch (SQLException e) {
				this.logger.log(e, true);
			}
		}
		return queryExecuted;
	}

	/**
	 * Get the values requested in the query
	 *
	 * @param query  The query to send to the DB
	 * @param values The values to insert into the query
	 * @param names  Names of the columns to request
	 * @return Matrix of requested data
	 */
	public String[][] select(String query, String[] values, String[] names) {
		int tableSize = 0;
		String[][] responses = null;
		if (this.isReady()) {
			try {
				this.openConnection();
				PreparedStatement preparedStatement = this.connection.prepareStatement(query);
				ResultSet queryResults = null;
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

			} catch (SQLException e) {
				this.logger.log(e, true);
			}
		}
		return responses;
	}
}
