package Tests;

import DAO.DAOProfessor;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOProfesorTests {

    @Test
    public void A_signUpProfesor(){
        assertTrue(getDAOProfesor().signUp());
    }
    @Test
    public void updateProfesor(){
        assertTrue(getDAOProfesor().update());
    }
    @Test
    public void C_isRegistered(){
        assertTrue(getDAOProfesor().isRegistered());
    }


    private DAOProfessor getDAOProfesor() {
        return new DAOProfessor(getInstanceProfesor());
    }
    private Professor getInstanceProfesor() {
        return new Professor(
                "Pablo",
                "Escamilla Buendia",
                "pabloeb@hotmail.com",
                "pablo123",
                "N1012131",
                1
        );
    }



}
