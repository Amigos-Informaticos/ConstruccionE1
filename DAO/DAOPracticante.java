package DAO;

import Connection.DBConnection;
import IDAO.IDAOPracticante;
import Models.Practicante;

public class DAOPracticante implements IDAOPracticante {
	private Practicante practicante;
	private DBConnection connection = new DBConnection();

	public Practicante getPracticante() {
		return practicante;
	}

	public void setPracticante(Practicante practicante) {
		this.practicante = practicante;
	}

	@Override
	public boolean update() {
		return false;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public boolean logIn() {
		boolean loggedIn = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ? " +
				"AND contrasena = ?";
		String[] values = {this.practicante.getCorreoElectronico(), this.practicante.getContrasena()};
		String[] names = {"TOTAL"};
		if (this.isRegistered()) {
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				loggedIn = true;
			}
		}
		return loggedIn;
	}

	@Override
	public boolean signUp() {
		return false;
	}

	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.practicante.getCorreoElectronico()};
		String[] names = {"TOTAL"};
		if (this.practicante.isComplete()) {
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT COUNT(idUsuario) AS TOTAL FROM Practicante INNER JOIN Usuario " +
						"ON Practicante.idUsuario = Usuario.idUsuario " +
						"WHERE Usuario.correElectronico = ?";
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					isRegistered = true;
				}
			}
		}
		return isRegistered;
	}

	public static Practicante[] getAll() {
		return new Practicante[0];
	}

	public static Practicante get(Practicante practicante) {
		return null;
	}
}
