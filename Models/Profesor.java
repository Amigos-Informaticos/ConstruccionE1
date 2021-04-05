package Models;

import DAO.DAOProfesor;
import IDAO.IDAOProfesor;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.Collections;

public class Profesor extends Usuario {
	private String personalNo;
	private String shift;
	
	public Profesor() {
		this.personalNo = null;
		this.shift = null;
	}
	
	public Profesor(String names, String lastnames, String email, String password,
					String personalNo, String shift) {
		super(names, lastnames, email, password);
		this.personalNo = personalNo;
		this.shift = shift;
	}
	
	public Profesor(Profesor profesor) {
		setNombres(profesor.getNombres());
		setApellidos(profesor.getApellidos());
		setEmail(profesor.getEmail());
		setContrasenaLimpia(profesor.getContrasena());
		setShift(profesor.getShift());
		setPersonalNo(profesor.getPersonalNo());
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
	
	public boolean logIn() throws  SQLException {
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
	
	public void obtenerProfesores(ObservableList<Profesor> listaProfesores) throws Exception {

		try{
			Profesor[] profesores = IDAOProfesor.obtenerTodosProfesores();
			Collections.addAll(listaProfesores, profesores);

		}catch(SQLException exception){
			throw new Exception(exception.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
