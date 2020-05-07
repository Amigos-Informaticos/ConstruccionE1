package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOStudent;
import Models.Project;
import Models.Student;
import javafx.collections.ObservableList;
import tools.File;
import tools.Logger;

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
		String id;
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
	public boolean update() {
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
	public boolean delete() {
		boolean deleted = false;
		assert this.student != null : "Student is null: DAOStudent.delete()";
		assert this.isRegistered() : "Student is not registered: DAOStudent.delete()";
		if (this.isActive()) {
			String query = "UPDATE Usuario SET status = 0 WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			if (this.connection.sendQuery(query, values)) {
				deleted = true;
			}
		} else {
			deleted = true;
		}
		return deleted;
	}
	
	@Override
	public boolean logIn() {
		boolean loggedIn;
		assert this.student != null : "Student is null: DAOStudent.logIn()";
		assert this.student.getEmail() != null : "Student.getEmail is null: DAOStudent.logIn()";
		assert this.isActive() : "Student is inactive: DAOStudent.logIn()";
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario " +
			"WHERE correoElectronico = ? AND contrasena = ? AND status = 1";
		String[] values = {this.student.getEmail(),
			this.student.getPassword()};
		String[] names = {"TOTAL"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean signUp() throws CustomException {
		assert this.student != null : "Student is null: DAOStudent.signUp()";
		boolean signedUp;
		if (!this.isRegistered()) {
			String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, " +
				"contrasena, status) VALUES (?, ?, ?, ?, 1)";
			String[] values = {this.student.getNames(), this.student.getLastnames(),
				this.student.getEmail(), this.student.getPassword()};
			if (this.connection.sendQuery(query, values)) {
				query = "INSERT INTO Practicante (idUsuario, matricula) VALUES " +
					"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
				values = new String[]{this.student.getEmail(), this.student.getRegNumber()};
				signedUp = this.connection.sendQuery(query, values);
			} else {
				throw new CustomException("Could not sign up into User: DAOStudent.signUp()",
					"NotSignUpUser");
			}
		} else {
			signedUp = this.reactive();
		}
		return signedUp;
	}
	
	@Override
	public boolean isRegistered() {
		assert this.student != null;
		assert this.student.getEmail() != null;
		String query = "SELECT COUNT(Usuario.idUsuario) AS TOTAL FROM Usuario INNER JOIN Practicante " +
			"ON Usuario.idUsuario = Practicante.idUsuario WHERE correoElectronico = ?";
		String[] values = {this.student.getEmail()};
		String[] names = {"TOTAL"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public boolean isActive() {
		boolean isActive;
		assert this.student != null : "Student is null: DAOStudent.isActive()";
		assert this.student.getEmail() != null : "Student.getEmail() is null: DAOStudent.isActive()";
		String query = "SELECT status FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.student.getEmail()};
		String[] names = {"status"};
		isActive = this.connection.select(query, values, names)[0][0].equals("1");
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
	
	public static Student get(Student student) {
		DBConnection connection = new DBConnection();
		Student returnStudent;
		assert student != null : "Student is null: DAOStudent.get()";
		assert new DAOStudent(student).isRegistered() : "Student not registered: DAOStudent.get()";
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, " +
			"matricula FROM Usuario INNER JOIN Practicante " +
			"ON Usuario.idUsuario = Practicante.idUsuario " +
			"WHERE Usuario.correoElectronico = ?";
		String[] values = {student.getEmail()};
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena",
			"matricula"};
		String[] results = connection.select(query, values, names)[0];
		returnStudent = new Student(results[0], results[1], results[2],
			results[3], results[4]);
		return returnStudent;
	}
	
	public boolean selectProject(String projectName) throws CustomException {
		return this.selectProject(new DAOProject().loadProject(projectName));
	}
	
	public boolean selectProject(Project project) throws CustomException {
		boolean selected = false;
		assert this.student != null : "Student is null: DAOStudent.selectProject()";
		assert this.student.isComplete() : "Student is incomplete: DAOStudent.selectProject()";
		assert this.isActive() : "Student is inactive: DAOStudent.selectProject()";
		assert project != null : "Project is null: DAOStudent.selectProject()";
		assert project.isComplete() : "Project is incomplete: DAOStudent.selectProject()";
		
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM SeleccionProyecto " +
			"WHERE idUsuario = (SELECT idUsuario FROM Usuario WHERE correoElectronico = ?)";
		String[] values = {this.student.getEmail()};
		String[] names = {"TOTAL"};
		int selectedProjects =
			Integer.parseInt(this.connection.select(query, values, names)[0][0]);
		if (selectedProjects < 3) {
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
				selected = this.connection.sendQuery(query, values);
			}
		}
		return selected;
	}
	
	public Project[] getProjects() {
		Project[] proyectos = null;
		assert this.student != null : "Student is null: DAOStudent.getProjects()";
		assert this.student.isComplete() : "Student is incomplete: DAOStudent.getProjects()";
		assert this.isActive() : "Student is inactive: DAOStudent.getProjects()";
		String query = "SELECT nombre " + "FROM Proyecto INNER JOIN SeleccionProyecto ON " +
			"Proyecto.idProyecto = SeleccionProyecto.idProyecto " +
			"WHERE SeleccionProyecto.idUsuario = " +
			"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?) AND Proyecto.status = 1";
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
		return proyectos;
	}
	
	public boolean deleteSelectedProject(String projectName) {
		boolean deleted = false;
		DAOProject daoProject = new DAOProject(projectName);
		assert this.student != null : "Student is null: DAOStudent.deleteSelectedProject()";
		assert this.student.getEmail() != null :
			"Student.getEmail() is null: DAOStudent.deleteSelectedProject()";
		assert this.isActive() : "Student is inactive: DAOStudent.deleteSelectedProject()";
		assert projectName != null : "ProjectName is null: DAOStudent.deleteSelectedProject()";
		assert daoProject.isRegistered() :
			"Project is not registered: DAOStudent.deleteSelectedProject()";
		boolean isSelected = false;
		for (Project project: this.getProjects()) {
			if (project != null && project.getName().equals(projectName)) {
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
			deleted = this.connection.sendQuery(query, values);
		}
		return deleted;
	}
	
	public boolean addReport(String filePath, String title) throws CustomException {
		boolean saved;
		assert this.student != null : "Student is null: DAOStudent.addReport()";
		assert this.student.getEmail() != null : "Student's email is null: DAOStudent.addReport()";
		assert this.isActive() : "Student is not active: DAOStudent.addReport()";
		assert filePath != null : "FilePath is null: DAOStudent.addReport()";
		assert title != null : "Title is null: DAOStudent.addReport()";
		assert File.exists(filePath) : "File does not exists: DAOStudent.addReport()";
		try {
			java.io.File file = new java.io.File(filePath);
			FileInputStream fis = new FileInputStream(file);
			
			String query = "INSERT INTO Reporte (titulo, fecha, practicante, reporte) " +
				"VALUES (?, (SELECT CURRENT_DATE()), " +
				"(SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?)";
			this.connection.openConnection();
			PreparedStatement statement = this.connection.getConnection().prepareStatement(query);
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
		return saved;
	}
	
	public boolean deleteReport(String title) throws CustomException {
		assert this.student != null : "Student is null: DAOStudent.deleteReport()";
		assert this.student.getEmail() != null :
			"Student's email is null: DAOStudents.deleteReport()";
		assert this.isActive() : "Student is inactive: DAOStudents.deleteReport()";
		assert title != null : "Title is null: DAOStudent.deleteReport()";
		boolean deleted;
		String query = "DELETE FROM Reporte WHERE titulo = ? AND practicante = ?";
		String[] values = {title, this.getId()};
		deleted = this.connection.sendQuery(query, values);
		return deleted;
	}
	
	public boolean setProject(String projectName) throws CustomException {
		assert this.student != null : "Student is null: DAOStudent.setProject()";
		assert this.isActive() : "Student is not active: DAOStudent.setProject()";
		assert projectName != null : "ProjectName is null: DAOStudent.setProject()";
		assert new DAOProject(projectName).isRegistered() :
			"Project is not registered: DAOStudent.setProject()";
		boolean set;
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
			"WHERE idPracticante = ?";
		String[] values = {this.getId()};
		String[] names = {"TOTAL"};
		if (this.connection.select(query, values, names)[0][0].equals("0")) {
			query = "INSERT INTO PracticanteProyecto (idPracticante, idProyecto) " +
				"VALUES (?, (SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1))";
			values = new String[]{this.getId(), projectName};
			set = this.connection.sendQuery(query, values);
		} else {
			throw new CustomException("Already setted project: setProject()", "ProjectAlreadySet");
		}
		return set;
	}
	
	public boolean deleteProject() throws CustomException {
		boolean deleted = false;
		assert this.student != null : "Student is null: DAOStudent.deleteProject()";
		assert this.student.getEmail() != null :
			"Student's email is null: DAOStudent.deleteProject()";
		assert this.isActive() : "Student is not active: DAOStudent.deleteProject()";
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM PracticanteProyecto " +
			"WHERE idPracticante = ?";
		String[] values = {this.getId()};
		String[] names = {"TOTAL"};
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			query = "DELETE FROM PracticanteProyecto WHERE idPracticante = ?";
			deleted = this.connection.sendQuery(query, values);
		}
		return deleted;
	}
	
	public Project getProject() throws CustomException {
		Project project;
		assert this.student != null : "Student is null: DAOStudent.getProject()";
		assert this.student.getEmail() != null : "Student's email is null: DAOStudent.getProject()";
		assert this.isActive() : "Student is inactive: DAOStudent.getProject()";
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
				this.connection.select(query, values, responses)[0][0]);
		} else {
			throw new CustomException("Project Not Set: getProject()", "ProjectNotSet");
		}
		return project;
	}
	
	@Override
	public boolean reactive() {
		boolean reactivated;
		assert this.student != null : "Student is null: DAOStudent.reactive()";
		assert this.isRegistered() : "Student is not registered: DAOStudent.reactive()";
		if (!this.isActive()) {
			String query = "UPDATE Usuario SET status = 1 WHERE correoElectronico = ?";
			String[] values = {this.student.getEmail()};
			reactivated = this.connection.sendQuery(query, values);
		} else {
			reactivated = true;
		}
		return reactivated;
	}
	
	public boolean replyActivity(String activityName, String documentPath) throws CustomException {
		boolean replied = false;
		assert this.student != null : "Student is null: DAOStudent.replyActivity()";
		assert this.isActive() : "Student is not active: DAOStudent.replyActivity()";
		assert documentPath != null : "Document Path is null: DAOStudent.replyActivity()";
		assert File.exists(documentPath) : "File doesnt exists: DAOStudent.replyActivity()";
		assert activityName != null : "Activity name is null: DAOStudent.replyActivity()";
		//verificar query
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad WHERE titulo = ?";
		String[] values = {activityName};
		String[] names = {"TOTAL"};
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			try {
				java.io.File file = new java.io.File(documentPath);
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
		return replied;
	}
	
	public boolean deleteReply(String activityName) throws CustomException {
		boolean replied;
		assert this.student != null : "Student is null: DAOStudent.deleteReply()";
		assert this.isActive() : "Student is not active: DAOStudent.deleteReply()";
		assert activityName != null : "Activity name is null: DAOStudent.deleteReply()";
		String query = "UPDATE Actividad SET documento = null, fechaEntrega = null " +
			"WHERE idPracticante = ? AND titulo = ?";
		String[] values = {this.getId(), activityName};
		replied = this.connection.sendQuery(query, values);
		return replied;
	}
	
	public boolean fillTableStudent(ObservableList<Student> listStudent) {
		boolean filled = false;
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula FROM Usuario INNER JOIN " +
			"Practicante on Usuario.idUsuario = Practicante.idUsuario";
		String[] values = null;
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] select = this.connection.select(query, values, names);
		int row = 0;
		while (row < select.length) {
			listStudent.add(
				new Student(
					select[row][0],
					select[row][1],
					select[row][2],
					select[row][3],
					select[row][4]
				)
			);
			if (!filled) {
				filled = true;
			}
			row++;
		}
		return filled;
	}
}