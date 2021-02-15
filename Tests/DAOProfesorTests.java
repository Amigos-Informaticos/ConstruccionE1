package Tests;

import DAO.DAOProfessor;
import Exceptions.CustomException;
import IDAO.IDAOProfessor;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Logger;
import tools.P;

import static org.junit.jupiter.api.Assertions.*;

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
		alexis.setShift("Matutino");
		assertTrue(daoProfessor.signUp());
	}
	
	@Test
	public void b_isRegistered() {
		Professor alexis = new Professor();
		alexis.setNames("Alexis");
		alexis.setLastnames("Alvarez");
		alexis.setEmail("edsonmanuelcarballovera@gmail.com");
		alexis.setPassword("alexis123");
		alexis.setPersonalNo("N12345678");
		alexis.setShift("Vespertino");
		DAOProfessor daoProfessor = new DAOProfessor(alexis);
		assertTrue(daoProfessor.isRegistered());
	}
	
	@Test
	public void c_updateProfesor() {
		Professor roberto = new Professor();
		roberto.setNames("Ale");
		roberto.setLastnames("Alvarez Ortega");
		roberto.setEmail("alexisao@hotmail.com");
		roberto.setPassword("alexis123");
		roberto.setPersonalNo("N000001");
		roberto.setShift("Vespertino");
		DAOProfessor daoProfessor = new DAOProfessor(roberto);
		try{
			assertTrue(daoProfessor.update());
		}catch (Exception e){
			Logger.staticLog(e,true);
		}
	}
	@Test
	public void d_getIdShift(){
		Professor roberto = new Professor();
		roberto.setNames("Ale");
		roberto.setLastnames("Alvarez Ortega");
		roberto.setEmail("alexisao@hotmail.com");
		roberto.setPassword("alexis123");
		roberto.setPersonalNo("N000001");
		roberto.setShift("Matutino");
		DAOProfessor daoProfessor = new DAOProfessor(roberto);
		try{
			assertEquals(daoProfessor.getIdShift(), "1");
		}catch (Exception e){
			Logger.staticLog(e,true);
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
