package Models;

public class Administrator extends User {
	public Administrator() {
	}
	
	public Administrator(String names, String lastnames, String email, String password) {
		super(names, lastnames, email, password);
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