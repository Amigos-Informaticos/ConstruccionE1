package Models;

import DAO.DAOAsignacion;
import tools.File;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Asignacion {
	private Practicante practicante;
	private Proyecto proyecto;
	private Profesor profesor;
	private Coordinador coordinador;
	private float score;
	
	public Asignacion(Practicante practicante, Proyecto proyecto, Coordinador coordinador) throws SQLException {
		this.practicante = practicante;
		this.proyecto = proyecto;
		practicante.cargarProfesor();
		this.profesor = practicante.getProfesor();
		this.coordinador = coordinador;
	}
	
	public Practicante getPracticante() {
		return practicante;
	}
	
	public void setPracticante(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}
	
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public Profesor getProfesor() {
		return profesor;
	}
	
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Coordinador getCoordinador() {
		return coordinador;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public boolean assignProject() throws FileNotFoundException, SQLException {
		boolean asignado = new DAOAsignacion(this).asignarProyecto();
		return asignado;
	}


	
	public boolean estaCompleto() {
		return this.practicante != null &&
			this.proyecto != null &&
			this.profesor != null &&
			this.practicante.estaCompleto() &&
			this.proyecto.estaCompleto() &&
			this.profesor.estaCompleto();
	}
	
	public boolean removeAssignment() throws SQLException {
		return new DAOAsignacion(this).eliminarAsignacion();
	}
	
	public static boolean guardarSolicitud(Practicante practicante, Proyecto proyecto) throws SQLException {
		return DAOAsignacion.guardarSolicitud(practicante, proyecto);
	}
	
	public static Proyecto[] proyectosSeleccionados(Practicante practicante) throws SQLException {
		return DAOAsignacion.proyectosSolicitados(practicante);
	}
	
}
