package Models;

import DAO.DAOAdmin;
import javafx.collections.ObservableList;

public class Admin extends User{
	public Admin(){
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

	public boolean fillTableProfessor(ObservableList<Professor> listProfessor){
		boolean filled = false;
		DAOAdmin daoAdmin = new DAOAdmin(this);
		if(daoAdmin.fillTableProfessor(listProfessor)){
			filled = true;
		}
		return filled;
	}
}
