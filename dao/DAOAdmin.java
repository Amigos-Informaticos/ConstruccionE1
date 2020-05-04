package dao;

import connection.DBConnection;
import models.Admin;
import models.Professor;
import javafx.collections.ObservableList;
import tools.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DAOAdmin {
    private Admin admin;
    private final DBConnection connection = new DBConnection();

    public DAOAdmin(Admin admin) {
        this.admin = admin;
    }

    public boolean fillTableProfessor(ObservableList<Professor> listProfessor) {
        boolean filled = false;
        String query = "SELECT nombres, apellidos, correoElectronico, noPersonal, Turno.turno " +
                "FROM Usuario INNER JOIN Profesor ON Usuario.idUsuario = Profesor.idUsuario " +
                "INNER JOIN Turno on Profesor.turno = Turno.idTurno WHERE status = ?";
        String[] values = {"1"};
        String[] names = {"nombres", "apellidos", "correoElectronico", "noPersonal", "turno"};
        String[][] select = this.connection.select(query, values, names);
        for (String[] selection : select) {
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
        boolean isRegistered = false;
        assert this.admin != null;
        String query = "SELECT COUNT(Administrador.idUsuario) AS TOTAL " +
                "FROM Usuario INNER JOIN Administrador " +
                "WHERE correoElectronico = ? AND contrasena = ?";
        String[] values = {this.admin.getEmail(), this.admin.getPassword()};
        String[] responses = {"TOTAL"};
        isRegistered = this.connection.select(query, values, responses)[0][0].equals("1");
        return isRegistered;
    }
}
