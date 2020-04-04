package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTest {

	@Test
	public void deletePracticante() {
		DAOPracticante daoPracticante = new DAOPracticante(
				new Practicante(
						"Edson Manuel",
						"Carballo Vera",
						"edsonn1999@hotmail.com",
						"relojito",
						"S18012130"
				)
		);
		assertTrue(daoPracticante.delete());
	}
}
