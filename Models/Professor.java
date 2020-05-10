package Models;

import DAO.DAOProfessor;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Professor extends User {
    private String personalNo;
    private String shift;
    
    public Professor() {
        this.personalNo = null;
        this.shift = null;
    }
    public Professor(String names, String lastnames, String email, String password,
                     String personalNo, String shift) {
        super(names, lastnames, email, password);
        this.personalNo = personalNo;
        this.shift=shift;
    }
    public Professor (Professor professor){
        setNames(professor.getNames());
        setLastnames(professor.getLastnames());
        setEmail(professor.getEmail());
        setCleanPassword(professor.getPassword());
        setShift(professor.getShift());
        setPersonalNo(professor.getPersonalNo());
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
        return new DAOProfessor(this).logIn();
    }

    public boolean signUp(){
        return  new DAOProfessor(this).signUp();
    }

    public boolean update () {
        return new DAOProfessor(this).update();
    }
    public boolean delete(){
        return new DAOProfessor(this).delete();
    }
	
	public boolean reactive(){
		return new DAOProfessor(this).reactive();
	}
	
	public boolean isRegistered(){
		return new DAOProfessor(this).isRegistered();
	}
	public boolean isComplete(){
        return super.isComplete() && personalNo != null && shift != null;
    }

    public void fillTableProfessor(ObservableList<Professor> listProfessor) {
        listProfessor.addAll(DAOProfessor.getAll());
    }
}
