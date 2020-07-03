package Models;

import DAO.DAOAssignment;

public class Assignment {
	private Student student;
	private Project project;
	private Professor professor;
	private float score;
	
	public Assignment(Student student, Project project) {
		this.student = student;
		this.project = project;
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
	
	public boolean assignProject() {
		return new DAOAssignment(this).assignProject();
	}
	
	public boolean isComplete() {
		return this.student != null &&
			this.project != null &&
			this.professor != null &&
			this.student.isComplete() &&
			this.project.isComplete() &&
			this.professor.isComplete();
	}
}
