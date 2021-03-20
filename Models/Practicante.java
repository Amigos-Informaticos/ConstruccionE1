package Models;

import DAO.DAOPracticante;
import DAO.DAOProfesor;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

public class Practicante extends Usuario {
	private String matricula;
	private Professor profesor;
	
	public Practicante() {
	}
	
	public Practicante(String nombres, String apellidos, String email, String contrasena,
	                   String matricula) {
		super(nombres, apellidos, email, contrasena);
		this.matricula = matricula;
	}
	
	public Practicante(Practicante practicante) {
		if (practicante != null) {
			this.setNombres(practicante.getNombres());
			this.setApellidos(practicante.getApellidos());
			this.setEmail(practicante.getEmail());
			this.setContrasenaLimpia(practicante.getContrasena());
			this.setMatricula(practicante.getMatricula());
			this.setProfesor(practicante.getProfesor());
		}
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String regNumber) {
		this.matricula = regNumber;
	}
	
	public Practicante get(String email) {
		Practicante practicante = new Practicante();
		practicante.setEmail(email);
		return DAOPracticante.get(practicante);
	}
	
	public Professor getProfesor() {
		return profesor;
	}
	
	public void setProfesor(Professor profesor) {
		this.profesor = profesor;
	}
	
	public boolean cargarProfesor() {
		assert this.getEmail() != null : "Email es nulo: Student.cargarProfesor()";
		boolean set = false;
		Professor profesor = DAOProfesor.getByStudent(this);
		if (profesor != null) {
			set = true;
			this.setProfesor(profesor);
		}
		return set;
	}
	
	public boolean registrar() {
		assert this.estaCompleto() : "Practicante incompleto: Student.registrarse()";
		return new DAOPracticante(this).registrar();
	}
	
	public boolean iniciarSesion() {
		return new DAOPracticante(this).iniciarSesion();
	}
	
	public boolean actualizar() {
		return new DAOPracticante(this).actualizar();
	}
	
	public boolean eliminar() {
		return new DAOPracticante(this).eliminar();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() &&
			this.matricula != null &&
			this.profesor != null;
	}
	
	public boolean seleccionarProyecto(String nombreProyecto) {
		return new DAOPracticante(this).seleccionarProyecto(nombreProyecto);
	}
	
	public boolean eliminarSeleccion(String nombreProyecto) {
		assert this.estaCompleto() : "Practicante incompleto: Practicante.eliminarSeleccion()";
		assert nombreProyecto != null : "Nombre de proyecto es nulo: Practicante.eliminarSeleccion()";
		return new DAOPracticante(this).eliminarProyectoSeleccionado(nombreProyecto);
	}
	
	public Proyecto[] getSeleccion() {
		return new DAOPracticante(this).getProyectos();
	}
	
	public boolean asignarProyecto(String nombreProyecto) throws CustomException {
		return new DAOPracticante(this).setProyecto(nombreProyecto);
	}
	
	public boolean eliminarProyecto() throws CustomException {
		return new DAOPracticante(this).eliminarProyecto();
	}
	
	public Proyecto getProyecto() throws CustomException {
		return new DAOPracticante(this).getProyecto();
	}
	
	public boolean estaRegistrado() {
		return new DAOPracticante(this).estaRegistrado();
	}
	
	public void llenarTablaPracticantes(ObservableList<Practicante> listaPracticantes) throws NullPointerException{
		new DAOPracticante(this).llenarTablaPracticantes(listaPracticantes);
	}
	
	public boolean tienePlanActividades() {
		return new DAOPracticante(this).tienePlanActividades();
	}
}