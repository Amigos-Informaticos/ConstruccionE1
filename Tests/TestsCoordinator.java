package Tests;

import DAO.DAOProjectResponsible;
import Exceptions.CustomException;
import Models.Coordinator;
import Models.Organization;
import Models.Project;
import Models.Student;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsCoordinator {
	Coordinator coordinator = new Coordinator();
	
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
		project.setStatus("1");
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
		assertTrue(organization.signUp());
	}
}