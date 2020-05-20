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
		String query = "SELECT COUNT(Administrador.idUsuario) AS TOTAL " +
			"FROM Usuario INNER JOIN Administrador " +
			"WHERE correoElectronico = ? AND contrasena = ?";
		String[] values = {this.admin.getEmail(), this.admin.getPassword()};
		String[] responses = {"TOTAL"};
		return this.connection.select(query, values, responses)[0][0].equals("1");
	}
}
