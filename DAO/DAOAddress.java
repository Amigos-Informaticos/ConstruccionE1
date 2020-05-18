package DAO;

import Connection.DBConnection;
import Models.Address;

public class DAOAddress {
    private Address address;
    private final DBConnection connection = new DBConnection();

    public DAOAddress(Address address) {
        this.address = address;
    }

    public boolean signUp(String idOrganization){
        assert this.address != null : "Address is null: DAOAddress.signUp()";
        String query = "INSERT INTO Direccion (calle, numero, colonia, localidad, idOrganizacion) VALUES (?, ?, ?, ?, ?)";
        String[] values = new String[]{this.address.getStreet(),
                                        this.address.getNo(),
                                        this.address.getColony(),
                                        this.address.getLocality(),
                                        idOrganization};
        return this.connection.sendQuery(query, values);
    }
}