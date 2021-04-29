package DAO;

import Connection.ConexionBD;
//import Connection.ConexionFTP;
import Exceptions.CustomException;
import IDAO.IDAOPracticante;
import Models.Practicante;
import Models.Proyecto;
import javafx.collections.ObservableList;
import tools.File;

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
	
	public String getId() throws SQLException {
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

	public String getIdCorreoAntiguo(String correoAntiguo) throws SQLException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.getId()";
		assert this.practicante.getEmail() != null :
				"Email del practicante es nulo: DAOPracticante.getId()";
		assert this.estaRegistrado() : "Practicante no registrado: DAOPracticante.getId()";

		String query = "SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?";
		String[] valores = {correoAntiguo};
		String[] nombres = {"idMiembro"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null ? resultados[0][0] : "";
	}
	public boolean estaRegistradoActualizar(String correoAntiguo) throws SQLException {

		String idPracticante = getIdCorreoAntiguo(correoAntiguo);



		boolean registrado = false;

		if(!estaRegistradoMatriculaActualizar(idPracticante)){

			assert this.practicante != null : "Practicante es nulo: DAOPracticante.estaRegistrado()";

			assert this.practicante.getEmail() != null :
					"Email de practicante es nulo: DAOPracticante.estaRegistrado()";

			String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
					"WHERE correoElectronico = ? AND idMiembro <> ?" ;


			String[] valores = {this.practicante.getEmail(), idPracticante };
			String[] nombres = {"TOTAL"};
			String[][] resultados = this.conexion.seleccionar(query, valores, nombres);

			if(resultados != null &&  Integer.parseInt(resultados[0][0])> 0 ){
				registrado = true;
			}
		}else {
			registrado = true;
		}


		return registrado;
	}

	public boolean estaRegistradoMatriculaActualizar(String idPracticante) throws SQLException{

		boolean registrado = false;

		String query = "SELECT COUNT(Practicante.matricula) AS TOTAL FROM Practicante " +
				"WHERE matricula= ?  AND idMiembro <> ?";

		String[] valores = {this.practicante.getMatricula(), idPracticante};

		String [] nombres = {"TOTAL"};

		String [][] resultados = this.conexion.seleccionar(query,valores,nombres);

		if( resultados!= null	&& Integer.parseInt(resultados[0][0]) > 0){
			registrado = true;
		}

		return  registrado;

	}



	
	@Override
	public boolean actualizar() throws SQLException {
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
		if (this.conexion.ejecutar(query, valores)) {
			query = "UPDATE Practicante SET matricula = ? WHERE idMiembro = " +
				"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
			valores = new String[] {this.practicante.getMatricula(), this.practicante.getEmail()};
			actualizado = this.conexion.ejecutar(query, valores);
		}
		return actualizado;
	}


	public  boolean actualizarConContrasenia(String correoElectronicoAntiguo)throws SQLException{
		boolean actualizado = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ?, correoElectronico = ?, "
				+ "contrasena = ? WHERE correoElectronico = ?";
		String[] valores = {
				this.practicante.getNombres(),
				this.practicante.getApellidos(),
				this.practicante.getEmail(),
				this.practicante.getContrasena(),
				correoElectronicoAntiguo
		};
		if (this.conexion.ejecutar(query, valores)) {
			query = "UPDATE Practicante SET matricula = ? WHERE idMiembro = " +
					"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
			valores = new String[] {this.practicante.getMatricula(), this.practicante.getEmail()};
			actualizado = this.conexion.ejecutar(query, valores);
		}


		return actualizado;
	}

	public  boolean actualizarSinContrasenia(String correoElectronicoAntiguo)throws SQLException{

		boolean actualizado = false;
		String query = "UPDATE MiembroFEI SET nombres = ?, apellidos = ?, correoElectronico = ? "
				+  "WHERE correoElectronico = ?";
		String[] valores = {
				this.practicante.getNombres(),
				this.practicante.getApellidos(),
				this.practicante.getEmail(),
				correoElectronicoAntiguo
		};
		if (this.conexion.ejecutar(query, valores)) {
			query = "UPDATE Practicante SET matricula = ? WHERE idMiembro = " +
					"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?)";
			valores = new String[] {this.practicante.getMatricula(), this.practicante.getEmail()};
			actualizado = this.conexion.ejecutar(query, valores);
		}

		return  actualizado;

	}

	
	@Override
	public boolean eliminar() throws SQLException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.eliminar()";
		assert this.estaRegistrado() : "Practicante no registrado: DAOPracticante.eliminar()";
		
		boolean eliminado = false;
		if (this.estaActivo()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 0 WHERE correoElectronico = ?";
			String[] valores = {this.practicante.getEmail()};
			eliminado = this.conexion.ejecutar(query, valores);
		}
		return eliminado;
	}
	
	@Override
	public boolean iniciarSesion() throws SQLException {
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
	public boolean registrar() throws SQLException {
		assert this.practicante != null : "Student is null: DAOStudent.signUp()";
		assert this.practicante.estaCompleto() : "Student is incomplete: DAOStudent.signUp()";
		
		boolean registrado = false;
		if (!this.estaRegistrado()) {
			String query = "INSERT INTO MiembroFEI (nombres, apellidos, correoElectronico, " +
				"contrasena, estaActivo) values (?, ?, ?, ?, 1)";
			String[] valores = {this.practicante.getNombres(), this.practicante.getApellidos(),
				this.practicante.getEmail(), this.practicante.getContrasena()};
			if (this.conexion.ejecutar(query, valores)) {
				query = "INSERT INTO Practicante (idMiembro, matricula, profesorCalificador) " +
					"values (?, ?, ?)";
				valores = new String[] {
					this.getId(),
					this.practicante.getMatricula(),
					DAOProfesor.getId(this.practicante.getProfesor().getEmail())
				};
				registrado = this.conexion.ejecutar(query, valores);
			}
		} else {
			registrado = this.reactivar();
		}
		return registrado;
	}

	public boolean actualizarProfesor() throws SQLException{
		boolean actualizado = false;
		String idProfesor = DAOProfesor.getId(this.practicante.getProfesor().getEmail());
		String matricula = this.practicante.getMatricula();

		String query = "UPDATE Practicante SET profesorCalificador = ? WHERE matricula = ?";
		String [] valores = {idProfesor, matricula};

		if(this.conexion.ejecutar(query, valores)){
			actualizado = true;
		}


		return	 actualizado;
	}
	
	@Override
	public boolean estaRegistrado() throws SQLException {

		boolean registrado = false;

		if(!estaRegistradoMatricula()){

			assert this.practicante != null : "Practicante es nulo: DAOPracticante.estaRegistrado()";
			assert this.practicante.getEmail() != null :
					"Email de practicante es nulo: DAOPracticante.estaRegistrado()";

			String query = "SELECT COUNT(MiembroFEI.idMiembro) AS TOTAL FROM MiembroFEI " +
					"WHERE correoElectronico = ?" ;


			String[] valores = {this.practicante.getEmail()};
			String[] nombres = {"TOTAL"};
			String[][] resultados = this.conexion.seleccionar(query, valores, nombres);

			if(resultados != null &&  Integer.parseInt(resultados[0][0])> 0 ){
				registrado = true;
			}
		}else {
			registrado = true;
		}


		return registrado;
	}

	public boolean estaRegistradoMatricula() throws SQLException{

		boolean registrado = false;

		String query = "SELECT COUNT(Practicante.matricula) AS TOTAL FROM Practicante " +
		"WHERE matricula= ? ";

		String[] valores = {this.practicante.getMatricula()};

		String [] nombres = {"TOTAL"};

		String [][] resultados = this.conexion.seleccionar(query,valores,nombres);

		if( resultados!= null	&& Integer.parseInt(resultados[0][0]) > 0){
			registrado = true;
		}

		return  registrado;

	}


	public boolean estaActivo() throws SQLException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.estaActivo()";
		assert this.practicante.getEmail() != null :
			"Practicante.getEmail() es nulo: DAOPracticante.estaActivo()";
		
		String query = "SELECT estaActivo FROM MiembroFEI WHERE correoElectronico = ?";
		String[] valores = {this.practicante.getEmail()};
		String[] nombres = {"estaActivo"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		return resultados != null && resultados[0][0].equals("1");
	}
	
	public static Practicante[] obtenerTodos() throws SQLException {
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
	
	public static Practicante get(Practicante practicante) throws SQLException {
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
	
	public boolean seleccionarProyecto(String nombreProyecto) throws SQLException {
		return this.seleccionarProyecto(new DAOProyecto().cargarProyecto(nombreProyecto));
	}
	
	public boolean seleccionarProyecto(Proyecto proyecto) throws SQLException {
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
				seleccionado = this.conexion.ejecutar(query, valores);
			}
		}
		return seleccionado;
	}
	
	public Proyecto[] getProyectos() throws SQLException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.getProyectos()";
		assert this.practicante.estaCompleto() :
			"Practicante incompleto: DAOPracticante.getProyectos()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.getProyectos()";
		
		Proyecto[] proyectos = null;
		String query = "SELECT nombre FROM Proyecto INNER JOIN Solicitud ON " +
			"Proyecto.idProyecto = Solicitud.idProyecto WHERE Solicitud.idMiembro = " +
			"? AND Proyecto.estaActivo = 1";
		String[] valores = {this.getId()};
		String[] nombres = {"nombre"};
		String[][] resultados = this.conexion.seleccionar(query, valores, nombres);
		
		if (resultados != null && resultados.length > 0) {
			proyectos = new Proyecto[resultados.length];
			for (int i = 0; i < resultados.length; i++) {
				proyectos[i] = new DAOProyecto().cargarProyecto(resultados[i][0]);
			}
		}
		return proyectos;
	}
	
	public boolean eliminarProyectoSeleccionado(String nombreProyecto) throws SQLException {
		DAOProyecto daoProyecto = new DAOProyecto(nombreProyecto);
		assert this.practicante != null :
			"Practicante es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert this.practicante.getEmail() != null :
			"Practicante.getEmail() es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert this.estaActivo() :
			"Practicante inactivo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert nombreProyecto != null :
			"Nombre del proyecto es nulo: DAOPracticante.eliminarProyectoSeleccionado()";
		assert daoProyecto.estaRegistrado() :
			"Proyecto no registrado: DAOPracticante.eliminarProyectoSeleccionado()";
		
		boolean eliminado = false;
		for (Proyecto proyecto: this.getProyectos()) {
			if (proyecto != null && proyecto.getNombre().equals(nombreProyecto)) {
				String query = "DELETE FROM Solicitud WHERE idMiembro = " +
					"(SELECT idMiembro FROM MiembroFEI WHERE correoElectronico = ?) " +
					"AND idProyecto = " +
					"(SELECT idProyecto FROM Proyecto WHERE nombre = ? AND estaActivo = 1)";
				String[] valores = {this.practicante.getEmail(), nombreProyecto};
				eliminado = this.conexion.ejecutar(query, valores);
				break;
			}
		}
		return eliminado;
	}
	
	public boolean setProyecto(String nombreProyecto) throws CustomException, SQLException {
		assert this.practicante != null : "Practicante es nulo: DAOPracticante.setProyecto()";
		assert this.estaActivo() : "Practicante inactivo: DAOPracticante.setProyecto()";
		assert nombreProyecto != null : "Nombre de proyecto es nulo: DAOPracticante.setProyecto()";
		assert new DAOProyecto(nombreProyecto).estaRegistrado() :
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
			establecido = this.conexion.ejecutar(query, valores);
		} else {
			throw new CustomException("Proyecto ya establecido: setProyecto()",
				"ProyectoYaEstablecido");
		}
		return establecido;
	}
	
	public boolean eliminarProyecto() throws SQLException {
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
			deleted = this.conexion.ejecutar(query, valores);
		}
		return deleted;
	}
	
	public Proyecto getProyecto() throws SQLException {
		assert this.practicante != null : "Student is null: DAOStudent.getProject()";
		assert this.practicante.getEmail() != null : "Student's email is null: DAOStudent.getProject()";
		assert this.estaActivo() : "Student is inactive: DAOStudent.getProject()";
		
		Proyecto proyecto = null;
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
			proyecto = resultados != null ? new DAOProyecto().cargarProyecto(resultados[0][0]) : null;
		}
		return proyecto;
	}
	
	@Override
	public boolean reactivar() throws SQLException {
		boolean reactivated = false;
		assert this.practicante != null : "Student is null: DAOStudent.reactive()";
		assert this.estaRegistrado() : "Student is not registered: DAOStudent.reactive()";
		
		if (!this.estaActivo()) {
			String query = "UPDATE MiembroFEI SET estaActivo = 1 WHERE correoElectronico = ?";
			String[] valores = {this.practicante.getEmail()};
			reactivated = this.conexion.ejecutar(query, valores);
		}
		return reactivated;
	}
	
	public boolean tienePlanActividades() throws SQLException {
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
	
	public boolean llenarTablaPracticantes(ObservableList<Practicante> listPracticante) throws NullPointerException, SQLException {
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
	/*
	public boolean guardarDocumento(File documento) throws SQLException {
		assert documento != null : "Documento es nulo: DAOPracticante.guardarDocumento()";
		assert this.practicante.estaCompleto() :
			"Practicante incompleto: DAOPracticante.guardarDocumento()";
		
		boolean guardado = false;
		String rutaRemota = this.practicante.getMatricula() + documento.getName();
		ConexionFTP ftp = new ConexionFTP();
		if (ftp.enviarArchivo(documento.getStringPath(), rutaRemota)) {
			String query = "INSERT INTO Documento (propietario, ruta, nombre)" +
				"VALUES (?, ?, ?)";
			String[] valores = {
				this.getId(),
				rutaRemota,
				documento.getNameNoExt()
			};
			guardado = this.conexion.ejecutar(query, valores);
		}
		return guardado;
	}
	
	 */
}