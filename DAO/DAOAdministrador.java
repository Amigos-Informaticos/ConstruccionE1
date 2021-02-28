package DAO;

import Connection.ConexionBD;
import Models.Administrador;

public class DAOAdministrador {
	private final Administrador administrador;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	
	public boolean estaRegistrado() {
		assert this.administrador != null :
			"Administrador es nulo: DAOAdministrador.estaRegistrado()";
		String query = "SELECT COUNT(Administrador.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"WHERE correoElectronico = ? AND contrasena = ?";
		String[] valores = {this.administrador.getEmail(), this.administrador.getContrasena()};
		String[] respuestas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, respuestas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public boolean iniciarSesion() {
		assert this.administrador != null :
			"Administrador es nulo: DAOAdministrador.iniciarSesion()";
		assert this.administrador.estaCompleto() :
			"Administrador incompleto: DAOAdministrador.iniciarSesion()";
		
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"ON MiembroFEI.idMiembro = Administrador.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] valores = {this.administrador.getEmail(), this.administrador.getContrasena()};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
}
