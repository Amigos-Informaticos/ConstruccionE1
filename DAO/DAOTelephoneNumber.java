package DAO;

import Connection.DBConnection;
import Models.TelephoneNumber;

public class DAOTelephoneNumber {
    private TelephoneNumber telephoneNumber;
    private final DBConnection connection = new DBConnection();

    public boolean signUp(String idOrganization){
        String query = "INSERT INTO TelefonoOrganizacion " +
                        "(telefono, idOrganizacion, telefono2) " +
                        "VALUES (?,?,?)";
        String[] values = {this.telephoneNumber.getNumber(),
                            idOrganization,
                            telephoneNumber.getNumber2()};
        return this.connection.sendQuery(query,values);
    }
}
