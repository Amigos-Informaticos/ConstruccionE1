package Tests;

import Exceptions.CustomException;
import Models.Coordinator;
import Models.Organization;
import Models.Project;
import Models.Student;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsCoordinator {
	Coordinator coordinator = new Coordinator();

	//JUST FOR TEST: I create an student object here because we don´t have GUI yet, so we can create it "manually"
	@Test
	public void studentManagementTest(){
		Student student = new Student("Efrain","Arenas","efrain@arenas.com","contrasenia123","s18012138");
		try {
			assertTrue(coordinator.signUpStudent(student));
			assertTrue(coordinator.deleteStudent(student));
		} catch (CustomException exception) {
			exception.printStackTrace();
		}
	}

	@Test
	public void organizationManagementTest(){
		Organization organization = new Organization("Duarte Fantasma","La casa del Duarte","1","1");
		try {
			assertTrue(coordinator.deleteOrganization(organization));
			assertTrue(coordinator.deleteOrganization(organization));
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void projectManagementTest(){
		Project project = new Project();
		project.setName("Hackear el pentagono");
		project.setMethodology("A punta de hacer pings");
		project.setGeneralObjective("Descubrir secretos");
		project.setMediateObjective("Dominar el mundo");
		project.setImmediateObjective("Empezar manana");
		project.setResources("Dos computadoras y un Xiaomi");
		project.setResponsibilities("Echarle ganas y pararse temprano");
		project.setStatus("1");
		project.setArea("1");
		project.setResponsible("correoResponsable1@correo.com");
		project.setIdPeriod("1");
		project.setIdOrganization("1");
		Student student = new Student("Efrain",
										"Arenas",
										"efrain@correo.com",
										"contrasenia123",
										"s18012138");


		try {
			assertTrue(coordinator.signUpProject(project));
			assertTrue(coordinator.signUpStudent(student));
			student.selectProject("Hackear el pentagono");
			coordinator.assignProject(student,"Hackear el pentagono");
			assertTrue(coordinator.deleteProject(project));
			assertTrue(coordinator.deleteStudent(student));
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}


}