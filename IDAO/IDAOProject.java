package IDAO;

import Exceptions.CustomException;
import Models.Proyecto;

public interface IDAOProject {
    boolean signUp() throws CustomException;
    
    boolean isRegistered();
    
    Proyecto cargarProyecto(String name);
    
    boolean delete() throws CustomException;
    
    boolean isActive();
    
    boolean reactivate();

}
