package Tests;

import Connection.FTPConnection;
import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class FTPTest {
	private final FTPConnection ftpConnection = new FTPConnection();
	
	@Test
	public void a_login() {
		assertTrue(ftpConnection.connect());
	}
	
	@Test
	public void b_sendFile() {
		assertTrue(ftpConnection.sendFile(
			"src/View/images/fei_photo.jpg",
			"/home/pi/"
		));
	}
	
	@Test
	public void c_close() {
		ftpConnection.close();
		assertTrue(true);
	}
}
