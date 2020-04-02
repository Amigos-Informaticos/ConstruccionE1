package DAO;

import Models.Usuario;

public class DAOProfesor extends DAOUsuario {
    private String noPersonal;
    private int turno;

    public DAOProfesor(String nombres, String apellidos, String correoElectronico, String contrasena, String noPersonal,
                       int turno) {
        super(nombres, apellidos, correoElectronico, contrasena);
        this.noPersonal = noPersonal;
        this.turno = turno;
    }

    public DAOProfesor(Profesor profesor) {
        super(profesor);
        this.noPersonal = profesor.getNoPersonal();
        this.turno = profesor.getTurno();
    }

    public int logIn() {
        int status = super.logIn();
        if (status == 1) {
            String query = "SELECT COUNT(Profesor.idUsuario) AS TOTAL FROM Profesor " +
                    "INNER JOIN Usuario ON Profesor.idUsuario = Usuario.idUsuario " +
                    "WHERE Usuario.correoElectronico = ?";
            String[] values = {this.getCorreoElectronico()};
            String[] names = {"TOTAL"};
            if (!this.connection.select(query, values, names)[0][0].equals("1")) {
                status = 5;
            }
        }
        return status;
    }

    public int signUp() {
        int status = super.signUp();
        if (status == 1) {
            String query = "INSERT INTO Profesor (idUsuario, fechaRegistro, noPersonal," +
                    " turno) VALUES ((SELECT idUsuario FROM Usuario WHERE correoElectronico" +
                    " = ?), (SELECT CONVERT(VARCHAR(10), getdate(), 103), " +
                    "?, ?);";
            String[] values = {this.getCorreoElectronico(), this.noPersonal, String.valueOf(this.turno)};
            if (!this.connection.preparedQuery(query, values)) {
                status = 5;
            }
        }
        return status;
    }
}
