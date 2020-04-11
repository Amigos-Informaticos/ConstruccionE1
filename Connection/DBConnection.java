package Connection;

import Configuration.Configuration;
import tools.Arch;
import tools.Logger;

import java.sql.*;

public class DBConnection {
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	private Connection connection;
	private Arch configurationFile;
	private Logger logger = new Logger();

	/**
	 * Empty default constructor<br/>
	 * Tries to load saved connection<br/>
	 * If there's no saved connection, loads default connection
	 */
	public DBConnection() {
		Configuration.loadConnection(this);
	}

	/**
	 * Parametrized constructor
	 *
	 * @param driver   String with driver to open the connection
	 * @param url      URL to connect to database
	 * @param user     User to log to the database
	 * @param password Password to enter the database
	 */
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

	/**
	 * Loads a saved connection
	 * <p>
	 * Tries to load a saved connection<br/>
	 * If there's no saved connection
	 * or if can't load it,
	 * loads default connection
	 * </p>
	 */
	public void loadConnection() {
		Configuration.loadConnection(this);
	}

	/**
	 * Loads the connection configuration from a file
	 *
	 * @param path Path of the configuration file as a String
	 * @return true => loaded | false => couldn't load
	 */
	public boolean loadFromFile(String path) {
		return Configuration.loadFromFile(this, path);
	}

	/**
	 * Saves the current configuration to a file
	 *
	 * @param path The configuration file's path as a string
	 * @return true => saved | false => couldn't save
	 */
	public boolean saveToFile(String path) {
		return Configuration.saveToFile(this, path);
	}

	/**
	 * Check if the connection is ready to open
	 *
	 * @return true => is Ready | false => isn't ready
	 */
	public boolean isReady() {
		boolean isReady = false;
		if (this.driver == null || this.url == null || this.user == null || this.password == null) {
			this.loadConnection();
			isReady = true;
		} else {
			isReady = true;
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
		if (this.isReady()) {
			try {
				Class.forName(this.driver);
				this.connection = DriverManager.getConnection(this.url, this.user, this.password);
				isOpen = true;
			} catch (ClassNotFoundException | SQLException e) {
				this.logger.log(e);
			}
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
			this.logger.log(e);
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
		try {
			this.openConnection();
			PreparedStatement statement = this.connection.prepareStatement(query);
			for (int i = 0; i < values.length; i++) {
				statement.setString(i + 1, values[i]);
			}
			queryExecuted = statement.executeUpdate() > 0 && (queryExecuted = true);
			this.closeConnection();
		} catch (SQLException e) {
			this.logger.log(e);
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
		String[][] responses = new String[0][0];
		try {
			if (this.openConnection()) {
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
			}
		} catch (SQLException e) {
			this.logger.log(e);
		}
		return responses;
	}
}
