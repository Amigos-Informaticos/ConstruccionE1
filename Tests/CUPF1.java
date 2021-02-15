package Tests;

import Models.Activity;
import Models.Professor;
import Models.Student;
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
		professor.setNames("Octavio");
		professor.setLastnames("Ocharan");
		professor.setEmail("ocha@hotmail.com");
		professor.setPassword("ocha1234");
		professor.setPersonalNo("N000002");
		professor.setShift("1");
		return professor;
	}
	
	public Activity getActivity() {
		Activity activity = new Activity();
		activity.setTitle("Actividad #1");
		activity.setDescription(
			"Para la primera actividad, se debe subir un archivo con extensión txt");
		activity.setStartDate(
			DateTimeFormatter.ofPattern("dd/MM/yyy").format(LocalDateTime.now()));
		activity.setDeliveryDate("2020-07-20");
		activity.setStudent(getStudent());
		activity.setProfessor(getProfessor());
		return activity;
	}
	
	public Student getStudent() {
		Student student = new Student();
		student.setNames("Ernesto Ermenegildo");
		student.setLastnames("Perez del Corral");
		student.setEmail("eepc@correo.com");
		student.setPassword("contrasenahida");
		student.setRegNumber("S19015160");
		student.setProfessor(getProfessor());
		return student;
	}
	
	@Test
	public void aRegisterProfessor() {
		assertTrue(getProfessor().signUp());
	}
	
	@Test
	public void bRegisterStudent() {
		assertTrue(getStudent().signUp());
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
		assertTrue(getStudent().delete());
	}
	
	@Test
	public void fDeleteProfessor() {
		assertTrue(getProfessor().delete());
	}
}
