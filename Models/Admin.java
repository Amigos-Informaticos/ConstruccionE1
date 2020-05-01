package Models;

import DAO.DAOAdmin;
import javafx.collections.ObservableList;

public class Admin {
	public Admin(){
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
