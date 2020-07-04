package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOProject;
import Models.Project;

public class DAOProject implements IDAOProject {
	private Project project;
	protected DBConnection connection;
	
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
	public boolean signUp() throws CustomException {
		boolean signedUp = false;
		assert this.project.isComplete() : "Project is incomplete: DAOProject.signUp()";
		if (!this.isRegistered() && !this.isActive()) {
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
				"fechaFin)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String[] values = { this.project.getName(),
				this.project.getDescription(),
				this.project.getMethodology(),
				this.project.getGeneralObjective(),
				this.project.getMediateObjective(),
				this.project.getImmediateObjective(),
				this.project.getResources(),
				this.project.getResponsibilities(),
				this.project.getCapacity(),
				getIdArea(),
				this.project.getResponsible().getEmail(),
				getIdPeriod(),
				getIdOrganization(),
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
		} else if (this.isActive()) {
			throw new CustomException("Project already registered and active");
		}
		return signedUp;
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
				"SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND estaActivo =1";
			String[] values = { name };
			String[] names = { "TOTAL" };
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT * FROM Proyecto WHERE nombre = ?";
				String[] results = { "idProyecto", "nombre", "metodologia", "objetivoGeneral",
					"objetivoMediato", "objetivoInmediato", "recursos", "responsabilidades",
					"estaActivo", "area", "responsable", "idPeriodo", "idOrganizacion" };
				String[] projectReturned = this.connection.select(query, values, results)[0];
				
				project = new Project();
				
				project.setName(projectReturned[1]);
				project.setMethodology(projectReturned[2]);
				project.setGeneralObjective(projectReturned[3]);
				project.setMediateObjective(projectReturned[4]);
				project.setImmediateObjective(projectReturned[5]);
				project.setResources(projectReturned[6]);
				project.setResponsibilities(projectReturned[7]);
				project.setStatus(projectReturned[8]);
				project.setArea(projectReturned[9]);
				project.setResponsible(DAOProjectResponsible.get(projectReturned[10]));
				project.setPeriod(projectReturned[11]);
				project.setOrganization(projectReturned[12]);
			}
		}
		return project;
	}
	
	@Override
	public boolean delete() throws CustomException {
		boolean deleted = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				if (this.haveStudents()) {
					String query = "DELETE FROM PracticanteProyecto WHERE idProyecto = ?;";
					String[] values = { this.getId() };
					if (!this.connection.sendQuery(query, values)) {
						throw new CustomException
							("Impossible to delete the relation between Project and Student");
					}
				}
				String query = "UPDATE Proyecto SET estaActivo = 0 WHERE nombre = ?;";
				String[] values = { this.project.getName() };
				
				if (this.connection.sendQuery(query, values)) {
					
					deleted = true;
				}
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
		String id = "0";
		String query = "SELECT idPeriodo FROM Periodo WHERE periodo = ?;";
		String[] values = { this.project.getPeriod() };
		String[] names = { "idPeriodo" };
		String[][] result = this.connection.select(query, values, names);
		if (!result[0][0].equals("")) {
			id = result[0][0];
		}
		return id;
	}
	
	public String getIdArea() {
		String id = "0";
		String query = "SELECT idArea FROM Area WHERE area = ?;";
		String[] values = { this.project.getArea() };
		String[] names = { "idArea" };
		String[][] result = this.connection.select(query, values, names);
		if (!result[0][0].equals("")) {
			id = result[0][0];
		}
		return id;
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
			"responsable, Periodo.periodo, Organizacion.nombre, fechaInicio, fechaFin, " +
			"Proyecto.estaActivo " +
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
			"estaActivo",
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
			projects[i].setStatus(responses[i][8]);
			projects[i].setCapacity(responses[i][9]);
			projects[i].setArea(responses[i][10]);
			projects[i].setResponsible(DAOProjectResponsible.get(responses[i][11]));
			projects[i].setPeriod(responses[i][12]);
			projects[i].setOrganization(responses[i][13]);
			projects[i].setStartDate(responses[i][14]);
			projects[i].setEndDate(responses[i][15]);
		}
		return projects;
	}
}