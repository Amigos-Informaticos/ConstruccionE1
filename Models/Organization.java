package Models;

import DAO.DAOrganization;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Organization {
	private String name;
	private String[] phoneNumber = new String[0];
	private String sector;
	private final Map<String, String> address = new HashMap<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String[] getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String[] phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void addPhoneNumber(String phoneNumber) {
		String[] aux = this.phoneNumber;
		this.phoneNumber = new String[aux.length + 1];
		if (aux.length >= 0) System.arraycopy(aux, 0, this.phoneNumber, 0, aux.length);
		this.phoneNumber[aux.length] = phoneNumber;
	}
	
	public void deletePhoneNumber(String phoneNumber) {
		String[] aux = this.phoneNumber;
		this.phoneNumber = new String[aux.length - 1];
		int counter = 0;
		for (String number: aux) {
			if (!number.equals(phoneNumber)) {
				this.phoneNumber[counter] = number;
				counter++;
			}
		}
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public void setAddress(String street, String number, String colony, String locality) {
		this.address.clear();
		this.address.put("street", street);
		this.address.put("number", number);
		this.address.put("colony", colony);
		this.address.put("locality", locality);
	}
	
	public Map<String, String> getAddress() {
		return this.address;
	}
	
	public boolean signUp() {
		return new DAOrganization(this).signUp();
	}
	
	public boolean isComplete() {
		return this.name != null &&
			!this.address.isEmpty() &&
			this.sector != null;
	}
	
	public boolean isRegistered() {
		DAOrganization daOrganization = new DAOrganization(this);
		return daOrganization.isRegistered();
	}
	
	public boolean delete() {
		boolean deleted = false;
		DAOrganization daOrganization = new DAOrganization(this);
		if (daOrganization.delete()) {
			deleted = true;
		}
		return deleted;
	}
	
	public boolean isActive() {
		DAOrganization daOrganization = new DAOrganization(this);
		return daOrganization.isActive();
	}
	
	public boolean reactive() {
		boolean reactivated = false;
		DAOrganization daOrganization = new DAOrganization(this);
		if (daOrganization.reactivate()) {
			reactivated = true;
		}
		return reactivated;
	}
	
	public static boolean fillTableOrganization(ObservableList<Organization> listOrganization) {
		boolean filled = false;
		DAOrganization daoOrg = new DAOrganization(new Organization());
		if (daoOrg.fillTableOrganization(listOrganization)) {
			filled = true;
		}
		return filled;
	}

	public static boolean fillSector(ObservableList<String> listOrganization) {
		boolean filled = false;
		DAOrganization daoOrg = new DAOrganization(new Organization());
		if (daoOrg.fillSector(listOrganization)) {
			filled = true;
		}
		return filled;
	}
	
	public static boolean fillOrganizationNames(ObservableList<String> listOrganization) {
		boolean filled = false;
		DAOrganization daoOrganization = new DAOrganization(new Organization());
		if (daoOrganization.fillOrganizationNames(listOrganization)) {
			filled = true;
		}
		return filled;
	}
	
	public String getId() {
		return new DAOrganization(this).getId();
	}

	public static Organization getByName(String name){
		DAOrganization daOrganization = new DAOrganization(new Organization());
		return DAOrganization.getByName(name);
	}


}
