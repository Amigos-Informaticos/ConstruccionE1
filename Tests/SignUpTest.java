package Tests;

import DAO.DAOCoordinador;
import Models.Coordinador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {

	@Test
	public void signUpTest() {
		Coordinador efra = new Coordinador("Efrain","Arenas","efrain@outlook.com","123","EFRA123");
		DAOCoordinador daoEfra = new DAOCoordinador(efra);
		assertEquals(true, daoEfra.signUp());
	}
}
