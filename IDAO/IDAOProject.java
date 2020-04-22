package IDAO;

import Exceptions.CustomException;
import Models.Project;

public interface IDAOProject {
    public boolean signUp() throws CustomException;

    public  boolean isRegistered();

    public Project loadProject(String name);

    public boolean delete()throws CustomException;

    public boolean isActive();

    public boolean reactive();

}
