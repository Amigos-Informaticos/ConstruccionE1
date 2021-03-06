package IDAO;

import Exceptions.CustomException;

public interface IDAOOrganizacion {
    boolean registrar() throws CustomException;
    
    boolean estaRegistrado();
    
    boolean eliminar();
    
    boolean estaActivo();
    
    boolean reactivar();
}
