package IDAO;

import Exceptions.CustomException;
import Models.Proyecto;

import java.sql.SQLException;

public interface IDAOProyecto {
	boolean registrarse() throws CustomException, SQLException;
	
	boolean estaRegistrado() throws SQLException;
	
	Proyecto cargarProyecto(String name) throws SQLException;
	
	boolean eliminar() throws CustomException, SQLException;
	
	boolean estaActivo() throws SQLException;
	
	boolean reactivar() throws SQLException;
}
