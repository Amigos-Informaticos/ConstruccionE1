package Tests;

import Models.ActividadCalendarizada;
import Models.Asignacion;
import Models.Coordinador;
import Models.Organizacion;
import Models.Practicante;
import Models.Professor;
import Models.Proyecto;
import Models.ResponsableProyecto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CUC {
	
	public Coordinador getCoordinator() {
		return new Coordinador(
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
		organizacion.setTelefono("123456789");
		return organizacion;
	}
	
	public ResponsableProyecto getResponsible() {
		ResponsableProyecto responsible = new ResponsableProyecto();
		responsible.setEmail("responsable@correo.com");
		responsible.setNombre("Quien sabe");
		responsible.setApellido("Sina Pellidos");
		responsible.setPosicion("Barrendero");
		responsible.setOrganizacion(getOrganization());
		return responsible;
	}
	
	public Proyecto getProject() {
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Sistema para Practicas Profesionales");
		proyecto.setDescripcion("Matenme");
		proyecto.setMetodologia("No hay, todo como vaya saliendo");
		proyecto.setObjetivoGeneral("Pasar la materia");
		proyecto.setObjetivoMediato("Salir bien del semestre");
		proyecto.setObjetivoInmediato("No morir en el intento");
		proyecto.setRecursos("tres vatos todos mecos con laptop");
		proyecto.setResponsabilidades("terminar");
		proyecto.setCapacidad(3);
		proyecto.setArea("Desarrollo");
		proyecto.setResponsable(this.getResponsible());
		proyecto.setPeriodo("FEB-JUN 2020");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setFechaInicio("2020-03-19");
		proyecto.setFechaFin("2020-10-07");
		return proyecto;
	}
	
	@Test
	public void aRegisterOrganization() {
		try {
			assertTrue(getOrganization().registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void bRegisterStudent() {
		try {
			assertTrue(getStudent().registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void cRegisterProyect() {
		Proyecto proyecto = getProject();
		ActividadCalendarizada[] activities = {
			new ActividadCalendarizada("Hacer diseno", "2020-05-01"),
			new ActividadCalendarizada("Construirlo", "2020-07-01"),
			new ActividadCalendarizada("Llorar", "2020-07-10")
		};
		proyecto.setActividaadCalendarizada(activities);
		try {
			assertTrue(proyecto.registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void dAssignProject() throws FileNotFoundException, SQLException {
		Asignacion asignacion = null;
		try {
			asignacion = new Asignacion(getStudent(), getProject(), getCoordinator());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		assertTrue(asignacion.assignProject());
	}
	
	@Test
	public void eRemoveProject() {
		try {
			assertTrue(new Asignacion(getStudent(), getProject(), getCoordinator()).removeAssignment());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void fDeleteStudent() {
		try {
			assertTrue(getStudent().eliminar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void zClean() {
		try {
			assertTrue(getProject().eliminarProyecto());
			assertTrue(getResponsible().eliminar());
			assertTrue(getOrganization().eliminar());

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		;
	}


}
