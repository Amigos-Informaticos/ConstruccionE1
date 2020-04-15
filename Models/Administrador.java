package Models;

public class Administrador extends Usuario {
	public Administrador(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
	}

	public Administrador(Administrador administrador) {
		if (administrador != null) {
			this.setNames(administrador.getNames());
			this.setApellidos(administrador.getApellidos());
			this.setCorreoElectronico(administrador.getEmail());
			this.setPassword(administrador.getPassword());
		}
	}
}
