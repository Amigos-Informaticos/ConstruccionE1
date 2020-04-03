package DAO;

import Models.Coordinador;
import Models.Practicante;

public class DAOCoordinador {
    private Coordinador coordinador;

    public DAOCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
        super(connection);
    }



    public int logIn() {
        int status = super.logIn();
        if (status == 1) {
            String query = "SELECT COUNT(Coordinador.idUsuario) AS TOTAL FROM Coordinador " +
                    "INNER JOIN Usuario ON Coordinador.idUsuario = Usuario.idUsuario " +
                    "WHERE Usuario.correoElectronico = ?";
            String[] values = {coordinador.getCorreoElectronico()};
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
     * @return The status description
     */
    public int signUp(Coordinador coordinador) {
        int status = super.signUp();
        if (status == 1) {
            String query = "INSERT INTO Coordinador (idUsuario, fechaRegistro, noPersonal) VALUES ( ( SELECT idUsuario FROM Usuario WHERE correoElectronico = ? ), (SELECT CURRENT_DATE ),? );";
            String[] values = {coordinador.getCorreoElectronico(),coordinador.getNoPersonal()};
            if (!this.connection.preparedQuery(query, values)) {
                status = 5;
            }
        }
        return status;
    }

    public int signUpPracticante(Practicante practicante){
        int status = super.signUp();
        if (status == 1) {
            String query = "INSERT INTO Practicante (idUsuario, fechaRegistro, matricula) VALUES ( ( SELECT idUsuario FROM Usuario WHERE correoElectronico = ? ), (SELECT CURRENT_DATE ),? );";
            String[] values = {practicante.getMatricula()};
            if (!this.connection.preparedQuery(query, values)) {
                status = 5;
            }
        }
        return status;
    }

}