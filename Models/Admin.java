package Models;

import DAO.DAOAdmin;
import tools.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Admin extends User {
	public Admin() {
	}
	
	public Admin(String names, String lastnames, String email, String password) {
		super(names, lastnames, email, password);
	}
	
	public Admin(Admin admin) {
		if (admin != null) {
			this.setNames(admin.getNames());
			this.setLastnames(admin.getLastnames());
			this.setEmail(admin.getEmail());
			this.setPassword(admin.getPassword());
		}
	}
	
	public boolean isRegistered() {
		return new DAOAdmin(this).isRegistered();
	}
	
	public boolean login() {
		return new DAOAdmin(this).login();
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
