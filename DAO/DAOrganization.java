package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOrganization;
import Models.Organization;
import javafx.collections.ObservableList;

public class DAOrganization implements IDAOrganization {
    private Organization organization;
    private DBConnection connection = new DBConnection();

    public DAOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean signUp() throws CustomException {
        boolean signedUp = false;
        if (this.organization != null) {
            if (!this.isRegistered()) {
                String query = "INSERT INTO Organizacion (nombre, status, idSector) VALUES (?, 1, ?)";
                String[] values = {this.organization.getName(),
                                    this.organization.getIdSector()};
                if(this.connection.sendQuery(query,values)){
                    signedUp = true;
                } else {
                    throw new CustomException("Could not insert into Organization: signUp()","NotSignUpOrganization");
                }
            } else {
                String query = "UPDATE Organizacion SET status = 1 WHERE nombre = ?";
                String[] values = {this.organization.getName()};
                if (this.connection.sendQuery(query, values)) {
                    signedUp = true;
                }
            }
        }else{
            throw new CustomException("Null Pointer Exception: signUp()");
        }
        return signedUp;
    }

        @Override
        public boolean isRegistered () {
            boolean isRegistered = false;
            String query = "SELECT COUNT (idOrganizacion) AS TOTAL FROM Organizacion WHERE nombre = ?"; //Comentar lo de no repetir nombres
            String[] values = {this.organization.getName()};
            String[] names = {"TOTAL"};
            if (this.connection.select(query, values, names)[0][0].equals("1")) {
                isRegistered = true;
            }
            return isRegistered;
        }

        @Override
        public boolean delete() {
            boolean deleted = false;
            if (this.organization != null && this.isRegistered()) {
                if (this.isActive()) {
                    String query = "UPDATE Organizacion SET status = 0 WHERE nombre = ?";
                    String[] values = {this.organization.getName()};
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
            if (this.organization != null && this.organization.getName() != null &&
                    this.isRegistered()) {
                String query = "SELECT status FROM Organizacion WHERE nombre = ?";
                String[] values = {this.organization.getName()};
                String[] names = {"status"};
                isActive = this.connection.select(query, values, names)[0][0].equals("1");
            }
            return isActive;
        }

        @Override
        public boolean reactivate () {
            boolean reactivated = false;
            if (this.organization != null && this.isRegistered()) {
                if (this.isActive()) {
                    String query = "UPDATE Organizacion SET status = 1 WHERE nombre = ?";
                    String[] values = {this.organization.getName()};
                    if (this.connection.sendQuery(query, values)) {
                        reactivated = true;
                    }
                } else {
                    reactivated = true;
                }
            }
            return reactivated;
        }

    public boolean fillTableOrganization(ObservableList<Organization> listOrganization) {
        boolean filled = false;
        String query = "SELECT nombre, calle, numero, colonia, localidad, telefono, telefono2, sector " +
                "FROM Organizacion O INNER  JOIN Sector S on O.idSector = S.idSector " +
                "LEFT OUTER JOIN  Direccion D on O.idOrganizacion = D.idOrganizacion " +
                "LEFT OUTER JOIN TelefonoOrganizacion T on O.idOrganizacion = T.idOrganizacion;";
        String values[] = null;
        String names[] = {"nombre", "calle", "numero", "colonia", "localidad", "telefono", "telefono2", "sector"};

        String[][] select = this.connection.select(query, values, names);
        int row = 0, col = 0;
        while(row<select.length){
            listOrganization.add(new Organization(
                    select[row][0],
                    select[row][1],
                    select[row][2],
                    select[row][3],
                    select[row][4],
                    select[row][5],
                    select[row][6],
                    select[row][7]
                    )
            );
            row++;
        }
        return filled;
    }


}
