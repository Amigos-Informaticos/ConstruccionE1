package Tests;

import DAO.DAOProject;
import DAO.DAOProjectResponsible;
import DAO.DAOrganization;
import Exceptions.CustomException;
import Models.Assignment;
import Models.Coordinator;
import Models.Organization;
import Models.Project;
import Models.ProjectResponsible;
import Models.Student;
import org.junit.Test;
import tools.Logger;
import tools.P;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsCoordinator {
	Coordinator coordinator = new Coordinator();
	private final Student student = new Student(
		"Juan Gabriel",
		"Lopez Doriga",
		"jbld@correo.com",
		"elmiguel123",
		"S17012130"
	);
	
	
	public Organization getOrganization() {
		Organization organization = new Organization();
		organization.setName("Organizacion 1");
		organization.setSector("Videojuegos");
		organization.setAddress(
			"Revillagigedo",
			"1928",
			"Centro",
			"Veracruz"
		);
		organization.addPhoneNumber("2291763687");
		organization.addPhoneNumber("1660748");
		return organization;
	}
	
	public Project getProject() {
		Project project = new Project();
		project.setName("Proyecto1");
		project.setDescription("Esel primer proyecto jaja");
		project.setMethodology("Cascada ágil");
		project.setGeneralObjective("Objetivo general");
		project.setMediateObjective("Objetivo mediato");
		project.setImmediateObjective("ganar jaja");
		project.setResources("todos los recursos del mundo");
		project.setResponsibilities("Llegar temprano e irse por las tortas");
		project.setCapacity(3);
		project.setArea("Vigilancia");
		project.setResponsible(this.getResponsible());
		project.setPeriod("FEB-JUN 2020");
		project.setOrganization(this.getOrganization());
		project.setStartDate("2020-07-19");
		project.setEndDate("2020-09-07");
		return project;
	}
	
	public ProjectResponsible getResponsible() {
		ProjectResponsible responsible = new ProjectResponsible();
		responsible.setEmail("responsible@gmail.com");
		responsible.setNames("Responsable Miguel");
		responsible.setLastNames("Apellidos responsable");
		responsible.setPosition("Intendente");
		responsible.setOrganization(DAOrganization.getByName("Organizacion 1"));
		return responsible;
	}
	
	@Test
	public void studentManagementTest() {
		Student student = new Student("Efrain", "Arenas", "efrain@arenas.com", "contrasenia123", "s18012138");
		try {
			assertTrue(coordinator.signUpStudent(student));
			assertTrue(coordinator.deleteStudent(student));
		} catch (CustomException exception) {
			exception.printStackTrace();
		}
	}
	
	
	@Test
	public void projectManagementTest() {
		Project project = new Project();
		project.setName("Hackear el pentagono");
		project.setDescription("Hackear el pentágono con un tamagotchi");
		project.setMethodology("A punta de hacer pings");
		project.setGeneralObjective("Descubrir secretos");
		project.setMediateObjective("Dominar el mundo");
		project.setImmediateObjective("Empezar manana");
		project.setResources("Dos computadoras y un Xiaomi");
		project.setResponsibilities("Echarle ganas y pararse temprano");
		project.setArea("Desarrollo");
		project.setResponsible(DAOProjectResponsible.get("correoResponsable1@correo.com"));
		project.setPeriod("FEB-JUN 2020");
		project.setOrganization("1");
		Student student = new Student("Efrain",
			"Arenas",
			"efrain@correo.com",
			"contrasenia123",
			"s18012138");
		
		
		try {
			assertTrue(coordinator.signUpProject(project));
			assertTrue(coordinator.signUpStudent(student));
			student.selectProject("Hackear el pentagono");
			coordinator.assignProject(student, "Hackear el pentagono");
			assertTrue(coordinator.deleteProject(project));
			assertTrue(coordinator.deleteStudent(student));
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void signUpCoordinator() {
		Coordinator angel = new Coordinator(
			"Angel Juan",
			"Sanchez",
			"ajs@hotmail.com",
			"angel123",
			"N000222"
		);
		angel.signUp();
	}
	
	@Test
	public void signUpOrganization() throws CustomException {
		assertTrue(this.getOrganization().signUp());
	}
	
	@Test
	public void registerProject() {
		try {
			assertTrue(this.getProject().register());
		} catch (CustomException e) {
			Logger.staticLog(e);
		}
	}
	
	@Test
	public void getAllProjects() {
		for (Project project: DAOProject.getAll()) {
			P.pln(project.getName());
		}
	}
	
	@Test
	public void getRequestedProjects() {
		for (Project project: Assignment.requestedProjects(student)) {
			P.pln(project.getName());
		}
	}
}