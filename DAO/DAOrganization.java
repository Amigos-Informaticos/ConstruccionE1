package DAO;

import Connection.DBConnection;
import IDAO.IDAOrganization;
import Models.Organization;
import javafx.collections.ObservableList;

public class DAOrganization implements IDAOrganization {
	private final Organization organization;
	private final DBConnection connection = new DBConnection();
	
	public DAOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@Override
	public boolean signUp() {
		assert this.organization != null : "Organization is null: DAOrganization.signUp()";
		String query;
		String[] values;
		if (!this.isRegistered()) {
			query = "INSERT INTO Organizacion (nombre, estaActivo, idSector) VALUES (?, 1, ?)";
			values = new String[]{this.organization.getName(),
					this.getIdSector(this.organization.getSector())};
		} else {
			query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
			values = new String[]{this.organization.getName()};
		}
		return this.connection.sendQuery(query, values);
	}
	
	@Override
	public boolean isRegistered() {
		String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion WHERE nombre = ?";
		String[] values = {this.organization.getName()};
		String[] names = {"TOTAL"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean delete() {
		assert this.organization != null : "Organization is null: DAOrganization.delete()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.delete()";
		assert this.isActive() : "Organization is not active: DAOrganization.delete()";
		String query = "UPDATE Organizacion SET estaActivo = 0 WHERE nombre = ?";
		String[] values = {this.organization.getName()};
		return this.connection.sendQuery(query, values);
	}
	
	@Override
	public boolean isActive() {
		assert this.organization != null : "Organization is null: DAOrganization.isActive()";
		assert this.organization.getName() != null :
			"Organization's name is null: DAOrganization.isActive()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.isActive()";
		String query = "SELECT estaActivo FROM Organizacion WHERE nombre = ?";
		String[] values = {this.organization.getName()};
		String[] names = {"estaActivo"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean reactivate() {
		assert this.organization != null : "Organization is null: DAOrganization.reactivate()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.reactivate()";
		assert this.isActive() : "Organization is not active: DAOrganization.reactivate()";
		String query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
		String[] values = {this.organization.getName()};
		return this.connection.sendQuery(query, values);
	}
	
	public boolean fillTableOrganization(ObservableList<Organization> listOrganization) {
		boolean filled = false;
		String query =
			"SELECT nombre, calle, numero, colonia, localidad, telefono, telefono2, sector " +
				"FROM Organizacion O INNER  JOIN Sector S on O.idSector = S.idSector " +
				"LEFT OUTER JOIN  Direccion D on O.idOrganizacion = D.idOrganizacion " +
				"LEFT OUTER JOIN TelefonoOrganizacion T on O.idOrganizacion = T.idOrganizacion;";
		String[] names = {"nombre",
							"calle",
							"numero",
							"colonia",
							"localidad",
							"telefono",
							"telefono2",
							"sector"};
		String[][] select = this.connection.select(query, null, names);
		for (int row = 0; row < select.length; row++) {
			listOrganization.add(new Organization(
			));
			if (!filled) {
				filled = true;
			}
		}
		return filled;
	}


	public boolean fillOrganizationNames(ObservableList<String> listOrganization) {
		boolean filled = false;
		String query = "SELECT nombre FROM Organizacion WHERE estaActivo = 1";
		for (String[] nombre: this.connection.select(query, null, new String[]{"nombre"})) {
			listOrganization.add(nombre[0]);
			filled = true;
		}
		return filled;
	}

	public String getId(){
		String query = "SELECT idOrganizacion FROM Organizacion WHERE nombre = ?";
		String[] values = {organization.getName()};
		String[] names ={"idOrganizacion"};
		return this.connection.select(query, values, names)[0][0];
	}


	public boolean signUpTellephoneNumber(String telephoneNumber){
		String query = "INSERT INTO TelefonoOrganizacion " +
						"(telefono, idOrganizacion) " +
						"VALUES (?,?)";
		String[] values = {telephoneNumber, this.getId()};
		return this.connection.sendQuery(query,values);
	}

	public boolean fillSector(ObservableList<String> listSector) {
		boolean filled = false;
		String query = "SELECT sector FROM Sector";
		for (String[] sector: this.connection.select(query, null, new String[]{"sector"})) {
			listSector.add(sector[0]);
			filled = true;
		}
		return filled;
	}

	public String getIdSector(String sector){
		String query = "SELECT idSector FROM Sector WHERE sector = ?";
		String[] values = {sector};
		String[] names ={"idSector"};
		return this.connection.select(query, values, names)[0][0];
	}
}