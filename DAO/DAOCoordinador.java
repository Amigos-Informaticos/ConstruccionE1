package DAO;

import Connection.DBConnection;
import IDAO.IDAOCoordinador;
import Models.Coordinador;

public class DAOCoordinador implements IDAOCoordinador {
	private Coordinador coordinador;
	private DBConnection connection = new DBConnection();

    public DAOCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }




    /**
     * Method to sign up a new Admin
     * <p>
     * STATUS DESCRIPTION
     * 0	->	Initial status: no action has been taken
     * 1	->	Success
     * 2	->	Error in sending query
     * 3	->	User already registered
     * 4	->	Malformed object
     * 5	->	Registered into User but not into Admin. Attend immediately
     * </p>
     *
     * @return The status description
     */
    @Override
    public boolean signUp() {
        boolean signedUp = false;
        if(this.coordinador.isComplete()){
            String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status)" +
                    "VALUES (?, ?, ?, ?, ?)";
            String[] values = {this.coordinador.getNombres(), this.coordinador.getApellidos(), this.coordinador.getCorreoElectronico(), this.coordinador.getContrasena(), "1"};
            if(this.connection.preparedQuery(query, values)) {
                query = "INSERT INTO Coordinador (idUsuario, noPersonal, fechaRegistro) VALUES ((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), ?, (SELECT CURRENT_DATE))";
                values = new String[]{this.coordinador.getCorreoElectronico(), this.coordinador.getNoPersonal()};
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
        String[] values = {this.coordinador.getCorreoElectronico()};
        String[] names = {"TOTAL"};
        if (this.connection.select(query, values, names)[0][0].equals("1")) {
            isRegistered = true;
        }
        return isRegistered;
    }

    @Override
    public boolean logIn() {
        boolean loggedIn = false;
        String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Coordinador WHERE correoElectronico = ? " +
                "AND contrasena = ?";
        String[] values = {this.coordinador.getCorreoElectronico(), this.coordinador.getContrasena()};
        String[] names = {"TOTAL"};
        if (this.isRegistered()) {
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    //@Override
    public boolean delete() {
        boolean deleted = false;
        if (this.coordinador != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE Usuario SET status = 0 WHERE correoElectronico = ?";
                String[] values = {this.coordinador.getCorreoElectronico()};
                if (this.connection.preparedQuery(query, values)) {
                    deleted = true;
                }
            } else {
                deleted = true;
            }
        }
        return deleted;
    }

    public boolean isActive() {
        boolean isActive = false;
        if (this.coordinador != null && this.coordinador.getCorreoElectronico() != null &&
                this.isRegistered()) {
            String query = "SELECT status FROM Usuario WHERE correoElectronico = ?";
            String[] values = {this.coordinador.getCorreoElectronico()};
            String[] names = {"status"};
            isActive = this.connection.select(query, values, names)[0][0].equals("1");
        }
        return isActive;
    }

    //@Override
    public boolean reactive() {
        boolean reactivated = false;
        if (this.coordinador != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE Usuario SET status = 1 WHERE correoElectronico = ?";
                String[] values = {this.coordinador.getCorreoElectronico()};
                if (this.connection.preparedQuery(query, values)) {
                    reactivated = true;
                }
            } else {
                reactivated = true;
            }
        }
        return reactivated;
    }



}