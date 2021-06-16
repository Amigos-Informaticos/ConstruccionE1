package Models;

import DAO.DAODocumento;
import DAO.DAOPracticante;
import tools.File;

import java.io.IOException;
import java.sql.SQLException;

public class Documento {
    private String id;
    private String nombre;
    private String type;
    private String ruta;
    private File file;
    private Usuario author;

    public Documento() {
    }

    public Documento(String nombre, String type, File file, Usuario author) {
        this.nombre = nombre;
        this.type = type;
        this.file = file;
        this.author = author;
    }

    public Documento(String nombre, String ruta, String type, String id) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.type = obtenerNombreTipo(type);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setAuthor(Usuario author) {
        this.author = author;
    }

    public Usuario getAutor() {
        return author;
    }

    private String obtenerNombreTipo(String type) {
        String tipo;
        switch (type) {
            case "0":
                tipo = "Asignacion";
                break;
            case "1":
                tipo = "Reporte";
                break;
            default:
                tipo = "";
        }
        return tipo;
    }

    public boolean saveLocally() throws IOException {
        return new DAODocumento(this).saveLocally();
    }

    public boolean downloadFile() throws SQLException {
        return new DAODocumento(this).downloadFile();
    }

	public boolean estaCompleto() {
		return this.nombre != null &&
			this.type != null;
	}
	
	public boolean guardar() throws SQLException{
		return new DAODocumento(this).save(
			new DAOPracticante((Practicante) this.author).getId());
	}

    @Override
    public String toString() {
        return "Documento{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", type='" + type + '\'' +
                ", ruta='" + ruta + '\'' +
                ", file=" + file +
                ", author=" + author +
                '}';
    }

}