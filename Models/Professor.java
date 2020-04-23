package Models;

import DAO.DAOProfessor;
import DAO.DAOStudent;
import Exceptions.CustomException;

public class Professor extends User {
    private String personalNo;
    private int shift;
    
    public Professor() {
    }
    public Professor(String names, String lastnames, String email, String password,
                     String personalNo, int shift) {
        super(names, lastnames, email, password);
        this.personalNo = personalNo;
        this.shift=shift;
    }

    public String getPersonalNo() {
        return personalNo;
    }

    public int getShift() {
        return shift;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }

    public void setShift(int shift) {
        this.shift = shift;
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
}
