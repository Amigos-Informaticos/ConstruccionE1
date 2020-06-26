package Tests;

import DAO.DAOProfessor;
import Exceptions.CustomException;
import IDAO.IDAOProfessor;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.P;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOProfesorTests {
	@Test
	public void a_signUpProfesor() {
		Professor alexis = new Professor();
		DAOProfessor daoProfessor = new DAOProfessor(alexis);
		alexis.setNames("Octavio");
		alexis.setLastnames("Ocharan");
		alexis.setEmail("ocha@hotmail.com");
		alexis.setPassword("ocha1234");
		alexis.setPersonalNo("N000002");
		alexis.setShift("1");
		assertTrue(daoProfessor.signUp());
	}
	
	@Test
	public void b_isRegistered() {
		Professor alexis = new Professor();
		alexis.setNames("Alexis");
		alexis.setLastnames("Alvarez");
		alexis.setEmail("alexisao@hotmail.com");
		alexis.setPassword("alexis123");
		alexis.setPersonalNo("N12345678");
		alexis.setShift("1");
		DAOProfessor daoProfessor = new DAOProfessor(alexis);
		assertTrue(daoProfessor.isRegistered());
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
