package IDAO;

import Models.Professor;

public interface IDAOProfessor extends IDAOUser {
    static Professor[] getAll() {
        return new Professor[0];
    }

    static Professor get(Professor professor) {
        return null;
    }

    boolean update();

    boolean delete();

}