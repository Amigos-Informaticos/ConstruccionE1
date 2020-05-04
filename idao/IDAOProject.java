package idao;

import exceptions.CustomException;
import models.Project;

public interface IDAOProject {
    boolean signUp() throws CustomException;
    
    boolean isRegistered();
    
    Project loadProject(String name);
    
    boolean delete() throws CustomException;
    
    boolean isActive();
    
    boolean reactive();

}
