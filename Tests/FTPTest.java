package Tests;

import Connection.ConexionFTP;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class FTPTest {
	private static final ConexionFTP CONEXION_FTP = new ConexionFTP();
	
	@Test
	public void a_login() {
		assertTrue(CONEXION_FTP.conectar());
	}
	
	@Test
	public void b_sendFile() {
		assertTrue(CONEXION_FTP.enviarArchivo(
			"src/View/images/fei_photo.jpg",
			"/home/pi/"
		));
	}
	
	@AfterClass
	public static void close() {
		CONEXION_FTP.cerrar();
	}
}
