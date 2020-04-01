package Models;

import Connection.DBConnection;

public class Usuario {
	private String nombres;
	private String apellidos;
	private String correoElectronico;
	private String contrasena;

	public Usuario() {
	}

	public Usuario(String nombres, String apellidos, String correoElectronico, String contrasena) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
	}

	public Usuario(Usuario user) {
		this.nombres = user.getNombres();
		this.apellidos = user.getApellidos();
		this.correoElectronico = user.getCorreoElectronico();
		this.contrasena = user.getContrasena();
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public boolean isComplete() {
		return this.nombres != null &&
				this.apellidos != null &&
				this.correoElectronico != null &&
				this.contrasena != null;
	}

}
