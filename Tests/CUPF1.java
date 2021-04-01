package Tests;

import Models.Practicante;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CUPF1 {
	
	public Professor getProfessor() {
		Professor professor = new Professor();
		professor.setNombres("Octavio");
		professor.setApellidos("Ocharan");
		professor.setEmail("ocha@hotmail.com");
		professor.setContrasena("ocha1234");
		professor.setPersonalNo("N000002");
		professor.setShift("1");
		return professor;
	}
	

	
	public Practicante getStudent() {
		Practicante practicante = new Practicante();
		practicante.setNombres("Ernesto Ermenegildo");
		practicante.setApellidos("Perez del Corral");
		practicante.setEmail("eepc@correo.com");
		practicante.setContrasena("contrasenahida");
		practicante.setMatricula("S19015160");
		practicante.setProfesor(getProfessor());
		return practicante;
	}
	
	@Test
	public void aRegisterProfessor() throws SQLException {
		assertTrue(getProfessor().signUp());
	}
	
	@Test
	public void bRegisterStudent() throws SQLException {
		assertTrue(getStudent().registrar());
	}
	

	@Test
	public void eDeleteStudent() throws SQLException {
		assertTrue(getStudent().eliminar());
	}
	
	@Test
	public void fDeleteProfessor() throws SQLException {
		assertTrue(getProfessor().delete());
	}
}
