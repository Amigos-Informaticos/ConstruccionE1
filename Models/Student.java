package Models;

import DAO.DAOStudent;
import Exceptions.CustomException;

public class Student extends User {
	private String regNumber;
	
	public Student() {
	}
	
	/**
	 * Parametrized constructor
	 *
	 * @param names     Student's names
	 * @param lastnames Student's lastnames
	 * @param email     Student's email
	 * @param password  Student's password
	 * @param regNumber Student's enrollment number
	 */
	public Student(String names, String lastnames, String email, String password, String regNumber) {
		super(names, lastnames, email, password);
		this.regNumber = regNumber;
	}
	
	/**
	 * Copy constructor
	 *
	 * @param student Instance of Student to copy
	 */
	public Student(Student student) {
		if (student != null) {
			this.setNames(student.getNames());
			this.setLastnames(student.getLastnames());
			this.setEmail(student.getEmail());
			this.setPassword(student.getPassword());
			this.setRegNumber(student.getRegNumber());
		}
	}
	
	public String getRegNumber() {
		return regNumber;
	}
	
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	/**
	 * Register a new Student to database
	 *
	 * @return true => registered<br/>
	 * false => couldn't register
	 */
	public boolean register() throws CustomException {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOStudent daoStudent = new DAOStudent(this);
			if (daoStudent.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
	
	/**
	 * Checks if the credentials are valid
	 *
	 * @return true => valid<br/>false => invalid
	 * @throws CustomException
	 */
	public boolean login() throws CustomException {
		return new DAOStudent(this).logIn();
	}
	
	/**
	 * Updates against the database the student information
	 *
	 * @return true => updated<br/>false => could not update
	 * @throws CustomException
	 */
	public boolean update() throws CustomException {
		return new DAOStudent(this).update();
	}
	
	/**
	 * Deactivates the current student in the database
	 *
	 * @return true => deactivated<br/>false => not deactivated
	 * @throws CustomException
	 */
	public boolean delete() throws CustomException {
		return new DAOStudent(this).delete();
	}
	
	/**
	 * Set a selected project to the current Student
	 *
	 * @param projectName Name of the project ot select
	 * @return true => selected<br/>false => couldn't select
	 */
	public boolean selectProject(String projectName) throws CustomException {
		return new DAOStudent(this).selectProject(projectName);
	}
	
	/**
	 * Removes a selected Project
	 *
	 * @param projectName The name of the project to deselect
	 * @return true => Deselected<br/>
	 * false => Not deselected
	 * @throws CustomException
	 */
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
	
	/**
	 * Sets the project
	 *
	 * @param projectName The name of the project to set
	 * @return true => set<br/>false => not set
	 * @throws CustomException
	 */
	public boolean setProject(String projectName) throws CustomException {
		return new DAOStudent(this).setProject(projectName);
	}
	
	/**
	 * Deletes the setted project
	 *
	 * @return true => deleted<br/>false => not deleted
	 * @throws CustomException
	 */
	public boolean deleteProject() throws CustomException {
		return new DAOStudent(this).deleteProject();
	}
	
	/**
	 * Returns the setted project
	 *
	 * @return Instance of the setted Project<br/>Null if there's no setted project
	 * @throws CustomException
	 */
	public Project getProject() throws CustomException {
		return new DAOStudent(this).getProject();
	}
	
	/**
	 * Replies to an activity with a document
	 *
	 * @param activityName The name of the activity to reply
	 * @param documentPath The path of the document to reply with
	 * @return true => replied<br/>false =>could not reply
	 * @throws CustomException
	 */
	public boolean replyActivity(String activityName, String documentPath) throws CustomException {
		return new DAOStudent(this).replyActivity(activityName, documentPath);
	}
	
	/**
	 * Deletes the setted reply to a specific activity
	 *
	 * @param activityName Name of the activity to reply
	 * @return true => deleted<br/>false => could not delete
	 * @throws CustomException
	 */
	public boolean deleteReply(String activityName) throws CustomException {
		return new DAOStudent(this).deleteReply(activityName);
	}
}