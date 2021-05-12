package IDAO;

import Exceptions.CustomException;

import java.sql.SQLException;

public interface IDAOUsuario {
	boolean iniciarSesion() throws CustomException, SQLException;
	
	boolean registrar() throws CustomException, SQLException;
	
	boolean estaRegistrado() throws CustomException, SQLException;
	
	boolean eliminar() throws CustomException, SQLException;
	
	boolean reactivar() throws CustomException, SQLException;
}
