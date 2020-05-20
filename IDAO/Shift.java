package IDAO;

import Connection.DBConnection;
import javafx.collections.ObservableList;

public interface Shift {
	static void fillShift(ObservableList<String> listShift) {
		String query = "SELECT turno FROM Turno";
		for (String[] turno: new DBConnection().select(query, null, new String[]{"turno"})) {
			listShift.add(turno[0]);
		}
	}
	
	String getShift();
}
