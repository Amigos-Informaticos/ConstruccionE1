package DAO;

import Connection.DBConnection;
import Exceptions.CustomException;
import IDAO.IDAOProfessor;
import Models.Professor;

public class DAOProfessor implements IDAOProfessor {
	private Professor professor;
	private final DBConnection connection = new DBConnection();
	
	public DAOProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	@Override
	public boolean update() throws CustomException {
		boolean updated;
		String query = "UPDATE Usuario SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
		String[] values = {this.professor.getNames(), this.professor.getLastnames(),
			this.professor.getEmail()};
		if (this.connection.sendQuery(query, values)) {
			query = "UPDATE Profesor SET noPersonal = ?, turno = ? WHERE idUsuario = ?";
			values = new String[]{this.professor.getPersonalNo(), this.getIdShift(), this.getIdProfessor()};
			if (this.connection.sendQuery(query, values)) {
				updated = true;
			} else {
				throw new CustomException("Can't Update Professor: NotUpdateProfessor");
			}
		} else {
			throw new CustomException("Can't Update User: NotUpdateUser");
		}
		return updated;
	}
	
	@Override
	public boolean delete() throws CustomException {
		boolean deleted = false;
		if (this.professor != null && this.isRegistered()) {
			if (this.isActive()) {
				String query = "UPDATE Usuario SET status = 0 WHERE correoElectronico = ?";
				String[] values = {this.professor.getEmail()};
				if (this.connection.sendQuery(query, values)) {
					deleted = true;
				}
			} else {
				deleted = true;
			}
		}
		return deleted;
	}
	
	public boolean reactive() throws CustomException {
		boolean reactivated = false;
		if (!this.isActive()) {
			String query = "UPDATE Usuario SET status = 1 WHERE correoElectronico = ?";
			String[] values = {this.professor.getEmail()};
			reactivated = this.connection.sendQuery(query, values);
		}
		return reactivated;
	}
	
	@Override
	public boolean logIn() throws CustomException {
		assert this.isRegistered() : "Professor is not registered: DAOProfessor.logIn()";
		assert this.isActive() : "Professor is not active: DAOProfessor.logIn()";
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ? " +
			"AND contrasena = ?";
		String[] values = {this.professor.getEmail(), this.professor.getPassword()};
		String[] names = {"TOTAL"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean signUp() {
		assert this.professor.isComplete() : "Professor not complete: DAOProfessor.signUp()";
		boolean signedUp = false;
		String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status)" +
			"VALUES (?, ?, ?, ?, ?)";
		String[] values = {this.professor.getNames(), this.professor.getLastnames(),
			this.professor.getEmail(), this.professor.getPassword(), "1"};
		if (this.connection.sendQuery(query, values)) {
			query = "INSERT INTO Profesor (idUsuario, fechaRegistro, noPersonal, turno) VALUES " +
				"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), (SELECT CURRENT_DATE), ?, ?)";
			values = new String[]{this.professor.getEmail(), this.professor.getPersonalNo(), this.getIdShift()};
			signedUp = this.connection.sendQuery(query, values);
		}
		return signedUp;
	}
	
	public boolean isActive() throws CustomException {
		assert this.professor != null : "Professor is null: DAOProfessor.isActive()";
		assert this.professor.getEmail() != null :
			"Professor's email is null: DAOProfessor.isActive()";
		assert this.isRegistered() : "Professor is not registered: DAOProfessor.isActive()";
		String query = "SELECT status FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"status"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	@Override
	public boolean isRegistered() {
		assert this.professor != null;
		assert this.professor.getEmail() != null;
		String query = "SELECT COUNT(Usuario.idUsuario) AS TOTAL FROM Usuario INNER JOIN Profesor " +
			"ON Usuario.idUsuario = Profesor.idUsuario WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"TOTAL"};
		return this.connection.select(query, values, names)[0][0].equals("1");
	}
	
	public String getIdShift() {
		String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
		String[] values = {this.professor.getShift()};
		String[] names = {"Turno"};
		return this.connection.select(query, values, names)[0][0];
	}
	
	private String getIdProfessor() {
		String query = "SELECT idUsuario AS idProfessor FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"idProfessor"};
		return this.connection.select(query, values, names)[0][0];
	}
	
	public static Professor[] getAll() {
		String query =
			"SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, " +
				"Turno.turno AS turno " +
				"FROM Usuario INNER JOIN Profesor ON Usuario.idUsuario = Profesor.idUsuario " +
				"INNER JOIN Turno ON Profesor.turno = Turno.idTurno WHERE status = 1";
		String[] names =
		{"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "turno"};
		String[][] responses = new DBConnection().select(query, null, names);
		Professor[] professors = new Professor[responses.length];
		for (int i = 0; i < responses.length; i++) {
			professors[i] = new Professor(
				responses[i][0],
				responses[i][1],
				responses[i][2],
				responses[i][3],
				responses[i][4],
				responses[i][5]
			);
		}
		return professors;
	}
	
	public static Professor[] get(Professor professor) {
		return null;
	}
}
