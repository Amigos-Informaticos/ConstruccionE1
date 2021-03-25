package Models;

import DAO.DAOProfesor;
import IDAO.IDAOProfessor;
import javafx.collections.ObservableList;

import java.sql.SQLException;
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
	
	public boolean logIn() throws SQLException {
		return new DAOProfesor(this).iniciarSesion();
	}
	
	public boolean signUp() throws SQLException {
		return new DAOProfesor(this).registrar();
	}
	
	public boolean update() throws SQLException {
		return new DAOProfesor(this).update();
	}
	
	public boolean delete() throws SQLException {
		return new DAOProfesor(this).eliminar();
	}
	
	public boolean reactive() throws SQLException {
		return new DAOProfesor(this).reactivar();
	}
	
	public boolean isRegistered() throws SQLException {
		return new DAOProfesor(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && personalNo != null && shift != null;
	}
	
	public void obtenerProfesores(ObservableList<Professor> listProfessor) throws Exception {

		try{
			Professor[] professors = IDAOProfessor.obtenerTodosProfesores();
			Collections.addAll(listProfessor, professors);

		}catch(Exception exception){
			throw new Exception(exception.getMessage());
		}

	}
}
