package Models;

import DAO.DAOCoordinator;
import Exceptions.CustomException;

public class Coordinator extends User {
	private String personalNo;
	
	
	public Coordinator() {
	}
	
	public Coordinator(String names, String lastNames, String email, String password,
	                   String personalNo) {
		super(names, lastNames, email, password);
		this.personalNo = personalNo;
	}
	
	public Coordinator(Coordinator coordinator) {
		if (coordinator != null) {
			this.setNames(coordinator.getNames());
			this.setLastnames(coordinator.getLastnames());
			this.setEmail(coordinator.getEmail());
			this.setPassword(coordinator.getPassword());
			this.setPersonalNo(coordinator.getPersonalNo());
		}
	}
	
	public String getPersonalNo() {
		return personalNo;
	}
	
	public void setPersonalNo(String noPersonal) {
		this.personalNo = noPersonal;
	}
	
	public boolean signUp() throws CustomException {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOCoordinator daoCoordinator = new DAOCoordinator(this);
			if (daoCoordinator.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
	
	public boolean logIn() {
		return new DAOCoordinator(this).logIn();
	}
	
	public boolean signUpProject(Project project) throws CustomException {
		return project.register();
	}
	
	public boolean deleteProject(Project project) throws CustomException {
		return project.deleteProject();
	}
	
	public boolean isAnother() {
		DAOCoordinator daoCoordinator = new DAOCoordinator(this);
		return daoCoordinator.isAnother();
	}
	
	public boolean signUpStudent(Student student) throws CustomException {
		return student.signUp();
	}
	
	public boolean deleteStudent(Student student) throws CustomException {
		return student.delete();
	}
	
	public boolean signUpOrganization(Organization organization) throws CustomException {
		return organization.signUp();
	}
	
	public boolean deleteOrganization(Organization organization) throws CustomException {
		return organization.signUp();
	}
	
	public boolean assignProject(Student student, String projectName) throws CustomException {
		return student.setProject(projectName);
	}
	
	public boolean isRegistered() {
		return new DAOCoordinator(this).isRegistered();
	}
}