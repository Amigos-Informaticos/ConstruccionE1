package Tests;

import DAO.DAOCoordinator;
import DAO.DAOProfessor;
import Exceptions.CustomException;
import IDAO.IDAOProfessor;
import Models.Coordinator;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Logger;
import tools.P;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOCoordinatorTests {
    @Test
    public void a_signUpCoordinator() {
        Coordinator coordinator = new Coordinator();
        coordinator.setNames("Angel Juan");
        coordinator.setLastnames("Rodriguez Perez");
        coordinator.setEmail("aj@hotmail.com");
        coordinator.setPassword("aj1234");
        coordinator.setPersonalNo("N000011");
        coordinator.setShift("Matutino");
        DAOCoordinator daoCoordinator = new DAOCoordinator(coordinator);
        try{
            assertTrue(daoCoordinator.signUp());
        }catch (AssertionError e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void b_isRegistered() {
        Coordinator coordinator = new Coordinator();
        coordinator.setNames("Angel Juan");
        coordinator.setLastnames("Rodriguez Perez");
        coordinator.setEmail("aj@hotmail.com");
        coordinator.setPassword("aj1234");
        coordinator.setPersonalNo("N000011");
        coordinator.setShift("Matutino");
        DAOCoordinator daoCoordinator = new DAOCoordinator(coordinator);
        assertTrue(daoCoordinator.isRegistered());
    }

    @Test
    public void c_updateProfesor() {
        Professor roberto = new Professor();
        DAOProfessor daoProfessor = new DAOProfessor(roberto);
        roberto.setNames("Alexis");
        roberto.setLastnames("Alvarez Ortega");
        roberto.setEmail("alexisao@hotmail.com");
        roberto.setPassword("alexis123");
        roberto.setPersonalNo("N000001");
        roberto.setShift("1");
        try {
            assertTrue(daoProfessor.update());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void d_deleteProfessor() {
        try {
            assertTrue(this.getDAOProfesor().delete());
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void e_testGetIdShift() {
        System.out.println(getDAOProfesor().getIdShift());
        assertNotNull(getDAOProfesor().getIdShift());
    }

    @Test
    public void z_getAll() {
        for (Professor professor: IDAOProfessor.getAll()) {
            assertNotNull(professor.getNames());
            P.pln(professor.getNames());
        }
    }

    private DAOProfessor getDAOProfesor() {
        return new DAOProfessor(getInstanceProfesor());
    }

    private Professor getInstanceProfesor() {
        return new Professor(
                "Alexis",
                "Alvarez Ortega",
                "alexisao@hotmail.com",
                "alexis123",
                "N000001",
                "Mixto"
        );
    }
}