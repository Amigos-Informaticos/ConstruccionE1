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

	/**
	 * Method to sign up a new project
	 * <p>
	 * STATUS DESCRIPTION<br/>
	 * 0	->	Initial status: no action has been taken<br/>
	 * 1	->	Success
	 * 2	->	Error in sending query
	 * 3	->	project already registered
	 * 4	->	Malformed object
	 * </p>
	 *
	 * @return true => succesfully registered | false => couldn't register
	 */
	@Override
	public boolean signUp() throws CustomException{
		boolean signedUp = false;
		if (this.project.isComplete()) {
			if (!this.isRegistered()) {
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
					System.out.println("SÍ SE REGISTRÓ");
				}else{
					throw new CustomException("Not registered proyecto");
				}
			} else {
				String query = "UPDATE Proyecto SET status = 1 WHERE nombre = ?";
				String[] values = {this.project.getName()};
				if (this.connection.sendQuery(query, values)) {
					signedUp = true;
				}
			}
		}else{
			throw new CustomException("Null pointer exception");
		}
		return signedUp;
	}

	/**
	 * Checks if the current project is already registered
	 *
	 * @return true => Already registered | Not registered
	 */
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		if (this.project != null && this.project.getName() != null) {
			String query = "SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND " +
				"status = 1";
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
	public boolean delete() {
		boolean deleted = false;
		if (this.project != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Proyecto SET status = 0 WHERE nombre = ?;";
				String[] values = {this.project.getName()};
				if (this.connection.sendQuery(query, values)) {
					deleted = true;
					System.out.println("OK 1");
				}
			} else {
				deleted = true;
				System.out.println("OK 2");
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
}
