package Models;

import DAO.DAOCoordinator;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Coordinator extends Usuario {
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
			this.setNombres(coordinator.getNombres());
			this.setApellidos(coordinator.getApellidos());
			this.setEmail(coordinator.getEmail());
			this.setContrasenaLimpia(coordinator.getContrasena());
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
		return new DAOCoordinator(this).registrarse();
	}
	
	public boolean logIn() {
		return new DAOCoordinator(this).iniciarSesion();
	}
	
	public boolean update() {
		return new DAOCoordinator(this).update();
	}
	
	public boolean delete() {
		return new DAOCoordinator(this).eliminar();
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
	
	public boolean signUpStudent(Practicante practicante) throws CustomException {
		return practicante.registrarse();
	}
	
	public boolean deleteStudent(Practicante practicante) throws CustomException {
		return practicante.eliminar();
	}
	
	public boolean signUpOrganization(Organizacion organizacion) throws CustomException {
		return organizacion.registrar();
	}
	
	public boolean deleteOrganization(Organizacion organizacion) throws CustomException {
		return organizacion.registrar();
	}
	
	public boolean assignProject(Practicante practicante, String projectName) throws CustomException {
		return practicante.setProyecto(projectName);
	}
	
	public boolean isRegistered() {
		return new DAOCoordinator(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && personalNo != null;
	}
	
	public void fillTableCoordinator(ObservableList<Coordinator> listCoordinator) {
		listCoordinator.addAll(DAOCoordinator.getAll());
	}
	
	public Coordinator[] getAll() {
		return DAOCoordinator.getAll();
	}

	public Coordinator getActive(){return DAOCoordinator.getActive();}

	public String toString() {
		return getNombres() + "\n" +
			getApellidos() + "\n" +
			getPersonalNo() + "\n" +
			getShift() + "\n" +
			getEmail() + "\n";
	}
}