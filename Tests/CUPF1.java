package Tests;

import Models.Actividad;
import Models.Practicante;
import Models.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
	
	public Actividad getActivity() {
		Actividad actividad = new Actividad();
		actividad.setTitle("Actividad #1");
		actividad.setDescription(
			"Para la primera actividad, se debe subir un archivo con extensión txt");
		actividad.setStartDate(
			DateTimeFormatter.ofPattern("dd/MM/yyy").format(LocalDateTime.now()));
		actividad.setDeliveryDate("2020-07-20");
		actividad.setStudent(getStudent());
		actividad.setProfessor(getProfessor());
		return actividad;
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
	public void aRegisterProfessor() {
		assertTrue(getProfessor().signUp());
	}
	
	@Test
	public void bRegisterStudent() {
		assertTrue(getStudent().registrarse());
	}
	
	@Test
	public void cGenerateActivity() {
		assertTrue(getActivity().create());
	}
	
	@Test
	public void dDeleteActivity() {
		assertTrue(getActivity().delete());
	}
	
	@Test
	public void eDeleteStudent() {
		assertTrue(getStudent().eliminar());
	}
	
	@Test
	public void fDeleteProfessor() {
		assertTrue(getProfessor().delete());
	}
}
