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
	public boolean signUp() throws CustomException{
		boolean signedUp = false;
		if (this.project.isComplete()) {
			if (!this.isRegistered() && !this.isActive()) {
				String query = "INSERT INTO Proyecto (nombre, metodologia, " +
					"objetivoGeneral, objetivoMediato, objetivoInmediato, recursos, " +
					"responsabilidades, status, area, responsable, idPeriodo, idOrganizacion)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				String[] values = {this.project.getName(),
					this.project.getMethodology(),
					this.project.getGeneralObjective(),
					this.project.getMediateObjective(),
					this.project.getImmediateObjective(),
					this.project.getResources(),
					this.project.getResponsibilities(),
					this.project.getStatus(),
					this.project.getArea(),
					this.project.getResponsible(),
					this.project.getIdPeriod(),
					this.project.getIdOrganization()
				};
				if (this.connection.sendQuery(query, values)) {
					signedUp = true;
				}
			} else if(this.isRegistered() && !this.isActive()){
				String query = "UPDATE Proyecto SET status = 1 WHERE nombre = ?";
				String[] values = {this.project.getName()};
				if (this.connection.sendQuery(query, values)) {
					signedUp = true;
				}
			}else if(this.isActive()){
				throw new CustomException("Project already registered and active");
			}
		}else{
			throw new CustomException("Null pointer exception");
		}
		return signedUp;
	}

	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		if (this.project != null && this.project.getName() != null) {
			String query = "SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ?";
			String[] values = {project.getName()};
			String[] names = {"TOTAL"};
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
				"SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND status =1";
			String[] values = {name};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT * FROM Proyecto WHERE nombre = ?";
				String[] results = {"idProyecto", "nombre", "metodologia", "objetivoGeneral",
					"objetivoMediato", "objetivoInmediato", "recursos", "responsabilidades",
					"status", "area", "responsable", "idPeriodo", "idOrganizacion"};
				String[][] projectReturned = this.connection.select(query, values, results);

				project = new Project();

				project.setName(projectReturned[0][1]);
				project.setMethodology(projectReturned[0][2]);
				project.setGeneralObjective(projectReturned[0][3]);
				project.setMediateObjective(projectReturned[0][4]);
				project.setImmediateObjective(projectReturned[0][5]);
				project.setResources(projectReturned[0][6]);
				project.setResponsibilities(projectReturned[0][7]);
				project.setStatus(projectReturned[0][8]);
				project.setArea(projectReturned[0][9]);
				project.setResponsible(projectReturned[0][10]);
				project.setIdPeriod(projectReturned[0][11]);
				project.setIdOrganization(projectReturned[0][12]);
			}
		}
		return project;
	}

	@Override
	public boolean delete() throws CustomException{
		boolean deleted = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				if(this.haveStudents()){
					String query = "DELETE FROM PracticanteProyecto WHERE idProyecto = ?;";
					String[] values = {this.getId()};
					if(!this.connection.sendQuery(query,values)){
						throw new CustomException
								("Impossible to delete the relation between Project and Student");
					}
				}
				String query = "UPDATE Proyecto SET status = 0 WHERE nombre = ?;";
				String[] values = {this.project.getName()};

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
			String query = "SELECT status FROM Proyecto WHERE nombre = ?";
			String[] values = {this.project.getName()};
			String[] names = {"status"};
			isActive = this.connection.select(query, values, names)[0][0].equals("1");
		}
		return isActive;
	}

	@Override
	public boolean reactive() {
		boolean reactivated = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Proyecto SET status = 1 WHERE nombre = ?";
				String[] values = {this.project.getName()};
				if (this.connection.sendQuery(query, values)) {
					reactivated = true;
				}
			} else {
				reactivated = true;
			}
		}
		return reactivated;
	}

	public String getId(){
		String id = "0";
		String query = "SELECT idProyecto FROM Proyecto WHERE nombre = ? AND status = 1;";
		String[] values = {this.project.getName()};
		String[] names = {"idProyecto"};
		String[][] result = this.connection.select(query,values,names);
		if(!result[0][0].equals("")){
			id = result[0][0];
		}
		return id;
	}

	public boolean haveStudents(){
		boolean withStudents = false;
		String query = "SELECT idProyecto FROM PracticanteProyecto WHERE idProyecto = ?";
		String[] values = {this.getId()};
		String[] names = {"idProyecto"};
		String[][] result = this.connection.select(query,values,names);
		if(!result[0][0].equals("")){
			withStudents = true;
		}
		return withStudents;
	}

	public static Project[] getAll(){
		String query =
				"SELECT nombre, " +
						"metodologia, " +
						"objetivoGeneral, " +
						"objetivoMediato, " +
						"objetivoInmediato, " +
						"recursos, " +
						"responsabilidades " +
						"FROM Project";
		String[] names =
						{"nombre",
						"metodologia",
						"objetivoGeneral",
						"objetivoMediato",
						"objetivoInmediato",
						"recursos",
						"responsabilidades"};
		String[][] responses = new DBConnection().select(query, null, names);
		Project[] projects = new Project[responses.length];
		for (int i = 0; i < responses.length; i++) {
			projects[i] = new Project(
					responses[i][0],
					responses[i][1],
					responses[i][2],
					responses[i][3],
					responses[i][4],
					responses[i][5],
					responses[i][6]
			);
		}
		return projects;
	}
}