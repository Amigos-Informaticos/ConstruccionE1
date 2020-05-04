package models;

import exceptions.CustomException;
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
		assert user != null : "User is null: User()";
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
	
	public String getNames() {
		return names;
	}
	
	public void setNames(String names) {
		this.names = this.isName(names) ? names : null;
	}
	
	public String getLastnames() {
		return lastnames;
	}
	
	public void setLastnames(String lastnames) {
		this.lastnames = this.isName(lastnames) ? lastnames : null;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = this.isEmail(email) ? email : null;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] message = messageDigest.digest(password.getBytes());
			BigInteger number = new BigInteger(1, message);
			StringBuilder hashed = new StringBuilder(number.toString(16));
			while (hashed.length() < 32) {
				hashed.insert(0, "0");
			}
			this.password = hashed.toString();
		} catch (NoSuchAlgorithmException e) {
			new Logger().log(e);
		}
	}
	
	public void setCleanPassword(String password) {
		this.password = password;
	}
	
	public boolean isComplete() {
		return this.names != null &&
			this.lastnames != null &&
			this.email != null &&
			this.password != null;
	}
	
	public boolean isName(String name) {
		return Pattern.compile("^[A-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$").matcher(name).matches();
	}
	
	public boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
			"+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])" +
			"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$";
		return Pattern.compile(emailRegex).matcher(email).matches();
	}
	
	public String getType() {
		String type = "null";
		assert this.getEmail() != null;
		assert this.getPassword() != null;
		Student auxiliaryStudent = new Student();
		auxiliaryStudent.setEmail(this.getEmail());
		auxiliaryStudent.setCleanPassword(this.getPassword());
		Professor auxiliaryProfessor = new Professor();
		auxiliaryProfessor.setEmail(this.getEmail());
		auxiliaryProfessor.setCleanPassword(this.getPassword());
		Coordinator auxiliaryCoordinator = new Coordinator();
		auxiliaryCoordinator.setEmail(this.getEmail());
		auxiliaryCoordinator.setCleanPassword(this.getPassword());
		Admin auxiliaryAdministrator = new Admin();
		auxiliaryAdministrator.setEmail(this.getEmail());
		auxiliaryAdministrator.setCleanPassword(this.getPassword());
		try {
			if (auxiliaryStudent.isRegistered()) {
				type = "Student";
			} else if (auxiliaryProfessor.isRegistered()) {
				type = "Professor";
			} else if (auxiliaryCoordinator.isRegistered()) {
				type = "Coordinator";
			} else if (auxiliaryAdministrator.isRegistered()) {
				type = "Admin";
			}
		} catch (CustomException e) {
			new Logger().log(e);
		}
		return type;
	}
}
