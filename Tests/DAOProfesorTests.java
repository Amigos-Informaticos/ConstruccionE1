package Tests;

import DAO.DAOPracticante;
import DAO.DAOProfesor;
import Models.Practicante;
import Models.Profesor;
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


    private DAOProfesor getDAOProfesor() {
        return new DAOProfesor(getInstanceProfesor());
    }
    private Profesor getInstanceProfesor() {
        return new Profesor(
                "Pablo",
                "Escamilla Buendia",
                "pabloeb@hotmail.com",
                "pablo123",
                "N1012131",
                1
        );
    }



}
