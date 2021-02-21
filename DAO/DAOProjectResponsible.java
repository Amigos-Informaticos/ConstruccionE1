package DAO;

import Connection.ConexionBD;
import Models.ProjectResponsible;

public class DAOProjectResponsible {
	private final ProjectResponsible projectResponsible;
	private final ConexionBD connection = new ConexionBD();
	
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
		
		String query = "INSERT INTO Responsable " +
			"(correoElectronico, nombres, apellidos, estaActivo, organizacion, cargo) " +
			"VALUES (?, ?, ?, 1, ?, ?)";
		String[] values = {
			this.projectResponsible.getEmail(),
			this.projectResponsible.getNames(),
			this.projectResponsible.getLastNames(),
			this.projectResponsible.getOrganization().getId(),
			this.projectResponsible.getPosition()
		};
		return this.connection.executar(query, values);
	}
	
	public boolean isRegistered() {
		String query = "SELECT COUNT (correoElectronico) AS TOTAL FROM Responsable " +
			"WHERE correoElectronico = ?";
		String[] values = {this.projectResponsible.getEmail()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	public boolean delete() {
		boolean deleted = false;
		if (this.projectResponsible != null && this.isRegistered()) {
			String query = "UPDATE Responsable SET estaActivo = WHERE correoElectronico = ?";
			String[] values = {this.projectResponsible.getEmail()};
			deleted = this.connection.executar(query, values);
		}
		return deleted;
	}
	
	public boolean isActive() {
		boolean isActive = false;
		if (this.isRegistered() &&
			this.projectResponsible != null &&
			this.projectResponsible.getEmail() != null) {
			String query = "SELECT estaActivo FROM Responsable WHERE correoElectronico = ?";
			String[] values = {this.projectResponsible.getEmail()};
			String[] names = {"estaActivo"};
			String[][] results = this.connection.seleccionar(query, values, names);
			isActive = results != null && results[0][0].equals("1");
		}
		return isActive;
	}
	
	public boolean reactivate() {
		boolean reactivated = false;
		if (this.projectResponsible != null && this.isRegistered() && this.isActive()) {
			String query = "UPDATE Responsable SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] values = {this.projectResponsible.getEmail()};
			reactivated = this.connection.executar(query, values);
		}
		return reactivated;
	}
	
	public static ProjectResponsible get(String responsibleEmail) {
		ConexionBD connection = new ConexionBD();
		ProjectResponsible responsible = new ProjectResponsible();
		responsible.setEmail(responsibleEmail);
		if (new DAOProjectResponsible(responsible).isRegistered()) {
			String query = "SELECT nombres, apellidos, cargo, organizacion FROM Responsable " +
				"WHERE correoElectronico = ?";
			String[] values = {responsibleEmail};
			String[] columns = {"nombres", "apellidos", "cargo", "organizacion"};
			String[][] responses = connection.seleccionar(query, values, columns);
			if (responses != null) {
				responsible.setNames(responses[0][1]);
				responsible.setLastNames(responses[0][1]);
				responsible.setPosition(responses[0][2]);
				responsible.setOrganization(DAOrganization.getByName(responses[0][3]));
			}
		}
		return responsible;
	}
}
