package DAO;

import Connection.ConexionBD;
import IDAO.IDAOActividad;
import Models.Actividad;

public class DAOActividad implements IDAOActividad {
	private final Actividad actividad;
	private final ConexionBD conexion = new ConexionBD();
	
	public DAOActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	@Override
	public boolean crear() {
		assert this.actividad != null : "Actividad nula: DAOActividad.crear()";
		assert this.actividad.estaCompleta() : "Actividad incompleta: DAOActividad.crear()";
		
		String query = "INSERT INTO Actividad " +
			"(idPracticante, titulo, descripcion, fechaCreacion, fechaCierre, profesorAsignador) " +
			"VALUES (?, ?, ?, (SELECT SYSDATE()), ?, ?)";
		String[] valores = {
			new DAOPracticante(this.actividad.getPracticante()).getId(),
			this.actividad.getTitulo(),
			this.actividad.getDescripcion(),
			this.actividad.getFechaEntrega(),
			new DAOProfesor(this.actividad.getProfesor()).getId()
		};
		return this.conexion.ejecutar(query, valores);
	}
	
	@Override
	public boolean actualizar() {
		assert this.actividad.getFechaInicio() != null :
			"Fecha de inicio nula: DAOActividad.actualizar()";
		
		String query = "UPDATE Actividad SET titulo = ?, descripcion = ? WHERE fechaCreacion = ?";
		String[] valores = {
			this.actividad.getTitulo(),
			this.actividad.getDescripcion(),
			this.actividad.getFechaInicio()
		};
		return this.conexion.ejecutar(query, valores);
	}
	
	@Override
	public boolean eliminar() {
		assert this.actividad != null : "Actividad nula: DAOActividad.eliminar()";
		assert this.estaRegistrada() : "Actividad no registrada: DAOActividad.eliminar()";
		String query = "DELETE FROM Actividad WHERE Actividad.idActividad = ?";
		String[] valores = {this.getIdActividad()};
		return this.conexion.ejecutar(query, valores);
	}
	
	public boolean estaRegistrada() {
		assert this.actividad != null : "Actividad nula: DAOActividad.estaRegistrada()";
		
		String query = "SELECT COUNT(idActividad) AS TOTAL FROM Actividad " +
			"WHERE titulo = ? AND descripcion = ?";
		String[] valores = {this.actividad.getTitulo(), this.actividad.getDescripcion()};
		String[] columnas = {"TOTAL"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public String getIdActividad() {
		assert this.estaRegistrada() : "Actividad no registrada: DAOActividad.getIdActividad()";
		
		String query = "SELECT idActividad FROM Actividad WHERE titulo = ? AND descripcion = ?";
		String[] valores = {this.actividad.getTitulo(), this.actividad.getDescripcion()};
		String[] columnas = {"idActividad"};
		String[][] resultados = this.conexion.seleccionar(query, valores, columnas);
		return resultados != null ? resultados[0][0] : "";
	}
}
