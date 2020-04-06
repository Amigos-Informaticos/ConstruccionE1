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
	public boolean Register() {
		boolean registered = false;
		if (proyecto.isComplete()) {
			if (!this.isRegistered()) {
				String query = "INSERT INTO Proyecto (nombre, , metodologia, objetivoGeneral, " +
						"objetivoMediato, objetivoInmediato, recursos, responsabilidades, status, " +
						"area, responsable, idPerdiodo, idOrganizacion) VALUES (?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?)";
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
					registered = true;
				}
			}
		}
		return registered;
	}

	/**
	 * Checks if the current proyecto is already registered
	 *
	 * @return true => Already registered | Not registered
	 */
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		String query = "SELECT COUNT(idProyecto) AS TOTAL FROM Proyecto WHERE nombre = ?";
		String[] values = {proyecto.getNombre()};
		String[] names = {"TOTAL"};
		if (this.connection.select(query, values, names)[0][0].equals("1")) {
			isRegistered = true;
		}
		return isRegistered;
	}
}
