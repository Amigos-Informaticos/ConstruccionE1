package DAO;

import Connection.DBConnection;
import Models.Admin;
import Models.Professor;
import javafx.collections.ObservableList;

public class DAOAdmin {
	private final Admin admin;
	private final DBConnection connection = new DBConnection();
	
	public DAOAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public boolean fillTableProfessor(ObservableList<Professor> listProfessor) {
		boolean filled = false;
		String query = "SELECT nombres, apellidos, correoElectronico, noPersonal, Turno.turno " +
			"FROM Usuario INNER JOIN Profesor ON Usuario.idUsuario = Profesor.idUsuario " +
			"INNER JOIN Turno on Profesor.turno = Turno.idTurno WHERE status = 1";
		String[] names = {"nombres", "apellidos", "correoElectronico", "noPersonal", "turno"};
		String[][] select = this.connection.select(query, null, names);
		for (String[] selection: select) {
			listProfessor.add(new Professor(
					selection[0],
					selection[1],
					selection[2],
					"NotNullFillList",
					selection[3],
					selection[4]
				)
			);
			if (!filled) {
				filled = true;
			}
		}
		return filled;
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
