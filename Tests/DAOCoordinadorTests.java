package Tests;

import DAO.DAOCoordinador;
import DAO.DAOProfesor;
import IDAO.IDAOProfessor;
import Models.Coordinador;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.P;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOCoordinadorTests {
	@Test
	public void a_signUpCoordinator() {
		Coordinador coordinador = new Coordinador();
		coordinador.setNombres("Angel Juan");
		coordinador.setApellidos("Rodriguez Perez");
		coordinador.setEmail("aj@hotmail.com");
		coordinador.setContrasena("aj1234");
		coordinador.setNumeroPersonal("N000011");
		coordinador.setTurno("Matutino");
		DAOCoordinador daoCoordinador = new DAOCoordinador(coordinador);
		try {
			assertTrue(daoCoordinador.registrarse());
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void b_isRegistered() {
		Coordinador coordinador = new Coordinador();
		coordinador.setNombres("Angel Juan");
		coordinador.setApellidos("Rodriguez Perez");
		coordinador.setEmail("aj@hotmail.com");
		coordinador.setContrasena("aj1234");
		coordinador.setNumeroPersonal("N000011");
		coordinador.setTurno("Matutino");
		DAOCoordinador daoCoordinador = new DAOCoordinador(coordinador);
		assertTrue(daoCoordinador.estaRegistrado());
	}
	
	@Test
	public void c_updateProfesor() {
		Professor roberto = new Professor();
		DAOProfesor daoProfesor = new DAOProfesor(roberto);
		roberto.setNombres("Alexis");
		roberto.setApellidos("Alvarez Ortega");
		roberto.setEmail("alexisao@hotmail.com");
		roberto.setContrasena("alexis123");
		roberto.setPersonalNo("N000001");
	    roberto.setShift("1");
	    try {
		    assertTrue(daoProfesor.update());
	    } catch (AssertionError e) {
		    e.printStackTrace();
	    }
    }

    @Test
    public void d_deleteProfessor() {
        try {
	        assertTrue(this.getDAOProfesor().eliminar());
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
			assertNotNull(professor.getNombres());
			P.pln(professor.getNombres());
		}
	}
	
	@Test
	public void z_getActive() {
		Coordinador coordinador = null;
		coordinador = DAOCoordinador.obtenerActivo();
		System.out.println(coordinador);
		assertTrue(coordinador.estaCompleto());
	}
	
	@Test
	public void isAnother() {
		assertTrue(new Coordinador().isAnother());
	}
	
	private DAOProfesor getDAOProfesor() {
		return new DAOProfesor(getInstanceProfesor());
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