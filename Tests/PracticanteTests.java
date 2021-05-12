package Tests;

import DAO.DAOProfesor;
import DAO.DAOProyecto;
import Exceptions.CustomException;
import Models.Asignacion;
import Models.Practicante;
import Models.Proyecto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Logger;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PracticanteTests {
	private final Practicante practicante = new Practicante(
		"Juan Gabriel",
		"Lopez Doriga",
		"jbld@correo.com",
		"elmiguel123",
		"S17012130"
	);
	
	@Test
	public void a_registrarPracticante() {
		try {
			this.practicante.setProfesor(DAOProfesor.getByEmail("profesor@gmail.com"));
			assertTrue(this.practicante.registrar());
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void b_loginPracticante() {
		try {
			assertTrue(this.practicante.iniciarSesion());
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void c_actualizarPracticante() {
		this.practicante.setNombres("Emilio Olvedo");
		try {
			assertTrue(this.practicante.actualizar());
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void d_seleccionarProyecto() {
		try {
			Proyecto proyecto = DAOProyecto.obtenerPorNombre("Gestion Estudiantes");
			assertTrue(Asignacion.guardarSolicitud(this.practicante, proyecto));
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void e_obtenerProyectosSeleccionados() {
		try {
			this.practicante.setProfesor(DAOProfesor.getByEmail("profesor@gmail.com"));
			Proyecto proyecto = DAOProyecto.obtenerPorNombre("Gestion Estudiantes");
			assertEquals(proyecto.getNombre(), this.practicante.getSeleccion()[0].getNombre());
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void f_asignarProyecto() {
		try {
			Proyecto proyecto = DAOProyecto.obtenerPorNombre("Gestion Estudiantes");
			assertTrue(this.practicante.asignarProyecto(proyecto.getNombre()));
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void g_eliminarProyectoSeleccionado() {
		try {
			this.practicante.setProfesor(DAOProfesor.getByEmail("profesor@gmail.com"));
			Proyecto[] proyectosSeleccinados = this.practicante.getSeleccion();
			if (proyectosSeleccinados != null) {
				for (Proyecto proyecto: proyectosSeleccinados) {
					assertTrue(this.practicante.eliminarSeleccion(proyecto.getNombre()));
				}
			}
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void h_eliminarProyectoAsignado() {
		try {
			assertTrue(this.practicante.eliminarProyecto());
		} catch (CustomException | SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
	
	@Test
	public void i_eliminarPracticante() {
		try {
			assertTrue(this.practicante.eliminar());
		} catch (SQLException throwable) {
			Logger.staticLog(throwable);
		}
	}
}
