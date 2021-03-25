package IDAO;

import Connection.ConexionBD;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface Turno {
	static void llenarTurno(ObservableList<String> listShift) {
		String query = "SELECT turno FROM Turno";
		for (String[] turno: new ConexionBD().seleccionar(query, null, new String[] {"turno"})) {
			listShift.add(turno[0]);
		}
	}
	
	String getTurno() throws SQLException;
}
