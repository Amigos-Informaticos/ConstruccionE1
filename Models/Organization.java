package Models;

import DAO.DAOrganization;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Organization {
    private String name;
    private String street;
    private String adressNo;
    private String colony;
    private String locality;
    private String sector;
    private String tel1;
    private String tel2;

    public Organization(String name,
                        String street,
                        String adressNo,
                        String colony,
                        String locality,
                        String sector,
                        String tel1,
                        String tel2) {
        this.name = name;
        this.street = street;
        this.adressNo = adressNo;
        this.colony = colony;
        this.locality = locality;
        this.sector = sector;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    public Organization(){}

    public Organization(String name,
                        String street,
                        String adressNo,
                        String colony,
                        String locality,
                        String sector,
                        String tel1) {
        this.name = name;
        this.street = street;
        this.adressNo = adressNo;
        this.colony = colony;
        this.locality = locality;
        this.sector = sector;
        this.tel1 = tel1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdressNo() {
        return adressNo;
    }

    public void setAdressNo(String adressNo) {
        this.adressNo = adressNo;
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

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
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

    public boolean isComplete() {
        return this.name != null &&
                this.street != null &&
                this.adressNo != null &&
                this.colony != null &&
                this.locality != null &&
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
}
