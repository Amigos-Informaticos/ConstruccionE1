package Tests;

import Models.Practicante;
import Models.Profesor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CUPF1 {
	
	public Profesor getProfessor() {
		Profesor profesor = new Profesor();
		profesor.setNombres("Octavio");
		profesor.setApellidos("Ocharan");
		profesor.setEmail("ocha@hotmail.com");
		profesor.setContrasena("ocha1234");
		profesor.setNumeroPersonal("N000002");
		profesor.setTurno("1");
		return profesor;
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
		assertTrue(getProfessor().registrar());
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
		assertTrue(getProfessor().eliminar());
	}
}
