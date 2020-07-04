package DAO;

import Connection.DBConnection;
import Models.Assignment;

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
	
	public boolean assignProject() {
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
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			query = "INSERT INTO Asignacion (idPracticante, idProyecto, profesorCalificador) " +
				"VALUES (?, ?, ?)";
			values = new String[]{ studentId, projectId, professorId };
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
}
