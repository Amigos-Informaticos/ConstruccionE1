package DAO;

import Connection.DBConnection;
import IDAO.IDAOProyecto;
import Models.Proyecto;

public class DAOProyecto implements IDAOProyecto {
	private Proyecto proyecto;
	protected DBConnection connection;

	public DAOProyecto() {
		this.connection = new DBConnection();
	}

	public DAOProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
		this.connection = new DBConnection();
	}

	public DAOProyecto(String name) {
		this.connection = new DBConnection();
		this.proyecto = this.loadProyecto(name);
	}

	/**
	 * Method to sign up a new proyecto
	 * <p>
	 * STATUS DESCRIPTION<br/>
	 * 0	->	Initial status: no action has been taken<br/>
	 * 1	->	Success
	 * 2	->	Error in sending query
	 * 3	->	proyecto already registered
	 * 4	->	Malformed object
	 * </p>
	 *
	 * @return true => succesfully registered | false => couldn't register
	 */
	@Override
	public boolean signUp() {
		boolean signedUp = false;
		if (this.proyecto.isComplete()) {
			if (!this.isRegistered()) {
				String query = "INSERT INTO Proyecto (idProyecto, nombre, metodologia, " +
					"objetivoGeneral, objetivoMediato, objetivoInmediato, recursos, " +
					"responsabilidades, status, area, responsable, idPeriodo, idOrganizacion)" +
					"VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				String[] values = {this.proyecto.getNombre(),
					this.proyecto.getMetodologia(),
					this.proyecto.getObjetivoGeneral(),
					this.proyecto.getObjetivoMediato(),
					this.proyecto.getObjetivoInmediato(),
					this.proyecto.getRecursos(),
					this.proyecto.getResponsabilidades(),
					this.proyecto.getStatus(),
					this.proyecto.getArea(),
					this.proyecto.getResponsable(),
					this.proyecto.getIdPeriodo(),
					this.proyecto.getIdOrganizacion()
				};
				if (this.connection.preparedQuery(query, values)) {
					signedUp = true;
				}
			}
		}
		return signedUp;
	}

	/**
	 * Checks if the current proyecto is already registered
	 *
	 * @return true => Already registered | Not registered
	 */
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		if (this.proyecto != null && this.proyecto.getNombre() != null) {
			String query = "SELECT COUNT(nombre) AS TOTAL FROM Proyecto WHERE nombre = ? AND " +
				"status = 1";
			String[] values = {proyecto.getNombre()};
			String[] names = {"TOTAL"};
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}

	public Proyecto loadProyecto(String name) {
		Proyecto proyecto = null;
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
				String[][] project = this.connection.select(query, values, results);

				proyecto = new Proyecto();

				proyecto.setNombre(project[0][1]);
				proyecto.setMetodologia(project[0][2]);
				proyecto.setObjetivoGeneral(project[0][3]);
				proyecto.setObjetivoMediato(project[0][4]);
				proyecto.setObjetivoInmediato(project[0][5]);
				proyecto.setRecursos(project[0][6]);
				proyecto.setResponsabilidades(project[0][7]);
				proyecto.setStatus(project[0][8]);
				proyecto.setArea(project[0][9]);
				proyecto.setResponsable(project[0][10]);
				proyecto.setIdPeriodo(project[0][11]);
				proyecto.setIdOrganizacion(project[0][12]);
			}
		}
		return proyecto;
	}
}
