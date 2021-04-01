package Models;

import DAO.DAOAdministrador;
import tools.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Administrador extends Usuario {
	public Administrador() {
	}
	
	public Administrador(String names, String lastnames, String email, String password) {
		super(names, lastnames, email, password);
	}
	
	public Administrador(Administrador administrador) {
		if (administrador != null) {
			this.setNombres(administrador.getNombres());
			this.setApellidos(administrador.getApellidos());
			this.setEmail(administrador.getEmail());
			this.setContrasena(administrador.getContrasena());
		}
	}
	
	public boolean isRegistered() throws SQLException {
		return new DAOAdministrador(this).estaRegistrado();
	}
	
	public boolean login() throws SQLException {
		return new DAOAdministrador(this).iniciarSesion();
	}
	
	public String getHashedPassword(String password) {
		String hashedPassword = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] message = messageDigest.digest(password.getBytes());
			BigInteger number = new BigInteger(1, message);
			StringBuilder hashed = new StringBuilder(number.toString(16));
			while (hashed.length() < 32) {
				hashed.insert(0, "0");
			}
			hashedPassword = hashed.toString();
			
		} catch (NoSuchAlgorithmException e) {
			new Logger().log(e);
		}
		return hashedPassword;
	}
}
