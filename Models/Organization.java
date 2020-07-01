package Models;

import DAO.DAOrganization;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Organization {
    private String name;
    private Address address;
    private String sector;

    public Organization(){}

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
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
