package DAO;

import Connection.DBConnection;
import Models.Organizacion;

public class DAOrganizacion {
    private Organizacion organizacion;
    private DBConnection connection = new DBConnection();

    public DAOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    //@Override
    public boolean signUp() {
        boolean signedUp = false;
        if (this.organizacion.isComplete()) {
            if (!this.isRegistered()) {
                String query = "INSERT INTO organizacion " +
                                "(idorganizacion, nombre, direccion, status, idSector)" +
                                "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                String[] values = {this.organizacion.getNombre(),
                        this.organizacion.getDireccion(),
                        this.organizacion.getStatus(),
                        this.organizacion.getIdSector()
                };
                if (this.connection.preparedQuery(query, values)) {
                    signedUp = true;
                }
            }
        }
        return signedUp;
    }

    //@Override
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
}
