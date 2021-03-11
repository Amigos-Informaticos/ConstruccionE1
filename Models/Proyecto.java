package Models;

import DAO.DAOOrganizacion;
import DAO.DAOProyecto;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Proyecto {
	
	private String nombre;
	private String descripcion;
	private String metodologia;
	private String objetivoGeneral;
	private String objetivoMediato;
	private String objetivoInmediato;
	private String recursos;
	private String responsabilidades;
	private int capacidad;
	private String area;
	private ResponsableProyecto responsable;
	private String periodo;
	private Organizacion organizacion;
	private String fechaInicio;
	private String fechaFin;
	private ActividadCalendarizada[] actividaadCalendarizada;
	private Coordinador coordinador;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getMetodologia() {
		return metodologia;
	}
	
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	
	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}
	
	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}
	
	public String getObjetivoMediato() {
		return objetivoMediato;
	}
	
	public void setObjetivoMediato(String objetivoMediato) {
		this.objetivoMediato = objetivoMediato;
	}
	
	public String getObjetivoInmediato() {
		return objetivoInmediato;
	}
	
	public void setObjetivoInmediato(String objetivoInmediato) {
		this.objetivoInmediato = objetivoInmediato;
	}
	
	public String getRecursos() {
		return recursos;
	}
	
	public void setRecursos(String recursos) {
		this.recursos = recursos;
	}
	
	public String getResponsabilidades() {
		return responsabilidades;
	}
	
	public void setResponsabilidades(String responsabilidades) {
		this.responsabilidades = responsabilidades;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public ResponsableProyecto getResponsable() {
		return responsable;
	}
	
	public void setResponsable(ResponsableProyecto responsable) {
		this.responsable = responsable;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public Organizacion getOrganization() {
		return organizacion;
	}
	
	public void setOrganization(String organization) {
		this.organizacion = DAOOrganizacion.obtenerPorNombre(organization);
	}
	
	public void setOrganization(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	public String getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Coordinador getCoordinator() {
		return coordinador;
	}
	
	public void setCoordinator(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
	public ActividadCalendarizada[] getActividaadCalendarizada() {
		return this.actividaadCalendarizada;
	}
	
	public void setActividaadCalendarizada(ActividadCalendarizada[] actividaadCalendarizada) {
		this.actividaadCalendarizada = actividaadCalendarizada;
	}
	
	public boolean estaCompleto() {
		return this.nombre != null &&
			this.descripcion != null &&
			this.metodologia != null &&
			this.objetivoGeneral != null &&
			this.objetivoMediato != null &&
			this.objetivoInmediato != null &&
			this.recursos != null &&
			this.responsabilidades != null &&
			this.area != null &&
			this.responsable != null &&
			this.periodo != null &&
			this.organizacion != null;
	}
	
	public boolean registrar() {
		boolean registered = false;
		DAOProyecto daoProyecto = new DAOProyecto(this);
		if (daoProyecto.registrarse() && daoProyecto.registCalendarizedActivities()) {
			registered = true;
		}
		return registered;
	}
	
	public boolean eliminarProyecto() {
		return new DAOProyecto(this).eliminar();
	}
	
	public boolean isActive() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.estaActivo();
	}
	
	public boolean reactive() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.reactivar();
	}
	
	public boolean haveStudents() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.haveStudents();
	}
	
	public boolean isRegistered() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.estaRegistrado();
	}
	
	public static void fillTable(ObservableList<Proyecto> projectsList) {
		Proyecto[] proyectos = DAOProyecto.getAll();
		Collections.addAll(projectsList, proyectos);
	}
	
	public static boolean fillAreaTable(ObservableList<String> listAreas) {
		boolean filled = false;
		DAOProyecto daoProyecto = new DAOProyecto();
		if (daoProyecto.fillAreaTable(listAreas)) {
			filled = true;
		}
		return filled;
	}
	
}
