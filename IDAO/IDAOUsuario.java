package IDAO;

import Exceptions.CustomException;

public interface IDAOUsuario {
	boolean iniciarSesion() throws CustomException;
	
	boolean registrar() throws CustomException;
	
	boolean estaRegistrado() throws CustomException;
	
	boolean eliminar() throws CustomException;
	
	boolean reactivar() throws CustomException;
}
