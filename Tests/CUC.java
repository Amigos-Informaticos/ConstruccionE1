package Tests;

import Models.Assignment;
import Models.CalendarizedActivity;
import Models.Coordinator;
import Models.Organization;
import Models.Professor;
import Models.Project;
import Models.ProjectResponsible;
import Models.Student;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CUC {
	/*
	public Coordinator getCoordinator() {
		return new Coordinator(
			"Angel Juan",
			"Sanchez",
			"ajs@hotmail.com",
			"angel123",
			"N000222"
		);
	}
	
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
	
	public Student getStudent() {
		Student student = new Student();
		student.setNames("Panfilo Filomeno");
		student.setLastnames("Isidoro Filomon");
		student.setEmail("pfif@correo.com");
		student.setPassword("166074819791292qe");
		student.setRegNumber("S1835460");
		student.setProfessor(getProfessor());
		return student;
	}

	public Organization getOrganization() {
		Organization organization = new Organization();
		organization.setName("La tercia de pibes");
		organization.setSector("Videojuegos");
		organization.setAddress(
			"Tlanelhuayocan",
			"1355",
			"N/A",
			"Tlanelhuayocan"
		);
		organization.addPhoneNumber("123456789");
		organization.addPhoneNumber("123456");
		return organization;
	}
	
	public ProjectResponsible getResponsible() {
		ProjectResponsible responsible = new ProjectResponsible();
		responsible.setEmail("responsable@correo.com");
		responsible.setNames("Quien sabe");
		responsible.setLastNames("Sina Pellidos");
		responsible.setPosition("Barrendero");
		responsible.setOrganization(getOrganization());
		return responsible;
	}
	
	public Project getProject() {
		Project project = new Project();
		project.setName("Sistema para Practicas Profesionales");
		project.setDescription("Matenme");
		project.setMethodology("No hay, todo como vaya saliendo");
		project.setGeneralObjective("Pasar la materia");
		project.setMediateObjective("Salir bien del semestre");
		project.setImmediateObjective("No morir en el intento");
		project.setResources("tres vatos todos mecos con laptop");
		project.setResponsibilities("terminar");
		project.setCapacity(3);
		project.setArea("Desarrollo");
		project.setResponsible(this.getResponsible());
		project.setPeriod("FEB-JUN 2020");
		project.setOrganization(this.getOrganization());
		project.setStartDate("2020-03-19");
		project.setEndDate("2020-10-07");
		return project;
	}
	
	@Test
	public void aRegisterOrganization() {
		assertTrue(getOrganization().signUp());
	}
	
	@Test
	public void bRegisterStudent() {
		assertTrue(getStudent().signUp());
	}
	
	@Test
	public void cRegisterProyect() {
		Project project = getProject();
		CalendarizedActivity[] activities = {
			new CalendarizedActivity("Hacer diseno", "2020-05-01"),
			new CalendarizedActivity("Construirlo", "2020-07-01"),
			new CalendarizedActivity("Llorar", "2020-07-10")
		};
		project.setCalendarizedActivities(activities);
		assertTrue(project.register());
	}
	
	@Test
	public void dAssignProject() throws FileNotFoundException {
		Assignment assignment = new Assignment(getStudent(), getProject(), getCoordinator());
		assertTrue(assignment.assignProject());
	}
	
	@Test
	public void eRemoveProject() {
		assertTrue(new Assignment(getStudent(), getProject(), getCoordinator()).removeAssignment());
	}
	
	@Test
	public void fDeleteStudent() {
		assertTrue(getStudent().delete());
	}
	
	@Test
	public void zClean() {
		assertTrue(getProject().deleteProject());
		assertTrue(getResponsible().delete());
		assertTrue(getOrganization().delete());
	}

	 */
}
