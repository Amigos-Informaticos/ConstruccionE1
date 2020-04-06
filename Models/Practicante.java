package Models;

import DAO.DAOPracticante;

public class Practicante extends Usuario {
	private String matricula;

	public Practicante() {
	}

	public Practicante(String nombres, String apellidos, String correoElectronico,
					   String contrasena, String matricula) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.matricula = matricula;
	}

	public Practicante(Practicante practicante) {
		if (practicante != null) {
			this.setNombres(practicante.getNombres());
			this.setApellidos(practicante.getApellidos());
			this.setCorreoElectronico(practicante.getCorreoElectronico());
			this.setContrasena(practicante.getContrasena());
			this.setMatricula(practicante.getMatricula());
		}
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean register() {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOPracticante daoPracticante = new DAOPracticante(this);
			if (daoPracticante.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}

	public boolean selectProyect(Proyecto proyecto) {
		boolean related = false;
		if (proyecto != null && proyecto.isComplete() && this.isComplete()) {
			DAOPracticante daoPracticante = new DAOPracticante(this);
			related = daoPracticante.selectProyect(proyecto);
		}
		return related;
	}
}