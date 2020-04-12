package DAO;

import Connection.DBConnection;
import IDAO.IDAOPracticante;
import Models.Practicante;
import Models.Proyecto;
import tools.Arch;
import tools.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	/**
	 * Returns the id of a PRACTICANTE by its emil
	 *
	 * @param email The email of the PRACTICANTE
	 * @return PRACTICANTE's id as a String<br/>
	 * If something goes wrong, returns an empty String
	 */
	public static String getId(String email) {
		String id = "";
		if (email != null) {
			Practicante practicante = new Practicante();
			practicante.setCorreoElectronico(email);
			id = DAOPracticante.getId(practicante);
		}
		return id;
	}
	
	/**
	 * Returns the id of a PRACTICANTE
	 *
	 * @param practicante Instance of PRACTICANTE to know his id
	 * @return PRACTICANTE's id as a String<br/>
	 * If something goes wrong, returns an empty String
	 */
	public static String getId(Practicante practicante) {
		String id = "";
		if (practicante != null && practicante.getCorreoElectronico() != null &&
			new DAOPracticante(practicante).isRegistered()) {
			String query = "SELECT idUsuario FROM Usuario WHERE correoElectronico = ?";
			String[] values = {practicante.getCorreoElectronico()};
			String[] names = {"idUsuario"};
			id = new DBConnection().select(query, values, names)[0][0];
		}
		return id;
	}
	
	/**
	 * Returns the current PRACTICANTE's id
	 *
	 * @return The current PRACTICANTE's id<br/>
	 * If something goes wrong, returns an empty String
	 */
	public String getId() {
		return DAOPracticante.getId(this.practicante);
	}
	
	/**
	 * Update the Database with the current PRACTICANTE
	 *
	 * @return true => updated | false => something went wrong
	 */
	@Override
	public boolean update() {
		boolean updated = false;
		if (this.isRegistered()) {
			String query = "UPDATE Usuario SET nombres = ?, apellidos = ?, correoElectronico = ?, "
				+ "contrasena = ? WHERE correoElectronico = ?";
			String[] values = {this.practicante.getNombres(), this.practicante.getApellidos(),
				this.practicante.getCorreoElectronico(), this.practicante.getContrasena(),
				this.practicante.getCorreoElectronico()};
			if (this.connection.preparedQuery(query, values)) {
				query = "UPDATE Practicante SET Matricula = ? WHERE idUsuario = (SELECT " +
					"idUsuario" + " " + "FROM Usuario WHERE correoElectronico = ?)";
				values = new String[]{this.practicante.getMatricula(),
					this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					updated = true;
				}
			}
		}
		return updated;
	}
	
	/**
	 * Delete the current PRACTICANTE from the Database
	 *
	 * @return true => deleted | false => not deleted
	 */
	@Override
	public boolean delete() {
		boolean deleted = false;
		if (this.practicante != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Usuario SET status = 0 WHERE correoElectronico = ?";
				String[] values = {this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					deleted = true;
				}
			} else {
				deleted = true;
			}
		}
		return deleted;
	}
	
	/**
	 * Log the current PRACTICANTE
	 * Verifies if the current instance is registered<br/>
	 * and has the correct credentials to log in
	 *
	 * @return true => registered and correct credentials<br/>
	 * false => not registered or incorrect credentials
	 */
	@Override
	public boolean logIn() {
		boolean loggedIn = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isActive()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario " +
				"WHERE correoElectronico = ? AND contrasena = ? AND status = 1";
			String[] values = {this.practicante.getCorreoElectronico(),
				this.practicante.getContrasena()};
			String[] names = {"TOTAL"};
			if (this.isRegistered()) {
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					loggedIn = true;
				}
			}
		}
		return loggedIn;
	}
	
	/**
	 * Register the current instance to the database
	 * <p>
	 * Verifies that the current instance is not already registered,<br/>
	 * if not, saves it to the database.
	 *
	 * @return true => registered | false => couldn't register
	 */
	@Override
	public boolean signUp() {
		boolean signedUp = false;
		if (this.practicante != null) {
			if (!this.isRegistered()) {
				String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, " +
					"contrasena, status) VALUES (?, ?, ?, ?, 1)";
				String[] values = {this.practicante.getNombres(), this.practicante.getApellidos(),
					this.practicante.getCorreoElectronico(), this.practicante.getContrasena()};
				if (this.connection.preparedQuery(query, values)) {
					query = "INSERT INTO Practicante (idUsuario, matricula) VALUES " +
						"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
					values = new String[]{this.practicante.getCorreoElectronico(),
						this.practicante.getMatricula()};
					if (this.connection.preparedQuery(query, values)) {
						signedUp = true;
					}
				}
			} else {
				String query = "UPDATE Usuario SET status = 1 WHERE correoElectronico = ?";
				String[] values = {this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					signedUp = true;
				}
			}
		}
		return signedUp;
	}
	
	/**
	 * Verifies the existence of the current PRACTICANTE against the database<br/>
	 * Checks if the email of the current PRACTICANTE
	 * is already registered in the database
	 *
	 * @return true => registered | false => not registered
	 */
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL " +
				"FROM Usuario WHERE correoElectronico = ?";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"TOTAL"};
			if (this.practicante != null && this.practicante.getCorreoElectronico() != null) {
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					query = "SELECT COUNT(Practicante.idUsuario) AS TOTAL FROM Practicante " +
						"INNER JOIN Usuario ON Practicante.idUsuario = Usuario.idUsuario " +
						"WHERE Usuario.correoElectronico = ?";
					if (this.connection.select(query, values, names)[0][0].equals("1")) {
						isRegistered = true;
					}
				}
			}
		}
		return isRegistered;
	}
	
	/**
	 * Verifies if the current PRACTICANTE is active in the DB
	 *
	 * @return true => his status is active<br/>
	 * false => his status is inactive
	 */
	public boolean isActive() {
		boolean isActive = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isRegistered()) {
			String query = "SELECT status FROM Usuario WHERE correoElectronico = ?";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"status"};
			isActive = this.connection.select(query, values, names)[0][0].equals("1");
		}
		return isActive;
	}
	
	/**
	 * Returns an array of all PRACTICANTE from DB
	 *
	 * @return Array of PRACTICANTE<br/>
	 * If there are no PRACTICANTEs registered, returns null
	 */
	public static Practicante[] getAll() {
		Practicante[] practicantes;
		DBConnection connection = new DBConnection();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM Usuario INNER JOIN Practicante ON Usuario.idUsuario = Practicante.idUsuario " +
			"WHERE Usuario.status = 1";
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] results = connection.select(query, null, names);
		practicantes = new Practicante[results.length];
		for (int i = 0; i < results.length; i++) {
			practicantes[i] = new Practicante(results[i][0], results[i][1], results[i][2],
				results[i][3], results[i][4]);
		}
		return practicantes;
	}
	
	/**
	 * Returns an instance of Practicante by its email
	 *
	 * @param practicante Instance of PRACTICANTE with email
	 * @return Instace of PRACTICANTE completed from the DB<br/>
	 * If the provided PRACTICANTE is not registered, it will return null
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
				returnPracticante = new Practicante(results[0][0], results[0][1], results[0][2],
					results[0][3], results[0][4]);
			}
		}
		return returnPracticante;
	}
	
	public boolean selectProyect(String projectName) {
		return this.selectProyect(new DAOProyecto().loadProyecto(projectName));
	}
	
	/**
	 * Saves the selection of a project from the current PRACTICANTE in the database
	 *
	 * @param proyecto Instance of PROYECTO to relate in the database
	 * @return true => selection registered<br/>
	 * false => not registered
	 */
	public boolean selectProyect(Proyecto proyecto) {
		boolean selected = false;
		if (this.practicante != null && this.practicante.isComplete() && this.isActive() &&
			proyecto != null && proyecto.isComplete() &&
			new DAOProyecto(proyecto).isRegistered()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM SeleccionProyecto " +
				"WHERE idUsuario = (SELECT idUsuario FROM Usuario WHERE correoElectronico = ?)";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"TOTAL"};
			int selectedProyects =
				Integer.parseInt(this.connection.select(query, values, names)[0][0]);
			if (selectedProyects < 3) {
				query = "INSERT INTO SeleccionProyecto (idProyecto, idUsuario) VALUES " + "(" +
					"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1), " +
					"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?))";
				values = new String[]{proyecto.getNombre(),
					this.practicante.getCorreoElectronico()};
				if (this.connection.preparedQuery(query, values)) {
					selected = true;
				}
			}
		}
		return selected;
	}
	
	/**
	 * Returns an array of the selected PROYECTO from the current PRACTICANTE
	 *
	 * @return Array of PROYECTO
	 */
	public Proyecto[] getProyects() {
		Proyecto[] proyectos = null;
		if (this.practicante != null && this.practicante.isComplete() && this.isActive()) {
			String query = "SELECT nombre " + "FROM Proyecto INNER JOIN SeleccionProyecto ON " +
				"Proyecto.idProyecto = SeleccionProyecto.idProyecto " +
				"WHERE SeleccionProyecto.idUsuario = " +
				"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?) " +
				"AND Proyecto.status = 1";
			String[] values = {this.practicante.getCorreoElectronico()};
			String[] names = {"nombre"};
			String[][] results = this.connection.select(query, values, names);
			if (results.length > 0) {
				DAOProyecto daoProyecto = new DAOProyecto();
				proyectos = new Proyecto[results.length];
				for (int i = 0; i < results.length; i++) {
					proyectos[i] = daoProyecto.loadProyecto(results[i][0]);
				}
			}
		}
		return proyectos;
	}
	
	/**
	 * Deletes the selected PROYECTO by its name
	 *
	 * @param projectName The name of the project to deselect
	 * @return true => selection deleted<br/>
	 * false => selection not deleted
	 */
	public boolean deleteSelectedProyect(String projectName) {
		boolean deleted = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isActive() && projectName != null) {
			DAOProyecto daoProyecto = new DAOProyecto(projectName);
			if (daoProyecto.isRegistered()) {
				boolean isSelected = false;
				for (Proyecto proyecto: this.getProyects()) {
					if (proyecto != null && proyecto.getNombre().equals(projectName)) {
						isSelected = true;
						break;
					}
				}
				if (isSelected) {
					String query = "DELETE FROM SeleccionProyecto WHERE idUsuario = " +
						"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?) " +
						"AND idProyecto = " +
						"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND " + "status = 1)";
					String[] values = {this.practicante.getCorreoElectronico(), projectName};
					if (this.connection.preparedQuery(query, values)) {
						deleted = true;
					}
				}
			}
		}
		return deleted;
	}
	
	/**
	 * Adds a report to the database
	 *
	 * @param filePath The path to the file to upload as report
	 * @param title    The title of the report
	 * @return true => Report uploaded and saved<br/>
	 * false => Report not uploaded
	 */
	public boolean addReporte(String filePath, String title) {
		boolean saved = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isActive() && filePath != null && title != null) {
			if (Arch.existe(filePath)) {
				try {
					File file = new File(filePath);
					FileInputStream fis = new FileInputStream(file);
					
					String query = "INSERT INTO Reporte (titulo, fecha, practicante, reporte) " +
						"VALUES (?, (SELECT CURRENT_DATE()), " +
						"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
					
					this.connection.openConnection();
					PreparedStatement statement =
						this.connection.getConnection().prepareStatement(query);
					statement.setString(1, title);
					statement.setString(2, this.practicante.getCorreoElectronico());
					
					statement.setBinaryStream(3, fis, (int) file.length());
					
					statement.executeUpdate();
					this.connection.closeConnection();
					saved = true;
					
				} catch (FileNotFoundException | SQLException e) {
					new Logger().log(e);
				}
			}
		}
		return saved;
	}
	
	/**
	 * Deletes a report by its name for the current PRACTICANTE
	 *
	 * @param title The title of the report to delete
	 * @return true => Deleted<br/>
	 * false => not deleted
	 */
	public boolean deleteReport(String title) {
		boolean deleted = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isActive() && title != null) {
			String query = "DELETE FROM Reporte WHERE titulo = ? AND practicante = ?";
			String[] values = {title, this.getId()};
			if (this.connection.preparedQuery(query, values)) {
				deleted = true;
			}
		}
		return deleted;
	}
	
	public boolean setProyect(String projectName) {
		boolean set = false;
		if (this.practicante != null && this.isActive() &&
			projectName != null && new DAOProyecto(projectName).isRegistered()) {
			Proyecto proyecto = new DAOProyecto().loadProyecto(projectName);
			String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
				"WHERE idPracticante = ?";
			String[] values = {this.getId()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("0")) {
				query = "INSERT INTO PracticanteProyecto (idPracticante, idProyecto) " +
					"VALUES (?, (SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1))";
				values = new String[]{this.getId(), projectName};
				if (this.connection.preparedQuery(query, values)) {
					set = true;
				}
			}
		}
		return set;
	}
	
	public boolean deleteProyect() {
		boolean deleted = false;
		if (this.practicante != null && this.practicante.getCorreoElectronico() != null &&
			this.isActive()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM PracticanteProyecto " +
				"WHERE idPracticante = ?";
			String[] values = {this.getId()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "DELETE FROM PracticanteProyecto WHERE idPracticante = ?";
				if (this.connection.preparedQuery(query, values)) {
					deleted = true;
				}
			}
		}
		return deleted;
	}
}