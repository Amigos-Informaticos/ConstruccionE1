package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {

	@Test
	public void signUpTest() {
		Practicante practicante = new Practicante("Edson Manuel", "Carballo Vera",
				"edsonn1999@hotmail.com", "relojito", "S18012130");
		DAOPracticante daopracticante = new DAOPracticante(practicante);
		assertEquals(1, daopracticante.signUp());
	}
}
