package DAO;

import Connection.DBConnection;
import Models.Admin;
import Models.Professor;
import javafx.collections.ObservableList;

public class DAOAdmin {
    private Admin admin;
    private DBConnection connection = new DBConnection();

    public DAOAdmin(Admin admin) {
        this.admin = admin;
    }

    public boolean fillTableProfessor(ObservableList<Professor> listProfessor) {
        boolean filled = false;
        String query = "SELECT nombres, apellidos, correoElectronico, noPersonal, Turno.turno FROM Usuario INNER JOIN " +
                "Profesor on Usuario.idUsuario = Profesor.idUsuario INNER JOIN Turno on Profesor.turno = Turno.idTurno";
        String values[] = null;
        String names[] = {"nombres", "apellidos", "correoElectronico", "noPersonal", "turno"};

        String[][] select = this.connection.select(query, values, names);
        int row = 0, col = 0;
        while(row<select.length){
            listProfessor.add(new Professor(
                            select[row][0],
                            select[row][1],
                            select[row][2],
                            null,
                            select[row][3],
                            select[row][4]
                    )
            );
            row++;
        }
        return filled;
    }
}
