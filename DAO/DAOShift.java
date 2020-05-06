package DAO;

import Connection.DBConnection;
import Models.Shift;
import javafx.collections.ObservableList;

public class DAOShift {
	private Shift shift;
	private final DBConnection connection = new DBConnection();
	
	public DAOShift(Shift shift) {
		this.shift = shift;
	}
	
	public boolean fillShift(ObservableList<String> listShift) {
		boolean filled = false;
		String query = "SELECT turno FROM Turno";
		for (String[] turno: this.connection.select(query, null, new String[]{"turno"})) {
			listShift.add(turno[0]);
			filled = true;
		}
		return filled;
	}
}
