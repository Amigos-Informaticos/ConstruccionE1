package Tests;

import Models.Assignment;
import Models.CalendarizedActivity;
import Models.Coordinator;
import Models.Organizacion;
import Models.Practicante;
import Models.Professor;
import Models.Project;
import Models.ProjectResponsible;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CUC {
	
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
		practicante.setNombres("Panfilo Filomeno");
		practicante.setApellidos("Isidoro Filomon");
		practicante.setEmail("pfif@correo.com");
		practicante.setContrasena("166074819791292qe");
		practicante.setMatricula("S1835460");
		practicante.setProfesor(getProfessor());
		return practicante;
	}
	
	public Organizacion getOrganization() {
		Organizacion organizacion = new Organizacion();
		organizacion.setNombre("La tercia de pibes");
		organizacion.setSector("Videojuegos");
		organizacion.setDireccion(
			"Tlanelhuayocan",
			"1355",
			"N/A",
			"Tlanelhuayocan"
		);
		organizacion.addPhoneNumber("123456789");
		organizacion.addPhoneNumber("123456");
		return organizacion;
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
		assertTrue(getOrganization().registrar());
	}
	
	@Test
	public void bRegisterStudent() {
		assertTrue(getStudent().registrarse());
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
		assertTrue(getStudent().eliminar());
	}
	
	@Test
	public void zClean() {
		assertTrue(getProject().deleteProject());
		assertTrue(getResponsible().delete());
		assertTrue(getOrganization().eliminar());
	}
}
