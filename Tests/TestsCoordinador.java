package Tests;

import DAO.DAOOrganizacion;
import DAO.DAOProjectResponsible;
import DAO.DAOProyecto;
import Exceptions.CustomException;
import Models.Asignacion;
import Models.CalendarizedActivity;
import Models.Coordinador;
import Models.Organizacion;
import Models.Practicante;
import Models.ProjectResponsible;
import Models.Proyecto;
import org.junit.Test;
import tools.P;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsCoordinador {
	Coordinador coordinador = new Coordinador();
	private final Practicante practicante = new Practicante(
		"Juan Gabriel",
		"Lopez Doriga",
		"jbld@correo.com",
		"elmiguel123",
		"S17012130"
	);
	
	public Organizacion getOrganization() {
		Organizacion organizacion = new Organizacion();
		organizacion.setNombre("Organizacion 1");
		organizacion.setSector("Videojuegos");
		organizacion.setDireccion(
			"Revillagigedo",
			"1928",
			"Centro",
			"Veracruz"
		);
		organizacion.setTelefono("2291763687");
		return organizacion;
	}
	
	public Proyecto getProject() {
		Proyecto proyecto = new Proyecto();
		proyecto.setName("Proyecto1");
		proyecto.setDescription("Esel primer proyecto jaja");
		proyecto.setMethodology("Cascada ágil");
		proyecto.setGeneralObjective("Objetivo general");
		proyecto.setMediateObjective("Objetivo mediato");
		proyecto.setImmediateObjective("ganar jaja");
		proyecto.setResources("todos los recursos del mundo");
		proyecto.setResponsibilities("Llegar temprano e irse por las tortas");
		proyecto.setCapacity(3);
		proyecto.setArea("Vigilancia");
		proyecto.setResponsible(this.getResponsible());
		proyecto.setPeriod("FEB-JUN 2020");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setStartDate("2020-07-19");
		proyecto.setEndDate("2020-09-07");
		return proyecto;
	}
	
	public ProjectResponsible getResponsible() {
		ProjectResponsible responsible = new ProjectResponsible();
		responsible.setEmail("responsible@gmail.com");
		responsible.setNames("Responsable Miguel");
		responsible.setLastNames("Apellidos responsable");
		responsible.setPosition("Intendente");
		responsible.setOrganization(DAOOrganizacion.obtenerPorNombre("Organizacion 1"));
		return responsible;
	}
	
	@Test
	public void studentManagementTest() {
		Practicante practicante = new Practicante("Efrain", "Arenas", "efrain@arenas.com", "contrasenia123", "s18012138");
		try {
			assertTrue(coordinador.signUpStudent(practicante));
			assertTrue(coordinador.deleteStudent(practicante));
		} catch (CustomException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void projectManagementTest() {
		Proyecto proyecto = new Proyecto();
		proyecto.setName("Hackear el pentagono");
		proyecto.setDescription("Hackear el pentágono con un tamagotchi");
		proyecto.setMethodology("A punta de hacer pings");
		proyecto.setGeneralObjective("Descubrir secretos");
		proyecto.setMediateObjective("Dominar el mundo");
		proyecto.setImmediateObjective("Empezar manana");
		proyecto.setResources("Dos computadoras y un Xiaomi");
		proyecto.setResponsibilities("Echarle ganas y pararse temprano");
		proyecto.setArea("Desarrollo");
		proyecto.setResponsible(DAOProjectResponsible.get("correoResponsable1@correo.com"));
		proyecto.setPeriod("FEB-JUN 2020");
		proyecto.setOrganization("1");
		Practicante practicante = new Practicante("Efrain",
			"Arenas",
			"efrain@correo.com",
			"contrasenia123",
			"s18012138");
		
		try {
			assertTrue(coordinador.signUpProject(proyecto));
			assertTrue(coordinador.signUpStudent(practicante));
			practicante.seleccionarProyecto("Hackear el pentagono");
			coordinador.assignProject(practicante, "Hackear el pentagono");
			assertTrue(coordinador.deleteProject(proyecto));
			assertTrue(coordinador.deleteStudent(practicante));
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void signUpCoordinator() {
		Coordinador angel = new Coordinador(
			"Angel Juan",
			"Sanchez",
			"ajs@hotmail.com",
			"angel123",
			"N000222"
		);
		angel.signUp();
	}
	
	@Test
	public void signUpOrganization() {
		assertTrue(this.getOrganization().registrar());
	}
	
	@Test
	public void registerProject() {
		assertTrue(this.getProject().register());
	}
	
	@Test
	public void getAllProjects() {
		for (Proyecto proyecto: DAOProyecto.getAll()) {
			assertNotNull(proyecto);
			P.pln(proyecto.getNombre());
		}
	}
	
	@Test
	public void getRequestedProjects() {
		for (Proyecto proyecto: Asignacion.requestedProjects(practicante)) {
			assertNotNull(proyecto);
			P.pln(proyecto.getNombre());
		}
	}
	
	@Test
	public void testCalendarization() {
		CalendarizedActivity[] calendarizedActivities = new CalendarizedActivity[6];
		for (int i = 0; i < 6; i++) {
			calendarizedActivities[i] = new CalendarizedActivity();
			calendarizedActivities[i].setName("ac" + i);
			calendarizedActivities[i].setDate("2020-10-11");
		}
		Proyecto proyecto = new Proyecto();
		proyecto.setName("Proyecto Genial");
		proyecto.setDescription("Esel primer proyecto jaja");
		proyecto.setMethodology("Cascada ágil");
		proyecto.setGeneralObjective("Objetivo general");
		proyecto.setMediateObjective("Objetivo mediato");
		proyecto.setImmediateObjective("ganar jaja");
		proyecto.setResources("todos los recursos del mundo");
		proyecto.setResponsibilities("Llegar temprano e irse por las tortas");
		proyecto.setCapacity(3);
		proyecto.setArea("Vigilancia");
		proyecto.setResponsible(this.getResponsible());
		proyecto.setPeriod("FEB-JUN");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setStartDate("2020-07-19");
		proyecto.setEndDate("2020-09-07");
		proyecto.setCalendarizedActivities(calendarizedActivities);
		proyecto.register();
	}
}