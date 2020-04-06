package DAO;

import Connection.DBConnection;
import IDAO.IDAOPracticante;
import Models.Practicante;
import Models.Proyecto;

public class DAOPracticante implements IDAOPracticante {
	private Practicante practicante;
	private DBConnection connection = new DBConnection();

	public DAOPracticante(Practicante practicante) {
		this.practicante = practicante;
	}

	public Practicante getPracticante() {
		return practicante;
	}

	public void setPracticante(Practicante practicante) {
		this.practicante = practicante;
	}

	@Override
	public boolean update() {
		boolean updated = false;
		if (this.isRegistered()) {
			String query = "UPDATE Usuario SET nombres = ?, apellidos = ?, correoElectronico = ?, " +
					"contrasena = ? WHERE correoElectronico = ?";
			String[] values = {this.practicante.getNombres(), this.practicante.getApellidos(),
					this.practicante.getCorreoElectronico(), this.practicante.getContrasena(),
					this.practicante.getCorreoElectronico()};
			if (this.connection.preparedQuery(query, values)) {
				query = "UPDATE Practicante SET Matricula = ? WHERE idUsuario = (SELECT idUsuario " +
						"FROM Usuario WHERE correoElectronico = ?)";
				values = new String[]{this.practicante.getMatricula(),
						this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					updated = true;
				}
			}
		}
		return updated;
	}

	@Override
	public boolean delete() {
		boolean deleted = false;
		String query = "DELETE FROM Practicante WHERE idUsuario = (SELECT idUsuario FROM Usuario " +
				"WHERE correoElectronico = ?)";

		String[] values = {this.practicante.getCorreoElectronico()};
		if (this.isRegistered()) {
			if (this.connection.preparedQuery(query, values)) {
				query = "DELETE FROM Usuario WHERE correoElectronico = ?";
				if (this.connection.preparedQuery(query, values)) {
					deleted = true;
				}
			}
		}
		return deleted;
	}

	@Override
	public boolean logIn() {
		boolean loggedIn = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ? " +
				"AND contrasena = ? AND status = 1";
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
		boolean signedUp = false;
		String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status) " +
				"VALUES (?, ?, ?, ?, 1)";
		String[] values = {this.practicante.getNombres(), this.practicante.getApellidos(),
				this.practicante.getCorreoElectronico(), this.practicante.getContrasena()};
		if (!this.isRegistered()) {
			if (this.connection.preparedQuery(query, values)) {
				query = "INSERT INTO Practicante (idUsuario, matricula) VALUES " +
						"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
				values = new String[]{this.practicante.getCorreoElectronico(),
						this.practicante.getMatricula()};
				if (this.connection.preparedQuery(query, values)) {
					signedUp = true;
				}
			}
		}
		return signedUp;
	}

	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.practicante.getCorreoElectronico()};
		String[] names = {"TOTAL"};
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null) {
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT COUNT(Practicante.idUsuario) AS TOTAL FROM Practicante INNER JOIN Usuario " +
						"ON Practicante.idUsuario = Usuario.idUsuario " +
						"WHERE Usuario.correoElectronico = ?";
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					isRegistered = true;
				}
			}
		}
		return isRegistered;
	}

	/**
	 * Returns an array of all Practicante from DB
	 *
	 * @return Array of Practicante
	 */
	public static Practicante[] getAll() {
		Practicante[] practicantes = null;
		DBConnection connection = new DBConnection();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
				"FROM Usuario INNER JOIN Practicante ON Usuario.idUsuario = Practicante.idUsuario;";
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] results = connection.select(query, null, names);
		practicantes = new Practicante[results.length];
		for (int i = 0; i < results.length; i++) {
			practicantes[i] = new Practicante(
					results[i][0],
					results[i][1],
					results[i][2],
					results[i][3],
					results[i][4]
			);
		}
		return practicantes;
	}

	/**
	 * Returns an instance of Practicante
	 * <p>
	 * Returns an instance of practicante by its email<br/>
	 * </p>
	 *
	 * @param practicante
	 * @return
	 */
	public static Practicante get(Practicante practicante) {
		DBConnection connection = new DBConnection();
		Practicante returnPracticante = null;
		if (practicante != null) {
			if (new DAOPracticante(practicante).isRegistered()) {
				String query = "SELECT nombres, apellidos, correoElectronico, contrasena, " +
						"matricula FROM Usuario INNER JOIN Practicante " +
						"ON Usuario.idUsuario = Practicante.idUsuario " +
						"WHERE Usuario.correoElectronico = ?";
				String[] values = {practicante.getCorreoElectronico()};
				String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena",
						"matricula"};
				String[][] results = connection.select(query, values, names);
				returnPracticante = new Practicante(
						results[0][0],
						results[0][1],
						results[0][2],
						results[0][3],
						results[0][4]
				);
			}
		}
		return returnPracticante;
	}

	public boolean selectProyect(Proyecto proyecto) {
		boolean selected = false;
		if (this.practicante != null && this.practicante.isComplete() &&
				proyecto != null && proyecto.isComplete()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM SeleccionProyecto " +
					"WHERE idUsuario = (SELECT idUsuario FROM Usuario WHERE correoElectronico = ?)";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"TOTAL"};
			int selectedProyects =
					Integer.parseInt(this.connection.select(query, values, names)[0][0]);
			if (selectedProyects < 3) {
				query = "INSERT INTO SeleccionProyecto VALUES " +
						"((SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1), " +
						"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?))";
				values = new String[]{proyecto.getNombre(), this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					selected = true;
				}
			}
		}
		return selected;
	}
}