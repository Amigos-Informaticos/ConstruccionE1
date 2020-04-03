package DAO;

import Models.Usuario;

public class DAOAdministrador extends DAOUsuario {
	public DAOAdministrador(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
	}

	public DAOAdministrador(Usuario user) {
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
	 * 5	->	Logged as User but not as Admin. Attend immediately
	 * </p>
	 *
	 * @return status of the DAO
	 */
	public int logIn() {
		int status = super.logIn();
		if (status == 1) {
			String query = "SELECT COUNT(Administrador.idUsuario) AS TOTAL FROM Administrador " +
					"INNER JOIN Usuario ON Administrador.idUsuario = Usuario.idUsuario " +
					"WHERE Usuario.correoElectronico = ?";
			String[] values = {this.getCorreoElectronico()};
			String[] names = {"TOTAL"};
			if (!this.connection.select(query, values, names)[0][0].equals("1")) {
				status = 5;
			}
		}
		return status;
	}
	/**
	 * Method to sign up a new Admin
	 * <p>
	 * STATUS DESCRIPTION
	 * 0	->	Initial status: no action has been taken
	 * 1	->	Success
	 * 2	->	Error in sending query
	 * 3	->	User already registered
	 * 4	->	Malformed object
	 * 5	->	Registered into User but not into Admin. Attend immediately
	 * </p>
	 *
	 * @return
	 */
	public int signUp() {
		int status = super.signUp();
		if (status == 1) {
			String query = "INSERT INTO Administrador SELECT idUsuario FROM Usuario " +
					"WHERE correoElectronico = ?";
			String[] values = {this.getCorreoElectronico()};
			if (!this.connection.preparedQuery(query, values)) {
				status = 5;
			}
		}
		return status;
	}
}
