package DAO;

import Connection.DBConnection;
import Models.Shift;
import javafx.collections.ObservableList;
import org.junit.Test;
import tools.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOShift {
    private Shift shift;
    private DBConnection connection = new DBConnection();

    public DAOShift(Shift shift){
        this.shift = shift;
    }

    public boolean fillShift(ObservableList<String> listShift){
        String query = "SELECT turno FROM Turno";
        boolean filled = false;
        try{
            this.connection.openConnection();
            Connection con = this.connection.getConnection();
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                listShift.add(
                        result.getString("turno")
                );
                filled = true;
            }
            con.close();
        }catch(SQLException eSQL){
            new Logger().log(eSQL.getMessage());
        }
        return filled;
    }
}
