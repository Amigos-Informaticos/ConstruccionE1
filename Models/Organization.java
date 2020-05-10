package Models;

import DAO.DAOrganization;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Organization {
    private String name;
    private Address address;
    private Sector sector;
    private TelephoneNumber tel;

    public Organization(){}

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name,
                        String street,
                        String no,
                        String colony,
                        String locality,
                        String tel,
                        String tel2,
                        String sector) {
        this.name = name;
        this.address = new Address(street,no,colony,locality);
        this.sector = new Sector(sector);
        this.tel = new TelephoneNumber(tel,tel2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TelephoneNumber getTel() {
        return tel;
    }

    public void setTel(TelephoneNumber tel) {
        this.tel = tel;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public boolean signUp() throws CustomException{
        boolean isRegistered = false;
        if (this.isComplete()) {
            DAOrganization daoOrganization = new DAOrganization(this);
                if (daoOrganization.signUp()) {
                    isRegistered = true;
                }
        }
        return isRegistered;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isComplete() {
        return this.name != null &&
                this.tel != null &&
                this.address != null &&
                this.sector != null;
    }

    public boolean isRegistered(){
        DAOrganization daOrganization = new DAOrganization(this);
        return daOrganization.isRegistered();
    }

    public boolean delete() throws CustomException{
        boolean deleted = false;
        DAOrganization daOrganization = new DAOrganization(this);
        if(daOrganization.delete()){
            deleted = true;
        }
        return deleted;
    }

    public boolean isActive(){
        DAOrganization daOrganization = new DAOrganization(this);
        return daOrganization.isActive();
    }

    public boolean reactive(){
        boolean reactivated = false;
        DAOrganization daOrganization = new DAOrganization(this);
        if(daOrganization.reactivate()){
            reactivated = true;
        }
        return reactivated;
    }

    public boolean fillTableOrganization(ObservableList<Organization> listOrganization){
        boolean filled = false;
        DAOrganization daoOrg = new DAOrganization(this);
        if(daoOrg.fillTableOrganization(listOrganization)){
            filled = true;
        }
        return filled;
    }

    public boolean fillOrganizationNames(ObservableList<String> listOrganization){
        boolean filled = false;
        DAOrganization daoOrganization = new DAOrganization(this);
        if(daoOrganization.fillOrganizationNames(listOrganization)){
            filled = true;
        }
        return filled;
    }

    public String getId(){
        DAOrganization daOrganization = new DAOrganization(this);
        return daOrganization.getId();
    }
}
