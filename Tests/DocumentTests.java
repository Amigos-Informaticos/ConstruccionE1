package Tests;

import Models.Document;
import Models.Student;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Dir;
import tools.File;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocumentTests {
	private final Student student = new Student(
		"Juan Gabriel",
		"Lopez Doriga",
		"jbld@correo.com",
		"elmiguel123",
		"S17012130"
	);
	
	public Document getDocument() {
		return new Document(
			"Documento de prueba",
			"Normal",
			new Date(),
			new File("src/Connection/DBConnection.java"),
			student);
	}
	
	@Test
	public void prueba() {
		Dir.mkdir("Downloads/a/b/");
	}
	
	@Test
	public void a_saveDocument() {
		assertTrue(this.getDocument().save());
	}
	
	@Test
	public void b_downloadDocument() {
		assertTrue(this.getDocument().downloadFile());
	}
}
