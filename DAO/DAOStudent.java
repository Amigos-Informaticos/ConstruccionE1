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
	
	public String getId() {
		assert this.student != null : "This Student is null: DAOStudent.getId()";
		assert this.student.getEmail() != null : "This Student email is null: DAOStudent.getId()";
		assert new DAOStudent(this.student).isRegistered() :
			"Student isnt registered: DAOStudent.getId()";
		String query = "SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = { this.student.getEmail() };
		String[] names = { "idMiembro" };
		return new DBConnection().select(query, values, names)[0][0];
	}
	
	@Override
	public boolean update() {
		boolean updated = false;
		assert this.isRegistered() : "This Student isnt registered: DAOStudent.update()";
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ?, correoElectronico = ?, "
			+ "contrasena = ? WHERE correoElectronico = ?";
		String[] values = { this.student.getNames(), this.student.getLastnames(),
			this.student.getEmail(), this.student.getPassword(),
			this.student.getEmail() };
		if (this.connection.sendQuery(query, values)) {
			query = "UPDATE Practicante SET matricula = ? WHERE idMiembro = " +
				"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
			values = new String[]{ this.student.getRegNumber(), this.student.getEmail() };
			updated = this.connection.sendQuery(query, values);
		}
		return updated;
	}
	
	@Override
	public boolean delete() {
		boolean deleted = false;
		assert this.student != null : "Student is null: DAOStudent.delete()";
		assert this.isRegistered() : "Student is not registered: DAOStudent.delete()";
		if (this.isActive()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
			String[] values = { this.student.getEmail() };
			deleted = this.connection.sendQuery(query, values);
		}
		return deleted;
	}
	
	@Override
	public boolean logIn() {
		assert this.student != null : "Student is null: DAOStudent.logIn()";
		assert this.student.getEmail() != null : "Student.getEmail is null: DAOStudent.logIn()";
		assert this.isActive() : "Student is inactive: DAOStudent.logIn()";
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM MiembroFEI " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] values = { this.student.getEmail(),
			this.student.getPassword() };
		String[] names = { "TOTAL" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean signUp() throws CustomException {
		assert this.student != null : "Student is null: DAOStudent.signUp()";
		boolean signedUp;
		if (!this.isRegistered()) {
			String query = "INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, " +
				"contrasena, estaActivo) VALUES (?, ?, ?, ?, 1)";
			String[] values = { this.student.getNames(), this.student.getLastnames(),
				this.student.getEmail(), this.student.getPassword() };
			if (this.connection.sendQuery(query, values)) {
				query = "INSERT INTO Practicante (idMiembro, matricula) VALUES " +
					"((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), ?)";
				values = new String[]{ this.student.getEmail(), this.student.getRegNumber() };
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
		assert this.student != null : "Student is null: DAOStudent.isRegistered()";
		assert this.student.getEmail() != null : "Students email is null: DAOStudent.isRegistered()";
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
			"INNER JOIN Practicante ON MiembroFEI.idMiembro = Practicante.idMiembro " +
			"WHERE correoElectronico = ?";
		String[] values = { this.student.getEmail() };
		String[] names = { "TOTAL" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public boolean isActive() {
		assert this.student != null : "Student is null: DAOStudent.isActive()";
		assert this.student.getEmail() != null : "Student.getEmail() is null: DAOStudent.isActive()";
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = { this.student.getEmail() };
		String[] names = { "estaActivo" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public static Student[] getAll() {
		Student[] students;
		DBConnection connection = new DBConnection();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro WHERE MiembroFEI.estaActivo = 1";
		String[] names = { "nombres", "apellidos", "correoElectronico", "contrasena", "matricula" };
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
			"matricula FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro " +
			"WHERE MiembroFEI.correoElectronico = ?";
		String[] values = { student.getEmail() };
		String[] names = { "nombres", "apellidos", "correoElectronico", "contrasena", "matricula" };
		String[] results = connection.select(query, values, names)[0];
		returnStudent = new Student(results[0], results[1], results[2], results[3], results[4]);
		return returnStudent;
	}
	
	public boolean selectProject(String projectName) {
		return this.selectProject(new DAOProject().loadProject(projectName));
	}
	
	public boolean selectProject(Project project) {
		boolean selected = false;
		assert this.student != null : "Student is null: DAOStudent.selectProject()";
		assert this.student.isComplete() : "Student is incomplete: DAOStudent.selectProject()";
		assert this.isActive() : "Student is inactive: DAOStudent.selectProject()";
		assert project != null : "Project is null: DAOStudent.selectProject()";
		assert project.isComplete() : "Project is incomplete: DAOStudent.selectProject()";
		
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM Solicitud " +
			"WHERE idMiembro = (SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
		String[] values = { this.student.getEmail() };
		String[] names = { "TOTAL" };
		
		int selectedProjects = Integer.parseInt(this.connection.select(query, values, names)[0][0]);
		if (selectedProjects < 3) {
			query = "SELECT COUNT(idMiembro) AS TOTAL FROM Solicitud " +
				"WHERE idMiembro = ? AND idProyecto = " +
				"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1)";
			values = new String[]{ this.getId(), project.getName() };
			names = new String[]{ "TOTAL" };
			if (this.connection.select(query, values, names)[0][0].equals("0")) {
				query = "INSERT INTO Solicitud (idProyecto, idMiembro) VALUES " +
					"((SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1), " +
					"(SELECT MiembroFEI.idMiembro FROM MiembroFEI WHERE correoElectronico = ?))";
				values = new String[]{ project.getName(), this.student.getEmail() };
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
		String query = "SELECT nombre " + "FROM Proyecto INNER JOIN Solicitud ON " +
			"Proyecto.idProyecto = Solicitud.idProyecto WHERE Solicitud.idMiembro = " +
			"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?) " +
			"AND Proyecto.estaActivo = 1";
		String[] values = { this.student.getEmail() };
		String[] names = { "nombre" };
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
			String query = "DELETE FROM Solicitud WHERE idMiembro = " +
				"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?) AND idProyecto = " +
				"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1)";
			String[] values = { this.student.getEmail(), projectName };
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
			
			String query;
			query = "INSERT INTO Documento (titulo, fecha, autor, archivo)" +
				"VALUES (?, (SELECT CURRENT_DATE()), ?, ?);" +
				"INSERT INTO Reporte (idDocumento, temporalidad) VALUES " +
				"((SELECT idDocumento FROM Documento WHERE titulo = ? AND autor = ?), " +
				"'Mensual')";
			this.connection.openConnection();
			PreparedStatement statement = this.connection.getConnection().prepareStatement(query);
			statement.setString(1, title);
			statement.setString(2, this.getId());
			statement.setBinaryStream(3, fis, (int) file.length());
			statement.setString(4, title);
			statement.setString(5, this.getId());
			statement.executeUpdate();
			this.connection.closeConnection();
			saved = true;
		} catch (FileNotFoundException | SQLException e) {
			new Logger().log(e);
			throw new CustomException(e.getMessage());
		}
		return saved;
	}
	
	public boolean deleteReport(String title) {
		assert this.student != null : "Student is null: DAOStudent.deleteReport()";
		assert this.student.getEmail() != null :
			"Student's email is null: DAOStudents.deleteReport()";
		assert this.isActive() : "Student is inactive: DAOStudents.deleteReport()";
		assert title != null : "Title is null: DAOStudent.deleteReport()";
		
		String query = "DELETE FROM Documento WHERE titulo = ? AND autor = ?";
		String[] values = { title, this.getId() };
		return this.connection.sendQuery(query, values);
	}
	
	public boolean setProject(String projectName) throws CustomException {
		assert this.student != null : "Student is null: DAOStudent.setProject()";
		assert this.isActive() : "Student is not active: DAOStudent.setProject()";
		assert projectName != null : "ProjectName is null: DAOStudent.setProject()";
		assert new DAOProject(projectName).isRegistered() :
			"Project is not registered: DAOStudent.setProject()";
		boolean set;
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] values = { this.getId() };
		String[] names = { "TOTAL" };
		if (this.connection.select(query, values, names)[0][0].equals("0")) {
			query = "INSERT INTO Asignacion (idPracticante, idProyecto) " +
				"VALUES (?, (SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1))";
			values = new String[]{ this.getId(), projectName };
			set = this.connection.sendQuery(query, values);
		} else {
			throw new CustomException("Already setted project: setProject()", "ProjectAlreadySet");
		}
		return set;
	}
	
	public boolean deleteProject() {
		boolean deleted = false;
		assert this.student != null : "Student is null: DAOStudent.deleteProject()";
		assert this.student.getEmail() != null :
			"Student's email is null: DAOStudent.deleteProject()";
		assert this.isActive() : "Student is not active: DAOStudent.deleteProject()";
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] values = { this.getId() };
		String[] names = { "TOTAL" };
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			query = "DELETE FROM Asignacion WHERE idPracticante = ?";
			deleted = this.connection.sendQuery(query, values);
		}
		return deleted;
	}
	
	public Project getProject() throws CustomException {
		Project project;
		assert this.student != null : "Student is null: DAOStudent.getProject()";
		assert this.student.getEmail() != null : "Student's email is null: DAOStudent.getProject()";
		assert this.isActive() : "Student is inactive: DAOStudent.getProject()";
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] values = { this.getId() };
		String[] responses = { "TOTAL" };
		if (this.connection.select(query, values, responses)[0][0].equals("1")) {
			query = "SELECT Proyecto.nombre FROM Proyecto INNER JOIN Asignacion " +
				"ON Proyecto.idProyecto = Asignacion.idProyecto " +
				"WHERE Asignacion.idPracticante = ?;";
			responses = new String[]{ "Proyecto.nombre" };
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
		boolean reactivated = false;
		assert this.student != null : "Student is null: DAOStudent.reactive()";
		assert this.isRegistered() : "Student is not registered: DAOStudent.reactive()";
		if (!this.isActive()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] values = { this.student.getEmail() };
			reactivated = this.connection.sendQuery(query, values);
		}
		return reactivated;
	}
	
	public boolean replyActivity(String activityName, String documentPath) {
		boolean replied = false;
		assert this.student != null : "Student is null: DAOStudent.replyActivity()";
		assert this.isActive() : "Student is not active: DAOStudent.replyActivity()";
		assert documentPath != null : "Document Path is null: DAOStudent.replyActivity()";
		assert File.exists(documentPath) : "File doesnt exists: DAOStudent.replyActivity()";
		assert activityName != null : "Activity name is null: DAOStudent.replyActivity()";
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad " +
			"WHERE titulo = ? AND idPracticante = ?";
		String[] values = { activityName, this.getId() };
		String[] names = { "TOTAL" };
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			try {
				java.io.File file = new java.io.File(documentPath);
				FileInputStream fis = new FileInputStream(file);
				query = "INSERT INTO Documento (titulo, fecha, tipo, archivo, autor) " +
					"VALUES (?, (SELECT CURRENT_DATE()), 'Actividad', ?, ?)";
				this.connection.openConnection();
				PreparedStatement statement =
					this.connection.getConnection().prepareStatement(query);
				statement.setString(1, activityName);
				statement.setBinaryStream(2, fis, (int) file.length());
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
	
	public boolean deleteReply(String activityName) {
		assert this.student != null : "Student is null: DAOStudent.deleteReply()";
		assert this.isActive() : "Student is not active: DAOStudent.deleteReply()";
		assert activityName != null : "Activity name is null: DAOStudent.deleteReply()";
		String query = "UPDATE Actividad SET documento = null, fechaEntrega = null " +
			"WHERE idPracticante = ? AND titulo = ?";
		String[] values = { this.getId(), activityName };
		return this.connection.sendQuery(query, values);
	}
	
	public boolean fillTableStudent(ObservableList<Student> listStudent) {
		boolean filled = false;
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM MiembroFEI INNER JOIN Practicante on MiembroFEI.idMiembro = Practicante.idMiembro";
		String[] names = { "nombres", "apellidos", "correoElectronico", "contrasena", "matricula" };
		String[][] select = this.connection.select(query, null, names);
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