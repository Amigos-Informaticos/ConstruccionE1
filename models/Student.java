package models;

import dao.DAOStudent;
import exceptions.CustomException;
import javafx.collections.ObservableList;

public class Student extends User {
	private String regNumber;
	
	public Student() {
	}
	
	public Student(String names, String lastnames, String email, String password, String regNumber) {
		super(names, lastnames, email, password);
		this.regNumber = regNumber;
	}
	
	public Student(Student student) {
		if (student != null) {
			this.setNames(student.getNames());
			this.setLastnames(student.getLastnames());
			this.setEmail(student.getEmail());
			this.setCleanPassword(student.getPassword());
			this.setRegNumber(student.getRegNumber());
		}
	}
	
	public String getRegNumber() {
		return regNumber;
	}
	
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	
	public boolean signUp() throws CustomException {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOStudent daoStudent = new DAOStudent(this);
			if (daoStudent.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
	
	
	public boolean login() throws CustomException {
		return new DAOStudent(this).logIn();
	}
	
	
	public boolean update() throws CustomException {
		return new DAOStudent(this).update();
	}
	
	
	public boolean delete() throws CustomException {
		return new DAOStudent(this).delete();
	}
	
	
	public boolean selectProject(String projectName) throws CustomException {
		return new DAOStudent(this).selectProject(projectName);
	}
	
	
	public boolean removeSelection(String projectName) throws CustomException {
		boolean removed = false;
		if (this.isComplete() && projectName != null) {
			DAOStudent daoStudent = new DAOStudent(this);
			removed = daoStudent.deleteSelectedProject(projectName);
		}
		return removed;
	}
	
	public Project[] getSelection() throws CustomException {
		return new DAOStudent(this).getProjects();
	}
	
	
	public boolean setProject(String projectName) throws CustomException {
		return new DAOStudent(this).setProject(projectName);
	}
	
	
	public boolean deleteProject() throws CustomException {
		return new DAOStudent(this).deleteProject();
	}
	
	
	public Project getProject() throws CustomException {
		return new DAOStudent(this).getProject();
	}
	
	
	public boolean replyActivity(String activityName, String documentPath) throws CustomException {
		return new DAOStudent(this).replyActivity(activityName, documentPath);
	}
	
	
	public boolean deleteReply(String activityName) throws CustomException {
		return new DAOStudent(this).deleteReply(activityName);
	}
	
	public boolean isRegistered() throws CustomException {
		return new DAOStudent(this).isRegistered();
	}

	public boolean fillTableStudent(ObservableList<Student> listStudent){
		boolean filled = false;
		DAOStudent daoStudent = new DAOStudent(this);
		if(daoStudent.fillTableStudent(listStudent)){
			filled = true;
		}
		return filled;
	}
}