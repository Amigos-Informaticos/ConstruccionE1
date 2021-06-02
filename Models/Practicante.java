package Models;

import DAO.DAOPracticante;
import DAO.DAOProfesor;
import Exceptions.CustomException;
import javafx.collections.ObservableList;
import tools.File;

import java.sql.SQLException;

public class Practicante extends Usuario {
	private String matricula;
	private Profesor profesor;
	
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
	
	public Practicante get(String email) throws SQLException {
		Practicante practicante = new Practicante();
		practicante.setEmail(email);
		return DAOPracticante.get(practicante);
	}
	
	public Profesor getProfesor() {
		return profesor;
	}
	
	public Profesor recuperarProfesor() throws SQLException {
		
		Profesor profesor = DAOProfesor.getByStudent(this);
		
		System.out.println("PROFESOR RECUPERADO: " + profesor.getNombres());
		
		return profesor;
		
	}
	
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public boolean cargarProfesor() throws SQLException {
		assert this.getEmail() != null : "Email es nulo: Student.cargarProfesor()";
		boolean set = false;
		Profesor profesor = DAOProfesor.getByStudent(this);
		if (profesor != null) {
			set = true;
			this.setProfesor(profesor);
		}
		return set;
	}
	
	public boolean registrar() throws SQLException {
		assert this.estaCompleto() : "Practicante incompleto: Student.registrarse()";
		return new DAOPracticante(this).registrar();
	}
	
	public boolean iniciarSesion() throws SQLException {
		return new DAOPracticante(this).iniciarSesion();
	}
	
	public boolean actualizar() throws SQLException {
		return new DAOPracticante(this).actualizar();
	}
	
	public boolean actualizarConContrasenia(String correoElectronicoAntiguo) throws SQLException {
		return new DAOPracticante(this).actualizarConContrasenia(correoElectronicoAntiguo);
	}
	
	public boolean actualizarSinContrasenia(String correoElectronicoAntiguo) throws SQLException {
		return new DAOPracticante(this).actualizarSinContrasenia(correoElectronicoAntiguo);
	}
	
	public boolean eliminar() throws SQLException {
		return new DAOPracticante(this).eliminar();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() &&
			this.matricula != null &&
			this.profesor != null;
	}

	public Documento[] obtenerExpediente(){
		return new DAOPracticante(this).obtenerExpediente();
	}
	
	public boolean seleccionarProyecto(String nombreProyecto) throws SQLException {
		return new DAOPracticante(this).seleccionarProyecto(nombreProyecto);
	}
	
	public boolean eliminarSeleccion(String nombreProyecto) throws SQLException {
		assert this.estaCompleto() : "Practicante incompleto: Practicante.eliminarSeleccion()";
		assert nombreProyecto != null : "Nombre de proyecto es nulo: Practicante.eliminarSeleccion()";
		return new DAOPracticante(this).eliminarProyectoSeleccionado(nombreProyecto);
	}
	
	public Proyecto[] getSeleccion() throws SQLException {
		return new DAOPracticante(this).getProyectos();
	}
	
	public boolean asignarProyecto(String nombreProyecto) throws SQLException {
		return new DAOPracticante(this).setProyecto(nombreProyecto);
	}
	
	public boolean eliminarProyecto() throws CustomException, SQLException {
		return new DAOPracticante(this).eliminarProyecto();
	}
	
	public Proyecto getProyecto() throws CustomException, SQLException {
		return new DAOPracticante(this).getProyecto();
	}
	
	public boolean estaRegistrado() throws SQLException {
		return new DAOPracticante(this).estaRegistrado();
	}
	
	public boolean estaRegistradoActualizar(String correoElectronicoAntiguo) throws SQLException {
		return new DAOPracticante(this).estaRegistradoActualizar(correoElectronicoAntiguo);
	}
	
	public void llenarTablaPracticantes(ObservableList<Practicante> listaPracticantes)
		throws NullPointerException, SQLException {
		new DAOPracticante(this).llenarTablaPracticantes(listaPracticantes);
	}

	public void llenarTablaPracticantes(ObservableList<Practicante> listaPracticante, String correoProfesor) throws
			NullPointerException, SQLException {
		new DAOPracticante(this).llenarTablaPracticantes(listaPracticante, correoProfesor);
	}
	
	public boolean guardarDocumento(File documento) throws SQLException {
		return new DAOPracticante(this).guardarDocumento(documento);
	}
	
	public boolean actualizarProfesor() throws SQLException {
		return new DAOPracticante(this).actualizarProfesor();
	}

	@Override
	public String toString() {
		return super.toString() + "Practicante{" +
				"matricula='" + matricula + '\'' +
				", profesor=" + profesor +
				'}';
	}
}