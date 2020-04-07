package Models;

import DAO.DAOCoordinador;

public class Coordinador extends Usuario {
	private String noPersonal;
	Practicante practicante;


	public Coordinador() {
	}

	public Coordinador(String nombres, String apellidos, String correoElectronico, String contrasena,
					   String noPersonal) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.noPersonal = noPersonal;
	}

	public Coordinador(Coordinador coordinador) {
		if (coordinador != null) {
			this.setNombres(coordinador.getNombres());
			this.setApellidos(coordinador.getApellidos());
			this.setCorreoElectronico(coordinador.getCorreoElectronico());
			this.setContrasena(coordinador.getContrasena());
			this.setNoPersonal(coordinador.getNoPersonal());
		}
	}

	public String getNoPersonal() { return noPersonal; }

	public void setNoPersonal(String noPersonal) { this.noPersonal = noPersonal; }

	public boolean signUpPracticante(Practicante practicante){
		return practicante.register();
	}

	public boolean register(){
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
