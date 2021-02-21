package Models;

import DAO.DAOAssignment;
import tools.File;
import tools.P;

import java.io.FileNotFoundException;

public class Assignment {
	private Practicante practicante;
	private Project project;
	private Professor professor;
	private Coordinator coordinator;
	private float score;
	
	public Assignment(Practicante practicante, Project project, Coordinator coordinator) {
		this.practicante = practicante;
		this.project = project;
		practicante.cargarProfesor();
		this.professor = practicante.getProfesor();
		this.coordinator = coordinator;
	}
	
	public Practicante getStudent() {
		return practicante;
	}
	
	public void setStudent(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Coordinator getCoordinator() {
		return coordinator;
	}
	
	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}
	
	public boolean assignProject() throws FileNotFoundException {
		Document document = new Document();
		document.setTitle("Documento de asignacion");
		document.setAuthor(this.coordinator);
		document.setFile(new File("asignacion.pdf"));
		document.setType("DocumentoAsignacion");
		//boolean generated = document.generateAssignmentDocument(this);
		boolean generated = true;
		document.save();
		boolean assigned = new DAOAssignment(this).assignProject(document);
		P.pln(generated);
		P.pln(assigned);
		return assigned && generated;
	}
	
	public boolean isComplete() {
		return this.practicante != null &&
			this.project != null &&
			this.professor != null &&
			this.practicante.estaCompleto() &&
			this.project.estaCompleto() &&
			this.professor.estaCompleto();
	}
	
	public boolean removeAssignment() {
		return new DAOAssignment(this).removeAssignment();
	}
	
	public static boolean saveRequest(Practicante practicante, Project project) {
		return DAOAssignment.saveRequest(practicante, project);
	}
	
	public static Project[] requestedProjects(Practicante practicante) {
		return DAOAssignment.requestedProjects(practicante);
	}
	
}
