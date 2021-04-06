package Models;

import DAO.DAOProfesor;
import IDAO.IDAOProfesor;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.Collections;

public class Profesor extends Usuario {
	private String noPersonal;
	private String turno;
	
	public Profesor() {
		this.noPersonal = null;
		this.turno = null;
	}
	
	public Profesor(String names, String lastnames, String email, String password,
					String noPersonal, String turno) {
		super(names, lastnames, email, password);
		this.noPersonal = noPersonal;
		this.turno = turno;
	}
	
	public Profesor(Profesor profesor) {
		setNombres(profesor.getNombres());
		setApellidos(profesor.getApellidos());
		setEmail(profesor.getEmail());
		setContrasenaLimpia(profesor.getContrasena());
		setTurno(profesor.getTurno());
		setNumeroPersonal(profesor.getNoPersonal());
	}
	
	public String getNoPersonal() {
		return noPersonal;
	}
	
	public String getTurno() {
		return turno;
	}

	public void setNumeroPersonal(String noPersonal) {
		if (esNoPersonal(noPersonal)) {
			this.noPersonal = noPersonal;
		} else {
			this.noPersonal = null;
		}
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	public boolean iniciarSesion() throws  SQLException {
		return new DAOProfesor(this).iniciarSesion();
	}
	
	public boolean registrar() throws SQLException {
		return new DAOProfesor(this).registrar();
	}
	
	public boolean update() throws SQLException {
		return new DAOProfesor(this).update();
	}
	
	public boolean eliminar() throws SQLException {
		return new DAOProfesor(this).eliminar();
	}
	
	public boolean reactivar() throws SQLException {
		return new DAOProfesor(this).reactivar();
	}

	public static boolean esNoPersonal(String noPersonal){
		return noPersonal != null && (noPersonal.length()>0 && noPersonal.length()<33);
	}

	public boolean estaRegistrado() throws SQLException {
		return new DAOProfesor(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && noPersonal != null && turno != null;
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
