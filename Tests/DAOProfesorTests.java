package Tests;

import DAO.DAOProfesor;
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
		DAOProfesor daoProfesor = new DAOProfesor(alexis);
		alexis.setNombres("Octavio");
		alexis.setApellidos("Ocharan");
		alexis.setEmail("ocha@hotmail.com");
		alexis.setContrasena("ocha1234");
		alexis.setPersonalNo("N000002");
		alexis.setShift("1");
		assertTrue(daoProfesor.registrarse());
	}
	
	@Test
	public void b_isRegistered() {
		Professor alexis = new Professor();
		alexis.setNombres("Alexis");
		alexis.setApellidos("Alvarez");
		alexis.setEmail("alexisao@hotmail.com");
		alexis.setContrasena("alexis123");
		alexis.setPersonalNo("N12345678");
		alexis.setShift("1");
		DAOProfesor daoProfesor = new DAOProfesor(alexis);
		assertTrue(daoProfesor.estaRegistrado());
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
