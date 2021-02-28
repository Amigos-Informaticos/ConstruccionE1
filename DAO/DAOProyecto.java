package DAO;

import Connection.ConexionBD;
import IDAO.IDAOProject;
import Models.CalendarizedActivity;
import Models.Proyecto;
import javafx.collections.ObservableList;

public class DAOProyecto implements IDAOProject {
	private Proyecto proyecto;
	private final ConexionBD connection;
	
	public DAOProyecto() {
		this.connection = new ConexionBD();
	}
	
	public DAOProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
		this.connection = new ConexionBD();
	}
	
	public DAOProyecto(String name) {
		this.connection = new ConexionBD();
		this.proyecto = this.cargarProyecto(name);
	}
	
	@Override
	public boolean signUp() {
		boolean signedUp = false;
		assert this.proyecto.estaCompleto() : "Project is incomplete: DAOProject.signUp()";
		if (!this.isRegistered()) {
			if (!this.proyecto.getResponsible().isRegistered()) {
				this.proyecto.getResponsible().signUp();
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
			String[] values = {this.proyecto.getNombre(),
				this.proyecto.getDescription(),
				this.proyecto.getMethodology(),
				this.proyecto.getGeneralObjective(),
				this.proyecto.getMediateObjective(),
				this.proyecto.getImmediateObjective(),
				this.proyecto.getResources(),
				this.proyecto.getResponsibilities(),
				String.valueOf(this.proyecto.getCapacity()),
				getIdArea(),
				this.proyecto.getResponsible().getEmail(),
				getIdPeriod(),
				this.proyecto.getOrganization().getId(),
				this.proyecto.getStartDate(),
				this.proyecto.getEndDate()
			};
			signedUp = this.connection.executar(query, values);
		} else if (this.isRegistered() && !this.isActive()) {
			String query = "UPDATE Proyecto SET estaActivo = 1 WHERE nombre = ?";
			String[] values = {this.proyecto.getNombre()};
			if (this.connection.executar(query, values)) {
				signedUp = true;
			}
		}
		return signedUp;
	}
	
	public boolean registCalendarizedActivities() {
		boolean registered = true;
		CalendarizedActivity[] calendarizedActivities = this.proyecto.getCalendarizedActivities();
		String query = "INSERT INTO ActividadCalendarizada (nombre,fecha,idProyecto) VALUES (?,?,?)";
		for (CalendarizedActivity calendarizedActivity: calendarizedActivities) {
			String[] values = {
				calendarizedActivity.getName(),
				calendarizedActivity.getDate(),
				this.getId()
			};
			if (calendarizedActivity.getName() != null) {
				registered = this.connection.executar(query, values);
			}
		}
		return registered;
	}
	
	@Override
	public boolean isRegistered() {
		assert this.proyecto != null : "Project is null: DAOProject.isRegistered()";
		assert this.proyecto.getNombre() != null : "Projects name is null: DAOProject.isRegistered()";
		
		String query = "SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ?";
		String[] values = {proyecto.getNombre()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	public Proyecto cargarProyecto(String name) {
		assert name != null : "Name is null: DAOProject.loadProject()";
		Proyecto proyecto = null;
		String query =
			"SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND estaActivo = 1";
		String[] values = {name};
		String[] names = {"TOTAL"};
		String[][] selection = this.connection.seleccionar(query, values, names);
		if (selection != null && selection[0][0].equals("1")) {
			query = "SELECT * FROM Proyecto WHERE nombre = ?";
			String[] results = {"idProyecto", "nombre", "descripcion", "metodologia",
				"objetivoGeneral", "objetivoMediato", "objetivoInmediato", "recursos",
				"responsabilidades", "area", "responsable", "idPeriodo", "idOrganizacion"};
			selection = this.connection.seleccionar(query, values, results);
			if (selection != null) {
				String[] projectReturned = this.connection.seleccionar(query, values, results)[0];
				
				proyecto = new Proyecto();
				proyecto.setName(projectReturned[1]);
				proyecto.setDescription(projectReturned[2]);
				proyecto.setMethodology(projectReturned[3]);
				proyecto.setGeneralObjective(projectReturned[4]);
				proyecto.setMediateObjective(projectReturned[5]);
				proyecto.setImmediateObjective(projectReturned[6]);
				proyecto.setResources(projectReturned[7]);
				proyecto.setResponsibilities(projectReturned[8]);
				proyecto.setArea(this.getAreaById(projectReturned[9]));
				proyecto.setResponsible(DAO.DAOProjectResponsible.get(projectReturned[10]));
				proyecto.setPeriod(getPeriodById(projectReturned[11]));
				proyecto.setOrganization(DAOOrganizacion.getNameById(projectReturned[12]));
			}
		}
		return proyecto;
	}
	
	@Override
	public boolean delete() {
		boolean deleted = false;
		if (this.proyecto != null && this.isRegistered()) {
			if (this.isActive()) {
				if (this.haveStudents()) {
					String query = "DELETE FROM Asignacion WHERE idProyecto = ?;";
					String[] values = {this.getId()};
					this.connection.executar(query, values);
				}
				String query = "UPDATE Proyecto SET estaActivo = 0 WHERE nombre = ?;";
				String[] values = {this.proyecto.getNombre()};
				deleted = this.connection.executar(query, values);
			} else {
				deleted = true;
			}
		}
		return deleted;
	}
	
	@Override
	public boolean isActive() {
		boolean isActive = false;
		if (this.proyecto != null && this.proyecto.getNombre() != null &&
			this.isRegistered()) {
			String query = "SELECT estaActivo FROM Proyecto WHERE nombre = ?";
			String[] values = {this.proyecto.getNombre()};
			String[] names = {"estaActivo"};
			String[][] results = this.connection.seleccionar(query, values, names);
			isActive = results != null && results[0][0].equals("1");
		}
		return isActive;
	}
	
	@Override
	public boolean reactivate() {
		boolean reactivated = false;
		if (this.proyecto != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Proyecto SET estaActivo = 1 WHERE nombre = ?";
				String[] values = {this.proyecto.getNombre()};
				reactivated = this.connection.executar(query, values);
			} else {
				reactivated = true;
			}
		}
		return reactivated;
	}
	
	public void registerArea() {
		String query = "SELECT COUNT(area) AS TOTAL FROM Area WHERE area = ?";
		String[] values = {this.proyecto.getArea()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, columns);
		if (results != null && results[0][0].equals("0")) {
			query = "INSERT INTO Area (area) VALUES (?)";
			this.connection.executar(query, values);
		}
	}
	
	public void registerPeriod() {
		String query = "SELECT COUNT(periodo) AS TOTAL FROM Periodo WHERE periodo = ?";
		String[] values = {this.proyecto.getPeriod()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, columns);
		if (results != null && results[0][0].equals("0")) {
			query = "INSERT INTO Periodo (periodo) VALUES (?)";
			this.connection.executar(query, values);
		}
	}
	
	public String getId() {
		String id;
		String query = "SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1;";
		String[] values = {this.proyecto.getNombre()};
		String[] names = {"idProyecto"};
		String[][] result = this.connection.seleccionar(query, values, names);
		id = result != null ? result[0][0] : "";
		return id;
	}
	
	public String getIdOrganization() {
		assert this.proyecto.getOrganization() != null :
			"Project's organization is null: DAOProject,getIdOrganization";
		return this.proyecto.getOrganization().getId();
	}
	
	public String getIdPeriod() {
		String query = "SELECT COUNT(periodo) AS TOTAL FROM Periodo WHERE periodo = ?";
		String[] values = {this.proyecto.getPeriod()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, columns);
		if (results != null && results[0][0].equals("0")) {
			this.registerPeriod();
		}
		query = "SELECT idPeriodo FROM Periodo WHERE periodo = ?;";
		String[] names = {"idPeriodo"};
		results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public String getIdArea() {
		String query = "SELECT COUNT(area) AS TOTAL FROM Area WHERE area = ?";
		String[] values = {this.proyecto.getArea()};
		String[] columns = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, columns);
		if (results != null && results[0][0].equals("0")) {
			this.registerArea();
		}
		query = "SELECT idArea FROM Area WHERE area = ?";
		values = new String[] {this.proyecto.getArea()};
		String[] names = {"idArea"};
		results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public String getAreaById(String idArea) {
		String query = "SELECT area FROM Area WHERE idArea = ?";
		String[] values = {idArea};
		String[] names = {"area"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public String getPeriodById(String idPeriod) {
		String query = "SELECT periodo FROM Periodo WHERE idPeriodo = ?";
		String[] values = {idPeriod};
		String[] names = {"periodo"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public boolean haveStudents() {
		String query = "SELECT COUNT(idProyecto) AS TOTAL FROM Asignacion " +
			"WHERE idProyecto = ? AND estaActivo = 1";
		String[] values = {this.getId()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && !results[0][0].equals("0");
	}
	
	public static Proyecto[] getAll() {
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
		String[][] responses = new ConexionBD().seleccionar(query, null, names);
		Proyecto[] proyectos = new Proyecto[responses.length];
		for (int i = 0; i < responses.length; i++) {
			proyectos[i] = new Proyecto();
			proyectos[i].setName(responses[i][0]);
			proyectos[i].setDescription(responses[i][1]);
			proyectos[i].setMethodology(responses[i][2]);
			proyectos[i].setGeneralObjective(responses[i][3]);
			proyectos[i].setMediateObjective(responses[i][4]);
			proyectos[i].setImmediateObjective(responses[i][5]);
			proyectos[i].setResources(responses[i][6]);
			proyectos[i].setResponsibilities(responses[i][7]);
			proyectos[i].setCapacity(Integer.parseInt(responses[i][8]));
			proyectos[i].setArea(responses[i][9]);
			proyectos[i].setResponsible(DAOProjectResponsible.get(responses[i][10]));
			proyectos[i].setPeriod(responses[i][11]);
			proyectos[i].setOrganization(responses[i][12]);
			proyectos[i].setStartDate(responses[i][13]);
			proyectos[i].setEndDate(responses[i][14]);
		}
		return proyectos;
	}
	
	public static Proyecto getByName(String name) {
		assert name != null : "Name is null: DAOProject.getByName()";
		ConexionBD connection = new ConexionBD();
		Proyecto proyecto;
		String query = "SELECT Proyecto.nombre, metodologia, objetivoGeneral, objetivoMediato, " +
			"objetivoInmediato, recursos, responsabilidades, cupo, descripcion, Area.area, " +
			"responsable, Periodo.periodo, Organizacion.nombre, fechaInicio, fechaFin " +
			"FROM Proyecto INNER JOIN Area ON Proyecto.area = Area.idArea " +
			"INNER JOIN Periodo ON Proyecto.idPeriodo = Periodo.idPeriodo " +
			"INNER JOIN Organizacion ON Proyecto.idOrganizacion = Organizacion.idOrganizacion " +
			"WHERE Proyecto.nombre = ?";
		String[] values = {name};
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
		String[] responses = connection.seleccionar(query, values, columns)[0];
		proyecto = new Proyecto();
		proyecto.setName(responses[0]);
		proyecto.setDescription(responses[1]);
		proyecto.setMethodology(responses[2]);
		proyecto.setGeneralObjective(responses[3]);
		proyecto.setMediateObjective(responses[4]);
		proyecto.setImmediateObjective(responses[5]);
		proyecto.setResources(responses[6]);
		proyecto.setResponsibilities(responses[7]);
		proyecto.setCapacity(Integer.parseInt(responses[8]));
		proyecto.setArea(responses[9]);
		proyecto.setResponsible(DAOProjectResponsible.get(responses[10]));
		proyecto.setPeriod(responses[11]);
		proyecto.setOrganization(responses[12]);
		proyecto.setStartDate(responses[13]);
		proyecto.setEndDate(responses[14]);
		return proyecto;
		
	}
	
	public boolean fillAreaTable(ObservableList<String> listAreas) {
		boolean filled = false;
		String query = "SELECT area FROM Area";
		for (String[] name: this.connection.seleccionar(query, null, new String[] {"area"})) {
			listAreas.add(name[0]);
			filled = true;
		}
		return filled;
	}
}