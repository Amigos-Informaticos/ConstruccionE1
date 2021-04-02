package DAO;

import Connection.ConexionBD;
import Models.Asignacion;
import Models.Documento;
import Models.Practicante;
import Models.Proyecto;

import java.sql.SQLException;

public class DAOAsignacion {
	private Asignacion asignacion;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}
	
	public boolean asignarProyecto(Documento documento) throws SQLException {
		assert this.asignacion.estaCompleto() :
			"Asignacion incompleta: DAOAsignacion.AsignarProyecto()";
		
		boolean asignado = false;
		String idPracticante = new DAOPracticante(this.asignacion.getPracticante()).getId();
		String idProyecto = new DAOProyecto(this.asignacion.getProyecto()).getId();
		String idProfesor = new DAOProfesor(this.asignacion.getProfesor()).getId();
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion " +
			"WHERE idPracticante = ? AND idProyecto = ? AND estaActivo = 1";
		String[] valores = {idPracticante, idProyecto};
		String[] columnas = {"TOTAL"};
		String idDocumento = new DAODocumento(documento).getId();
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		if (resultados != null && resultados[0][0].equals("0")) {
			query = "INSERT INTO Asignacion (idPracticante, idProyecto, profesorCalificador, " +
				"documentoAsignacion, estaActivo) VALUES (?, ?, ?, ?, 1)";
			valores = new String[] {
				idPracticante,
				idProyecto,
				idProfesor,
				idDocumento
			};
			asignado = this.conexion.ejecutar(query, valores);
		}
		return asignado;
	}
	
	public boolean eliminarAsignacion() throws SQLException {
		assert this.asignacion.getPracticante().estaCompleto() :
			"Practicante incompleto: DAOAsignacion.asignarProyecto()";
		
		boolean eliminado = false;
		String idPracticante = new DAOPracticante(this.asignacion.getPracticante()).getId();
		String query = "SELECT COUNT(idPracticante) AS TOTAL FROM Asignacion " +
			"WHERE idPracticante = ? AND estaActivo = 1";
		String[] valores = {idPracticante};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		if (resultados != null && resultados[0][0].equals("1")) {
			query = "UPDATE Asignacion SET estaActivo = 0 WHERE idPracticante = ?";
			valores = new String[] {idPracticante};
			eliminado = this.conexion.ejecutar(query, valores);
		}
		return eliminado;
	}
	
	public static boolean guardarSolicitud(Practicante practicante, Proyecto proyecto) throws SQLException {
		boolean guardado = false;
		ConexionBD conexionBD = new ConexionBD();
		String query = "SELECT COUNT(idMiembro) AS TOTAL FROM Solicitud " +
			"WHERE idMiembro = ? AND idProyecto = ? AND estaActiva = 1";
		String[] valores = {
			new DAOPracticante(practicante).getId(),
			new DAOProyecto(proyecto).getId()
		};
		String[] columnas = {"TOTAL"};
		String[][] resultados = conexionBD.seleccionar(query, valores, columnas);
		if (resultados != null && resultados[0][0].equals("0")) {
			query = "INSERT INTO Solicitud (idMiembro, idProyecto) VALUES (?, ?)";
			guardado = conexionBD.ejecutar(query, valores);
		}
		return guardado;
	}
	
	public static Proyecto[] proyectosSolicitados(Practicante practicante) throws SQLException {
		Proyecto[] proyectos = null;
		ConexionBD conexionBD = new ConexionBD();
		String query = "SELECT nombre " +
			"FROM Proyecto INNER JOIN SeleccionProyecto " +
			"ON Proyecto.idProyecto = SeleccionProyecto.idProyecto " +
			"INNER JOIN MiembroFEI " +
			"ON SeleccionProyecto.idMiembro = MiembroFEI.idMiembro " +
			"WHERE MiembroFEI.idMiembro = " +
			"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
		
		String[] valores = {practicante.getEmail()};
		String[] columnas = {"nombre"};
		String[][] resultados = conexionBD.seleccionar(query, valores, columnas);
		if (resultados != null && resultados.length > 0) {
			proyectos = new Proyecto[resultados.length];
			for (int i = 0; i < resultados.length; i++) {
				proyectos[i] = DAOProyecto.getByName(resultados[i][0]);
			}
		}
		return proyectos;
	}
}
