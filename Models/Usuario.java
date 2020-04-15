package Models;

import java.util.regex.Pattern;

public class Usuario {
	private String nombres;
	private String apellidos;
	private String correoElectronico;
	private String contrasena;

	public Usuario() {
	}

	public Usuario(String nombres, String apellidos, String correoElectronico, String contrasena) {
		if (this.isName(nombres)) {
			this.nombres = nombres;
		}
		if (this.isName(apellidos)) {
			this.apellidos = apellidos;
		}
		if (this.isEmail(correoElectronico)) {
			this.correoElectronico = correoElectronico;
		}
		this.contrasena = contrasena;
	}

	public Usuario(Usuario user) {
		if (user != null) {
			if (this.isName(user.getNames())) {
				this.nombres = user.getNames();
			}
			if (this.isName(user.getApellidos())) {
				this.apellidos = user.getApellidos();
			}
			if (this.isEmail(user.getEmail())) {
				this.correoElectronico = user.getEmail();
			}
			this.contrasena = user.getPassword();
		}
	}

	public String getNames() {
		return nombres;
	}

	public void setNames(String nombres) {
		if (this.isName(nombres)) {
			this.nombres = nombres;
		}
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		if (this.isName(apellidos)) {
			this.apellidos = apellidos;
		}
	}

	public String getEmail() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		if (this.isEmail(correoElectronico)) {
			this.correoElectronico = correoElectronico;
		}
	}

	public String getPassword() {
		return contrasena;
	}

	public void setPassword(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Checks if the actual instance has all basic attributes
	 *
	 * @return true => has every attribute | false => lacks at least one attribute
	 */
	public boolean isComplete() {
		return this.nombres != null &&
				this.apellidos != null &&
				this.correoElectronico != null &&
				this.contrasena != null;
	}

	/**
	 * Checks if the provided string has name format
	 *
	 * @param name The string to check
	 * @return true => it has name format | false => it doesn't
	 */
	public boolean isName(String name) {
		String nameRegex = "^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
		return Pattern.compile(nameRegex).matcher(name).matches();
	}

	/**
	 * Checks if the provided string has email format
	 *
	 * @param email the string to check
	 * @return true => has email format | false => it doesn't
	 */
	public boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
				"+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,}[a-zA-Z0-9])" +
				"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,}[a-zA-Z0-9])?)*$";
		return Pattern.compile(emailRegex).matcher(email).matches();
	}
}
