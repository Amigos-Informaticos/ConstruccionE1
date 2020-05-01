package Models;

import DAO.DAOProfessor;
import Exceptions.CustomException;

public class Professor extends User {
    private String personalNo;
    private String shift;
    
    public Professor() {
    }
    public Professor(String names, String lastnames, String email, String password,
                     String personalNo, String shift) {
        super(names, lastnames, email, password);
        this.personalNo = personalNo;
        this.shift=shift;
    }

    public String getPersonalNo() {
        return personalNo;
    }

    public String getShift() {
        return shift;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
    public boolean logIn() throws CustomException{
        boolean loggedIn = false;
        if (this.isComplete()){
            DAOProfessor daoProfessor = new DAOProfessor(this);
            if(daoProfessor.logIn()){
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    public boolean signUp() throws CustomException {
        boolean isRegistered = false;
        if (this.isComplete()){
            DAOProfessor daoProfessor = new DAOProfessor(this);
            if(daoProfessor.signUp()){
                isRegistered = true;
            }
        }
        return isRegistered;
    }
    public boolean update ()  throws CustomException {
        boolean updated = false;
        DAOProfessor daoProfessor = new DAOProfessor(this);
        if (daoProfessor.update()){
            updated = true;
        }
        return updated;
    }
    public boolean delete() throws CustomException {
        boolean deleted = false;
        DAOProfessor daoProfessor = new DAOProfessor(this);
        if(daoProfessor.delete()){
            deleted = true;
        }
	    return deleted;
    }
	
	public boolean reactive() throws CustomException {
		boolean reactivated = false;
		DAOProfessor daoProfessor = new DAOProfessor(this);
		if (daoProfessor.reactive()) {
			reactivated = true;
		}
		return reactivated;
	}
	
	public boolean isRegistered() throws CustomException {
		return new DAOProfessor(this).isRegistered();
	}
}
