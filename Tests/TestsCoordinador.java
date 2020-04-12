package Tests;

import DAO.DAOProyecto;
import DAO.DAOrganizacion;
import Models.Coordinador;
import Models.Organizacion;
import Models.Proyecto;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestsCoordinador {
	Coordinador coordinador = new Coordinador();

	/*
	@Test
	public void registPracticante(){
		Practicante edson = new Practicante("Edson","Carballo","edson@edson.com","holajaja","s12345");
		assertTrue(coordinador.signUpPracticante(edson));
	}

	 */
	//@Test
	public void registProyecto() {
		Proyecto proyecto = new Proyecto("Hackear la nasa", "A punta de ifs", "Ver aliens", "Entrar a sus servidores", "Jaquearlos muajaja", "Dos computadpras", "Despertarse a las 6", "1", "1", "correoResponsable1@correo.com", "1", "1");
		assertTrue(coordinador.registerProyecto(proyecto));
	}

	//@Test
	public void recuperarProyecto() {
		DAOProyecto daoProyecto = new DAOProyecto();
		assertNotNull(daoProyecto.loadProyecto("Hackear la nasa"));
	}


	//@Test
	public void registrarOrganizacion() {
		Organizacion organizacion = new Organizacion("EfrainIndustries", "La casa de Efrain", "1", "1");
		DAOrganizacion daoOrganizacion = new DAOrganizacion(organizacion);
		assertTrue(daoOrganizacion.signUp());
	}

	@Test
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

	@Test
	public void B_createAndDeleteProyect() {
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("proyecto2");
		proyecto.setMetodologia("a punta de ifs");
		proyecto.setObjetivoGeneral("Proyecto2");
		proyecto.setObjetivoMediato("objetivo mediato");
		proyecto.setObjetivoInmediato("objetivo inmediato");
		proyecto.setRecursos("dos switch");
		proyecto.setResponsabilidades("llegar a las 5");
		proyecto.setStatus("1");
		proyecto.setArea("1");
		proyecto.setResponsable("correoResponsable1@correo.com");
		proyecto.setIdPeriodo("1");
		proyecto.setIdOrganizacion("1");
		DAOProyecto daoProyecto = new DAOProyecto(proyecto);
		assertTrue(daoProyecto.signUp());
		assertTrue(daoProyecto.delete());
	}
}