package DAO;

import Connection.DBConnection;
import Models.Proyecto;

public class DAOProyecto {
    private Proyecto proyecto;
    protected DBConnection connection;

    public DAOProyecto() {
        this.connection = new DBConnection();
    }

    public DAOProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        this.connection = new DBConnection();
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
     * </p>
     *
     * @return status of the DAO
     */
    /*public int logIn() {
        int status = 0;
        if (this.proyecto.getCorreoElectronico() != null && this.proyecto.getContrasena() != null) {
            if (this.isRegistered()) {
                String query = "SELECT COUNT(idProyecto) AS TOTAL FROM Proyecto WHERE correoElectronico = ? AND contrasena = ?";
                String[] values = {this.proyecto.getCorreoElectronico(), this.proyecto.getContrasena()};
                String[] names = {"TOTAL"};
                if (this.connection.select(query, values, names)[0][0].equals("1")) {
                    status = 1;
                } else {
                    status = 2;
                }
            } else {
                status = 3;
            }
        } else {
            status = 4;
        }
        return status;
    }*/

    /**
     * Method to sign up a new proyecto
     * <p>
     * STATUS DESCRIPTION<br/>
     * 0	->	Initial status: no action has been taken<br/>
     * 1	->	Success
     * 2	->	Error in sending query
     * 3	->	proyecto already registered
     * 4	->	Malformed object
     * </p>
     *
     * @return true => succesfully registered | false => couldn't register
     */
    public int Register() {
        int status = 0;
        if (proyecto.isComplete()) {
            if (!this.isRegistered()) {
                String query = "INSERT INTO Proyecto (nombre, , metodologia, objetivoGeneral, " +
                        "objetivoMediato, objetivoInmediato, recursos, responsabilidades, status, " +
                        "area, responsable, idPerdiodo, idOrganizacion) VALUES (?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?)";
                String[] values = {this.proyecto.getNombre(),
                                    this.proyecto.getMetodologia(),
                                    this.proyecto.getObjetivoGeneral(),
                                    this.proyecto.getObjetivoMediato(),
                                    this.proyecto.getObjetivoInmediato(),
                                    this.proyecto.getRecursos(),
                                    this.proyecto.getResponsabilidades(),
                                    this.proyecto.getStatus(),
                                    this.proyecto.getArea(),
                                    this.proyecto.getResponsable(),
                                    this.proyecto.getIdPeriodo(),
                                    this.proyecto.getIdOrganizacion()
                                    };
                if (this.connection.preparedQuery(query, values)) {
                    status = 1;
                } else {
                    status = 2;
                }
            } else {
                status = 3;
            }
        } else {
            status = 4;
        }
        return status;
    }

    /**
     * Checks if the current proyecto is already registered
     *
     * @return true => Already registered | Not registered
     */
    public boolean isRegistered() {
        boolean isRegistered = false;
        String query = "SELECT COUNT(idProyecto) AS TOTAL FROM Proyecto WHERE idProyecto = ?";
        String[] values = {Integer.toString(proyecto.getIdProyecto())};
        String[] names = {"TOTAL"};
        if (this.connection.select(query, values, names)[0][0].equals("1")) {
            isRegistered = true;
        }
        return isRegistered;
    }
}
