package Models;

public class Administrador extends User {
	public Administrador(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
	}
	
	public Administrador(Administrador administrador) {
		if (administrador != null) {
			this.setNames(administrador.getNames());
			this.setLastnames(administrador.getLastnames());
			this.setEmail(administrador.getEmail());
			this.setPassword(administrador.getPassword());
		}
	}
}
