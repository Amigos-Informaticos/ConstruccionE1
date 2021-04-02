package Models;

import DAO.DAOAsignacion;
import tools.File;
import tools.P;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Asignacion {
	private Practicante practicante;
	private Proyecto proyecto;
	private Professor professor;
	private Coordinador coordinador;
	private float score;
	
	public Asignacion(Practicante practicante, Proyecto proyecto, Coordinador coordinador) throws SQLException {
		this.practicante = practicante;
		this.proyecto = proyecto;
		practicante.cargarProfesor();
		this.professor = practicante.getProfesor();
		this.coordinador = coordinador;
	}
	
	public Practicante getPracticante() {
		return practicante;
	}
	
	public void setStudent(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}
	
	public void setProject(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public Professor getProfesor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Coordinador getCoordinator() {
		return coordinador;
	}
	
	public void setCoordinator(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
	public boolean assignProject() throws FileNotFoundException, SQLException {
		Documento documento = new Documento();
		documento.setTitle("Documento de asignacion");
		documento.setAuthor(this.coordinador);
		documento.setFile(new File("asignacion.pdf"));
		documento.setType("DocumentoAsignacion");
		//boolean generated = document.generateAssignmentDocument(this);
		boolean generated = true;
		documento.save();
		boolean assigned = new DAOAsignacion(this).asignarProyecto(documento);
		P.pln(generated);
		P.pln(assigned);
		return assigned && generated;
	}
	
	public boolean estaCompleto() {
		return this.practicante != null &&
			this.proyecto != null &&
			this.professor != null &&
			this.practicante.estaCompleto() &&
			this.proyecto.estaCompleto() &&
			this.professor.estaCompleto();
	}
	
	public boolean removeAssignment() throws SQLException {
		return new DAOAsignacion(this).eliminarAsignacion();
	}
	
	public static boolean saveRequest(Practicante practicante, Proyecto proyecto) throws SQLException {
		return DAOAsignacion.guardarSolicitud(practicante, proyecto);
	}
	
	public static Proyecto[] proyectosSeleccionados(Practicante practicante) throws SQLException {
		return DAOAsignacion.proyectosSolicitados(practicante);
	}
	
}
