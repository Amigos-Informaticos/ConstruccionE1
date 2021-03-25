package Models;

import DAO.DAOResponsableProyecto;

import java.sql.SQLException;

public class ResponsableProyecto {
	private String email;
	private String nombres;
	private String apellidos;
	private String posicion;
	private Organizacion organizacion;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNombres() {
		return nombres;
	}
	
	public void setNombre(String names) {
		this.nombres = names;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	
	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	public void setApellido(String lastNames) {
		this.apellidos = lastNames;
	}
	
	public String getPosicion() {
		return posicion;
	}
	
	public void setPosicion(String position) {
		this.posicion = position;
	}
	
	public boolean estaCompleto() {
		return this.email != null &&
			this.nombres != null &&
			this.apellidos != null;
	}
	
	public boolean registrar() throws SQLException {
		return new DAOResponsableProyecto(this).registrarse();
	}
	
	public boolean estaRegistrado() throws SQLException {
		return new DAOResponsableProyecto(this).estaRegistrado();
	}
	
	public boolean eliminar() throws SQLException {
		return new DAOResponsableProyecto(this).eliminar();
	}
}
