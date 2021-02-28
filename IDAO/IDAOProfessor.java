package IDAO;

import Connection.ConexionBD;
import Models.Professor;

public interface IDAOProfessor extends IDAOUser {
    static Professor[] getAll(){
        String query =
                "SELECT nombres, apellidos, correoElectronico, contrasena, noPersonal, " +
                        "Turno.turno AS turno " +
                        "FROM MiembroFEI INNER JOIN Profesor ON MiembroFEI.idMiembro = Profesor.idMiembro " +
                        "INNER JOIN Turno ON Profesor.turno = Turno.idTurno WHERE estaActivo = 1";
	    String[] names =
		    {"nombres", "apellidos", "correoElectronico", "contrasena", "noPersonal", "turno"};
	    String[][] responses = new ConexionBD().seleccionar(query, null, names);
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

    static Professor get(Professor professor) {
        return null;
    }
	
	boolean iniciarSesion();
	
	boolean registrarse();

    boolean update();
	
	boolean eliminar();

    boolean reactivar();

}
