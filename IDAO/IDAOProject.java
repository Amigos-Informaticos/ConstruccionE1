package IDAO;

import Models.Project;

public interface IDAOProject {
    public boolean signUp();

    public  boolean isRegistered();

    public Project loadProject(String name);

    public boolean delete();

    public boolean isActive();

    public boolean reactive();

}
