package Tests;

import Models.Administrador;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdministradorTests {
	@Test
	public void a_getHashedPassword() {
		Administrador administrador = new Administrador();
		String contrasena = administrador.getHashedPassword("beethoven");
		assertNotNull(contrasena);
		System.out.println(administrador.getHashedPassword("beethoven"));
	}
}
