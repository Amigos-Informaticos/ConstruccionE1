package IDAO;

import Connection.ConexionBD;
import Models.Professor;

import java.sql.SQLException;

public interface IDAOProfessor extends IDAOUsuario {
	static Professor[] obtenerTodosProfesores() throws Exception {
		Professor[] professors = null;
		try{
			String query =
					"SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, " +
							"Turno.turno AS turno " +
							"FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
							"INNER JOIN Turno ON Profesor.turno = Turno.idTurno WHERE estaActivo = 1";
			String[] names =
					{"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "turno"};
			String[][] responses = new ConexionBD().seleccionar(query, null, names);
			professors = new Professor[responses.length];
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
		}catch(Exception exception){
			throw new Exception(exception.getMessage());
		}

		return professors;
	}
	
	static Professor get(Professor professor) {
		return null;
	}
	
	boolean iniciarSesion() throws SQLException;
	
	boolean registrar() throws SQLException;
	
	boolean update() throws SQLException;
	
	boolean eliminar() throws SQLException;
	
	boolean reactivar() throws SQLException;
	
}
