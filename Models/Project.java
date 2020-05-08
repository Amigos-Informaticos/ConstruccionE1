package Models;

import DAO.DAOProject;
import Exceptions.CustomException;

public class Project {
	
	private String
		name,
		methodology,
		generalObjective,
		mediateObjective,
		immediateObjective,
		resources,
		responsibilities,
		status,
		area,
		responsible,
		idPeriod,
		idOrganization;
	
	public Project(String name,
	               String methodology,
	               String generalObjective,
	               String mediateObjective,
	               String immediateObjective,
	               String resources,
	               String responsibilities,
	               String status,
	               String area,
	               String responsible,
	               String idPeriod,
	               String idOrganization) {
		this.name = name;
		this.methodology = methodology;
		this.generalObjective = generalObjective;
		this.mediateObjective = mediateObjective;
		this.immediateObjective = immediateObjective;
		this.resources = resources;
		this.responsibilities = responsibilities;
		this.status = status;
		this.area = area;
		this.responsible = responsible;
		this.idPeriod = idPeriod;
		this.idOrganization = idOrganization;
	}
	
	public Project() {
		DAOProject dao = new DAOProject(this);
	}

	public Project(String name,
				   String methodology,
				   String generalObjective,
				   String mediateObjective,
				   String immediateObjective,
				   String resources,
				   String responsibilities) {
		this.name = name;
		this.methodology = methodology;
		this.generalObjective = generalObjective;
		this.mediateObjective = mediateObjective;
		this.immediateObjective = immediateObjective;
		this.resources = resources;
		this.responsibilities = responsibilities;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getResponsible() {
		return responsible;
	}
	
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	public String getIdPeriod() {
		return idPeriod;
	}
	
	public void setIdPeriod(String idPeriod) {
		this.idPeriod = idPeriod;
	}
	
	public String getIdOrganization() {
		return idOrganization;
	}
	
	public void setIdOrganization(String idOrganization) {
		this.idOrganization = idOrganization;
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
			this.idPeriod != null &&
			this.idOrganization != null;
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
