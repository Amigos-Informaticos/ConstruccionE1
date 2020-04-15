package Models;

import DAO.DAOCoordinador;
import Exceptions.CustomException;

public class Coordinador extends User {
	private String noPersonal;
	Student student;
	
	
	public Coordinador() {
	}
	
	public Coordinador(String nombres, String apellidos, String correoElectronico, String contrasena,
	                   String noPersonal) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.noPersonal = noPersonal;
	}
	
	public Coordinador(Coordinador coordinador) {
		if (coordinador != null) {
			this.setNames(coordinador.getNames());
			this.setLastnames(coordinador.getLastnames());
			this.setEmail(coordinador.getEmail());
			this.setPassword(coordinador.getPassword());
			this.setNoPersonal(coordinador.getNoPersonal());
		}
	}
	
	public String getNoPersonal() {
		return noPersonal;
	}
	
	public void setNoPersonal(String noPersonal) {
		this.noPersonal = noPersonal;
	}
	
	public boolean signUpPracticante(Student student) throws CustomException {
		return student.register();
	}
	
	public boolean register() {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOCoordinador daoCoordinador = new DAOCoordinador(this);
			if (daoCoordinador.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}

	public boolean registerProyecto(Proyecto proyecto){
		return proyecto.register();
	}
/*
	public boolean registerOrganizacion(Organizacion organizacion){

	}


 */


}
