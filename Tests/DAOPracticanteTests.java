package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOPracticanteTests {

	@Ignore
	@Test
	public void A_signUpPracticante() {
		assertTrue(getInstancePracticante().register());
	}

	@Ignore
	@Test
	public void B_loginPracticante() {
		assertTrue(getDAOPracticante().logIn());
	}

	@Ignore
	@Test
	public void C_getAllPracticantes() {
		Practicante[] practicantes = DAOPracticante.getAll();
		for (Practicante practicante : practicantes) {
			assertNotNull(practicante);
		}
	}

	@Ignore
	@Test
	public void D_getPracticante() {
		assertNotNull(DAOPracticante.get(getInstancePracticante()));
	}

	@Ignore
	@Test
	public void E_updatePracticante() {
		Practicante practicante = getInstancePracticante();
		practicante.setNombres("Jose Joaquin");
		assertTrue(new DAOPracticante(practicante).update());
	}

	@Ignore
	@Test
	public void F_deletePracticante() {
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
