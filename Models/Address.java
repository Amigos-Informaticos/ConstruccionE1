package Models;

import DAO.DAOAddress;

public class Address {
    private String street;
    private String no;
    private String colony;
    private String locality;

    public Address(){}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Address(String street, String no, String colony, String locality) {
        this.street = street;
        this.no = no;
        this.colony = colony;
        this.locality = locality;
    }

    public boolean signUp(String idOrganizacion){
        DAOAddress daoAddress = new DAOAddress(this);
        return daoAddress.signUp(idOrganizacion);
    }
}
