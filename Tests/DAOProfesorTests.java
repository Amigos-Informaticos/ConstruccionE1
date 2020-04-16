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
    public void A_signUpProfesor(){
        assertTrue(getDAOProfesor().signUp());
    }
    @Test
    public void updateProfesor(){
        Professor roberto = new Professor();
        DAOProfessor daoProfessor = new DAOProfessor(roberto);
        roberto.setNames("Alexis");
        roberto.setLastnames("Alvarez");
        roberto.setEmail("mariaeugenia-og@hotmail.com");
        roberto.setPassword("alexis123");
        roberto.setPersonalNo("N12345678");
        roberto.setShift(1);
        try{
            assertTrue(daoProfessor.update());
        }catch(CustomException e){
            e.getCauseMessage();
        }
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
                "Maria Eugenia",
                "Ortiz Gonzales",
                "mariaeugenia-og@hotmail.com",
                "maria123",
                "N1012132",
                1
        );
    }



}
