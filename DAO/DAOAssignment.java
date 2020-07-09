package DAO;

import Connection.DBConnection;
import Models.Assignment;
import Models.Document;
import Models.Project;
import Models.Student;

public class DAOAssignment {
	private Assignment assignment;
	private final DBConnection connection = new DBConnection();
	
	public DAOAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	public Assignment getAssignment() {
		return assignment;
	}
	
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	public boolean assignProject(Document document) {
		boolean assigned;
		assert this.assignment.isComplete() :
			"Assignment is incomplete: DAOAssignment.assignProject()";
		String studentId = new DAOStudent(this.assignment.getStudent()).getId();
		String projectId = new DAOProject(this.assignment.getProject()).getId();
		String professorId = new DAOProfessor(this.assignment.getProfessor()).getId();
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion " +
			"WHERE idPracticante = ? AND idProyecto = ? AND estaActivo = 1";
		String[] values = { studentId, studentId };
		String[] columns = { "TOTAL" };
		String documentId = new DAODocument(document).getId();
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			query = "INSERT INTO Asignacion (idPracticante, idProyecto, profesorCalificador, " +
				"documentoAsignacion, estaActivo) " +
				"VALUES (?, ?, ?, ?, 1)";
			values = new String[]{
				studentId,
				projectId,
				professorId,
				documentId
			};
			assigned = this.connection.sendQuery(query, values);
		} else {
			assigned = false;
		}
		return assigned;
	}
	
	public boolean removeAssignment() {
		assert this.assignment.getStudent().isComplete() :
			"Student is incomplete: DAOAssignment.assignProject()";
		boolean removed;
		String studentId = new DAOStudent(this.assignment.getStudent()).getId();
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion " +
			"WHERE idPracticante = ? AND estaActivo = 1";
		String[] values = { studentId };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("1")) {
			query = "UPDATE Asignacion SET estaActivo = 0 WHERE idPracticante = ?";
			values = new String[]{ studentId };
			removed = this.connection.sendQuery(query, values);
		} else {
			removed = false;
		}
		return removed;
	}
	
	public static boolean saveRequest(Student student, Project project) {
		boolean saved;
		DBConnection connection = new DBConnection();
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM Solicitud " +
			"WHERE idMiembro = ? AND idProyecto = ? AND estaActiva = 1";
		String[] values = {
			new DAOStudent(student).getId(),
			new DAOProject(project).getId()
		};
		String[] columns = { "TOTAL" };
		if (connection.select(query, values, columns)[0][0].equals("0")) {
			query = "INSERT INTO Solicitud (idMiembro, idProyecto) " +
				"VALUES (?, ?)";
			saved = connection.sendQuery(query, values);
		} else {
			saved = false;
		}
		return saved;
	}
	
	public static Project[] requestedProjects(Student student) {
		Project[] projects;
		DBConnection connection = new DBConnection();
		String query = "SELECT nombre " +
			"FROM Proyecto INNER JOIN Solicitud ON Proyecto.idProyecto = Solicitud.idProyecto " +
			"WHERE Solicitud.idMiembro = ?";
		String[] values = { new DAOStudent(student).getId() };
		String[] columns = { "nombre" };
		String[][] responses = connection.select(query, values, columns);
		projects = new Project[responses.length];
		for (int i = 0; i < responses.length; i++) {
			projects[i] = DAOProject.getByName(responses[i][0]);
		}
		return projects;
	}
}
