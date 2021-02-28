package Tests;

import Models.Administrador;
import org.junit.Test;

public class AdministradorTests {
	@Test
	public void a_getHashedPassword() {
		Administrador administrador = new Administrador();
		System.out.println(administrador.getHashedPassword("beethoven"));
	}
}
