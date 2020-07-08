package Models;

import DAO.DAOCoordinator;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Coordinator extends User {
	private String personalNo;
	private String registrationDate;
	private String dischargeDate;
	private String shift;

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public Coordinator() {
	}
	
	public Coordinator(String names, String lastNames, String email, String password,
	                   String personalNo) {
		super(names, lastNames, email, password);
		this.personalNo = personalNo;
	}

	public Coordinator(String names, String lastNames, String email, String password,
					   String personalNo, String registrationDate, String dischargeDate) {
		super(names, lastNames, email, password);
		this.personalNo = personalNo;
		this.registrationDate = registrationDate;
		this.dischargeDate = dischargeDate;
	}
	
	public Coordinator(Coordinator coordinator) {
		if (coordinator != null) {
			this.setNames(coordinator.getNames());
			this.setLastnames(coordinator.getLastnames());
			this.setEmail(coordinator.getEmail());
			this.setCleanPassword(coordinator.getPassword());
			this.setPersonalNo(coordinator.getPersonalNo());
		}
	}
	
	public String getPersonalNo() {
		return personalNo;
	}
	
	public void setPersonalNo(String noPersonal) {
		this.personalNo = noPersonal;
	}
	
	public boolean signUp() {
		return new DAOCoordinator(this).signUp();
	}
	
	public boolean logIn() {
		return new DAOCoordinator(this).logIn();
	}

	public boolean update() { return new DAOCoordinator(this).update();}

	public boolean delete() {return new DAOCoordinator(this).delete();}
	
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

	public boolean isComplete(){
		return super.isComplete() && personalNo != null;
	}

	public void fillTableCoordinator(ObservableList<Coordinator> listCoordinator) {
		listCoordinator.addAll(DAOCoordinator.getAll());
	}

	public Coordinator[] getAll(){
		return DAOCoordinator.getAll();
	}

	public Coordinator getActive(){return DAOCoordinator.getActive();}

	public String toString(){
		return getNames()+"\n" +
				getLastnames()+"\n" +
				getPersonalNo()+"\n"+
				getShift()+"\n"+
				getEmail()+"\n";
	}
}