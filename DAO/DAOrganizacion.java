package DAO;

import Connection.ConexionBD;
import IDAO.IDAOrganization;
import Models.Organizacion;
import javafx.collections.ObservableList;

public class DAOrganizacion implements IDAOrganization {
	private final Organizacion organizacion;
	private final ConexionBD connection = new ConexionBD();
	
	public DAOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	@Override
	public boolean registrar() {
		assert this.organizacion != null : "Organization is null: DAOrganization.signUp()";
		assert this.organizacion.estaCompleto() :
			"Organization is incomplete: DAOrganization.signUp()";
		boolean signed = false;
		String query;
		String[] values;
		if (!this.estaRegistrado()) {
			String sector = this.organizacion.getSector();
			if (!this.isSectorRegistered(sector)) {
				this.registerSector(sector);
			}
			query = "INSERT INTO Organizacion (nombre, estaActivo, idSector) VALUES (?, 1, ?)";
			values = new String[] {this.organizacion.getNombre(), this.getIdSector()};
			signed = this.connection.executar(query, values) &&
				this.registerAddress() &&
				this.signUpTelephoneNumber();
		} else {
			this.reactivar();
		}
		return signed;
	}
	
	@Override
	public boolean estaRegistrado() {
		assert this.organizacion.getNombre() != null :
			"Organization's name is null: DAOrganization.isRegistered()";
		String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion " +
			"WHERE nombre = ?";
		String[] values = {this.organizacion.getNombre()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean eliminar() {
		assert this.organizacion != null : "Organization is null: DAOrganization.delete()";
		assert this.estaRegistrado() : "Organization is not registered: DAOrganization.delete()";
		assert this.estaActivo() : "Organization is not active: DAOrganization.delete()";
		String query = "UPDATE Organizacion SET estaActivo = 0 WHERE nombre = ?";
		String[] values = {this.organizacion.getNombre()};
		return this.connection.executar(query, values);
	}
	
	@Override
	public boolean estaActivo() {
		assert this.organizacion != null : "Organization is null: DAOrganization.isActive()";
		assert this.organizacion.getNombre() != null :
			"Organization's name is null: DAOrganization.isActive()";
		assert this.estaRegistrado() : "Organization is not registered: DAOrganization.isActive()";
		String query = "SELECT estaActivo FROM Organizacion WHERE nombre = ?";
		String[] values = {this.organizacion.getNombre()};
		String[] names = {"estaActivo"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean reactivar() {
		assert this.organizacion != null : "Organization is null: DAOrganization.reactivate()";
		assert this.estaRegistrado() : "Organization is not registered: DAOrganization.reactivate()";
		String query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
		String[] values = {this.organizacion.getNombre()};
		return this.connection.executar(query, values);
	}
	
	public String getId() {
		String query = "SELECT idOrganizacion FROM Organizacion WHERE nombre = ?";
		String[] values = {organizacion.getNombre()};
		String[] names = {"idOrganizacion"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public boolean registerAddress() {
		assert !this.organizacion.getDireccion().isEmpty() :
			"Address is empty: DAOrganization.registerAddress()";
		String query = "INSERT INTO Direccion (idOrganizacion, calle, numero, colonia, localidad) " +
			"VALUES (?, ?, ?, ?, ?)";
		String[] values = {
			this.getId(),
			this.organizacion.getDireccion().get("street"),
			this.organizacion.getDireccion().get("number"),
			this.organizacion.getDireccion().get("colony"),
			this.organizacion.getDireccion().get("locality")
		};
		return this.connection.executar(query, values);
	}
	
	public boolean registerSector(String sector) {
		assert sector != null : "Sector is null: DAOrganization.registerSector()";
		boolean registered;
		if (!this.isSectorRegistered(sector)) {
			String query = "INSERT INTO Sector (sector) VALUES (?)";
			String[] values = {sector};
			registered = this.connection.executar(query, values);
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
		String[][] results = this.connection.seleccionar(query, values, columns);
		return results != null && results[0][0].equals("1");
	}
	
	public String getIdSector() {
		String query = "SELECT idSector FROM Sector WHERE sector = ?";
		String[] values = {this.organizacion.getSector()};
		String[] names = {"idSector"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public boolean llenarNombres(ObservableList<String> listOrganization) {
		boolean filled = false;
		String query = "SELECT nombre FROM Organizacion WHERE estaActivo = 1";
		for (String[] name: this.connection.seleccionar(query, null, new String[] {"nombre"})) {
			listOrganization.add(name[0]);
			filled = true;
		}
		return filled;
	}
	
	public boolean llenarSector(ObservableList<String> listSector) {
		boolean filled = false;
		String query = "SELECT sector FROM Sector";
		for (String[] sector: this.connection.seleccionar(query, null, new String[] {"sector"})) {
			listSector.add(sector[0]);
			filled = true;
		}
		return filled;
	}
	
	public boolean llenarTablaOrganizacion(ObservableList<Organizacion> listOrganizacion) {
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
		String[][] select = this.connection.seleccionar(query, null, names);
		for (String[] strings: select) {
			Organizacion organizacion = new Organizacion();
			organizacion.setNombre(strings[0]);
			organizacion.setDireccion(strings[1],
				strings[2],
				strings[3],
				strings[4]);
			organizacion.setSector(strings[6]);
			listOrganizacion.add(organizacion);
			if (!filled) {
				filled = true;
			}
		}
		return filled;
	}
	
	public static Organizacion obtenerPorNombre(String organizationName) {
		assert organizationName != null : "Name is null: DAOrganization.getByName()";
		ConexionBD connection = new ConexionBD();
		Organizacion organizacion = new Organizacion();
		organizacion.setNombre(organizationName);
		if (new DAOrganizacion(organizacion).estaRegistrado()) {
			String query = "SELECT Sector.sector, calle, numero, colonia, localidad " +
				"FROM Organizacion INNER JOIN Sector ON Organizacion.idSector = Sector.idSector " +
				"INNER JOIN Direccion ON Organizacion.idOrganizacion = Direccion.idOrganizacion " +
				"WHERE Organizacion.nombre = ? AND Organizacion.estaActivo = 1";
			String[] values = {organizationName};
			String[] columns =
				new String[] {"sector", "calle", "numero", "colonia", "localidad"};
			String[] responses = connection.seleccionar(query, values, columns)[0];
			organizacion.setSector(responses[0]);
			organizacion.setDireccion(
				responses[1],
				responses[2],
				responses[3],
				responses[4]
			);
			query = "SELECT telefono FROM TelefonoOrganizacion WHERE idOrganizacion = ?";
			values = new String[] {new DAOrganizacion(organizacion).getId()};
			columns = new String[] {"telefono"};
			responses = connection.seleccionar(query, values, columns)[0];
			for (String number: responses) {
				organizacion.addPhoneNumber(number);
			}
		}
		return organizacion;
	}
	
	public static Organizacion getNameById(String idOrganization) {
		Organizacion organizacion = null;
		String query = "SELECT nombre FROM Organizacion WHERE idOrganizacion = ?";
		String[] values = {idOrganization};
		String[] names = {"nombre"};
		ConexionBD connection = new ConexionBD();
		String[][] results = connection.seleccionar(query, values, names);
		if (results != null) {
			organizacion = new Organizacion();
			organizacion.setNombre(connection.seleccionar(query, values, names)[0][0]);
		}
		return organizacion;
	}
	
}