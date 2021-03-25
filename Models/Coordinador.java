package Models;

import DAO.DAOCoordinador;
import Exceptions.CustomException;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Coordinador extends Usuario {
	private String noPersonal;
	private String fechaRegistro;
	private String fechaBaja;
	private String turno;
	
	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String shift) {
		this.turno = shift;
	}
	
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(String registrationDate) {
		this.fechaRegistro = registrationDate;
	}
	
	public String getFechaBaja() {
		return fechaBaja;
	}
	
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public Coordinador() {
	}
	
	public Coordinador(String names, String lastNames, String email, String password,
	                   String noPersonal) {
		super(names, lastNames, email, password);
		this.noPersonal = noPersonal;
	}
	
	public Coordinador(String names, String lastNames, String email, String password,
	                   String noPersonal, String fechaRegistro, String fechaBaja) {
		super(names, lastNames, email, password);
		this.noPersonal = noPersonal;
		this.fechaRegistro = fechaRegistro;
		this.fechaBaja = fechaBaja;
	}
	
	public Coordinador(Coordinador coordinador) {
		if (coordinador != null) {
			this.setNombres(coordinador.getNombres());
			this.setApellidos(coordinador.getApellidos());
			this.setEmail(coordinador.getEmail());
			this.setContrasenaLimpia(coordinador.getContrasena());
			this.setNumeroPersonal(coordinador.getNoPersonal());
		}
	}
	
	public String getNoPersonal() {
		return noPersonal;
	}
	
	public void setNumeroPersonal(String noPersonal) {
		this.noPersonal = noPersonal;
	}
	
	public boolean registrar() {
		return new DAOCoordinador(this).registrar();
	}
	
	public boolean iniciarSesion() {
		return new DAOCoordinador(this).iniciarSesion();
	}
	
	public boolean actualizar() {
		return new DAOCoordinador(this).actualizar();
	}
	
	public boolean eliminar() {
		return new DAOCoordinador(this).eliminar();
	}
	
	public boolean registrarProyecto(Proyecto proyecto) throws CustomException {
		return proyecto.registrar();
	}
	
	public boolean eliminarProyecto(Proyecto proyecto) throws CustomException {
		return proyecto.eliminarProyecto();
	}
	
	public boolean hayOtro() {
		DAOCoordinador daoCoordinador = new DAOCoordinador(this);
		return daoCoordinador.hayOtro();
	}
	
	public boolean registrarPracticante(Practicante practicante) throws CustomException, SQLException {
		return practicante.registrar();
	}
	
	public boolean eliminarPracticante(Practicante practicante) throws CustomException, SQLException {
		return practicante.eliminar();
	}
	
	public boolean registrarOrganizacion(Organizacion organizacion) {
		return organizacion.registrar();
	}
	
	public boolean eliminarOrganizacion(Organizacion organizacion) {
		return organizacion.registrar();
	}
	
	public boolean asignarProyecto(Practicante practicante, String projectName)
		throws CustomException, SQLException {
		return practicante.asignarProyecto(projectName);
	}
	
	public boolean estaRegistrado() {
		return new DAOCoordinador(this).estaRegistrado();
	}
	
	public boolean estaCompleto() {
		return super.estaCompleto() && noPersonal != null;
	}
	
	public void llenarTablaCoordinador(ObservableList<Coordinador> listaCoordinador) {
		listaCoordinador.addAll(DAOCoordinador.obtenerTodos());
	}
	
	public Coordinador[] obtenerTodos() {
		return DAOCoordinador.obtenerTodos();
	}
	
	public Coordinador obtenerActivo() {
		return DAOCoordinador.obtenerActivo();
	}
	
	public String toString() {
		return getNombres() + "\n" +
			getApellidos() + "\n" +
			getNoPersonal() + "\n" +
			getTurno() + "\n" +
			getEmail() + "\n";
	}
}