package DAO;

import Connection.ConexionBD;
import Models.Admin;

public class DAOAdmin {
	private final Admin admin;
	private final ConexionBD connection = new ConexionBD();
	
	public DAOAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public boolean isRegistered() {
		assert this.admin != null;
		String query = "SELECT COUNT(Administrador.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"WHERE correoElectronico = ? AND contrasena = ?";
		String[] values = {this.admin.getEmail(), this.admin.getContrasena()};
		String[] responses = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, responses);
		return results != null && results[0][0].equals("1");
	}
	
	public boolean login() {
		assert this.admin != null : "Admin is null: DAOAdmin.login()";
		assert this.admin.estaCompleto() : "Admin is incomplete";
		
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"ON MiembroFEI.idMiembro = Administrador.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] values = {this.admin.getEmail(), this.admin.getContrasena()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, columns);
		return results != null && results[0][0].equals("1");
	}
}
