package Models;

import DAO.DAOStudent;
import Exceptions.CustomException;
import tools.Logger;

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
	public boolean register() {
		boolean isRegistered = false;
		try {
			if (this.isComplete()) {
				DAOStudent daoStudent = new DAOStudent(this);
				if (daoStudent.signUp()) {
					isRegistered = true;
				}
			}
		} catch (CustomException e) {
			new Logger().log(e);
		}
		return isRegistered;
	}
	
	
	/**
	 * Set a selected project to the current Student
	 *
	 * @param project Instance of Project to relate to this Student
	 * @return true => selected<br/>false => couldn't select
	 */
	public boolean selectProject(Proyecto project) {
		boolean related = false;
		try {
			if (project != null && project.isComplete() && this.isComplete()) {
				DAOStudent daoStudent = new DAOStudent(this);
				related = daoStudent.selectProject(project);
			}
		} catch (CustomException e) {
			new Logger().log(e);
		}
		return related;
	}
	
	public boolean removeSelection(String projectName) {
		boolean removed = false;
		try {
			if (this.isComplete() && projectName != null) {
				DAOStudent daoStudent = new DAOStudent(this);
				removed = daoStudent.deleteSelectedProject(projectName);
			}
		} catch (CustomException e) {
			new Logger().log(e);
		}
		return removed;
	}
}