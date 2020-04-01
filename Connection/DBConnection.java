package Connection;

import java.sql.*;

public class DBConnection {
	private String driver;
	private String url;
	private String user;
	private String password;
	private Connection connection;

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
	 * Check if the connection is ready to open
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
	 * @return true => The connection is open | false => is not open
	 */
	public boolean openConnection() {
		boolean isOpen = false;
		try {
			Class.forName(this.driver);
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			isOpen = true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	/**
	 * Send a prepared query to the DB
	 * @param query The query to send
	 * @param values The values to insert into the query
	 * @return true => The query is sent | false => it couldn't send
	 */
	public boolean preparedQuery(String query, String[] values) {
		boolean queryExecuted = false;
		if (this.isReady()) {
			try {
				PreparedStatement statement = this.connection.prepareStatement(query);
				for (int i = 0; i < values.length; i++) {
					statement.setString(i + 1, values[i]);
				}
				this.openConnection();
				queryExecuted = statement.executeUpdate() > 0 && (queryExecuted = true);
				this.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return queryExecuted;
	}

	/**
	 * Get the values requested in the query
	 * @param query The query to send to the DB
	 * @param values The values to insert into the query
	 * @param names Names of the columns to request
	 * @return Matrix of requested data
	 */
	public String[][] select(String query, String[] values, String[] names) {
		int tableSize = 0;
		String[][] responses = null;
		if (this.isReady()) {
			try {
				PreparedStatement preparedStatement = this.connection.prepareStatement(query);
				for (int i = 0; i < values.length; i++) {
					preparedStatement.setString(i + 1, values[i]);
				}
				this.openConnection();
				ResultSet queryResults = preparedStatement.executeQuery();
				this.closeConnection();
				if (queryResults.last()) {
					tableSize = queryResults.getRow();
					responses = new String[tableSize][names.length];
					queryResults.beforeFirst();
					for (int i = 0; i < tableSize; i++) {
						queryResults.next();
						for (int j = 0; j < names.length; j++) {
							responses[i][j] = queryResults.getString(names[i]);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return responses;
	}
}
