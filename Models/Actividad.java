package Models;

import DAO.DAOActividad;

public class Actividad {
	private String title;
	private String description;
	private String startDate;
	private String deliveryDate;
	private Practicante practicante;
	private Professor professor;
	
	public Practicante getPracticante() {
		return practicante;
	}
	
	public void setStudent(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public Professor getProfesor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public String getTitulo() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescripcion() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFechaInicio() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getFechaEntrega() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public boolean create() {
		boolean created = false;
		if (this.estaCompleta()) {
			created = new DAOActividad(this).crear();
		}
		return created;
	}
	
	public boolean delete() {
		return new DAOActividad(this).eliminar();
	}
	
	public boolean update() {
		boolean updated;
		DAOActividad activity = new DAOActividad(this);
		updated = activity.actualizar();
		return updated;
	}
	
	public boolean estaCompleta() {
		return this.title != null &&
			this.description != null &&
			this.deliveryDate != null &&
			this.practicante != null &&
			this.professor != null;
	}
	
	public String getIdActivity() {
		return new DAOActividad(this).getIdActividad();
	}
}
