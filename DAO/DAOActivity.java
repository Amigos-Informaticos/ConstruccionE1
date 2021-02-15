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
		String query =
			"INSERT INTO Actividad " +
				"(idPracticante, titulo, descripcion, fechaCreacion, fechaCierre, " +
				"profesorAsignador) " +
				"VALUES (?, ?, ?, (SELECT SYSDATE()), ?, ?)";
		String[] values = {
			new DAOStudent(this.activity.getStudent()).getId(),
			this.activity.getTitle(),
			this.activity.getDescription(),
			this.activity.getDeliveryDate(),
			new DAOProfessor(this.activity.getProfessor()).getId()
		};
		return this.connection.sendQuery(query, values);
	}
	
	@Override
	public boolean update() {
		assert this.activity.getStartDate() != null : "key startDate null on update()";
		String query = "UPDATE Actividad SET titulo = ?, descripcion = ? WHERE fechaCreacion = ?";
		String[] values = {
			this.activity.getTitle(),
			this.activity.getDescription(),
			this.activity.getStartDate()
		};
		return this.connection.sendQuery(query, values);
	}
	
	@Override
	public boolean delete() {
		assert this.activity != null : "Activity null on delete()";
		assert this.isRegistered() : "Activity not registered on delete()";
		String query = "DELETE FROM Actividad WHERE Actividad.idActividad = ?";
		String[] values = {this.getIdActivity()};
		return this.connection.sendQuery(query, values);
	}
	
	public boolean isRegistered() {
		assert this.activity != null : "Activity null on isRegistered()";
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad " +
			"WHERE titulo = ? AND descripcion = ?";
		String[] values = {this.activity.getTitle(), this.activity.getDescription()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.select(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	public String getIdActivity() {
		assert this.isRegistered() : "Activity is not registered on getIdActivity()";
		String query = "SELECT idActividad FROM Actividad WHERE titulo = ? AND descripcion = ?";
		String[] values = {this.activity.getTitle(), this.activity.getDescription()};
		String[] names = {"idActividad"};
		String[][] results = this.connection.select(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public String getStartDate() {
		String query = "SELECT fechaCreacion FROM Actividad WHERE titulo = ?";
		String[] values = {this.activity.getTitle()};
		String[] names = {"fechaInicio"};
		String[][] results = this.connection.select(query, values, names);
		return results != null ? results[0][0] : "";
	}
}
