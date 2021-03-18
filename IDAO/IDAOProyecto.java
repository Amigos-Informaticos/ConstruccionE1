package IDAO;

import Exceptions.CustomException;
import Models.Proyecto;

public interface IDAOProyecto {
	boolean registrarse() throws CustomException;
	
	boolean estaRegistrado();
	
	Proyecto cargarProyecto(String name);
	
	boolean eliminar() throws CustomException;
	
	boolean estaActivo();
	
	boolean reactivar();
}
