package IDAO;

import Models.Profesor;

public interface IDAOProfesor extends IDAOUser {
    static Profesor[] getAll() {
        return new Profesor[0];
    }
    
    static Profesor get(Profesor profesor) {
        return null;
    }
    
    boolean update();

    boolean delete();

}
