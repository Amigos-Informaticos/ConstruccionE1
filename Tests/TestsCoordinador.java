package Tests;

import DAO.DAOCoordinator;
import DAO.DAOProject;
import DAO.DAOrganization;
import Exceptions.CustomException;
import Models.Coordinator;
import Models.Organization;
import Models.Project;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsCoordinador {
	Coordinator coordinator = new Coordinator();

	/*
	@Test
	public void registPracticante(){
		Practicante edson = new Practicante("Edson","Carballo","edson@edson.com","holajaja","s12345");
		assertTrue(coordinador.signUpPracticante(edson));
	}

	 */
	//@Test
	public void registProject() {
		Project project = new Project("Hackear la nasa", "A punta de ifs", "Ver aliens", "Entrar a sus servidores", "Jaquearlos muajaja", "Dos computadpras", "Despertarse a las 6", "1", "1", "correoResponsable1@correo.com", "1", "1");
		assertTrue(coordinator.registerProject(project));
	}

	//@Test
	public void recuperarProject() {
		DAOProject daoProject = new DAOProject();
		assertNotNull(daoProject.loadProject("Hackear la nasa"));
	}


	@Test
	public void registrarOrganization() throws CustomException {
		Organization organization = new Organization("EfrainIndustries", "La casa de Efrain", "1", "1");
		DAOrganization daoOrganization = new DAOrganization(organization);
		assertTrue(daoOrganization.signUp());
	}

	//@Test
	public void A_createAndDeleteOrg() throws CustomException {
		DAOrganization daOrganization = new DAOrganization(
			new Organization(
				"Org1",
				"La casa del alfajor",
				"1",
				"1"
			)
		);
		assertTrue(daOrganization.signUp());
		assertTrue(daOrganization.delete());
	}


	//@Test
	public void B_createAndDeleteProyect() throws CustomException {
		Project project = new Project();
		project.setName("project2");
		project.setMethodology("a punta de ifs");
		project.setGeneralObjective("Project2");
		project.setMediateObjective("objetivo mediato");
		project.setImmediateObjective("objetivo inmediato");
		project.setResources("dos switch");
		project.setResponsibilities("llegar a las 5");
		project.setStatus("1");
		project.setArea("1");
		project.setResponsible("correoResponsable1@correo.com");
		project.setIdPeriod("1");
		project.setIdOrganization("1");
		DAOProject daoProject = new DAOProject(project);
		assertTrue(daoProject.signUp());
		assertTrue(daoProject.delete());
	}

	@Test
	public void C_isAnother(){
		DAOCoordinator daoCoordinator = new DAOCoordinator(coordinator);
		assertTrue(daoCoordinator.isAnother());
	}
}