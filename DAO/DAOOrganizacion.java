package DAO;

import Connection.ConexionBD;
import IDAO.IDAOOrganizacion;
import Models.Organizacion;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class DAOOrganizacion implements IDAOOrganizacion {
	private final Organizacion organizacion;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	private boolean actualizarDireccion() throws SQLException {
		assert !this.organizacion.getDireccion().isEmpty() :
			"Direccion esta vacia: DAOOrganizacion.actualizarDireccion()";
		
		String query = "UPDATE Direccion SET calle = ?, numero = ?, colonia = ?, localidad = ? WHERE idOrganizacion = ?;";
		String[] valores = {
			this.organizacion.getDireccion().get("calle"),
			this.organizacion.getDireccion().get("numero"),
			this.organizacion.getDireccion().get("colonia"),
			this.organizacion.getDireccion().get("localidad"),
			this.getId()
		};
		return this.conexion.ejecutar(query, valores);
	}
	
	@Override
	public boolean registrar() throws SQLException {
		assert this.organizacion != null : "Organizacion es nula: DAOOrganizacion.registrar()";
		assert this.organizacion.estaCompleto() :
			"Organizacion esta incompleta: DAOOrganizacion.registrar()";
		boolean registrado;
		String query;
		String[] valores;
		query = "INSERT INTO Organizacion (nombre, estaActivo, idSector, telefono) " +
			"VALUES (?, 1, ?, ?)";
		valores = new String[] {
			this.organizacion.getNombre(),
			this.getIdSector(),
			this.organizacion.getTelefono()};
		registrado = this.conexion.ejecutar(query, valores) && this.registrarDireccion();
		return registrado;
	}
	
	public boolean actualizar(String nombreAntiguo) throws SQLException {
		assert this.organizacion != null : "Organizacion es nula: DAOOrganizacion.actualizar()";
		assert this.organizacion.estaCompleto() : "Organizacion esta incompleta: DAOOrganizacion.actualizar()";
		boolean actualizada = false;
		String query;
		String[] valores;
		String sector = this.organizacion.getSector();
		if (!this.estaRegistradoSector(sector)) {
			this.registrarSector(sector);
		}
		query = "UPDATE Organizacion SET nombre = ?, telefono = ?, idSector = ? WHERE nombre = ?;";
		valores = new String[] {
			this.organizacion.getNombre(),
			this.organizacion.getTelefono(),
			this.getIdSector(),
			nombreAntiguo
		};
		actualizada = this.conexion.ejecutar(query, valores) && this.actualizarDireccion();
		return actualizada;
	}
	
	@Override
	public boolean estaRegistrado() throws SQLException {
		assert this.organizacion.getNombre() != null :
			"Nombre organizacion es nulo: DAOOrganizacion.estaRegistrado()";
		
		String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion " +
			"WHERE nombre = ?";
		String[] valores = {this.organizacion.getNombre()};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	@Override
	public boolean eliminar() throws SQLException {
		assert this.organizacion != null : "Organizacion es nula: DAOOrganizacion.eliminar()";
		assert this.estaRegistrado() : "Organizacion no esta registrada: DAOOrganizacion.eliminar()";
		assert this.estaActivo() : "Organizacion no esta activa: DAOOrganizacion.eliminar()";
		
		boolean eliminado = false;
		String query = "UPDATE Organizacion SET estaActivo = 0 WHERE nombre = ?";
		String[] valores = {this.organizacion.getNombre()};
		if (this.conexion.ejecutar(query, valores)) {
			query = "UPDATE Proyecto SET estaActivo = 0 WHERE idOrganizacion = ?";
			valores = new String[] {this.getId()};
			eliminado = this.conexion.ejecutar(query, valores);
			eliminado = true;
		}
		return eliminado;
	}
	
	@Override
	public boolean estaActivo() throws SQLException {
		assert this.organizacion != null : "Organzacion es nula: DAOOrganizacion.estaActivo()";
		assert this.organizacion.getNombre() != null :
			"Nombre organizacion es nulo: DAOOrganizacion.estaActivo()";
		assert this.estaRegistrado() : "Organizacion no registrada: DAOOrganizacion.estaActivo()";
		
		String query = "SELECT estaActivo FROM Organizacion WHERE nombre = ?";
		String[] valores = {this.organizacion.getNombre()};
		String[] columnas = {"estaActivo"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	@Override
	public boolean reactivar() throws SQLException {
		assert this.organizacion != null : "Organizacion es nula: DAOOrganizacion.reactivar()";
		assert this.estaRegistrado() : "Organizacion no registrada: DAOOrganizacion.reactivar()";
		assert this.estaActivo() : "Organizacion inactiva: DAOOrganizacion.reactivar()";
		
		String query = "UPDATE Organizacion SET estaActivo = 1 WHERE nombre = ?";
		String[] valores = {this.organizacion.getNombre()};
		return this.conexion.ejecutar(query, valores);
	}
	
	public String getId() throws SQLException {
		String query = "SELECT idOrganizacion FROM Organizacion WHERE nombre = ?";
		String[] valores = {organizacion.getNombre()};
		String[] columnas = {"idOrganizacion"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null ? resultados[0][0] : "";
	}
	
	public boolean registrarDireccion() throws SQLException {
		assert !this.organizacion.getDireccion().isEmpty() :
			"Direccion esta vacia: DAOOrganizacion.registrarDireccion()";
		
		String query = "INSERT INTO Direccion (idOrganizacion, calle, numero, colonia, localidad) " +
			"VALUES (?, ?, ?, ?, ?)";
		String[] valores = {
			this.getId(),
			this.organizacion.getDireccion().get("calle"),
			this.organizacion.getDireccion().get("numero"),
			this.organizacion.getDireccion().get("colonia"),
			this.organizacion.getDireccion().get("localidad")
		};
		return this.conexion.ejecutar(query, valores);
	}
	
	public boolean registrarSector(String sector) throws SQLException {
		assert sector != null : "Sector es nulo: DAOOrganizacion.registrarSector()";
		boolean registrado = false;
		if (!this.estaRegistradoSector(sector)) {
			String query = "INSERT INTO Sector (sector) VALUES (?)";
			String[] valores = {sector};
			registrado = this.conexion.ejecutar(query, valores);
		}
		return registrado;
	}
	
	public boolean estaRegistradoSector(String sector) throws SQLException {
		assert sector != null : "Sector es nulo: DAOOrganizacion.estaRegistradoSector()";
		
		String query = "SELECT COUNT(sector) AS TOTAL FROM Sector WHERE sector = ?";
		String[] valores = {sector};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public String getIdSector() throws SQLException {
		String query = "SELECT idSector FROM Sector WHERE sector = ?";
		String[] valores = {this.organizacion.getSector()};
		String[] columnas = {"idSector"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null ? resultados[0][0] : "";
	}
	
	public boolean llenarNombresOrganizaciones(ObservableList<String> listaOrganizaciones) throws SQLException {
		boolean lleno = false;
		String query = "SELECT nombre FROM Organizacion WHERE estaActivo = 1";
		String[] columnas = {"nombre"};
		String[][] resultados = this.conexion.seleccionar(query, null, columnas);
		if (resultados != null){
			for (String[] name: resultados) {
				listaOrganizaciones.add(name[0]);
				lleno = true;
			}
		}
		return lleno;
	}
	
	public boolean llenarNombresTodasOrganizaciones(ObservableList<String> listaOrganizaciones) throws SQLException {
		boolean lleno = false;
		String query = "SELECT nombre FROM Organizacion";
		String[] columnas = {"nombre"};
		String[][] resultados = this.conexion.seleccionar(query, null, columnas);
		for (String[] name: resultados) {
			listaOrganizaciones.add(name[0]);
			lleno = true;
		}
		return lleno;
	}
	
	public boolean llenarSector(ObservableList<String> listaSectores) throws SQLException {
		boolean lleno = false;
		String query = "SELECT sector FROM Sector";
		String[] columnas = {"sector"};
		String[][] resultados = this.conexion.seleccionar(query, null, columnas);
		for (String[] sector: resultados) {
			listaSectores.add(sector[0]);
			lleno = true;
		}
		return lleno;
	}
	
	public boolean llenarTablaOrganizacion(ObservableList<Organizacion> listaOrganizacion) throws SQLException {
		boolean lleno = false;
		String query =
			"SELECT nombre, calle, numero, colonia, localidad, telefono, sector " +
				"FROM Organizacion O INNER  JOIN Sector S on O.idSector = S.idSector " +
				"LEFT OUTER JOIN  Direccion D on O.idOrganizacion = D.idOrganizacion " +
				"WHERE estaActivo = 1;";
		String[] columnas = {
			"nombre",
			"calle",
			"numero",
			"colonia",
			"localidad",
			"telefono",
			"sector"
		};
		String[][] seleccionar = this.conexion.seleccionar(query, null, columnas);
		if (seleccionar != null){
			for (String[] strings: seleccionar) {
				Organizacion organizacion = new Organizacion();
				organizacion.setNombre(strings[0]);
				organizacion.setDireccion(strings[1],
						strings[2],
						strings[3],
						strings[4]);
				organizacion.setTelefono(strings[5]);
				organizacion.setSector(strings[6]);
				listaOrganizacion.add(organizacion);
		}
			if (!lleno) {
				lleno = true;
			}
		}
		return lleno;
	}
	
	public static Organizacion obtenerPorNombre(String nombre) throws SQLException {
		assert nombre != null : "Nombre es nulo: DAOOrganizacion.obtenerPorNombre()";
		
		ConexionBD connection = new ConexionBD();
		Organizacion organizacion = new Organizacion();
		organizacion.setNombre(nombre);
		if (new DAOOrganizacion(organizacion).estaRegistrado()) {
			String query = "SELECT Sector.sector, calle, numero, colonia, localidad, telefono " +
				"FROM Organizacion INNER JOIN Sector ON Organizacion.idSector = Sector.idSector " +
				"INNER JOIN Direccion ON Organizacion.idOrganizacion = Direccion.idOrganizacion " +
				"WHERE Organizacion.nombre = ? AND Organizacion.estaActivo = 1";
			String[] valores = {nombre};
			String[] columnas =
				new String[] {"sector", "calle", "numero", "colonia", "localidad", "telefono"};
			String[][] resultados = connection.seleccionar(query, valores, columnas);
			if (resultados != null && resultados.length > 0) {
				organizacion.setSector(resultados[0][0]);
				organizacion.setDireccion(
					resultados[0][1],
					resultados[0][2],
					resultados[0][3],
					resultados[0][4]
				);
				organizacion.setTelefono(resultados[0][5]);
			}
		}
		return organizacion;
	}
	
	public static Organizacion getNameById(String idOrganization) throws SQLException {
		Organizacion organizacion = null;
		String query = "SELECT nombre FROM Organizacion WHERE idOrganizacion = ?";
		String[] valores = {idOrganization};
		String[] columnas = {"nombre"};
		ConexionBD conexion = new ConexionBD();
		String[][] results = conexion.seleccionar(query, valores, columnas);
		if (results != null) {
			organizacion = new Organizacion();
			organizacion.setNombre(results[0][0]);
		}
		return organizacion;
	}
	
}