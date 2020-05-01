package Models;

import DAO.DAOAdmin;
import javafx.collections.ObservableList;

public class Admin {
	public Admin(){
	}
	/*public Admin(String nombres, String apellidos, String correoElectronico, String contrasena) {
		super(nombres, apellidos, correoElectronico, contrasena);
	}*/
	
	/*public Admin(Admin admin) {
		if (admin != null) {
			this.setNames(admin.getNames());
			this.setLastnames(admin.getLastnames());
			this.setEmail(admin.getEmail());
			this.setPassword(admin.getPassword());
		}
	}*/

	public boolean fillTableProfessor(ObservableList<Professor> listProfessor){
		boolean filled = false;
		DAOAdmin daoAdmin = new DAOAdmin(this);
		if(daoAdmin.fillTableProfessor(listProfessor)){
			filled = true;
		}
		return filled;
	}
}
