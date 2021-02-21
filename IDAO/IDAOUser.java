package IDAO;

import Exceptions.CustomException;

public interface IDAOUser {
	boolean iniciarSesion() throws CustomException;
	
	boolean registrarse() throws CustomException;
	
	boolean estaRegistrado() throws CustomException;
	
	boolean eliminar() throws CustomException;
	
	boolean reactive() throws CustomException;
}
