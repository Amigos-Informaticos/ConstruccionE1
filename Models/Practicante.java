package Models;

public class Practicante extends Usuario {
	private String matricula;

	public Practicante() {
	}

	public Practicante(String nombres, String apellidos, String correoElectronico,
					   String contrasena, String matricula) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.matricula = matricula;
	}

	public Practicante(Practicante practicante){
		this.setNombres(practicante.getNombres());
		this.setApellidos(practicante.getApellidos());
		this.setCorreoElectronico(practicante.getCorreoElectronico());
		this.setContrasena(practicante.getContrasena());
		this.setMatricula(practicante.getMatricula());
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
