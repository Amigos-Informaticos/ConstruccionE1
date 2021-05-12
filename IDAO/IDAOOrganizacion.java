package IDAO;

import Exceptions.CustomException;

import java.sql.SQLException;

public interface IDAOOrganizacion {
    boolean registrar() throws CustomException, SQLException;
    
    boolean estaRegistrado() throws SQLException;
    
    boolean eliminar() throws SQLException;
    
    boolean estaActivo() throws SQLException;
    
    boolean reactivar() throws SQLException;
}
