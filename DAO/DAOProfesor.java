package DAO;

import Connection.ConexionBD;
import IDAO.IDAOProfesor;
import IDAO.Turno;
import Models.Practicante;
import Models.Profesor;

import java.sql.SQLException;

public class DAOProfesor implements IDAOProfesor, Turno {
	private Profesor profesor;
	private final ConexionBD connection = new ConexionBD();
	
	public DAOProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Profesor getProfessor() {
		return profesor;
	}
	
	public void setProfessor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	@Override
	public boolean update() throws SQLException {
		assert profesor != null : "Professor is null : update()";
		assert profesor.estaCompleto() : "Professor is not complete : update()";
		assert this.isActive() : "Professor has not a status active on the system";
		boolean updated = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
		String[] values = {this.profesor.getNombres(), this.profesor.getApellidos(),
			this.profesor.getEmail()};
		if (this.connection.ejecutar(query, values)) {
			query = "UPDATE Profesor SET noPersonal = ?, turno = ? WHERE idMiembro = ?";
			values = new String[] {this.profesor.getPersonalNo(), this.getIdShift(), this.getId()};
			if (this.connection.ejecutar(query, values)) {
				updated = true;
			}
		}
		return updated;
	}
	
	@Override
	public boolean eliminar() throws SQLException {
		assert this.profesor != null : "Professor null is null : delete()";
		assert this.isActive() : "Professor is not active : delete()";
		String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		return this.connection.ejecutar(query, values);
	}
	
	@Override
	public boolean reactivar() throws SQLException {
		assert this.profesor != null : "Professor is null : reactive()";
		assert !this.isActive() : "Professor is not active : reactive()";
		String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		return this.connection.ejecutar(query, values);
	}
	
	@Override
	public boolean iniciarSesion() throws SQLException {
		assert this.estaRegistrado() : "Professor is not registered: DAOProfessor.logIn()";
		assert this.isActive() : "Professor is not active: DAOProfessor.logIn()";
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] valores = {this.profesor.getEmail(), this.profesor.getContrasena()};
		String[] nombres = {"TOTAL"};

		String[][] resultados = new String[0][];
		try {
			resultados = this.connection.seleccionar(query, valores, nombres);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return resultados != null && resultados[0][0].equals("1");
	}
	
	@Override
	public boolean registrar() throws SQLException {
		assert this.profesor.estaCompleto() : "Professor not complete: DAOProfessor.signUp()";
		boolean registrado = false;
		if (!this.estaRegistrado()) {
			String query = "INSERT INTO MiembroFEI " +
				"(nombres, apellidos, correoElectronico, contrasena, estaActivo) " +
				"VALUES (?, ?, ?, ?, ?)";
			String[] valores = {this.profesor.getNombres(), this.profesor.getApellidos(),
				this.profesor.getEmail(), this.profesor.getContrasena(), "1"};
			if (this.connection.ejecutar(query, valores)) {
				query = "INSERT INTO Profesor (idMiembro, fechaRegistro, noPersonal, turno) " +
					"VALUES ((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), " +
					"(SELECT CURRENT_DATE), ?, ?)";
				valores = new String[] {
					this.profesor.getEmail(),
					this.profesor.getPersonalNo(),
					this.getIdShift()
				};
				registrado = this.connection.ejecutar(query, valores);
			}
		} else {
			if (!this.isActive()) {
				this.reactivar();
				registrado = true;
			}
		}
		return registrado;
	}
	
	public boolean isActive() throws SQLException {
		assert this.profesor != null : "Professor is null: DAOProfessor.isActive()";
		assert this.profesor.getEmail() != null :
			"Professor's email is null: DAOProfessor.isActive()";
		assert this.estaRegistrado() : "Professor is not registered: DAOProfessor.isActive()";
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		String[] names = {"estaActivo"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean estaRegistrado() throws SQLException {
		assert this.profesor != null;
		assert this.profesor.getEmail() != null;
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
			"WHERE correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	public String getIdShift() throws SQLException {
		String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
		String[] values = {this.profesor.getShift()};
		String[] names = {"Turno"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	@Override
	public String getTurno() throws SQLException {
		assert this.profesor != null : "Professor is null: DAOProfessor.getShift()";
		assert this.profesor.getEmail() != null :
			"Professor's email is null: DAOProfessor.getShoft()";
		String query = "SELECT turno FROM Turno " +
			"INNER JOIN Profesor ON Turno.idTurno = Profesor.turno " +
			"INNER JOIN Usuario ON Profesor.idUsuario = Usuario.idUsuario " +
			"WHERE Usuario.correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		String[] responses = {"turno"};
		String[][] results = this.connection.seleccionar(query, values, responses);
		return results != null ? results[0][0] : "";
	}
	
	public String getId() throws SQLException {
		assert this.profesor.getEmail() != null : "Professor's email is null: DAOProfessor.getId()";
		String query = "SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.profesor.getEmail()};
		String[] names = {"idMiembro"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public static String getId(String professorEmail) throws SQLException {
		Profesor profesor = new Profesor();
		profesor.setEmail(professorEmail);
		return new DAOProfesor(profesor).getId();
	}
	
	public static Profesor getByEmail(String email) throws SQLException {
		assert email != null : "Email is null: DAOProfessor.getByEmail()";
		ConexionBD connection = new ConexionBD();
		Profesor profesor = null;
		String query = "SELECT COUNT(correoElectronico) AS TOTAL " +
			"FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {email};
		String[] columns = {"TOTAL"};
		if (connection.seleccionar(query, values, columns)[0][0].equals("1")) {
			query = "SELECT nombres, apellidos, contrasena, noPersonal, Turno.turno AS turno " +
				"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
				"INNER JOIN Turno ON Profesor.turno = Turno.idTurno " +
				"WHERE correoElectronico = ? AND estaActivo = 1";
			columns = new String[] {"nombres", "apellidos", "contrasena", "noPersonal", "turno"};
			String[] responses = connection.seleccionar(query, values, columns)[0];
			profesor = new Profesor(
				responses[0],
				responses[1],
				email,
				responses[2],
				responses[3],
				responses[4]
			);
		}
		return profesor;
	}
	
	public static Profesor getByStudent(Practicante practicante) throws SQLException {
		assert practicante != null : "Student is null: DAOProfessor.getByStudent()";
		assert practicante.getEmail() != null : "Student's email is null: DAOProfessor.getByStudent()";
		assert practicante.estaRegistrado() : "Student is not registered: DAOProfessor.getByStudent()";
		ConexionBD connection = new ConexionBD();
		Profesor profesor;
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, " +
			"Turno.turno AS turno " +
			"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
			"INNER JOIN Turno ON Turno.idturno = Profesor.turno " +
			"INNER JOIN Practicante ON Profesor.idMiembro = Practicante.profesorCalificador " +
			"WHERE Practicante.idMiembro = ?";
		String[] values = {new DAOPracticante(practicante).getId()};
		String[] columns = {
			"nombres",
			"apellidos",
			"correoElectronico",
			"contrasena",
			"noPersonal",
			"turno"
		};
		String[] responses = connection.seleccionar(query, values, columns)[0];
		profesor = new Profesor();
		profesor.setNombres(responses[0]);
		profesor.setApellidos(responses[1]);
		profesor.setEmail(responses[2]);
		profesor.setContrasena(responses[3]);
		profesor.setPersonalNo(responses[4]);
		profesor.setShift(responses[5]);
		return profesor;
	}
}
