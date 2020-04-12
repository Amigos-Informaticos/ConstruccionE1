package Models;

import DAO.DAOrganizacion;

public class Organizacion {
    private String nombre,
                    direccion,
                    status,
                    idSector;

    public Organizacion(String nombre,String direccion,String status,String idSector){
        this.nombre = nombre;
        this.direccion = direccion;
        this.status = status;
        this.idSector = idSector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
            DAOrganizacion daoOrganizacion = new DAOrganizacion(this);
            if (daoOrganizacion.signUp()) {
                isRegistered = true;
            }
        }
        return isRegistered;
    }

    public boolean isComplete() {
        return this.nombre != null &&
                this.direccion != null &&
                this.status != null &&
                this.idSector != null;
    }
}
