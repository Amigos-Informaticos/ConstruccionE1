package DAO;

import Connection.DBConnection;
import Models.Usuario;

public class DAOUsuario extends Usuario {
	protected DBConnection connection;

	public DAOUsuario(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.connection = new DBConnection();
		if (!this.connection.loadFromFile("connection.config")) {
			this.connection = new DBConnection(
					"org.mariadb.jdbc.Driver",
					"jdbc:mariadb://remotemysql.com:3306/YZPyYHED8C",
					"YZPyYHED8C",
					"VrUiqUGLra"
			);
		}
	}

	public DAOUsuario(Usuario user) {
		super(user);
	}

	/**
	 * Method to log in against the DB
	 *
	 * <p>
	 * STATUS DESCRIPTION<br/>
	 * 0	->	Initial status: no action has been taken<br/>
	 * 1	->	loggedIn<br/>
	 * 2	->	Wrong password<br/>
	 * 3	->	Unmatched email<br/>
	 * 4	->	Malformed object
	 * </p>
	 *
	 * @return status of the DAO
	 */
	public int logIn() {
		int status = 0;
		if (this.getCorreoElectronico() != null && this.getContrasena() != null) {
			if (this.isRegistered()) {
				String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ? AND contrasena = ?";
				String[] values = {this.getCorreoElectronico(), this.getContrasena()};
				String[] names = {"TOTAL"};
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					status = 1;
				} else {
					status = 2;
				}
			} else {
				status = 3;
			}
		} else {
			status = 4;
		}
		return status;
	}

	/**
	 * Method to sign up a new User
	 * <p>
	 * STATUS DESCRIPTION<br/>
	 * 0	->	Initial status: no action has been taken<br/>
	 * 1	->	Success
	 * 2	->	Error in sending query
	 * 3	->	User already registered
	 * 4	->	Malformed object
	 * </p>
	 *
	 * @return true => succesfully registered | false => couldn't register
	 */
	public int signUp() {
		int status = 0;
		if (this.isComplete()) {
			if (!this.isRegistered()) {
				String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status)" +
						"VALUES (?, ?, ?, ?, ?)";
				String[] values = {this.getNombres(), this.getApellidos(), this.getCorreoElectronico(), this.getContrasena(), "1"};
				if (this.connection.preparedQuery(query, values)) {
					status = 1;
				} else {
					status = 2;
				}
			} else {
				status = 3;
			}
		} else {
			status = 4;
		}
		return status;
	}

	/**
	 * Checks if the current User is already registered
	 *
	 * @return true => Already registered | Not registered
	 */
	public boolean isRegistered() {
		boolean isRegistered = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.getCorreoElectronico()};
		String[] names = {"TOTAL"};
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			isRegistered = true;
		}
		return isRegistered;
	}
}
