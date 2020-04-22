package Models;

import DAO.DAOCoordinator;
import Exceptions.CustomException;

public class Coordinator extends User {
	private String personalNo;
	Student student;


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
	
	public boolean signUpStudent(Student student) throws CustomException {
		return student.register();
	}
	
	public boolean register() {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOCoordinator daoCoordinator = new DAOCoordinator(this);
			try {
				if (daoCoordinator.signUp()) {
					isRegistered = true;
				}
			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
		return isRegistered;
	}

	public boolean registerProject(Project project){
		return project.register();
	}

}
