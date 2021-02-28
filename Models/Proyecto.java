package Models;

import DAO.DAOOrganizacion;
import DAO.DAOProyecto;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Proyecto {
	
	private String name;
	private String description;
	private String methodology;
	private String generalObjective;
	private String mediateObjective;
	private String immediateObjective;
	private String resources;
	private String responsibilities;
	private int capacity;
	private String area;
	private ProjectResponsible responsible;
	private String period;
	private Organizacion organizacion;
	private String startDate;
	private String endDate;
	private CalendarizedActivity[] calendarizedActivities;
	private Coordinador coordinador;
	
	public String getNombre() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMethodology() {
		return methodology;
	}
	
	public void setMethodology(String methodology) {
		this.methodology = methodology;
	}
	
	public String getGeneralObjective() {
		return generalObjective;
	}
	
	public void setGeneralObjective(String generalObjective) {
		this.generalObjective = generalObjective;
	}
	
	public String getMediateObjective() {
		return mediateObjective;
	}
	
	public void setMediateObjective(String mediateObjective) {
		this.mediateObjective = mediateObjective;
	}
	
	public String getImmediateObjective() {
		return immediateObjective;
	}
	
	public void setImmediateObjective(String immediateObjective) {
		this.immediateObjective = immediateObjective;
	}
	
	public String getResources() {
		return resources;
	}
	
	public void setResources(String resources) {
		this.resources = resources;
	}
	
	public String getResponsibilities() {
		return responsibilities;
	}
	
	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public ProjectResponsible getResponsible() {
		return responsible;
	}
	
	public void setResponsible(ProjectResponsible responsible) {
		this.responsible = responsible;
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
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
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Coordinador getCoordinator() {
		return coordinador;
	}
	
	public void setCoordinator(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
	public CalendarizedActivity[] getCalendarizedActivities() {
		return this.calendarizedActivities;
	}
	
	public void setCalendarizedActivities(CalendarizedActivity[] calendarizedActivities) {
		this.calendarizedActivities = calendarizedActivities;
	}
	
	public boolean estaCompleto() {
		return this.name != null &&
			this.description != null &&
			this.methodology != null &&
			this.generalObjective != null &&
			this.mediateObjective != null &&
			this.immediateObjective != null &&
			this.resources != null &&
			this.responsibilities != null &&
			this.area != null &&
			this.responsible != null &&
			this.period != null &&
			this.organizacion != null;
	}
	
	public boolean register() {
		boolean registered = false;
		DAOProyecto daoProyecto = new DAOProyecto(this);
		if (daoProyecto.signUp() && daoProyecto.registCalendarizedActivities()) {
			registered = true;
		}
		return registered;
	}
	
	public boolean deleteProject() {
		return new DAOProyecto(this).delete();
	}
	
	public boolean isActive() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.isActive();
	}
	
	public boolean reactive() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.reactivate();
	}
	
	public boolean haveStudents() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.haveStudents();
	}
	
	public boolean isRegistered() {
		DAOProyecto daoProyecto = new DAOProyecto(this);
		return daoProyecto.isRegistered();
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
