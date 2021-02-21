package IDAO;

import Connection.ConexionBD;
import javafx.collections.ObservableList;

public interface Shift {
	static void fillShift(ObservableList<String> listShift) {
		String query = "SELECT turno FROM Turno";
		for (String[] turno: new ConexionBD().seleccionar(query, null, new String[] {"turno"})) {
			listShift.add(turno[0]);
		}
	}
	
	String getShift();
}
