package DAO;

import Connection.DBConnection;
import IDAO.IDAOProfesor;
import Models.Profesor;

public class DAOProfesor implements IDAOProfesor {
    private Profesor profesor;
    private DBConnection connection = new DBConnection();

    public DAOProfesor(Profesor profesor){
        this.profesor=profesor;
    }
    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean reactive() {
        boolean reactivated = false;
        if (this.profesor != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE Practicante SET status = 1 WHERE correoElectronico = ?";
                String[] values = {this.profesor.getCorreoElectronico()};
                if (this.connection.preparedQuery(query, values)) {
                    reactivated = true;
                }
            } else {
                reactivated = true;
            }
        }
        return reactivated;
    }

    @Override
    public boolean logIn() {
        boolean loggedIn = false;
        String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Profesor WHERE correoElectronico = ? " +
                "AND contrasena = ?";
        String[] values = {this.profesor.getCorreoElectronico(), this.profesor.getContrasena()};
        String[] names = {"TOTAL"};
        if (this.isRegistered()) {
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    @Override
    public boolean signUp() {
        boolean signedUp = false;
        if(this.profesor.isComplete()){
            String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status)" +
                    "VALUES (?, ?, ?, ?, ?)";
            String[] values = {this.profesor.getNombres(), this.profesor.getApellidos(),
                    this.profesor.getCorreoElectronico(), this.profesor.getContrasena(), "1"};
            if(this.connection.preparedQuery(query, values)) {
                query = "INSERT INTO Profesor (idUsuario, fechaRegistro, noPersonal, turno) VALUES " +
                        "((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), (SELECT CURRENT_DATE), ?, ?)";
                values = new String[]{this.profesor.getCorreoElectronico(), this.profesor.getNoPersonal(), String.valueOf(this.profesor.getTurno())};
                if(this.connection.preparedQuery(query, values)){
                    signedUp = true;
                }
            }
        }
        return signedUp;
    }

    @Override
    public boolean isRegistered() {
        boolean isRegistered = false;
        String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ?";
        String[] values = {this.profesor.getCorreoElectronico()};
        String[] names = {"TOTAL"};
        if (this.profesor.isComplete()) {
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                query = "SELECT COUNT(idUsuario) AS TOTAL FROM Profesor INNER JOIN Usuario " +
                        "ON Profesor.idUsuario = Usuario.idUsuario " +
                        "WHERE Usuario.correElectronico = ?";
                if (this.connection.select(query, values, names)[0][0].equals("1")) {
                    isRegistered = true;
                }
            }
        }
        return isRegistered;
    }

    public static Profesor[] getAll() {
        return new Profesor[0];
    }

    public static Profesor[] get(Profesor profesor) {
        return null;
    }
}
