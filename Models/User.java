package Models;

import Exceptions.CustomException;
import tools.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User {
	private String names;
	private String lastnames;
	private String email;
	private String password;
	
	public User() {
	}
	
	public User(String names, String lastnames, String email, String password) {
		if (this.isName(names)) {
			this.names = names;
		}
		if (this.isName(lastnames)) {
			this.lastnames = lastnames;
		}
		if (this.isEmail(email)) {
			this.email = email;
		}
		this.setPassword(password);
	}
	
	public User(User user) {
		if (user != null) {
			if (this.isName(user.getNames())) {
				this.names = user.getNames();
			}
			if (this.isName(user.getLastnames())) {
				this.lastnames = user.getLastnames();
			}
			if (this.isEmail(user.getEmail())) {
				this.email = user.getEmail();
			}
			this.password = user.getPassword();
		}
	}
	
	public String getNames() {
		return names;
	}
	
	public void setNames(String names) {
		if (this.isName(names)) {
			this.names = names;
		}
	}
	
	public String getLastnames() {
		return lastnames;
	}
	
	public void setLastnames(String lastnames) {
		if (this.isName(lastnames)) {
			this.lastnames = lastnames;
		}
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		if (this.isEmail(email)) {
			this.email = email;
		}
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] message = messageDigest.digest(password.getBytes());
			BigInteger number = new BigInteger(1, message);
			String hashed = number.toString(16);
			while (hashed.length() < 32) {
				hashed = "0" + hashed;
			}
			this.password = hashed;
		} catch (NoSuchAlgorithmException e) {
			new Logger().log(e);
		}
	}
	
	public boolean isComplete() {
		return this.names != null &&
			this.lastnames != null &&
			this.email != null &&
			this.password != null;
	}
	
	public boolean isName(String name) {
		String nameRegex = "^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
		return Pattern.compile(nameRegex).matcher(name).matches();
	}
	
	
	public boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
			"+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,}[a-zA-Z0-9])" +
			"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,}[a-zA-Z0-9])?)*$";
		return Pattern.compile(emailRegex).matcher(email).matches();
	}
	
	public String getType() {
		String type = "";
		assert this.getEmail() != null;
		assert this.getPassword() != null;
		try {
			if (new Student((Student) this).login()) {
				type = "Student";
			}
			if (new Professor((Professor) this).logIn()) {
				type = "Professor";
			}
			if (new Coordinator((Coordinator) this).logIn()) {
				type = "Coordinator";
			}
		} catch (CustomException e) {
			new Logger().log(e);
		}
		return type;
	}
}
