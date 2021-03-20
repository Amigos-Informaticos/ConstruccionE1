package Models;

import DAO.DAOProfesor;
import Exceptions.CustomException;
import IDAO.IDAOProfessor;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Professor extends Usuario {
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
		this.shift = shift;
	}
	
	public Professor(Professor professor) {
		setNombres(professor.getNombres());
		setApellidos(professor.getApellidos());
		setEmail(professor.getEmail());
		setContrasenaLimpia(professor.getContrasena());
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
	
	public boolean logIn() throws CustomException {
		return new DAOProfesor(this).iniciarSesion();
	}
	
	public boolean signUp() {
		return new DAOProfesor(this).registrar();
	}
	
	public boolean update() {
		return new DAOProfesor(this).update();
	}
	
	public boolean delete() {
		return new DAOProfesor(this).eliminar();
	}
	
	public boolean reactive() {
		return new DAOProfesor(this).reactivar();
	}
	
	public boolean isRegistered() {
		return new DAOProfesor(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && personalNo != null && shift != null;
	}
	
	public void fillTableProfessor(ObservableList<Professor> listProfessor) throws NullPointerException{
		Professor[] professors = IDAOProfessor.getAll();
		Collections.addAll(listProfessor, professors);
	}
}
