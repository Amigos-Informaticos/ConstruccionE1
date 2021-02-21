package DAO;

import Connection.ConexionBD;
import IDAO.IDAOCoordinator;
import IDAO.Shift;
import Models.Coordinator;

public class DAOCoordinator implements IDAOCoordinator, Shift {
	private Coordinator coordinator;
	private final ConexionBD connection = new ConexionBD();
	
	public DAOCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}
	
	@Override
	public boolean registrarse() {
		assert this.coordinator.estaCompleto() : "Coordinator isnt complete: DAOCoordinator.signUp()";
		assert !this.estaRegistrado() : "Coordinator is already registered: DAOCoordinator.signUp()";
		assert !this.isAnother() : "There is another coordinator: DAOCoordinator.signUp()";
		boolean signedUp = false;
		String query =
			"INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, contrasena, estaActivo)" +
				"VALUES (?, ?, ?, ?, ?)";
		String[] values =
			{this.coordinator.getNombres(),
				this.coordinator.getApellidos(),
				this.coordinator.getEmail(),
				this.coordinator.getContrasena(), "1"};
		if (this.connection.executar(query, values)) {
			query = "INSERT INTO Coordinador (idMiembro, noPersonal, fechaRegistro, turno, registrador) VALUES " +
				"((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?),?,(SELECT CURRENT_DATE), ?,?)";
			values = new String[] {this.coordinator.getEmail(), this.coordinator.getPersonalNo(), "1", "16"};
			signedUp = this.connection.executar(query, values);
		}
		return signedUp;
	}
	
	public boolean update() {
		assert coordinator != null : "Coordinator is null : update()";
		assert coordinator.estaCompleto() : "Coordinator is not complete : update()";
		assert this.isActive() : "Coordinator has not a status active on the system";
		boolean updated = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getNombres(), this.coordinator.getApellidos(),
			this.coordinator.getEmail()};
		if (this.connection.executar(query, values)) {
			query = "UPDATE Coordinador SET noPersonal = ? WHERE idMiembro = ?";
			values = new String[] {this.coordinator.getPersonalNo(), this.getIdCoordinator()};
			if (this.connection.executar(query, values)) {
				updated = true;
			}
		}
		return updated;
	}
	
	@Override
	public boolean estaRegistrado() {
		assert this.coordinator != null;
		assert this.coordinator.getEmail() != null;
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
			"INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro " +
			"WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean iniciarSesion() {
		assert this.estaRegistrado() : "Coordinator is not registered: DAOCoordinator.logIn()";
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM Coordinador INNER JOIN MiembroFEI " +
			"ON Coordinador.idMiembro = MiembroFEI.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] values = {this.coordinator.getEmail(), this.coordinator.getContrasena()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean eliminar() {
		assert this.coordinator != null : "Coordinator is null: DAOCoordinator.delete()";
		assert this.estaRegistrado() : "Coordinator is not registered: DAOCoordinator.delete()";
		assert this.isActive() : "Coordinator is not active: DAOCoordinator.delete()";
		String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		return this.connection.executar(query, values);
	}
	
	public boolean isActive() {
		assert this.coordinator != null : "Coordinator is null: DAOCoordinator.isActive()";
		assert this.coordinator.getEmail() != null :
			"Coordinator email is null: DAOCoordinator.isActive()";
		assert this.estaRegistrado() : "Coordinator is not registered: DAOCoordinator.isActive()";
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		String[] names = {"estaActivo"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean reactive() {
		assert this.coordinator != null : "Coordinator is null: DAOCoordinator.reactive()";
		assert this.estaRegistrado() : "Coordinator is not registered: DAOCoordinator.reactive()";
		assert this.isActive() : "Coordinator is not active: DAOCoordinator.reactive()";
		String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		return this.connection.executar(query, values);
	}
	
	public boolean isAnother() {
		String query = "SELECT COUNT (Coordinador.idMiembro) AS TOTAL FROM Coordinador " +
			"INNER JOIN MiembroFEI ON Coordinador.idMiembro = MiembroFEI.idMiembro WHERE MiembroFEI.estaActivo = 1";
		String[] names = {"TOTAL"};
		String[][] resultQuery = this.connection.seleccionar(query, null, names);
		return resultQuery != null && Integer.parseInt(resultQuery[0][0]) > 0;
	}
	
	@Override
	public String getShift() {
		assert this.coordinator != null : "Professor is null: DAOCoordinator.getShift()";
		assert this.coordinator.getEmail() != null :
			"Professor's email is null: DAOProfessor.getShift()";
		String query = "SELECT turno FROM Turno " +
			"INNER JOIN Coordinador ON Turno.idTurno = Coordinador.turno " +
			"INNER JOIN MiembroFEI ON Coordinador.idMiembro = MiembroFEI.idMiembro " +
			"WHERE MiembroFEI.correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		String[] responses = {"turno"};
		String[][] results = this.connection.seleccionar(query, values, responses);
		return results != null ? results[0][0] : "";
	}
	
	public static Coordinator getActive() {
		Coordinator coordinator = new Coordinator();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena noPersonal, fechaRegistro, Turno.turno FROM " +
			"MiembroFEI INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro INNER JOIN Turno ON " +
			"Turno.idTurno = Coordinador.turno WHERE estaActivo = 1";
		String[] names = {"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "fechaRegistro", "turno"};
		String[][] responses = new ConexionBD().seleccionar(query, null, names);
		if (responses.length > 0) {
			coordinator.setNombres(responses[0][0]);
			coordinator.setApellidos(responses[0][1]);
			coordinator.setEmail(responses[0][2]);
			coordinator.setContrasena(responses[0][3]);
			coordinator.setPersonalNo(responses[0][4]);
			coordinator.setRegistrationDate(responses[0][5]);
			coordinator.setShift(responses[0][6]);
		}
		return coordinator;
	}
	
	public static Coordinator[] getAll() {
		String query =
			"SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, estaActivo, fechaRegistro, fechaBaja FROM " +
				"MiembroFEI INNER JOIN Coordinador ON MiembroFEI.idMiembro = Coordinador.idMiembro WHERE estaActivo=1";
		String[] names =
			{"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "estaActivo", "fechaRegistro",
				"fechaBaja"};
		String[][] responses = new ConexionBD().seleccionar(query, null, names);
		Coordinator[] coordinators = new Coordinator[responses.length];
		for (int i = 0; i < responses.length; i++) {
			coordinators[i] = new Coordinator(
				responses[i][0],
				responses[i][1],
				responses[i][2],
				responses[i][3],
				responses[i][4],
				responses[i][6],
				responses[i][7]
			);
		}
		return coordinators;
	}
	
	private String getIdCoordinator() {
		String query = "SELECT idMiembro AS idCoordinator FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.coordinator.getEmail()};
		String[] names = {"idCoordinator"};
		return this.connection.seleccionar(query, values, names)[0][0];
	}
	
	public String getIdShift() {
		String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
		String[] values = {this.coordinator.getShift()};
		String[] names = {"Turno"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
}
