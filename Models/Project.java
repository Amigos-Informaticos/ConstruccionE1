package Models;

import DAO.DAOProject;
import DAO.DAOrganizacion;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Project {
	
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
	private Coordinator coordinator;
	
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
		this.organizacion = DAOrganizacion.obtenerPorNombre(organization);
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
	
	public Coordinator getCoordinator() {
		return coordinator;
	}
	
	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
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
		DAOProject daoProject = new DAOProject(this);
		if (daoProject.signUp() && daoProject.registCalendarizedActivities()) {
			registered = true;
		}
		return registered;
	}
	
	public boolean deleteProject() {
		return new DAOProject(this).delete();
	}
	
	public boolean isActive() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.isActive();
	}
	
	public boolean reactive() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.reactivate();
	}
	
	public boolean haveStudents() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.haveStudents();
	}
	
	public boolean isRegistered() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.isRegistered();
	}
	
	public static void fillTable(ObservableList<Project> projectsList) {
		Project[] projects = DAOProject.getAll();
		Collections.addAll(projectsList, projects);
	}
	
	public static boolean fillAreaTable(ObservableList<String> listAreas) {
		boolean filled = false;
		DAOProject daoProject = new DAOProject();
		if (daoProject.fillAreaTable(listAreas)) {
			filled = true;
		}
		return filled;
	}
	
}
