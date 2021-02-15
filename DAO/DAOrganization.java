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
		boolean signed;
		String query;
		String[] values;
		if (!this.isRegistered()) {
			String sector = this.organization.getSector();
			if (!this.isSectorRegistered(sector)) {
				this.registerSector(sector);
			}
			query = "INSERT INTO Organizacion (nombre, estaActivo, idSector) VALUES (?, 1, ?)";
			values = new String[] {this.organization.getName(), this.getIdSector()};
			signed = this.connection.sendQuery(query, values) &&
				this.registerAddress() &&
				this.signUpTellephoneNumber();
		} else {
			query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
			values = new String[] {this.organization.getName()};
			signed = this.connection.sendQuery(query, values);
		}
		return signed;
	}
	
	@Override
	public boolean isRegistered() {
		assert this.organization.getName() != null :
			"Organization's name is null: DAOrganization.isRegistered()";
		String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion " +
			"WHERE nombre = ?";
		String[] values = {this.organization.getName()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.select(query, values, names);
		return results != null && results[0][0].equals("1");
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
		String[][] results = this.connection.select(query, values, names);
		return results != null && results[0][0].equals("1");
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
	
	public String getId() {
		String query = "SELECT idOrganizacion FROM Organizacion WHERE nombre = ?";
		String[] values = {organization.getName()};
		String[] names = {"idOrganizacion"};
		String[][] results = this.connection.select(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public boolean signUpTellephoneNumber() {
		String id = this.getId();
		String query = "INSERT INTO TelefonoOrganizacion (idOrganizacion, telefono) VALUES ";
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
			String[] values = {sector};
			registered = this.connection.sendQuery(query, values);
		} else {
			registered = false;
		}
		return registered;
	}
	
	public boolean isSectorRegistered(String sector) {
		assert sector != null : "Sector is null: DAOrganization.isSectorRegistered()";
		String query = "SELECT COUNT(sector) AS TOTAL FROM Sector WHERE sector = ?";
		String[] values = {sector};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.select(query, values, columns);
		return results != null && results[0][0].equals("1");
	}
	
	public String getIdSector() {
		String query = "SELECT idSector FROM Sector WHERE sector = ?";
		String[] values = {this.organization.getSector()};
		String[] names = {"idSector"};
		String[][] results = this.connection.select(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public boolean fillOrganizationNames(ObservableList<String> listOrganization) {
		boolean filled = false;
		String query = "SELECT nombre FROM Organizacion WHERE estaActivo = 1";
		for (String[] name: this.connection.select(query, null, new String[] {"nombre"})) {
			listOrganization.add(name[0]);
			filled = true;
		}
		return filled;
	}
	
	public boolean fillSector(ObservableList<String> listSector) {
		boolean filled = false;
		String query = "SELECT sector FROM Sector";
		for (String[] sector: this.connection.select(query, null, new String[] {"sector"})) {
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
			Organization organization = new Organization();
			organization.setName(select[row][0]);
			organization.setAddress(select[row][1],
				select[row][2],
				select[row][3],
				select[row][4]);
			organization.setSector(select[row][6]);
			listOrganization.add(organization);
			if (!filled) {
				filled = true;
			}
		}
		return filled;
	}
	
	public static Organization getByName(String organizationName) {
		assert organizationName != null : "Name is null: DAOrganization.getByName()";
		DBConnection connection = new DBConnection();
		Organization organization = new Organization();
		organization.setName(organizationName);
		if (new DAOrganization(organization).isRegistered()) {
			String query = "SELECT Sector.sector, calle, numero, colonia, localidad " +
				"FROM Organizacion INNER JOIN Sector ON Organizacion.idSector = Sector.idSector " +
				"INNER JOIN Direccion ON Organizacion.idOrganizacion = Direccion.idOrganizacion " +
				"WHERE Organizacion.nombre = ? AND Organizacion.estaActivo = 1";
			String[] values = {organizationName};
			String[] columns =
				new String[] {"sector", "calle", "numero", "colonia", "localidad"};
			String[] responses = connection.select(query, values, columns)[0];
			organization.setSector(responses[0]);
			organization.setAddress(
				responses[1],
				responses[2],
				responses[3],
				responses[4]
			);
			query = "SELECT telefono FROM TelefonoOrganizacion WHERE idOrganizacion = ?";
			values = new String[] {new DAOrganization(organization).getId()};
			columns = new String[] {"telefono"};
			responses = connection.select(query, values, columns)[0];
			for (String number: responses) {
				organization.addPhoneNumber(number);
			}
		}
		return organization;
	}
	
	public static Organization getNameById(String idOrganization) {
		Organization organization = null;
		String query = "SELECT nombre FROM Organizacion WHERE idOrganizacion = ?";
		String[] values = {idOrganization};
		String[] names = {"nombre"};
		DBConnection connection = new DBConnection();
		String[][] results = connection.select(query, values, names);
		if (results != null) {
			organization = new Organization();
			organization.setName(connection.select(query, values, names)[0][0]);
		}
		return organization;
	}
	
}