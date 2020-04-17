package Tests;

import DAO.DAOProfessor;
import Exceptions.CustomException;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOProfesorTests {

    @Test
    public void A_signUpProfesor() {
        Professor alexis = new Professor();
        DAOProfessor daoProfessor = new DAOProfessor(alexis);
        alexis.setNames("Alexis");
        alexis.setLastnames("Alvarez");
        alexis.setEmail("alexisao@hotmail.com");
        alexis.setPassword("alexis123");
        alexis.setPersonalNo("N12345678");
        alexis.setShift(1);
        assertTrue(daoProfessor.signUp());
    }
    @Test
    public void B_isRegistered(){
        Professor alexis = new Professor();
        DAOProfessor daoProfessor = new DAOProfessor(alexis);
        alexis.setNames("Alexis");
        alexis.setLastnames("Alvarez");
        alexis.setEmail("alexisao@hotmail.com");
        alexis.setPassword("alexis123");
        alexis.setPersonalNo("N12345678");
        alexis.setShift(1);
        assertTrue(daoProfessor.isRegistered());
    }
    @Test
    public void C_isRegistered(){
        assertTrue(getDAOProfesor().isRegistered());
    }

    @Test
    public void D_updateProfesor(){
        Professor roberto = new Professor();
        DAOProfessor daoProfessor = new DAOProfessor(roberto);
        roberto.setNames("Alexis");
        roberto.setLastnames("Alvarez Ortega");
        roberto.setEmail("alexisao@hotmail.com");
        roberto.setPassword("alexis123");
        roberto.setPersonalNo("N000001");
        roberto.setShift(1);
        try{
            assertTrue(daoProfessor.update());
        }catch(CustomException e){
            e.getCauseMessage();
        }
    }

    @Test
    public void E_deleteProfessor(){
        assertTrue(this.getDAOProfesor().delete());
    }


    private DAOProfessor getDAOProfesor() {
        return new DAOProfessor(getInstanceProfesor());
    }
    private Professor getInstanceProfesor() {
        return new Professor(
                "Maria Eugenia",
                "Ortiz Gonzales",
                "alexisao@hotmail.com",
                "maria123",
                "N1012132",
                1
        );
    }



}
