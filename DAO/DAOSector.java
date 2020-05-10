package DAO;

import Connection.DBConnection;
import Models.Sector;
import javafx.collections.ObservableList;

public class DAOSector {
    private Sector sector;
    private final DBConnection connection = new DBConnection();

    public DAOSector(Sector sector) {
        this.sector = sector;
    }

    public boolean fillSector(ObservableList<String> listSector) {
        boolean filled = false;
        String query = "SELECT sector FROM Sector";
        for (String[] sector: this.connection.select(query, null, new String[]{"sector"})) {
            listSector.add(sector[0]);
            filled = true;
        }
        return filled;
    }

    public String getId(){
        String query = "SELECT idSector FROM Sector WHERE sector = ?";
        String[] values = {sector.getName()};
        String[] names ={"idSector"};
        return this.connection.select(query, values, names)[0][0];
    }
}
