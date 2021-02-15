package DAO;

import Connection.DBConnection;
import Models.Admin;

public class DAOAdmin {
	private final Admin admin;
	private final DBConnection connection = new DBConnection();
	
	public DAOAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public boolean isRegistered() {
		assert this.admin != null;
		String query = "SELECT COUNT(Administrador.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"WHERE correoElectronico = ? AND contrasena = ?";
		String[] values = {this.admin.getEmail(), this.admin.getPassword()};
		String[] responses = {"TOTAL"};
		return this.connection.select(query, values, responses)[0][0].equals("1");
	}
	
	public boolean login() {
		assert this.admin != null : "Admin is null: DAOAdmin.login()";
		assert this.admin.isComplete() : "Admin is incomplete";
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Administrador " +
			"ON MiembroFEI.idMiembro = Administrador.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] values = {this.admin.getEmail(), this.admin.getPassword()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.select(query, values, columns);
		return results != null && results[0][0].equals("1");
	}
}
