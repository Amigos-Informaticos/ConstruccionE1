package DAO;

import Connection.DBConnection;
import IDAO.IDAOProfessor;
import Models.Professor;

public class DAOProfessor implements IDAOProfessor {
	private Professor professor;
	private DBConnection connection = new DBConnection();
	
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
	public boolean update() {
		return false;
	}
	
	@Override
	public boolean delete() {
		return false;
	}
	
	@Override
	public boolean logIn() {
		boolean loggedIn = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Profesor WHERE correoElectronico = ? " +
			"AND contrasena = ?";
		String[] values = {this.professor.getEmail(), this.professor.getPassword()};
		String[] names = {"TOTAL"};
		if (this.isRegistered()) {
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				loggedIn = true;
			}
		}
		return loggedIn;
	}
	
	@Override
	public boolean signUp() {
		boolean signedUp = false;
		if (this.professor.isComplete()) {
			String query = "INSERT INTO Usuario (nombres, apellidos, correoElectronico, contrasena, status)" +
				"VALUES (?, ?, ?, ?, ?)";
			String[] values = {this.professor.getNames(), this.professor.getLastnames(),
				this.professor.getEmail(), this.professor.getPassword(), "1"};
			if (this.connection.sendQuery(query, values)) {
				query = "INSERT INTO Profesor (idUsuario, fechaRegistro, noPersonal, turno) VALUES " +
					"((SELECT idUsuario FROM Usuario WHERE correoElectronico = ?), (SELECT CURRENT_DATE), ?, ?)";
				values = new String[]{this.professor.getEmail(), this.professor.getPersonalNo(), String.valueOf(this.professor.getShift())};
				if (this.connection.sendQuery(query, values)) {
					signedUp = true;
				}
			}
		}
		return signedUp;
	}
	
	@Override
	public boolean isRegistered() {
		boolean isRegistered = false;
		String query = "SELECT COUNT(idUsuario) AS TOTAL FROM Usuario WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"TOTAL"};
		if (this.professor.isComplete()) {
			if (this.connection.select(query, values, names)[0][0].equals("1")) {
				query = "SELECT COUNT(idUsuario) AS TOTAL FROM Profesor INNER JOIN Usuario " +
					"ON Profesor.idUsuario = Usuario.idUsuario " +
					"WHERE Usuario.correElectronico = ?";
				if (this.connection.select(query, values, names)[0][0].equals("1")) {
					isRegistered = true;
				}
			}
		}
		return isRegistered;
	}
	
	public static Professor[] getAll() {
		return new Professor[0];
	}
	
	public static Professor[] get(Professor professor) {
		return null;
	}
	
	@Override
	public boolean reactive() {
		return true;
	}
}
