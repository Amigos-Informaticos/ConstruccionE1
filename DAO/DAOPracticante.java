package DAO;

import Connection.ConexionBD;
import Exceptions.CustomException;
import IDAO.IDAOPracticante;
import Models.Practicante;
import Models.Project;
import javafx.collections.ObservableList;
import tools.File;
import tools.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOPracticante implements IDAOPracticante {
	private Practicante practicante;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOPracticante(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public Practicante getPracticante() {
		return practicante;
	}
	
	public void setPracticante(Practicante practicante) {
		this.practicante = practicante;
	}
	
	public String getId() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.getId()";
		assert this.practicante.getEmail() != null :
			"Email del practicante es nulo: DAOPracticante.getId()";
		assert this.estaRegistrado() : "Practicante no registrado: DAOPracticante.getId()";
		
		String query = "SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?";
		String[] valores = {this.practicante.getEmail()};
		String[] nombres = {"idMiembro"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null ? resultados[0][0] : "";
	}
	
	@Override
	public boolean actualizar() {
		assert this.estaRegistrado() : "Practicante no registrado: DAOPracticante.actualizar()";
		
		boolean actualizado = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ?, correoElectronico = ?, "
			+ "contrasena = ? WHERE correoElectronico = ?";
		String[] valores = {
			this.practicante.getNombres(),
			this.practicante.getApellidos(),
			this.practicante.getEmail(),
			this.practicante.getContrasena(),
			this.practicante.getEmail()
		};
		if (this.conexion.executar(query, valores)) {
			query = "UPDATE Practicante SET matricula = ? WHERE idMiembro = " +
				"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
			valores = new String[] {this.practicante.getMatricula(), this.practicante.getEmail()};
			actualizado = this.conexion.executar(query, valores);
		}
		return actualizado;
	}
	
	@Override
	public boolean eliminar() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.eliminar()";
		assert this.estaRegistrado() : "Practicante no registrado: DAOPracticante.eliminar()";
		
		boolean eliminado = false;
		if (this.estaActivo()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
			String[] valores = {this.practicante.getEmail()};
			eliminado = this.conexion.executar(query, valores);
		}
		return eliminado;
	}
	
	@Override
	public boolean iniciarSesion() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.iniciarSesion()";
		assert this.practicante.getEmail() != null :
			"Email de practicante es nulo: DAOPracticante.iniciarSesion()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.iniciarSesion()";
		
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL " +
			"FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro " +
			"WHERE correoElectronico = ? AND contrasena = ? AND estaActivo = 1";
		String[] valores = {this.practicante.getEmail(), this.practicante.getContrasena()};
		String[] nombres = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	@Override
	public boolean registrarse() {
		assert this.practicante != null : "Student is null: DAOStudent.signUp()";
		assert this.practicante.estaCompleto() : "Student is incomplete: DAOStudent.signUp()";
		
		boolean registrado = false;
		if (!this.estaRegistrado()) {
			String query = "INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, " +
				"contrasena, estaActivo) valores (?, ?, ?, ?, 1)";
			String[] valores = {this.practicante.getNombres(), this.practicante.getApellidos(),
				this.practicante.getEmail(), this.practicante.getContrasena()};
			if (this.conexion.executar(query, valores)) {
				query = "INSERT INTO Practicante (idMiembro, matricula, profesorCalificador) " +
					"valores (?, ?, ?)";
				valores = new String[] {
					this.getId(),
					this.practicante.getMatricula(),
					DAOProfesor.getId(this.practicante.getProfesor().getEmail())
				};
				registrado = this.conexion.executar(query, valores);
			}
		} else {
			registrado = this.reactive();
		}
		return registrado;
	}
	
	@Override
	public boolean estaRegistrado() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.estaRegistrado()";
		assert this.practicante.getEmail() != null :
			"Email de practicante es nulo: DAOPracticante.estaRegistrado()";
		
		String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
			"WHERE correoElectronico = ?";
		String[] valores = {this.practicante.getEmail()};
		String[] nombres = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public boolean estaActivo() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.estaActivo()";
		assert this.practicante.getEmail() != null :
			"Practicante.getEmail() es nulo: DAOPracticante.estaActivo()";
		
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] valores = {this.practicante.getEmail()};
		String[] nombres = {"estaActivo"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public static Practicante[] obtenerTodos() {
		Practicante[] practicantes;
		ConexionBD conexion = new ConexionBD();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro WHERE MiembroFEI.estaActivo = 1";
		String[] nombres = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] resultados = conexion.seleccionar(query, null, nombres);
		practicantes = new Practicante[resultados.length];
		for (int i = 0; i < resultados.length; i++) {
			practicantes[i] = new Practicante(resultados[i][0], resultados[i][1], resultados[i][2],
				resultados[i][3], resultados[i][4]);
		}
		return practicantes;
	}
	
	public static Practicante[] obtenerPorProfesor() {
		Practicante[] practicantes;
		ConexionBD conexion = new ConexionBD();
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM MiembroFEI INNER JOIN ProfesorPracticante WHERE MiembroFEI.estaActivo = 1";
		String[] nombres = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] resultados = conexion.seleccionar(query, null, nombres);
		practicantes = new Practicante[resultados.length];
		for (int i = 0; i < resultados.length; i++) {
			practicantes[i] = new Practicante(resultados[i][0], resultados[i][1], resultados[i][2],
				resultados[i][3], resultados[i][4]);
		}
		return practicantes;
	}
	
	public static Practicante get(Practicante practicante) {
		assert practicante != null : "Practicante es nulo: DAOPracticante.get()";
		assert new DAOPracticante(practicante).estaRegistrado() :
			"Practicante no registrado: DAOPracticante.get()";
		
		ConexionBD conexion = new ConexionBD();
		Practicante practicanteAuxiliar;
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, " +
			"matricula FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro " +
			"WHERE MiembroFEI.correoElectronico = ?";
		String[] valores = {practicante.getEmail()};
		String[] nombres = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[] resultados = conexion.seleccionar(query, valores, nombres)[0];
		practicanteAuxiliar = new Practicante(
			resultados[0], resultados[1], resultados[2], resultados[3], resultados[4]);
		return practicanteAuxiliar;
	}
	
	public boolean seleccionarProyecto(String nombreProyecto) {
		return this.seleccionarProyecto(new DAOProject().cargarProyecto(nombreProyecto));
	}
	
	public boolean seleccionarProyecto(Project proyecto) {
		assert this.practicante != null :
			"Practicante es nulo: DAOPracticante.seleccionarProyecto()";
		assert this.practicante.estaCompleto()
			: "Practicante incompleto: DAOPracticante.seleccionarProyecto()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.seleccionarProyecto()";
		assert proyecto != null : "Proyecto es nulo: DAOPracticante.seleccionarProyecto()";
		assert proyecto.estaCompleto() : "Proyecto incompleto: DAOPracticante.seleccionarProyecto()";
		
		boolean seleccionado = false;
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM SeleccionProyecto" +
			"WHERE idMiembro = (SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
		String[] valores = {this.practicante.getEmail()};
		String[] nombres = {"TOTAL"};
		String[][] proyectos = this.conexion.seleccionar(query, valores, nombres);
		int proyectosSeleccionados = proyectos != null ? Integer.parseInt(proyectos[0][0]) : 0;
		
		if (proyectosSeleccionados < 3) {
			query = "SELECT COUNT(idMiembro) AS TOTAL FROM Solicitud " +
				"WHERE idMiembro = ? AND idProyecto = " +
				"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1)";
			valores = new String[] {this.getId(), proyecto.getNombre()};
			nombres = new String[] {"TOTAL"};
			if (this.conexion.seleccionar(query, valores, nombres)[0][0].equals("0")) {
				query = "INSERT INTO Solicitud (idProyecto, idMiembro) valores " +
					"((SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1), " +
					"(SELECT MiembroFEI.idMiembro FROM MiembroFEI WHERE correoElectronico = ?))";
				valores = new String[] {proyecto.getNombre(), this.practicante.getEmail()};
				seleccionado = this.conexion.executar(query, valores);
			}
		}
		return seleccionado;
	}
	
	public Project[] getProyectos() {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.getProyectos()";
		assert this.practicante.estaCompleto() :
			"Practicante incompleto: DAOPracticante.getProyectos()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.getProyectos()";
		
		Project[] proyectos = null;
		String query = "SELECT nombre FROM Proyecto INNER JOIN Solicitud ON " +
			"Proyecto.idProyecto = Solicitud.idProyecto WHERE Solicitud.idMiembro = " +
			"? AND Proyecto.estaActivo = 1";
		String[] valores = {this.getId()};
		String[] nombres = {"nombre"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		
		if (resultados != null && resultados.length > 0) {
			proyectos = new Project[resultados.length];
			for (int i = 0; i < resultados.length; i++) {
				proyectos[i] = new DAOProject().cargarProyecto(resultados[i][0]);
			}
		}
		return proyectos;
	}
	
	public boolean eliminarProyectoSeleccionado(String nombreProyecto) {
		DAOProject daoProyecto = new DAOProject(nombreProyecto);
		assert this.practicante != null :
			"Practicante es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert this.practicante.getEmail() != null :
			"Practicante.getEmail() es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert this.estaActivo() :
			"Practicante inactivo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert nombreProyecto != null :
			"Nombre del proyecto es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert daoProyecto.isRegistered() :
			"Proyecto no registrado: DAOPracticante.eliminarProyectoSeleccionado()";
		
		boolean eliminado = false;
		for (Project proyecto: this.getProyectos()) {
			if (proyecto != null && proyecto.getNombre().equals(nombreProyecto)) {
				String query = "DELETE FROM Solicitud WHERE idMiembro = " +
					"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?) " +
					"AND idProyecto = " +
					"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1)";
				String[] valores = {this.practicante.getEmail(), nombreProyecto};
				eliminado = this.conexion.executar(query, valores);
				break;
			}
		}
		return eliminado;
	}
	
	public boolean setProyecto(String nombreProyecto) throws CustomException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.setProyecto()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.setProyecto()";
		assert nombreProyecto != null : "Nombre de proyecto es nulo: DAOPracticante.setProyecto()";
		assert new DAOProject(nombreProyecto).isRegistered() :
			"Proyecto no registrado: DAOPracticante.setProyecto()";
		
		boolean establecido;
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] valores = {this.getId()};
		String[] nombres = {"TOTAL"};
		String[][] practicantes = this.conexion.seleccionar(query, valores, nombres);
		if (practicantes != null && practicantes[0][0].equals("0")) {
			query = "INSERT INTO Asignacion (idPracticante, idProyecto) " +
				"valores (?, (SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1))";
			valores = new String[] {this.getId(), nombreProyecto};
			establecido = this.conexion.executar(query, valores);
		} else {
			throw new CustomException("Proyecto ya establecido: setProyecto()",
				"ProyectoYaEstablecido");
		}
		return establecido;
	}
	
	//TODO continuar refactorizacion a partir de aqui
	public boolean eliminarProyecto() {
		boolean deleted = false;
		assert this.practicante != null : "Student is null: DAOStudent.deleteProject()";
		assert this.practicante.getEmail() != null :
			"Student's email is null: DAOStudent.deleteProject()";
		assert this.estaActivo() : "Student is not active: DAOStudent.deleteProject()";
		
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] valores = {this.getId()};
		String[] nombres = {"TOTAL"};
		String[][] students = this.conexion.seleccionar(query, valores, nombres);
		if (students != null && students[0][0].equals("1")) {
			query = "DELETE FROM Asignacion WHERE idPracticante = ?";
			deleted = this.conexion.executar(query, valores);
		}
		return deleted;
	}
	
	public Project getProyecto() {
		assert this.practicante != null : "Student is null: DAOStudent.getProject()";
		assert this.practicante.getEmail() != null : "Student's email is null: DAOStudent.getProject()";
		assert this.estaActivo() : "Student is inactive: DAOStudent.getProject()";
		
		Project project = null;
		String query =
			"SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion WHERE idPracticante = ?";
		String[] valores = {this.getId()};
		String[] responses = {"TOTAL"};
		String[][] students = this.conexion.seleccionar(query, valores, responses);
		if (students != null && students[0][0].equals("1")) {
			query = "SELECT Proyecto.nombre FROM Proyecto INNER JOIN Asignacion " +
				"ON Proyecto.idProyecto = Asignacion.idProyecto " +
				"WHERE Asignacion.idPracticante = ?;";
			responses = new String[] {"Proyecto.nombre"};
			String[][] resultados = this.conexion.seleccionar(query, valores, responses);
			project = resultados != null ? new DAOProject().cargarProyecto(resultados[0][0]) : null;
		}
		return project;
	}
	
	@Override
	public boolean reactive() {
		boolean reactivated = false;
		assert this.practicante != null : "Student is null: DAOStudent.reactive()";
		assert this.estaRegistrado() : "Student is not registered: DAOStudent.reactive()";
		
		if (!this.estaActivo()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] valores = {this.practicante.getEmail()};
			reactivated = this.conexion.executar(query, valores);
		}
		return reactivated;
	}
	
	public boolean replyActivity(String activityName, String documentPath) {
		boolean replied = false;
		assert this.practicante != null : "Student is null: DAOStudent.replyActivity()";
		assert this.estaActivo() : "Student is not active: DAOStudent.replyActivity()";
		assert documentPath != null : "Document Path is null: DAOStudent.replyActivity()";
		assert File.exists(documentPath) : "File doesnt exists: DAOStudent.replyActivity()";
		assert activityName != null : "Activity name is null: DAOStudent.replyActivity()";
		
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad " +
			"WHERE titulo = ? AND idPracticante = ?";
		String[] valores = {activityName, this.getId()};
		String[] nombres = {"TOTAL"};
		String[][] activities = this.conexion.seleccionar(query, valores, nombres);
		if (activities != null && activities[0][0].equals("1")) {
			try {
				java.io.File file = new java.io.File(documentPath);
				FileInputStream fis = new FileInputStream(file);
				query = "INSERT INTO Documento (titulo, fecha, tipo, archivo, autor) " +
					"valores (?, (SELECT CURRENT_DATE()), 'Actividad', ?, ?)";
				this.conexion.openConnection();
				PreparedStatement statement =
					this.conexion.getConnection().prepareStatement(query);
				statement.setString(1, activityName);
				statement.setBinaryStream(2, fis, (int) file.length());
				statement.setString(3, getId());
				statement.executeUpdate();
				this.conexion.closeConnection();
				replied = true;
			} catch (FileNotFoundException | SQLException e) {
				Logger.staticLog(e, true);
			}
		}
		return replied;
	}
	
	public boolean deleteReply(String activityName) {
		assert this.practicante != null : "Student is null: DAOStudent.deleteReply()";
		assert this.estaActivo() : "Student is not active: DAOStudent.deleteReply()";
		assert activityName != null : "Activity name is null: DAOStudent.deleteReply()";
		
		String query = "UPDATE Actividad SET documento = null, fechaEntrega = null " +
			"WHERE idPracticante = ? AND titulo = ?";
		String[] valores = {this.getId(), activityName};
		return this.conexion.executar(query, valores);
	}
	
	public boolean tienePlanActividades() {
		assert this.practicante != null : "Student is null: DAOStudent.getActivityPlan()";
		assert this.estaActivo() : "Student is not active: DAOStudent.getActivityPlan()";
		
		boolean hasPlan = false;
		if (this.getProyecto() != null) {
			String query = "SELECT COUNT(idDocumento) AS TOTAL FROM Documento " +
				"WHERE tipo = 'PlanActividades' AND autor = ?";
			String[] valores = {this.getId()};
			String[] columns = {"TOTAL"};
			String[][] resultados = this.conexion.seleccionar(query, valores, columns);
			hasPlan = resultados != null && resultados[0][0].equals("1");
		}
		return hasPlan;
	}
	
	public boolean llenarTablaPracticantes(ObservableList<Practicante> listPracticante) {
		boolean filled = false;
		String query = "SELECT nombres, apellidos, correoElectronico, contrasena, matricula " +
			"FROM MiembroFEI INNER JOIN Practicante " +
			"ON MiembroFEI.idMiembro = Practicante.idMiembro " +
			"WHERE estaActivo = 1";
		String[] nombres = {"nombres", "apellidos", "correoElectronico", "contrasena", "matricula"};
		String[][] select = this.conexion.seleccionar(query, null, nombres);
		int row = 0;
		while (row < select.length) {
			listPracticante.add(
				new Practicante(
					select[row][0],
					select[row][1],
					select[row][2],
					select[row][3],
					select[row][4]
				)
			);
			if (!filled) {
				filled = true;
			}
			row++;
		}
		return filled;
	}
}