package Tests;

import DAO.DAOPracticante;
import DAO.DAOProfesor;
import Models.Practicante;
import Models.Profesor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {

	@Test
	public void signUpTest() {
		Profesor revo = new Profesor("Juan Carlos", "Perez Arriaga",
				"elrevo@hotmail.com", "elrevo", "N123456789",1);
		DAOProfesor daoprofesor = new DAOProfesor(revo);
		assertEquals(true , daoprofesor.signUp());
	}
}
