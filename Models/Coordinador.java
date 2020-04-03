package Models;

public class Coordinador extends Usuario {
	private String noPersonal;

	public Coordinador() {
	}

	public Coordinador(String nombres, String apellidos, String correoElectronico, String contrasena,
					   String noPersonal) {
		super(nombres, apellidos, correoElectronico, contrasena);
		this.noPersonal = noPersonal;
	}

	public Coordinador(Coordinador coordinador) {
		this.setNombres(coordinador.getNombres());
		this.setApellidos(coordinador.getApellidos());
		this.setCorreoElectronico(coordinador.getCorreoElectronico());
		this.setContrasena(coordinador.getContrasena());
		this.setNoPersonal(coordinador.getNoPersonal());
	}

	public String getNoPersonal() { return noPersonal; }

	public void setNoPersonal(String noPersonal) { this.noPersonal = noPersonal; }

	public void signUpPracticante(Practicante practicante){

	}

}
