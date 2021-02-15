package IDAO;

import Exceptions.CustomException;
import Models.Project;

public interface IDAOProject {
    boolean signUp() throws CustomException;
    
    boolean isRegistered();
    
    Project loadProject(String name);
    
    boolean delete() throws CustomException;
    
    boolean isActive();
    
    boolean reactivate();

}
