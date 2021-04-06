package Models;

import tools.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Usuario {
	private String nombres;
	private String apellidos;
	private String email;
	private String contrasena;
	
	public Usuario() {
	}
	
	public Usuario(String nombres, String apellidos, String email, String contrasena) {
		if (Usuario.esNombre(nombres)) {
			this.nombres = nombres;
		}
		if (Usuario.esNombre(apellidos)) {
			this.apellidos = apellidos;
		}
		if (Usuario.esEmail(email)) {
			this.email = email;
		}
		this.setContrasena(contrasena);
	}
	
	public Usuario(Usuario usuario) {
		assert usuario != null : "Usuario es null: User()";
		if (Usuario.esNombre(usuario.getNombres())) {
			this.nombres = usuario.getNombres();
		}
		if (Usuario.esNombre(usuario.getApellidos())) {
			this.apellidos = usuario.getApellidos();
		}
		if (Usuario.esEmail(usuario.getEmail())) {
			this.email = usuario.getEmail();
		}
		this.contrasena = usuario.getContrasena();
	}
	
	public String getNombres() {
		return nombres;
	}
	
	public void setNombres(String nombres) {
		this.nombres = Usuario.esNombre(nombres) ? nombres : null;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = Usuario.esNombre(apellidos) ? apellidos : null;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = Usuario.esEmail(email) ? email : null;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		if(esContrasena(contrasena)){
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				byte[] mensaje = messageDigest.digest(contrasena.getBytes());
				BigInteger numero = new BigInteger(1, mensaje);
				StringBuilder hasheado = new StringBuilder(numero.toString(16));
				while (hasheado.length() < 32) {
					hasheado.insert(0, "0");
				}
				this.contrasena = hasheado.toString();
			} catch (NoSuchAlgorithmException e) {
				Logger.staticLog(e, true);
			}
		} else {
			this.contrasena = null;
		}
	}
	
	public void setContrasenaLimpia(String password) {
		this.contrasena = password;
	}
	
	public boolean estaCompleto() {
		return this.nombres != null &&
			this.apellidos != null &&
			this.email != null &&
			this.contrasena != null;
	}
	
	public static boolean esNombre(String name) {
		return Pattern.compile("^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$").matcher(name==null?"":name).matches();
	}
	
	public static boolean esEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
			"+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])" +
			"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$";
		return Pattern.compile(emailRegex).matcher(email==null?"":email).matches();
	}

	public static boolean esContrasena(String contrasena){
		return contrasena != null && (contrasena.length()>0 && contrasena.length()<33);
	}
	
	public String tipo() throws SQLException {
		assert this.getEmail() != null;
		assert this.getContrasena() != null;
		String tipo = "null";
		Practicante practicanteAuxiliar = new Practicante();
		practicanteAuxiliar.setEmail(this.getEmail());
		practicanteAuxiliar.setContrasenaLimpia(this.getContrasena());
		Profesor profesorAuxiliar = new Profesor();
		profesorAuxiliar.setEmail(this.getEmail());
		profesorAuxiliar.setContrasenaLimpia(this.getContrasena());
		Coordinador coordinadorAuxiliar = new Coordinador();
		coordinadorAuxiliar.setEmail(this.getEmail());
		coordinadorAuxiliar.setContrasenaLimpia(this.getContrasena());
		Administrador administradorAuxiliar = new Administrador();
		administradorAuxiliar.setEmail(this.getEmail());
		administradorAuxiliar.setContrasenaLimpia(this.getContrasena());
		if (practicanteAuxiliar.iniciarSesion()) {
			tipo = "Practicante";
		} else if (profesorAuxiliar.logIn()) {
			tipo = "Profesor";
		} else if (coordinadorAuxiliar.iniciarSesion()) {
			tipo = "Coordinador";
		} else if (administradorAuxiliar.login()) {
			tipo = "Administrador";
		}
		return tipo;
	}
}
