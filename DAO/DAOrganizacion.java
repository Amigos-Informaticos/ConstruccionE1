package DAO;

import Connection.DBConnection;
import IDAO.IDAOrganizacion;
import Models.Organizacion;

public class DAOrganizacion implements IDAOrganizacion {
    private Organizacion organizacion;
    private DBConnection connection = new DBConnection();

    public DAOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    @Override
    public boolean signUp() {
        boolean signedUp = false;
        if (this.organizacion.isComplete()) {
            if (!this.isRegistered()) {
                String query = "INSERT INTO Organizacion " +
                                "(nombre, direccion, status, idSector)" +
                                "VALUES (?, ?, ?, ?);";
                String[] values = {this.organizacion.getNombre(),
                        this.organizacion.getDireccion(),
                        this.organizacion.getStatus(),
                        this.organizacion.getIdSector()
                };
                if (this.connection.sendQuery(query, values)) {
                    signedUp = true;
                }
            }
        }
        return signedUp;
    }

    @Override
    public boolean isRegistered() {
        boolean isRegistered = false;
        String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion WHERE Nombre = ?"; //Comentar lo de no repetir nombres
        String[] values = {this.organizacion.getNombre()};
        String[] names = {"TOTAL"};
        if (this.connection.select(query, values, names)[0][0].equals("1")) {
            isRegistered = true;
        }
        return isRegistered;
    }

    @Override
    public boolean delete() {
        boolean deleted = false;
        if (this.organizacion != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE Organizacion SET status = 0 WHERE nombre = ?";
                String[] values = {this.organizacion.getNombre()};
                if (this.connection.sendQuery(query, values)) {
                    deleted = true;
                }
            } else {
                deleted = true;
            }
        }
        return deleted;
    }

    @Override
    public boolean isActive() {
        boolean isActive = false;
        if (this.organizacion != null && this.organizacion.getNombre() != null &&
                this.isRegistered()) {
            String query = "SELECT status FROM Organizacion WHERE nombre = ?";
            String[] values = {this.organizacion.getNombre()};
            String[] names = {"status"};
            isActive = this.connection.select(query, values, names)[0][0].equals("1");
        }
        return isActive;
    }

    @Override
    public boolean reactivate() {
        boolean reactivated = false;
        if (this.organizacion != null && this.isRegistered()) {
            if (this.isActive()) {
                String query = "UPDATE Organizacion SET status = 1 WHERE nombre = ?";
                String[] values = {this.organizacion.getNombre()};
                if (this.connection.sendQuery(query, values)) {
                    reactivated = true;
                }
            } else {
                reactivated = true;
            }
        }
        return reactivated;
    }
}
