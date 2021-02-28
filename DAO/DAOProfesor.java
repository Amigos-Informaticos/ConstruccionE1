package DAO;

import Connection.ConexionBD;
import IDAO.IDAOProfessor;
import IDAO.Turno;
import Models.Practicante;
import Models.Professor;

public class DAOProfesor implements IDAOProfessor, Turno {
	private Professor professor;
	private final ConexionBD connection = new ConexionBD();
	
	public DAOProfesor(Professor professor) {
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
		assert professor != null : "Professor is null : update()";
		assert professor.estaCompleto() : "Professor is not complete : update()";
		assert this.isActive() : "Professor has not a status active on the system";
		boolean updated = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ? WHERE correoElectronico = ?";
		String[] values = {this.professor.getNombres(), this.professor.getApellidos(),
			this.professor.getEmail()};
		if (this.connection.executar(query, values)) {
			query = "UPDATE Profesor SET noPersonal = ?, turno = ? WHERE idMiembro = ?";
			values = new String[] {this.professor.getPersonalNo(), this.getIdShift(), this.getId()};
			if (this.connection.executar(query, values)) {
				updated = true;
			}
		}
		return updated;
	}
	
	@Override
	public boolean eliminar() {
		assert this.professor != null : "Professor null is null : delete()";
		assert this.isActive() : "Professor is not active : delete()";
		String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		return this.connection.executar(query, values);
	}
	
	@Override
	public boolean reactivar() {
		assert this.professor != null : "Professor is null : reactive()";
		assert !this.isActive() : "Professor is not active : reactive()";
		String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		return this.connection.executar(query, values);
	}
	
	@Override
	public boolean iniciarSesion() {
		assert this.estaRegistrado() : "Professor is not registered: DAOProfessor.logIn()";
		assert this.isActive() : "Professor is not active: DAOProfessor.logIn()";
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] values = {this.professor.getEmail(), this.professor.getContrasena()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean registrarse() {
		assert this.professor.estaCompleto() : "Professor not complete: DAOProfessor.signUp()";
		boolean signedUp = false;
		if (!this.estaRegistrado()) {
			String query = "INSERT INTO MiembroFEI " +
				"(nombres, apellidos, correoElectronico, contrasena, estaActivo) " +
				"VALUES (?, ?, ?, ?, ?)";
			String[] values = {this.professor.getNombres(), this.professor.getApellidos(),
				this.professor.getEmail(), this.professor.getContrasena(), "1"};
			if (this.connection.executar(query, values)) {
				query = "INSERT INTO Profesor (idMiembro, fechaRegistro, noPersonal, turno) " +
					"VALUES ((SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?), " +
					"(SELECT CURRENT_DATE), ?, ?)";
				values = new String[] {
					this.professor.getEmail(),
					this.professor.getPersonalNo(),
					this.getIdShift()
				};
				signedUp = this.connection.executar(query, values);
			}
		} else {
			if (!this.isActive()) {
				this.reactivar();
				signedUp = true;
			}
		}
		return signedUp;
	}
	
	public boolean isActive() {
		assert this.professor != null : "Professor is null: DAOProfessor.isActive()";
		assert this.professor.getEmail() != null :
			"Professor's email is null: DAOProfessor.isActive()";
		assert this.estaRegistrado() : "Professor is not registered: DAOProfessor.isActive()";
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"estaActivo"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	@Override
	public boolean estaRegistrado() {
		assert this.professor != null;
		assert this.professor.getEmail() != null;
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
			"WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"TOTAL"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null && results[0][0].equals("1");
	}
	
	public String getIdShift() {
		String query = "SELECT Turno.idTurno AS Turno FROM Turno WHERE turno = ?";
		String[] values = {this.professor.getShift()};
		String[] names = {"Turno"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	@Override
	public String getTurno() {
		assert this.professor != null : "Professor is null: DAOProfessor.getShift()";
		assert this.professor.getEmail() != null :
			"Professor's email is null: DAOProfessor.getShoft()";
		String query = "SELECT turno FROM Turno " +
			"INNER JOIN Profesor ON Turno.idTurno = Profesor.turno " +
			"INNER JOIN Usuario ON Profesor.idUsuario = Usuario.idUsuario " +
			"WHERE Usuario.correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] responses = {"turno"};
		String[][] results = this.connection.seleccionar(query, values, responses);
		return results != null ? results[0][0] : "";
	}
	
	public String getId() {
		assert this.professor.getEmail() != null : "Professor's email is null: DAOProfessor.getId()";
		String query = "SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?";
		String[] values = {this.professor.getEmail()};
		String[] names = {"idMiembro"};
		String[][] results = this.connection.seleccionar(query, values, names);
		return results != null ? results[0][0] : "";
	}
	
	public static String getId(String professorEmail) {
		Professor professor = new Professor();
		professor.setEmail(professorEmail);
		return new DAOProfesor(professor).getId();
	}
	
	public static Professor getByEmail(String email) {
		assert email != null : "Email is null: DAOProfessor.getByEmail()";
		ConexionBD connection = new ConexionBD();
		Professor professor = null;
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
			professor = new Professor(
				responses[0],
				responses[1],
				email,
				responses[2],
				responses[3],
				responses[4]
			);
		}
		return professor;
	}
	
	public static Professor getByStudent(Practicante practicante) {
		assert practicante != null : "Student is null: DAOProfessor.getByStudent()";
		assert practicante.getEmail() != null : "Student's email is null: DAOProfessor.getByStudent()";
		assert practicante.estaRegistrado() : "Student is not registered: DAOProfessor.getByStudent()";
		ConexionBD connection = new ConexionBD();
		Professor professor;
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
		professor = new Professor();
		professor.setNombres(responses[0]);
		professor.setApellidos(responses[1]);
		professor.setEmail(responses[2]);
		professor.setContrasena(responses[3]);
		professor.setPersonalNo(responses[4]);
		professor.setShift(responses[5]);
		return professor;
	}
}
