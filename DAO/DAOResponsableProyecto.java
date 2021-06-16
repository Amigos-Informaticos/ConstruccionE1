package DAO;

import Connection.ConexionBD;
import Models.ResponsableProyecto;

import java.sql.SQLException;

public class DAOResponsableProyecto {
	private final ResponsableProyecto responsableProyecto;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOResponsableProyecto(ResponsableProyecto responsableProyecto) {
		this.responsableProyecto = responsableProyecto;
	}
	
	public boolean registrarse() throws SQLException {
		assert this.responsableProyecto != null :
			"Responable es nulo: DAOResponsableProyecto.registrarse()";
		assert this.responsableProyecto.estaCompleto() :
			"Responsable incompleto: DAOResponsableProyecto.registrarse()";
		assert this.responsableProyecto.getOrganizacion().estaCompleto() :
			"Organizacion del responsable incompleta: DAOResponsableProyecto.registrarse()";
		
		String query = "INSERT INTO Responsable " +
			"(correoElectronico, nombres, apellidos, estaActivo, organizacion, cargo) " +
			"VALUES (?, ?, ?, 1, ?, ?)";
		String[] valores = {
			this.responsableProyecto.getEmail(),
			this.responsableProyecto.getNombres(),
			this.responsableProyecto.getApellidos(),
			this.responsableProyecto.getOrganizacion().getId(),
			this.responsableProyecto.getPosicion()
		};
		return this.conexion.ejecutar(query, valores);
	}
	
	public boolean estaRegistrado() throws SQLException {
		String query = "SELECT COUNT (correoElectronico) AS TOTAL FROM Responsable " +
			"WHERE correoElectronico = ?";
		String[] valores = {this.responsableProyecto.getEmail()};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public boolean eliminar() throws SQLException {
		boolean eliminado = false;
		if (this.responsableProyecto != null && this.estaRegistrado()) {
			String query = "UPDATE Responsable SET estaActivo = WHERE correoElectronico = ?";
			String[] valores = {this.responsableProyecto.getEmail()};
			eliminado = this.conexion.ejecutar(query, valores);
		}
		return eliminado;
	}
	
	public boolean estaActivo() throws SQLException {
		boolean estaActivo = false;
		if (this.estaRegistrado() &&
			this.responsableProyecto != null &&
			this.responsableProyecto.getEmail() != null) {
			String query = "SELECT estaActivo FROM Responsable WHERE correoElectronico = ?";
			String[] valores = {this.responsableProyecto.getEmail()};
			String[] columnas = {"estaActivo"};
			String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
			estaActivo = resultados != null && resultados[0][0].equals("1");
		}
		return estaActivo;
	}
	
	public boolean reactivar() throws SQLException {
		boolean reactivado = false;
		if (this.responsableProyecto != null && this.estaRegistrado() && this.estaActivo()) {
			String query = "UPDATE Responsable SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] valores = {this.responsableProyecto.getEmail()};
			reactivado = this.conexion.ejecutar(query, valores);
		}
		return reactivado;
	}
	
	public static ResponsableProyecto get(String responsibleEmail) throws SQLException {
		ConexionBD conexion = new ConexionBD();
		ResponsableProyecto responsable = new ResponsableProyecto();
		responsable.setEmail(responsibleEmail);
		if (new DAOResponsableProyecto(responsable).estaRegistrado()) {
			String query = "SELECT nombres, apellidos, cargo, organizacion FROM Responsable " +
				"WHERE correoElectronico = ?";
			String[] valores = {responsibleEmail};
			String[] columnas = {"nombres", "apellidos", "cargo", "organizacion"};
			String[][] respuestas = conexion.seleccionar(query, valores, columnas);
			if (respuestas != null) {
				responsable.setNombre(respuestas[0][0]);
				responsable.setApellido(respuestas[0][1]);
				responsable.setPosicion(respuestas[0][2]);
				responsable.setOrganizacion(DAOOrganizacion.obtenerPorNombre(respuestas[0][3]));
			}
		}
		return responsable;
	}
}
