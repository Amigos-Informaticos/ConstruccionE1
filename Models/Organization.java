package Models;

import DAO.DAOrganization;
import Exceptions.CustomException;

public class Organization {
    private String name,
                    adress,
                    status,
                    idSector;

    public Organization(String name, String adress, String status, String idSector){
        this.name = name;
        this.adress = adress;
        this.status = status;
        this.idSector = idSector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdSector() {
        return idSector;
    }

    public void setIdSector(String idSector) {
        this.idSector = idSector;
    }

    public boolean register(){
        boolean isRegistered = false;
        if (this.isComplete()) {
            DAOrganization daoOrganization = new DAOrganization(this);
            try {
                if (daoOrganization.signUp()) {
                    isRegistered = true;
                }
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
        return isRegistered;
    }

    public boolean isComplete() {
        return this.name != null &&
                this.adress != null &&
                this.status != null &&
                this.idSector != null;
    }
}
