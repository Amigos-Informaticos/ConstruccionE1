package Models;

import DAO.DAOProjectResponsible;

public class ProjectResponsible {
	private String email;
	private String names;
	private String lastNames;
	private String position;
	private Organizacion organizacion;
	
	public ProjectResponsible() {
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNames() {
		return names;
	}
	
	public void setNames(String names) {
		this.names = names;
	}
	
	public String getLastNames() {
		return lastNames;
	}
	
	public Organizacion getOrganization() {
		return organizacion;
	}
	
	public void setOrganization(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public boolean isComplete() {
		return this.email != null &&
			this.names != null &&
			this.lastNames != null;
	}
	
	public boolean signUp() {
		return new DAOProjectResponsible(this).signUp();
	}
	
	public boolean isRegistered() {
		return new DAOProjectResponsible(this).isRegistered();
	}
	
	public boolean delete() {
		return new DAOProjectResponsible(this).delete();
	}
}
