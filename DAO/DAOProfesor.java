package DAO;

import Models.Usuario;
import Models.Profesor;

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

    /**
     * Method to log in against the DB
     *
     * <p>
     * STATUS DESCRIPTION<br/>
     * 0	->	Initial status: no action has been taken<br/>
     * 1	->	loggedIn<br/>
     * 2	->	Wrong password<br/>
     * 3	->	Unmatched email<br/>
     * 4	->	Malformed object
     * 5	->	Logged as User but not as Admin. Attend immediately
     * </p>
     *
     * @return status of the DAO
     */
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
     * @return
     */
    public int signUp() {
        int status = super.signUp();
        if (status == 1) {
            String query = "INSERT INTO Profesor  VALUES ((SELECT idUsuario FROM Usuario WHERE correoElectronico" +
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
