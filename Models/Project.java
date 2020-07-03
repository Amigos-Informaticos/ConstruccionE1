package Models;

import DAO.DAOProject;
import DAO.DAOrganization;
import Exceptions.CustomException;

public class Project {
	
	private String name;
	private String description;
	private String methodology;
	private String generalObjective;
	private String mediateObjective;
	private String immediateObjective;
	private String resources;
	private String responsibilities;
	private String status;
	private String capacity;
	private String area;
	private ProjectResponsible responsible;
	private String period;
	private Organization organization;
	private String startDate;
	private String endDate;
	
	public Project() {
	}
	
	public String getName() {
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCapacity() {
		return capacity;
	}
	
	public void setCapacity(String capacity) {
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
	
	public Organization getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = DAOrganization.getByName(organization);
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
	
	public boolean isComplete() {
		return this.name != null &&
			this.methodology != null &&
			this.generalObjective != null &&
			this.mediateObjective != null &&
			this.immediateObjective != null &&
			this.resources != null &&
			this.responsibilities != null &&
			this.status != null &&
			this.area != null &&
			this.responsible != null &&
			this.period != null &&
			this.organization != null;
	}
	
	public boolean register() throws CustomException {
		boolean isRegistered = false;
		if (this.isComplete()) {
			DAOProject daoProject = new DAOProject(this);
			if (daoProject.signUp()) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
	
	public boolean deleteProject() throws CustomException {
		return new DAOProject(this).delete();
	}
	
	public boolean isActive() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.isActive();
	}
	
	public boolean reactive() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.reactive();
	}
	
	public boolean haveStudents() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.haveStudents();
	}
	
	public boolean isRegistered() {
		DAOProject daoProject = new DAOProject(this);
		return daoProject.isRegistered();
	}
}
