package Tests;

import DAO.DAOOrganizacion;
import DAO.DAOProyecto;
import DAO.DAOResponsableProyecto;
import Exceptions.CustomException;
import Models.ActividadCalendarizada;
import Models.Asignacion;
import Models.Coordinador;
import Models.Organizacion;
import Models.Practicante;
import Models.Proyecto;
import Models.ResponsableProyecto;
import org.junit.Test;
import tools.P;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsCoordinador {
	private Coordinador coordinador = new Coordinador();
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
		proyecto.setNombre("Proyecto1");
		proyecto.setDescripcion("Esel primer proyecto jaja");
		proyecto.setMetodologia("Cascada ágil");
		proyecto.setObjetivoGeneral("Objetivo general");
		proyecto.setObjetivoMediato("Objetivo mediato");
		proyecto.setObjetivoInmediato("ganar jaja");
		proyecto.setRecursos("todos los recursos del mundo");
		proyecto.setResponsabilidades("Llegar temprano e irse por las tortas");
		proyecto.setCapacidad(3);
		proyecto.setArea("Vigilancia");
		proyecto.setResponsable(this.getResponsible());
		proyecto.setPeriodo("FEB-JUN 2020");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setFechaInicio("2020-07-19");
		proyecto.setFechaFin("2020-09-07");
		return proyecto;
	}
	
	public ResponsableProyecto getResponsible() {
		ResponsableProyecto responsible = new ResponsableProyecto();
		responsible.setEmail("responsible@gmail.com");
		responsible.setNombre("Responsable Miguel");
		responsible.setApellido("Apellidos responsable");
		responsible.setPosicion("Intendente");
		try {
			responsible.setOrganizacion(DAOOrganizacion.obtenerPorNombre("Organizacion 1"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return responsible;
	}
	
	@Test
	public void studentManagementTest() {
		Practicante practicante = new Practicante("Efrain", "Arenas", "efrain@arenas.com", "contrasenia123", "s18012138");
		try {
			assertTrue(coordinador.registrarPracticante(practicante));
			assertTrue(coordinador.eliminarPracticante(practicante));
		} catch (CustomException exception) {
			exception.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void projectManagementTest() {
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Hackear el pentagono");
		proyecto.setDescripcion("Hackear el pentágono con un tamagotchi");
		proyecto.setMetodologia("A punta de hacer pings");
		proyecto.setObjetivoGeneral("Descubrir secretos");
		proyecto.setObjetivoMediato("Dominar el mundo");
		proyecto.setObjetivoInmediato("Empezar manana");
		proyecto.setRecursos("Dos computadoras y un Xiaomi");
		proyecto.setResponsabilidades("Echarle ganas y pararse temprano");
		proyecto.setArea("Desarrollo");
		try {
			proyecto.setResponsable(DAOResponsableProyecto.get("correoResponsable1@correo.com"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		proyecto.setPeriodo("FEB-JUN 2020");
		try {
			proyecto.setOrganization("1");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		Practicante practicante = new Practicante("Efrain",
			"Arenas",
			"efrain@correo.com",
			"contrasenia123",
			"s18012138");
		
		try {
			assertTrue(coordinador.registrarProyecto(proyecto));
			assertTrue(coordinador.registrarPracticante(practicante));
			practicante.seleccionarProyecto("Hackear el pentagono");
			coordinador.asignarProyecto(practicante, "Hackear el pentagono");
			assertTrue(coordinador.eliminarProyecto(proyecto));
			assertTrue(coordinador.eliminarPracticante(practicante));
		} catch (CustomException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
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
		try {
			assertTrue(angel.registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void signUpOrganization() {
		try {
			assertTrue(this.getOrganization().registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void registerProject() {
		try {
			assertTrue(this.getProject().registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void getAllProjects() {
		try {
			for (Proyecto proyecto: DAOProyecto.obtenerTodos()) {
				assertNotNull(proyecto);
				P.pln(proyecto.getNombre());
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void getRequestedProjects() {
		try {
			for (Proyecto proyecto: Asignacion.proyectosSeleccionados(practicante)) {
				assertNotNull(proyecto);
				P.pln(proyecto.getNombre());
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void testCalendarization() {
		ActividadCalendarizada[] calendarizedActivities = new ActividadCalendarizada[6];
		for (int i = 0; i < 6; i++) {
			calendarizedActivities[i] = new ActividadCalendarizada();
			calendarizedActivities[i].setNombre("ac" + i);
			calendarizedActivities[i].setFecha("2020-10-11");
		}
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto Genial");
		proyecto.setDescripcion("Esel primer proyecto jaja");
		proyecto.setMetodologia("Cascada ágil");
		proyecto.setObjetivoGeneral("Objetivo general");
		proyecto.setObjetivoMediato("Objetivo mediato");
		proyecto.setObjetivoInmediato("ganar jaja");
		proyecto.setRecursos("todos los recursos del mundo");
		proyecto.setResponsabilidades("Llegar temprano e irse por las tortas");
		proyecto.setCapacidad(3);
		proyecto.setArea("Vigilancia");
		proyecto.setResponsable(this.getResponsible());
		proyecto.setPeriodo("FEB-JUN");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setFechaInicio("2020-07-19");
		proyecto.setFechaFin("2020-09-07");
		try {
			assertTrue(proyecto.registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}