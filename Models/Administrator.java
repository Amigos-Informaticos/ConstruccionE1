package Models;

public class Administrator extends User {
	public Administrator() {
	}
	
	public Administrator(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
	}
	
	public Administrator(Administrator administrator) {
		if (administrator != null) {
			this.setNames(administrator.getNames());
			this.setLastnames(administrator.getLastnames());
			this.setEmail(administrator.getEmail());
			this.setPassword(administrator.getPassword());
		}
	}
}
