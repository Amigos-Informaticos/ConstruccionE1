package Models;

import DAO.DAOAssignment;
import tools.File;
import tools.P;

import java.io.FileNotFoundException;

public class Assignment {
	private Student student;
	private Project project;
	private Professor professor;
	private Coordinator coordinator;
	private float score;
	
	public Assignment(Student student, Project project, Coordinator coordinator) {
		this.student = student;
		this.project = project;
		student.loadProfessor();
		this.professor = student.getProfessor();
		this.coordinator = coordinator;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
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
		return this.student != null &&
			this.project != null &&
			this.professor != null &&
			this.student.isComplete() &&
			this.project.isComplete() &&
			this.professor.isComplete();
	}
	
	public boolean removeAssignment() {
		return new DAOAssignment(this).removeAssignment();
	}
	
	public static boolean saveRequest(Student student, Project project) {
		return DAOAssignment.saveRequest(student, project);
	}
	
	public static Project[] requestedProjects(Student student) {
		return DAOAssignment.requestedProjects(student);
	}
	
}
