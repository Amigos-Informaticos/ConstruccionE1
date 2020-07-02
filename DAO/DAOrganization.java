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
		assert this.organization.isComplete() :
			"Organization is incomplete: DAOrganization.signUp()";
		String query;
		String[] values;
		if (!this.isRegistered()) {
			query = "INSERT INTO Organizacion (nombre, estaActivo, idSector) VALUES (?, 1, ?)";
			values = new String[]{ this.organization.getName(), this.getIdSector() };
		} else {
			query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
			values = new String[]{ this.organization.getName() };
		}
		return this.connection.sendQuery(query, values) && this.registerAddress();
	}
	
	@Override
	public boolean isRegistered() {
		String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion " +
			"WHERE nombre = ? AND estaActivo = 1";
		String[] values = { this.organization.getName() };
		String[] names = { "TOTAL" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean delete() {
		assert this.organization != null : "Organization is null: DAOrganization.delete()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.delete()";
		assert this.isActive() : "Organization is not active: DAOrganization.delete()";
		String query = "UPDATE Organizacion SET estaActivo = 0 WHERE nombre = ?";
		String[] values = { this.organization.getName() };
		return this.connection.sendQuery(query, values);
	}
	
	@Override
	public boolean isActive() {
		assert this.organization != null : "Organization is null: DAOrganization.isActive()";
		assert this.organization.getName() != null :
			"Organization's name is null: DAOrganization.isActive()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.isActive()";
		String query = "SELECT estaActivo FROM Organizacion WHERE nombre = ?";
		String[] values = { this.organization.getName() };
		String[] names = { "estaActivo" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean reactivate() {
		assert this.organization != null : "Organization is null: DAOrganization.reactivate()";
		assert this.isRegistered() : "Organization is not registered: DAOrganization.reactivate()";
		assert this.isActive() : "Organization is not active: DAOrganization.reactivate()";
		String query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
		String[] values = { this.organization.getName() };
		return this.connection.sendQuery(query, values);
	}
	
	public String getId() {
		String query = "SELECT idOrganizacion FROM Organizacion WHERE nombre = ?";
		String[] values = { organization.getName() };
		String[] names = { "idOrganizacion" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public boolean signUpTellephoneNumber() {
		String id = this.getId();
		String query = "INSERT INTO TelefonoOrganizacion (idOrganizacion, telefono) " +
			"VALUES ";
		for (int i = 0; i < this.organization.getPhoneNumber().length; i++) {
			query += "(" + id + ", ?)";
			if (i < this.organization.getPhoneNumber().length - 1) {
				query += ", ";
			}
		}
		return this.connection.sendQuery(query, this.organization.getPhoneNumber());
	}
	
	public boolean registerAddress() {
		assert !this.organization.getAddress().isEmpty() :
			"Address is empty: DAOrganization.registerAddress()";
		String query = "INSERT INTO Direccion (idOrganizacion, calle, numero, colonia, localidad) " +
			"VALUES (?, ?, ?, ?, ?)";
		String[] values = {
			this.getId(),
			this.organization.getAddress().get("street"),
			this.organization.getAddress().get("number"),
			this.organization.getAddress().get("colony"),
			this.organization.getAddress().get("locality")
		};
		return this.connection.sendQuery(query, values);
	}
	
	public boolean registerSector(String sector) {
		assert sector != null : "Sector is null: DAOrganization.registerSector()";
		boolean registered;
		if (!this.isSectorRegistered(sector)) {
			String query = "INSERT INTO Sector (sector) VALUES (?)";
			String[] values = { sector };
			registered = this.connection.sendQuery(query, values);
		} else {
			registered = false;
		}
		return registered;
	}
	
	public boolean isSectorRegistered(String sector) {
		assert sector != null : "Sector is null: DAOrganization.isSectorRegistered()";
		String query = "SELECT COUNT(sector) AS TOTAL FROM Sector WHERE sector = ?";
		String[] values = { sector };
		String[] columns = { "TOTAL" };
		return this.connection.select(query, values, columns)[0][0].equals("1");
	}
	
	public String getIdSector() {
		String query = "SELECT idSector FROM Sector WHERE sector = ?";
		String[] values = { this.organization.getSector() };
		String[] names = { "idSector" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public boolean fillOrganizationNames(ObservableList<String> listOrganization) {
		boolean filled = false;
		String query = "SELECT nombre FROM Organizacion WHERE estaActivo = 1";
		for (String[] nombre: this.connection.select(query, null, new String[]{ "nombre" })) {
			listOrganization.add(nombre[0]);
			filled = true;
		}
		return filled;
	}
	
	public boolean fillSector(ObservableList<String> listSector) {
		boolean filled = false;
		String query = "SELECT sector FROM Sector";
		for (String[] sector: this.connection.select(query, null, new String[]{ "sector" })) {
			listSector.add(sector[0]);
			filled = true;
		}
		return filled;
	}
	
	public boolean fillTableOrganization(ObservableList<Organization> listOrganization) {
		boolean filled = false;
		String query =
			"SELECT nombre, calle, numero, colonia, localidad, telefono, sector " +
				"FROM Organizacion O INNER  JOIN Sector S on O.idSector = S.idSector " +
				"LEFT OUTER JOIN  Direccion D on O.idOrganizacion = D.idOrganizacion " +
				"LEFT OUTER JOIN TelefonoOrganizacion T on O.idOrganizacion = T.idOrganizacion;";
		String[] names = {
			"nombre",
			"calle",
			"numero",
			"colonia",
			"localidad",
			"telefono",
			"sector"
		};
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
}