package DAO;

import Connection.DBConnection;
import IDAO.IDAOProject;
import Models.CalendarizedActivity;
import Models.Project;
import javafx.collections.ObservableList;

public class DAOProject implements IDAOProject {
	private Project project;
	private final DBConnection connection;
	
	public DAOProject() {
		this.connection = new DBConnection();
	}
	
	public DAOProject(Project project) {
		this.project = project;
		this.connection = new DBConnection();
	}
	
	public DAOProject(String name) {
		this.connection = new DBConnection();
		this.project = this.loadProject(name);
	}
	
	@Override
	public boolean signUp() {
		boolean signedUp = false;
		assert this.project.isComplete() : "Project is incomplete: DAOProject.signUp()";
		if (!this.isRegistered()) {
			if (!this.project.getResponsible().isRegistered()) {
				this.project.getResponsible().signUp();
			}
			String query = "INSERT INTO Proyecto (nombre, " +
				"descripcion, " +
				"metodologia, " +
				"objetivoGeneral, " +
				"objetivoMediato, " +
				"objetivoInmediato, " +
				"recursos, " +
				"responsabilidades, " +
				"cupo, " +
				"area, " +
				"responsable, " +
				"idPeriodo, " +
				"idOrganizacion, " +
				"fechaInicio, " +
				"fechaFin) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String[] values = { this.project.getName(),
				this.project.getDescription(),
				this.project.getMethodology(),
				this.project.getGeneralObjective(),
				this.project.getMediateObjective(),
				this.project.getImmediateObjective(),
				this.project.getResources(),
				this.project.getResponsibilities(),
				String.valueOf(this.project.getCapacity()),
				getIdArea(),
				this.project.getResponsible().getEmail(),
				getIdPeriod(),
				this.project.getOrganization().getId(),
				this.project.getStartDate(),
				this.project.getEndDate()
			};
			signedUp = this.connection.sendQuery(query, values);
		} else if (this.isRegistered() && !this.isActive()) {
			String query = "UPDATE Proyecto SET estaActivo = 1 WHERE nombre = ?";
			String[] values = { this.project.getName() };
			if (this.connection.sendQuery(query, values)) {
				signedUp = true;
			}
		}
		return signedUp;
	}
	
	public boolean registCalendarizedActivities() {
		boolean registered = true;
		CalendarizedActivity[] calendarizedActivities = this.project.getCalendarizedActivities();
		String query = "INSERT INTO ActividadCalendarizada (nombre,fecha,idProyecto) VALUES (?,?,?)";
		for (CalendarizedActivity calendarizedActivity: calendarizedActivities) {
			String[] values = {
				calendarizedActivity.getName(),
				calendarizedActivity.getDate(),
				this.getId()
			};
			if (calendarizedActivity.getName() != null) {
				registered = this.connection.sendQuery(query, values);
			}
		}
		return registered;
	}
	
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		if (this.project != null && this.project.getName() != null) {
			String query = "SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ?";
			String[] values = { project.getName() };
			String[] names = { "TOTAL" };
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}
	
	public Project loadProject(String name) {
		Project project = null;
		if (name != null) {
			String query =
				"SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND estaActivo = 1";
			String[] values = { name };
			String[] names = { "TOTAL" };
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT * FROM Proyecto WHERE nombre = ?";
				String[] results = { "idProyecto", "nombre", "descripcion", "metodologia", "objetivoGeneral",
					"objetivoMediato", "objetivoInmediato", "recursos", "responsabilidades",
					"area", "responsable", "idPeriodo", "idOrganizacion" };
				String[] projectReturned = this.connection.select(query, values, results)[0];
				
				project = new Project();
				
				project.setName(projectReturned[1]);
				project.setDescription(projectReturned[2]);
				project.setMethodology(projectReturned[3]);
				project.setGeneralObjective(projectReturned[4]);
				project.setMediateObjective(projectReturned[5]);
				project.setImmediateObjective(projectReturned[6]);
				project.setResources(projectReturned[7]);
				project.setResponsibilities(projectReturned[8]);
				project.setArea(this.getAreaById(projectReturned[9]));
				project.setResponsible(DAOProjectResponsible.get(projectReturned[10]));
				project.setPeriod(getPeriodById(projectReturned[11]));
				project.setOrganization(DAOrganization.getNameById(projectReturned[12]));
			}
		}
		return project;
	}
	
	@Override
	public boolean delete() {
		boolean deleted = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				if (this.haveStudents()) {
					String query = "DELETE FROM Asignacion WHERE idProyecto = ?;";
					String[] values = { this.getId() };
					this.connection.sendQuery(query, values);
				}
				String query = "UPDATE Proyecto SET estaActivo = 0 WHERE nombre = ?;";
				String[] values = { this.project.getName() };
				deleted = this.connection.sendQuery(query, values);
			} else {
				deleted = true;
			}
		}
		return deleted;
	}
	
	@Override
	public boolean isActive() {
		boolean isActive = false;
		if (this.project != null && this.project.getName() != null &&
			this.isRegistered()) {
			String query = "SELECT estaActivo FROM Proyecto WHERE nombre = ?";
			String[] values = { this.project.getName() };
			String[] names = { "estaActivo" };
			isActive = this.connection.select(query, values, names)[0][0].equals("1");
		}
		return isActive;
	}
	
	@Override
	public boolean reactive() {
		boolean reactivated = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Proyecto SET estaActivo = 1 WHERE nombre = ?";
				String[] values = { this.project.getName() };
				if (this.connection.sendQuery(query, values)) {
					reactivated = true;
				}
			} else {
				reactivated = true;
			}
		}
		return reactivated;
	}
	
	public void registerArea() {
		String query = "SELECT COUNT(area) AS TOTAL FROM Area WHERE area = ?";
		String[] values = { this.project.getArea() };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			query = "INSERT INTO Area (area) VALUES (?)";
			this.connection.sendQuery(query, values);
		}
	}
	
	public void registerPeriod() {
		String query = "SELECT COUNT(periodo) AS TOTAL FROM Periodo WHERE periodo = ?";
		String[] values = { this.project.getPeriod() };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			query = "INSERT INTO Periodo (periodo) VALUES (?)";
			this.connection.sendQuery(query, values);
		}
	}
	
	public String getId() {
		String id = "0";
		String query = "SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1;";
		String[] values = { this.project.getName() };
		String[] names = { "idProyecto" };
		String[][] result = this.connection.select(query, values, names);
		if (!result[0][0].equals("")) {
			id = result[0][0];
		}
		return id;
	}
	
	public String getIdOrganization() {
		assert this.project.getOrganization() != null :
			"Project's organization is null: DAOProject,getIdOrganization";
		return this.project.getOrganization().getId();
	}
	
	public String getIdPeriod() {
		String query = "SELECT COUNT(periodo) AS TOTAL FROM Periodo WHERE periodo = ?";
		String[] values = { this.project.getPeriod() };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			this.registerPeriod();
		}
		query = "SELECT idPeriodo FROM Periodo WHERE periodo = ?;";
		String[] names = { "idPeriodo" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public String getIdArea() {
		String query = "SELECT COUNT(area) AS TOTAL FROM Area WHERE area = ?";
		String[] values = { this.project.getArea() };
		String[] columns = { "TOTAL" };
		if (this.connection.select(query, values, columns)[0][0].equals("0")) {
			this.registerArea();
		}
		query = "SELECT idArea FROM Area WHERE area = ?";
		values = new String[]{ this.project.getArea() };
		String[] names = { "idArea" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public String getAreaById(String idArea) {
		String query = "SELECT area FROM Area WHERE idArea = ?";
		String[] values = { idArea };
		String[] names = { "area" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public String getPeriodById(String idPeriod) {
		String query = "SELECT periodo FROM Periodo WHERE idPeriodo = ?";
		String[] values = { idPeriod };
		String[] names = { "periodo" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public boolean haveStudents() {
		String query = "SELECT COUNT(idProyecto) AS TOTAL FROM Asignacion " +
			"WHERE idProyecto = ? AND estaActivo = 1";
		String[] values = { this.getId() };
		String[] names = { "TOTAL" };
		return !this.connection.select(query, values, names)[0][0].equals("0");
	}
	
	public static Project[] getAll() {
		String query = "SELECT Proyecto.nombre, metodologia, objetivoGeneral, objetivoMediato, " +
			"objetivoInmediato, recursos, responsabilidades, cupo, descripcion, Area.area, " +
			"responsable, Periodo.periodo, Organizacion.nombre, fechaInicio, fechaFin " +
			"FROM Proyecto INNER JOIN Area ON Proyecto.area = Area.idArea " +
			"INNER JOIN Periodo ON Proyecto.idPeriodo = Periodo.idPeriodo " +
			"INNER JOIN Organizacion ON Proyecto.idOrganizacion = Organizacion.idOrganizacion";
		String[] names = {
			"Proyecto.nombre",
			"descripcion",
			"metodologia",
			"objetivoGeneral",
			"objetivoMediato",
			"objetivoInmediato",
			"recursos",
			"responsabilidades",
			"cupo",
			"area",
			"responsable",
			"periodo",
			"Organizacion.nombre",
			"fechaInicio",
			"fechaFin"
		};
		String[][] responses = new DBConnection().select(query, null, names);
		Project[] projects = new Project[responses.length];
		for (int i = 0; i < responses.length; i++) {
			projects[i] = new Project();
			projects[i].setName(responses[i][0]);
			projects[i].setDescription(responses[i][1]);
			projects[i].setMethodology(responses[i][2]);
			projects[i].setGeneralObjective(responses[i][3]);
			projects[i].setMediateObjective(responses[i][4]);
			projects[i].setImmediateObjective(responses[i][5]);
			projects[i].setResources(responses[i][6]);
			projects[i].setResponsibilities(responses[i][7]);
			projects[i].setCapacity(Integer.parseInt(responses[i][8]));
			projects[i].setArea(responses[i][9]);
			projects[i].setResponsible(DAOProjectResponsible.get(responses[i][10]));
			projects[i].setPeriod(responses[i][11]);
			projects[i].setOrganization(responses[i][12]);
			projects[i].setStartDate(responses[i][13]);
			projects[i].setEndDate(responses[i][14]);
		}
		return projects;
	}
	
	public static Project getByName(String name) {
		assert name != null : "Name is null: DAOProject.getByName()";
		DBConnection connection = new DBConnection();
		Project project;
		String query = "SELECT Proyecto.nombre, metodologia, objetivoGeneral, objetivoMediato, " +
			"objetivoInmediato, recursos, responsabilidades, cupo, descripcion, Area.area, " +
			"responsable, Periodo.periodo, Organizacion.nombre, fechaInicio, fechaFin " +
			"FROM Proyecto INNER JOIN Area ON Proyecto.area = Area.idArea " +
			"INNER JOIN Periodo ON Proyecto.idPeriodo = Periodo.idPeriodo " +
			"INNER JOIN Organizacion ON Proyecto.idOrganizacion = Organizacion.idOrganizacion " +
			"WHERE Proyecto.nombre = ?";
		String[] values = { name };
		String[] columns = {
			"Proyecto.nombre",
			"descripcion",
			"metodologia",
			"objetivoGeneral",
			"objetivoMediato",
			"objetivoInmediato",
			"recursos",
			"responsabilidades",
			"cupo",
			"area",
			"responsable",
			"periodo",
			"Organizacion.nombre",
			"fechaInicio",
			"fechaFin"
		};
		String[] responses = connection.select(query, values, columns)[0];
		project = new Project();
		project.setName(responses[0]);
		project.setDescription(responses[1]);
		project.setMethodology(responses[2]);
		project.setGeneralObjective(responses[3]);
		project.setMediateObjective(responses[4]);
		project.setImmediateObjective(responses[5]);
		project.setResources(responses[6]);
		project.setResponsibilities(responses[7]);
		project.setCapacity(Integer.parseInt(responses[8]));
		project.setArea(responses[9]);
		project.setResponsible(DAOProjectResponsible.get(responses[10]));
		project.setPeriod(responses[11]);
		project.setOrganization(responses[12]);
		project.setStartDate(responses[13]);
		project.setEndDate(responses[14]);
		return project;
		
	}
	
	public boolean fillAreaTable(ObservableList<String> listAreas) {
		boolean filled = false;
		String query = "SELECT area FROM Area";
		for (String[] name: this.connection.select(query, null, new String[]{ "area" })) {
			listAreas.add(name[0]);
			filled = true;
		}
		return filled;
	}
}