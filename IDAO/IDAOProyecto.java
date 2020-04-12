package IDAO;

import Models.Proyecto;

public interface IDAOProyecto {
    public boolean signUp();

    public  boolean isRegistered();

    public Proyecto loadProyecto(String name);

    public boolean delete();

    public boolean isActive();

    public boolean reactive();

}
