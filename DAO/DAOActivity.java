package DAO;

import Connection.DBConnection;
import IDAO.IDAOActivity;
import Models.Activity;

public class DAOActivity implements IDAOActivity {
	private final Activity activity;
	private final DBConnection connection = new DBConnection();
	
	public DAOActivity(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public boolean create() {
		assert this.activity != null : "Activity null on create()";
		assert this.activity.isComplete() : "Some atribute is empty";
		boolean created = false;
		String query =
			"INSERT INTO Actividad " +
				"(idPracticante, titulo, descripcion, fechaCreacion, fechaCierre) " +
				"VALUES ((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), " +
				"?, ?, (SELECT SYSDATE()), ?)";
		String[] values = { "edsonn1999@hotmail.com", this.activity.getTitle(), this.activity.getDescription(),
			this.activity.getDeliveryDate() };
		if (this.connection.sendQuery(query, values)) {
			this.activity.setStartDate(this.getStartDate());
			created = true;
		}
		return created;
	}
	
	@Override
	public boolean update() {
		assert this.activity.getStartDate() != null : "key startDate null on update()";
		String query = "UPDATE Actividad SET titulo = ?, descripcion = ? WHERE fechaCreacion = ?";
		String[] values = { this.activity.getTitle(),
			this.activity.getDescription(),
			this.activity.getStartDate() };
		return this.connection.sendQuery(query, values);
	}
	
	
	@Override
	public boolean delete() {
		assert this.activity != null : "Activity null on delete()";
		assert this.isRegistered() : "Activity not registered on delete()";
		String query = "DELETE FROM Actividad WHERE fechaCreacion = ?";
		String[] values = { this.activity.getStartDate() };
		return this.connection.sendQuery(query, values);
	}
	
	public boolean isRegistered() {
		assert this.activity != null : "Activity null on isRegistered()";
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad " +
			"WHERE titulo = ? AND descripcion = ?";
		String[] values = { this.activity.getTitle(), this.activity.getDescription() };
		String[] names = { "TOTAL" };
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public String getIdActivity() {
		assert this.isRegistered() : "Activity is not registered on getIdActivity()";
		String query = "SELECT idActividad FROM Actividad WHERE titulo = ? AND descripcion = ?";
		String[] values = { this.activity.getTitle(), this.activity.getDescription() };
		String[] names = { "idActividad" };
		return this.connection.select(query, values, names)[0][0];
	}
	
	public String getStartDate() {
		String query = "SELECT fechaCreacion FROM Actividad WHERE titulo = ?";
		String[] values = { this.activity.getTitle() };
		String[] names = { "fechaInicio" };
		return this.connection.select(query, values, names)[0][0];
	}
}
