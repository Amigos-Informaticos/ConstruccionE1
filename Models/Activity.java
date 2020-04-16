package Models;

import java.util.Calendar;

public class Activity {
    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private String documento;


    public Activity(){
        titulo=null;
        descripcion=null;
        fechaEntrega=null;
        documento=null;
    }
    public Activity(String titulo, String descripcion, String fechaEntrega, String documento) {
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.fechaEntrega=fechaEntrega;
        this.documento=documento;
    }

    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getFechaEntrega() {
        return fechaEntrega;
    }
    public String getDocumento() {
        return documento;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
