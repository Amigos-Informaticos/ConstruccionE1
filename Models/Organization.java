package Models;

import DAO.DAOrganization;
import Exceptions.CustomException;

public class Organization {
    private String name;
    private String calle;
    private String numero;
    private String colonia;
    private String localidad;
    private String idSector;
    private String tel1;
    private String tel2;

    public Organization(String name,
                        String calle,
                        String numero,
                        String colonia,
                        String localidad,
                        String idSector,
                        String tel1,
                        String tel2) {
        this.name = name;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.localidad = localidad;
        this.idSector = idSector;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    public Organization(String name,
                        String calle,
                        String numero,
                        String colonia,
                        String localidad,
                        String idSector,
                        String tel1) {
        this.name = name;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.localidad = localidad;
        this.idSector = idSector;
        this.tel1 = tel1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
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

    public String getIdSector() {
        return idSector;
    }

    public void setIdSector(String idSector) {
        this.idSector = idSector;
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
                this.calle != null &&
                this.numero != null &&
                this.colonia != null &&
                this.localidad != null &&
                this.idSector != null;
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
}
