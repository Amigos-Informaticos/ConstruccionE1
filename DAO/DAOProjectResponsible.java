package DAO;

import Connection.DBConnection;
import Models.ProjectResponsible;

public class DAOProjectResponsible {
	private final ProjectResponsible projectResponsible;
	private final DBConnection connection = new DBConnection();
	
	public DAOProjectResponsible(ProjectResponsible projectResponsible) {
		this.projectResponsible = projectResponsible;
	}
	
	public boolean signUp() {
		assert this.projectResponsible != null :
			"Responsible is null: DAOProjectResponsible.signUp()";
		assert this.projectResponsible.isComplete() :
			"Responsible is not complete: DAOProjectResponsible.signUp()";
		assert this.projectResponsible.getOrganization().isComplete() :
			"Responsible organization is not complete: DAOProjectResponsible.signUp()";
		String query =
			"INSERT INTO Responsable " +
				"(correoElectronico, nombres, apellidos, estaActivo, organizacion) " +
				"VALUES (?, ?, ?, 1, ?)";
		String[] values = {
			this.projectResponsible.getEmail(),
			this.projectResponsible.getNames(),
			this.projectResponsible.getLastNames(),
			this.projectResponsible.getOrganization().getId()
		};
		return this.connection.sendQuery(query, values);
	}
	
	public boolean isRegistered() {
		String query = "SELECT COUNT (correoElectronico) AS TOTAL FROM Responsable " +
			"WHERE correoElectronico = ?";
		String[] values = { this.projectResponsible.getEmail() };
		String[] names = { "TOTAL" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public boolean delete() {
		boolean deleted = false;
		if (this.projectResponsible != null && this.isRegistered()) {
			String query = "UPDATE Responsable SET estaActivo = 0 WHERE correoElectronico = ?";
			String[] values = { this.projectResponsible.getEmail() };
			if (this.connection.sendQuery(query, values)) {
				deleted = true;
			}
		}
		return deleted;
	}
	
	public boolean isActive() {
		boolean isActive = false;
		if (this.isRegistered() &&
			this.projectResponsible != null &&
			this.projectResponsible.getEmail() != null) {
			String query = "SELECT estaActivo FROM Responsable WHERE correoElectronico = ?";
			String[] values = { this.projectResponsible.getEmail() };
			String[] names = { "status" };
			isActive = this.connection.select(query, values, names)[0][0].equals("");
		}
		return isActive;
	}
	
	public boolean reactive() {
		boolean reactivated = false;
		if (this.projectResponsible != null && this.isRegistered() && this.isActive()) {
			String query = "UPDATE Responsable SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] values = { this.projectResponsible.getEmail() };
			reactivated = this.connection.sendQuery(query, values);
		}
		return reactivated;
	}
	
	public static ProjectResponsible get(String responsibleEmail) {
		DBConnection connection = new DBConnection();
		ProjectResponsible responsible = new ProjectResponsible();
		responsible.setEmail(responsibleEmail);
		if (new DAOProjectResponsible(responsible).isRegistered()) {
			String query = "SELECT nombres, apellidos, cargo, organizacion FROM Responsable " +
				"WHERE correoElectronico = ?";
			String[] values = { responsibleEmail };
			String[] columns = { "nombres", "apellidos", "cargo", "organizacion" };
			String[] responses = connection.select(query, values, columns)[0];
			responsible.setNames(responses[0]);
			responsible.setLastNames(responses[1]);
			responsible.setPosition(responses[2]);
			responsible.setOrganization(DAOrganization.getByName(responses[3]));
		}
		return responsible;
	}
}
