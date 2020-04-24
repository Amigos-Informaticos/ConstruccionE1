package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOStudent;
import Models.Project;
import Models.Student;
import tools.Arch;
import tools.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOStudent implements IDAOStudent {
	private Student student;
	private final DBConnection connection = new DBConnection();
	
	public DAOStudent(Student student) {
		this.student = student;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public String getId() throws CustomException {
		String id = "";
		if (this.student != null && this.student.getEmail() != null &&
			new DAOStudent(this.student).isRegistered()) {
			String query = "SELECT idUsuario FROM Usuario WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			String[] names = {"idUsuario"};
			id = new DBConnection().select(query, values, names)[0][0];
		} else {
			throw new CustomException(
				"Null Pointer Exception: getId()",
				"NullPointerException", "NullEmail", "StudentNotRegistered"
			);
		}
		return id;
	}
	
	@Override
	public boolean update() throws CustomException {
		boolean updated = false;
		if (this.isRegistered()) {
			String query = "UPDATE Usuario SET nombres = ?, apellidos = ?, correoElectronico = ?, "
				+ "contrasena = ? WHERE correoElectronico = ?";
			String[] values = {this.student.getNames(), this.student.getLastnames(),
				this.student.getEmail(), this.student.getPassword(),
				this.student.getEmail()};
			if (this.connection.sendQuery(query, values)) {
				query = "UPDATE Practicante SET Matricula = ? WHERE idUsuario = (SELECT " +
					"idUsuario" + " " + "FROM Usuario WHERE correoElectronico = ?)";
				values = new String[]{this.student.getRegNumber(),
					this.student.getEmail()};
				if (this.connection.sendQuery(query, values)) {
					updated = true;
				}
			}
		}
		return updated;
	}
	
	@Override
	public boolean delete() throws CustomException {
		boolean deleted = false;
		if (this.student != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Usuario SET status = 0 WHERE correoElectronico = ?";
				String[] values = {this.student.getEmail()};
				if (this.connection.sendQuery(query, values)) {
					deleted = true;
				}
			} else {
				deleted = true;
			}
		}
		return deleted;
	}
	
	@Override
	public boolean logIn() throws CustomException {
		boolean loggedIn = false;
		if (this.student != null && this.student.getEmail() != null &&
			this.isActive()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario " +
				"WHERE correoElectronico = ? AND contrasena = ? AND status = 1";
			String[] values = {this.student.getEmail(),
				this.student.getPassword()};
			String[] names = {"TOTAL"};
			if (this.isRegistered()) {
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					loggedIn = true;
				}
			}
		}
		return loggedIn;
	}
	
	@Override
	public boolean signUp() throws CustomException {
		boolean signedUp = false;
		if (this.student != null) {
			if (!this.isRegistered()) {
				String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, " +
					"contrasena, status) VALUES (?, ?, ?, ?, 1)";
				String[] values = {this.student.getNames(), this.student.getLastnames(),
					this.student.getEmail(), this.student.getPassword()};
				if (this.connection.sendQuery(query, values)) {
					query = "INSERT INTO Practicante (idUsuario, matricula) VALUES " +
						"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
					values = new String[]{this.student.getEmail(),
						this.student.getRegNumber()};
					if (this.connection.sendQuery(query, values)) {
						signedUp = true;
					} else {
						throw new CustomException(
							"Could not sign up into Student: DAOStudent.signUp()");
					}
				} else {
					throw new CustomException("Could not sign upt into User: DAOStudent.signUp()",
						"NotSignUpUser");
				}
			} else {
				signedUp = this.reactive();
			}
		}
		return signedUp;
	}
	
	@Override
	public boolean isRegistered() throws CustomException {
		boolean isRegistered = false;
		if (this.student != null && this.student.getEmail() != null) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL " +
				"FROM Usuario WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT COUNT(Practicante.idUsuario) AS TOTAL FROM Practicante " +
					"INNER JOIN Usuario ON Practicante.idUsuario = Usuario.idUsuario " +
					"WHERE Usuario.correoElectronico = ?";
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					isRegistered = true;
				}
			}
		} else {
			throw new CustomException("Null Pointer Exception: isRegistered()");
		}
		return isRegistered;
	}
	
	public boolean isActive() throws CustomException {
		boolean isActive = false;
		if (this.student != null && this.student.getEmail() != null) {
			String query = "SELECT status FROM Usuario WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			String[] names = {"status"};
			isActive = this.connection.select(query, values, names)[0][0].equals("1");
		} else {
			throw new CustomException("Null Pointer Exception: isActive()");
		}
		return isActive;
	}
	
	public static Student[] getAll() {
		Student[] students;
		DBConnection connection = new DBConnection();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM Usuario INNER JOIN Practicante ON Usuario.idUsuario = Practicante.idUsuario " +
			"WHERE Usuario.status = 1";
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] results = connection.select(query, null, names);
		students = new Student[results.length];
		for (int i = 0; i < results.length; i++) {
			students[i] = new Student(results[i][0], results[i][1], results[i][2],
				results[i][3], results[i][4]);
		}
		return students;
	}
	
	public static Student get(Student student) throws CustomException {
		DBConnection connection = new DBConnection();
		Student returnStudent = null;
		if (student != null) {
			if (new DAOStudent(student).isRegistered()) {
				String query = "SELECT nombres, apellidos, correoElectronico, contrasena, " +
					"matricula FROM Usuario INNER JOIN Practicante " +
					"ON Usuario.idUsuario = Practicante.idUsuario " +
					"WHERE Usuario.correoElectronico = ?";
				String[] values = {student.getEmail()};
				String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena",
					"matricula"};
				String[][] results = connection.select(query, values, names);
				returnStudent = new Student(results[0][0], results[0][1], results[0][2],
					results[0][3], results[0][4]);
			} else {
				throw new CustomException("Not registered: get()");
			}
		} else {
			throw new CustomException("Null Pointer Exception: get()");
		}
		return returnStudent;
	}
	
	public boolean selectProject(String projectName) throws CustomException {
		return this.selectProject(new DAOProject().loadProject(projectName));
	}
	
	public boolean selectProject(Project project) throws CustomException {
		boolean selected = false;
		assert this.student != null;
		assert this.student.isComplete();
		assert this.isActive();
		assert project != null;
		assert project.isComplete();
		if (this.student != null && this.student.isComplete() && this.isActive() &&
			project != null && project.isComplete() &&
			new DAOProject(project).isRegistered()) {
			String query = "SELECT COUNT(idUsuario) AS TOTAL FROM SeleccionProyecto " +
				"WHERE idUsuario = (SELECT idUsuario FROM Usuario WHERE correoElectronico = ?)";
			String[] values = {this.student.getEmail()};
			String[] names = {"TOTAL"};
			int selectedProyects =
				Integer.parseInt(this.connection.select(query, values, names)[0][0]);
			if (selectedProyects < 3) {
				query = "SELECT COUNT(idUsuario) AS TOTAL FROM SeleccionProyecto " +
					"WHERE idUsuario = ? AND idProyecto = " +
					"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1)";
				values = new String[]{this.getId(), project.getName()};
				names = new String[]{"TOTAL"};
				if (this.connection.select(query, values, names)[0][0].equals("0")) {
					query = "INSERT INTO SeleccionProyecto (idProyecto, idUsuario) VALUES " + "(" +
						"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1), " +
						"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?))";
					values = new String[]{project.getName(), this.student.getEmail()};
					if (this.connection.sendQuery(query, values)) {
						selected = true;
					}
				}
			}
		} else {
			throw new CustomException("Null pointer exception: selectProject()");
		}
		return selected;
	}
	
	public Project[] getProjects() throws CustomException {
		Project[] proyectos = null;
		if (this.student != null && this.student.isComplete() && this.isActive()) {
			String query = "SELECT nombre " + "FROM Proyecto INNER JOIN SeleccionProyecto ON " +
				"Proyecto.idProyecto = SeleccionProyecto.idProyecto " +
				"WHERE SeleccionProyecto.idUsuario = " +
				"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?) " +
				"AND Proyecto.status = 1";
			String[] values = {this.student.getEmail()};
			String[] names = {"nombre"};
			String[][] results = this.connection.select(query, values, names);
			if (results.length > 0) {
				DAOProject daoProject = new DAOProject();
				proyectos = new Project[results.length];
				for (int i = 0; i < results.length; i++) {
					proyectos[i] = daoProject.loadProject(results[i][0]);
				}
			}
		} else {
			throw new CustomException("Null pointer exception: getProjects()");
		}
		return proyectos;
	}
	
	public boolean deleteSelectedProject(String projectName) throws CustomException {
		boolean deleted = false;
		if (this.student != null && this.student.getEmail() != null &&
			this.isActive() && projectName != null) {
			DAOProject daoProyecto = new DAOProject(projectName);
			if (daoProyecto.isRegistered()) {
				boolean isSelected = false;
				for (Project proyecto: this.getProjects()) {
					if (proyecto != null && proyecto.getName().equals(projectName)) {
						isSelected = true;
						break;
					}
				}
				if (isSelected) {
					String query = "DELETE FROM SeleccionProyecto WHERE idUsuario = " +
						"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?) " +
						"AND idProyecto = " +
						"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND " + "status = 1)";
					String[] values = {this.student.getEmail(), projectName};
					if (this.connection.sendQuery(query, values)) {
						deleted = true;
					}
				}
			} else {
				throw new CustomException("Null Pointer Exception: deleteSelectedProject()");
			}
		}
		return deleted;
	}
	
	public boolean addReport(String filePath, String title) throws CustomException {
		boolean saved = false;
		if (this.student != null && this.student.getEmail() != null &&
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
					statement.setString(2, this.student.getEmail());
					
					statement.setBinaryStream(3, fis, (int) file.length());
					
					statement.executeUpdate();
					this.connection.closeConnection();
					saved = true;
					
				} catch (FileNotFoundException | SQLException e) {
					new Logger().log(e);
					throw new CustomException(e.getMessage());
				}
			} else {
				throw new CustomException("File does not exists: addReport()");
			}
		} else {
			throw new CustomException("Null Pointer Exception: addReport()");
		}
		return saved;
	}
	
	public boolean deleteReport(String title) throws CustomException {
		boolean deleted = false;
		if (this.student != null && this.student.getEmail() != null &&
			this.isActive() && title != null) {
			String query = "DELETE FROM Reporte WHERE titulo = ? AND practicante = ?";
			String[] values = {title, this.getId()};
			if (this.connection.sendQuery(query, values)) {
				deleted = true;
			} else {
				throw new CustomException("Could not delete from Reporte: deleteReport()");
			}
		} else {
			throw new CustomException("Null Pointer Exception: deleteReport()");
		}
		return deleted;
	}
	
	public boolean setProject(String projectName) throws CustomException {
		boolean set = false;
		if (this.student != null && this.isActive() &&
			projectName != null && new DAOProject(projectName).isRegistered()) {
			Project proyecto = new DAOProject().loadProject(projectName);
			String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
				"WHERE idPracticante = ?";
			String[] values = {this.getId()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("0")) {
				query = "INSERT INTO PracticanteProyecto (idPracticante, idProyecto) " +
					"VALUES (?, (SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1))";
				values = new String[]{this.getId(), projectName};
				if (this.connection.sendQuery(query, values)) {
					set = true;
				} else {
					throw new CustomException("Could not insert into StudentProject: setProject()");
				}
			} else {
				throw new CustomException("Already setted project: setProject()", "ProjectAlreadySet");
			}
		} else {
			throw new CustomException("Null Pointer Excepcion: setProject()");
		}
		return set;
	}
	
	public boolean deleteProject() throws CustomException {
		boolean deleted = false;
		if (this.student != null && this.student.getEmail() != null &&
			this.isActive()) {
			String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
				"WHERE idPracticante = ?";
			String[] values = {this.getId()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "DELETE FROM PracticanteProyecto WHERE idPracticante = ?";
				if (this.connection.sendQuery(query, values)) {
					deleted = true;
				}
			}
		}
		return deleted;
	}
	
	public Project getProject() throws CustomException {
		Project project = null;
		if (this.student != null && this.student.getEmail() != null && this.isActive()) {
			String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
				"WHERE idPracticante = ?";
			String[] values = {this.getId()};
			String[] responses = {"TOTAL"};
			if (this.connection.select(query, values, responses)[0][0].equals("1")) {
				query = "SELECT Proyecto.nombre FROM Proyecto INNER JOIN PracticanteProyecto " +
					"ON Proyecto.idProyecto = PracticanteProyecto.idProyecto " +
					"WHERE PracticanteProyecto.idPracticante = ?;";
				responses = new String[]{"Proyecto.nombre"};
				DAOProject daoProject = new DAOProject();
				project = daoProject.loadProject(
					this.connection.select(query, values, responses)[0][0]
				);
			} else {
				throw new CustomException("Project Not Set: getProject()", "ProjectNotSet");
			}
		} else {
			throw new CustomException(
				"Null Pointer Exception: getProject()",
				"NullPointerException");
		}
		return project;
	}
	
	@Override
	public boolean reactive() throws CustomException {
		boolean reactivated = false;
		assert this.student != null;
		assert this.isRegistered();
		if (!this.isActive()) {
			String query = "UPDATE Usuario SET status = 1 WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			if (this.connection.sendQuery(query, values)) {
				reactivated = true;
			}
		} else {
			reactivated = true;
		}
		return reactivated;
	}
	
	public boolean replyActivity(String activityName, String documentPath) throws CustomException {
		boolean replied = false;
		if (this.student != null && this.isActive() && documentPath != null &&
			Arch.existe(documentPath) && activityName != null) {
			String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad WHERE titulo = ?";
			String[] values = {activityName};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				try {
					File file = new File(documentPath);
					FileInputStream fis = new FileInputStream(file);
					
					query = "UPDATE Actividad SET documento = ?, " +
						"fechaEntrega = (SELECT CURRENT_DATE()) " +
						"WHERE titulo = ? AND idPracticante = ?";
					this.connection.openConnection();
					PreparedStatement statement =
						this.connection.getConnection().prepareStatement(query);
					statement.setBinaryStream(1, fis, (int) file.length());
					statement.setString(2, activityName);
					statement.setString(3, getId());
					statement.executeUpdate();
					this.connection.closeConnection();
					replied = true;
				} catch (FileNotFoundException | SQLException e) {
					new Logger().log(e);
				}
			}
		}
		return replied;
	}
	
	public boolean deleteReply(String activityName) throws CustomException {
		boolean replied = false;
		if (this.student != null && this.isActive() && activityName != null) {
			String query = "UPDATE Actividad SET documento = null, fechaEntrega = null WHERE";
		} else {
			throw new CustomException("Null Pointer Exception: deleteReply()");
		}
		return replied;
	}
}