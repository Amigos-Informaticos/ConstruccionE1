package Models;

import DAO.DAOCoordinador;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Coordinador extends Usuario {
	private String personalNo;
	private String registrationDate;
	private String dischargeDate;
	private String shift;
	
	public String getShift() {
		return shift;
	}
	
	public void setTurno(String shift) {
		this.shift = shift;
	}
	
	public String getRegistrationDate() {
		return registrationDate;
	}
	
	public void setFechaRegistro(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public String getDischargeDate() {
		return dischargeDate;
	}
	
	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	
	public Coordinador() {
	}
	
	public Coordinador(String names, String lastNames, String email, String password,
	                   String personalNo) {
		super(names, lastNames, email, password);
		this.personalNo = personalNo;
	}
	
	public Coordinador(String names, String lastNames, String email, String password,
	                   String personalNo, String registrationDate, String dischargeDate) {
		super(names, lastNames, email, password);
		this.personalNo = personalNo;
		this.registrationDate = registrationDate;
		this.dischargeDate = dischargeDate;
	}
	
	public Coordinador(Coordinador coordinador) {
		if (coordinador != null) {
			this.setNombres(coordinador.getNombres());
			this.setApellidos(coordinador.getApellidos());
			this.setEmail(coordinador.getEmail());
			this.setContrasenaLimpia(coordinador.getContrasena());
			this.setNumeroPersonal(coordinador.getPersonalNo());
		}
	}
	
	public String getPersonalNo() {
		return personalNo;
	}
	
	public void setNumeroPersonal(String noPersonal) {
		this.personalNo = noPersonal;
	}
	
	public boolean signUp() {
		return new DAOCoordinador(this).registrarse();
	}
	
	public boolean logIn() {
		return new DAOCoordinador(this).iniciarSesion();
	}
	
	public boolean update() {
		return new DAOCoordinador(this).actualizar();
	}
	
	public boolean delete() {
		return new DAOCoordinador(this).eliminar();
	}
	
	public boolean signUpProject(Proyecto proyecto) throws CustomException {
		return proyecto.register();
	}
	
	public boolean deleteProject(Proyecto proyecto) throws CustomException {
		return proyecto.deleteProject();
	}
	
	public boolean isAnother() {
		DAOCoordinador daoCoordinador = new DAOCoordinador(this);
		return daoCoordinador.hayOtro();
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
		return new DAOCoordinador(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && personalNo != null;
	}
	
	public void fillTableCoordinator(ObservableList<Coordinador> listCoordinador) {
		listCoordinador.addAll(DAOCoordinador.obtenerTodos());
	}
	
	public Coordinador[] getAll() {
		return DAOCoordinador.obtenerTodos();
	}
	
	public Coordinador getActive() {
		return DAOCoordinador.obtenerActivo();
	}
	
	public String toString() {
		return getNombres() + "\n" +
			getApellidos() + "\n" +
			getPersonalNo() + "\n" +
			getShift() + "\n" +
			getEmail() + "\n";
	}
}