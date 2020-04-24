package Models;

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
		this.password = password;
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
		this.password = password;
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
}
