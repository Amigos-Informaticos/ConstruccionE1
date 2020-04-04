package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

	@Test
	public void loginTest() {
		DAOPracticante daoPracticante = new DAOPracticante(
				new Practicante(
						"Edson Manuel",
						"Carballo Vera",
						"edsonn1999@hotmail.com",
						"relojito",
						"S18012130"
				)
		);
		assertTrue(daoPracticante.logIn());
	}
}