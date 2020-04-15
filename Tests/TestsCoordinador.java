package Tests;

import DAO.DAOCoordinator;
import DAO.DAOProject;
import DAO.DAOrganizacion;
import Models.Coordinator;
import Models.Organizacion;
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


	//@Test
	public void registrarOrganizacion() {
		Organizacion organizacion = new Organizacion("EfrainIndustries", "La casa de Efrain", "1", "1");
		DAOrganizacion daoOrganizacion = new DAOrganizacion(organizacion);
		assertTrue(daoOrganizacion.signUp());
	}

	//@Test
	public void A_createAndDeleteOrg() {
		DAOrganizacion daOrganizacion = new DAOrganizacion(
			new Organizacion(
				"Org1",
				"La casa del alfajor",
				"1",
				"1"
			)
		);
		assertTrue(daOrganizacion.signUp());
		assertTrue(daOrganizacion.delete());
	}


	//@Test
	public void B_createAndDeleteProyect() {
		Project project = new Project();
		project.setNombre("project2");
		project.setMetodologia("a punta de ifs");
		project.setObjetivoGeneral("Project2");
		project.setObjetivoMediato("objetivo mediato");
		project.setObjetivoInmediato("objetivo inmediato");
		project.setRecursos("dos switch");
		project.setResponsabilidades("llegar a las 5");
		project.setStatus("1");
		project.setArea("1");
		project.setResponsable("correoResponsable1@correo.com");
		project.setIdPeriodo("1");
		project.setIdOrganizacion("1");
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