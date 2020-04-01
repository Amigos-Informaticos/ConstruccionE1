package Models;

import Connection.DBConnection;

public class Usuario {
	private String nombres;
	private String apellidos;
	private String correoElectronico;
	private String contrasena;

	public Usuario(String nombres, String apellidos, String correoElectronico, String contrasena) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
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

	public int iniciarSesion() {
		/*
		Status description
		0	->	No status
		1	->	Logged
		2	->	Wrong email
		3	->	Wrong password
		 */
		int status = 0;
		if (this.correoElectronico != null && this.contrasena != null) {
			DBConnection connection = new DBConnection();
			connection.loadFromFile("Connection/connection.txt");

		}
		return status;
	}
}
