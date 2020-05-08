package Models;

import DAO.DAOStudent;
import Exceptions.CustomException;
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
		assert this.isComplete() : "Student is not complete: Student.signUp()";
		return new DAOStudent(this).signUp();
	}
	
	public boolean login() {
		return new DAOStudent(this).logIn();
	}
	
	
	public boolean update() {
		return new DAOStudent(this).update();
	}
	
	
	public boolean delete() {
		return new DAOStudent(this).delete();
	}
	
	public boolean isComplete() {
		return super.isComplete() && this.regNumber != null;
	}
	
	public boolean selectProject(String projectName) throws CustomException {
		return new DAOStudent(this).selectProject(projectName);
	}
	
	
	public boolean removeSelection(String projectName) {
		assert this.isComplete() : "Student is not complete: Student.removeSelection()";
		assert projectName != null : "Project name is null: Student.removeSelection()";
		return new DAOStudent(this).deleteSelectedProject(projectName);
	}
	
	public Project[] getSelection() {
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
	
	public boolean fillTableStudent(ObservableList<Student> listStudent) {
		return new DAOStudent(this).fillTableStudent(listStudent);
	}
}