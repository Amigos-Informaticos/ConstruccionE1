package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import Models.Proyecto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOPracticanteTests {
	
	@Test
	public void A_signUpPracticante() {
		assertTrue(getInstancePracticante().register());
	}
	
	@Test
	public void B_loginPracticante() {
		assertTrue(getDAOPracticante().logIn());
	}
	
	@Test
	public void C_getAllPracticantes() {
		Practicante[] practicantes = DAOPracticante.getAll();
		for (Practicante practicante: practicantes) {
			assertNotNull(practicante);
		}
	}
	
	@Test
	public void D_getPracticante() {
		assertNotNull(DAOPracticante.get(getInstancePracticante()));
	}
	
	@Test
	public void E_updatePracticante() {
		Practicante practicante = getInstancePracticante();
		practicante.setNames("Jose Joaquin");
		assertTrue(new DAOPracticante(practicante).update());
	}
	
	@Test
	public void F1_selectProyect() {
		assertTrue(getDAOPracticante().selectProyect("Hackear la nasa"));
	}
	
	@Test
	public void F2_getSelectedProyects() {
		for (Proyecto proyecto: getDAOPracticante().getProyects()) {
			assertNotNull(proyecto);
		}
	}
	
	@Test
	public void F3_deleteSelectedProyect() {
		assertTrue(getDAOPracticante().deleteSelectedProyect("Hackear la nasa"));
	}
	
	@Test
	public void G1_addReport() {
		assertTrue(getDAOPracticante().addReporte("src/Configuration/Configuration.java",
			"Configuracion"));
	}
	
	@Test
	public void G2_deleteReport() {
		assertTrue(getDAOPracticante().deleteReport("Configuracion"));
	}
	
	@Test
	public void Z_deletePracticante() {
		assertTrue(getDAOPracticante().delete());
	}
	
	private DAOPracticante getDAOPracticante() {
		return new DAOPracticante(getInstancePracticante());
	}
	
	private Practicante getInstancePracticante() {
		return new Practicante(
			"Miguel Joaquin",
			"Lopez Doriga",
			"mjld@hotmail.com",
			"elmiguel123",
			"S18012150"
		);
	}
}
