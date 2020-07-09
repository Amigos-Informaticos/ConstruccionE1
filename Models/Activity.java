package Models;

import DAO.DAOActivity;

public class Activity {
	private String title;
	private String description;
	private String startDate;
	private String deliveryDate;
	private Student student;
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	private Professor professor;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public boolean create() {
		boolean created = false;
		if (this.isComplete()) {
			created = new DAOActivity(this).create();
		}
		return created;
	}
	
	public boolean delete() {
		return new DAOActivity(this).delete();
	}
	
	public boolean update() {
		boolean updated;
		DAOActivity activity = new DAOActivity(this);
		updated = activity.update();
		return updated;
	}
	
	public boolean isComplete() {
		return this.title != null &&
			this.description != null &&
			this.deliveryDate != null &&
			this.student != null &&
			this.professor != null;
	}
	
	public String getIdActivity() {
		return new DAOActivity(this).getIdActivity();
	}
}
