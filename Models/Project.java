package Models;

import DAO.DAOProject;

public class Project {
    //private int idProject;
    private String
            nombre,
            metodologia,
            objetivoGeneral,
            objetivoMediato,
            objetivoInmediato,
            recursos,
            responsabilidades,
            status,
            area,
            responsable,
            idPeriodo,
            idOrganizacion;
/*
    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

 */



    public Project(String nombre,
                    String metodologia,
                    String objetivoGeneral,
                    String objetivoMediato,
                    String objetivoInmediato,
                    String recursos,
                    String responsabilidades,
                    String status,
                    String area,
                    String responsable,
                    String idPeriodo,
                    String idOrganizacion) {
        this.nombre = nombre;
        this.metodologia = metodologia;
        this.objetivoGeneral = objetivoGeneral;
        this.objetivoMediato = objetivoMediato;
        this.objetivoInmediato = objetivoInmediato;
        this.recursos = recursos;
        this.responsabilidades = responsabilidades;
        this.status = status;
        this.area = area;
        this.responsable = responsable;
        this.idPeriodo = idPeriodo;
        this.idOrganizacion = idOrganizacion;
    }

    public Project() {
        DAOProject dao = new DAOProject(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getObjetivoMediato() {
        return objetivoMediato;
    }

    public void setObjetivoMediato(String objetivoMediato) {
        this.objetivoMediato = objetivoMediato;
    }

    public String getObjetivoInmediato() {
        return objetivoInmediato;
    }

    public void setObjetivoInmediato(String objetivoInmediato) {
        this.objetivoInmediato = objetivoInmediato;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getResponsabilidades() {
        return responsabilidades;
    }

    public void setResponsabilidades(String responsabilidades) {
        this.responsabilidades = responsabilidades;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(String idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public boolean isComplete() {
        return this.nombre != null &&
                this.metodologia != null &&
                this.objetivoGeneral != null &&
                this.objetivoMediato != null &&
                this.objetivoInmediato != null &&
                this.recursos != null &&
                this.responsabilidades != null &&
                this.status != null &&
                this.area != null &&
                this.responsable != null &&
                this.idPeriodo != null &&
                this.idOrganizacion != null;
    }

    public boolean register(){
        boolean isRegistered = false;
        if (this.isComplete()) {
            DAOProject daoProject = new DAOProject(this);
            if (daoProject.signUp()) {
                isRegistered = true;
            }
        }
        return isRegistered;
    }



}
