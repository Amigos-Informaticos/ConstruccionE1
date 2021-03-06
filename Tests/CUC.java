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
		proyecto.setName("Sistema para Practicas Profesionales");
		proyecto.setDescription("Matenme");
		proyecto.setMethodology("No hay, todo como vaya saliendo");
		proyecto.setGeneralObjective("Pasar la materia");
		proyecto.setMediateObjective("Salir bien del semestre");
		proyecto.setImmediateObjective("No morir en el intento");
		proyecto.setResources("tres vatos todos mecos con laptop");
		proyecto.setResponsibilities("terminar");
		proyecto.setCapacity(3);
		proyecto.setArea("Desarrollo");
		proyecto.setResponsible(this.getResponsible());
		proyecto.setPeriod("FEB-JUN 2020");
		proyecto.setOrganization(this.getOrganization());
		proyecto.setStartDate("2020-03-19");
		proyecto.setEndDate("2020-10-07");
		return proyecto;
	}
	
	@Test
	public void aRegisterOrganization() {
		assertTrue(getOrganization().registrar());
	}
	
	@Test
	public void bRegisterStudent() {
		assertTrue(getStudent().registrar());
	}
	
	@Test
	public void cRegisterProyect() {
		Proyecto proyecto = getProject();
		ActividadCalendarizada[] activities = {
			new ActividadCalendarizada("Hacer diseno", "2020-05-01"),
			new ActividadCalendarizada("Construirlo", "2020-07-01"),
			new ActividadCalendarizada("Llorar", "2020-07-10")
		};
		proyecto.setCalendarizedActivities(activities);
		assertTrue(proyecto.registrar());
	}
	
	@Test
	public void dAssignProject() throws FileNotFoundException {
		Asignacion asignacion = new Asignacion(getStudent(), getProject(), getCoordinator());
		assertTrue(asignacion.assignProject());
	}
	
	@Test
	public void eRemoveProject() {
		assertTrue(new Asignacion(getStudent(), getProject(), getCoordinator()).removeAssignment());
	}
	
	@Test
	public void fDeleteStudent() {
		assertTrue(getStudent().eliminar());
	}
	
	@Test
	public void zClean() {
		assertTrue(getProject().eliminarProyecto());
		assertTrue(getResponsible().eliminar());
		assertTrue(getOrganization().eliminar());
	}
}
