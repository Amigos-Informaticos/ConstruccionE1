package Models;

import DAO.DAOPracticante;

public class Practicante extends Usuario {
	private String matricula;
	
	public Practicante() {
	}
	
	/**
	 * Parametrized constructor
	 *
	 * @param nombres           PRACTICANTE's names
	 * @param apellidos         PRACTICANTE's lastnames
	 * @param correoElectronico PRACTICANTE's email
	 * @param contrasena        PRACTICANTE's password
	 * @param matricula         PRACTICANTE's enrollment number
	 */
	public Practicante(String nombres, String apellidos, String correoElectronico,
	                   String contrasena, String matricula) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.matricula = matricula;
	}
	
	/**
	 * Copy constructor
	 *
	 * @param practicante Instance of PRACTICANTE to copy
	 */
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
	
	/**
	 * Register a new PRACTICANTE to database
	 *
	 * @return true => registered | false => couldn't register
	 */
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
	
	
	/**
	 * Set a selected project to the current PRACTICANTE
	 *
	 * @param proyecto Instance of Proyecto to relate to this PRACTICANTE
	 * @return true => selected | false => couldn't select
	 */
	public boolean selectProject(Proyecto proyecto) {
		boolean related = false;
		if (proyecto != null && proyecto.isComplete() && this.isComplete()) {
			DAOPracticante daoPracticante = new DAOPracticante(this);
			related = daoPracticante.selectProyect(proyecto);
		}
		return related;
	}
	
	public boolean removeSelection(String projectName) {
		boolean removed = false;
		if (this.isComplete() && projectName != null) {
			DAOPracticante daoPracticante = new DAOPracticante(this);
			removed = daoPracticante.deleteSelectedProyect(projectName);
		}
		return removed;
	}
}