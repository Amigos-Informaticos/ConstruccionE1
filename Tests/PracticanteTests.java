package Tests;

import DAO.DAOProfesor;
import DAO.DAOProyecto;
import Exceptions.CustomException;
import Models.Practicante;
import Models.Proyecto;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;
import tools.Logger;
import tools.TelegramBot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PracticanteTests {
	private static final TelegramBot bot = new TelegramBot("AITests");
	private final Practicante practicante = new Practicante(
		"Juan Gabriel",
		"Lopez Doriga",
		"jbld@correo.com",
		"elmiguel123",
		"S17012130"
	);
	
	@Rule
	public TestWatcher watcher = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			bot.addMessage(description.getClassName() + "." + description.getMethodName() + "\tSUCCESS");
		}
		
		@Override
		protected void failed(Throwable e, Description description) {
			bot.addMessage(description.getClassName() + "." + description.getMethodName() + "\tFAILED");
		}
	};
	
	@AfterClass
	public static void send() {
		bot.send();
	}
	
	@Test
	public void a_signUpStudent() {
		this.practicante.setProfesor(DAOProfesor.getByEmail("joo@hotmail.com"));
		assertTrue(this.practicante.registrarse());
	}
	
	@Test
	public void b_loginStudent() {
		assertTrue(this.practicante.iniciarSesion());
	}
	
	@Test
	public void c_updatePracticante() {
		this.practicante.setNombres("Emilio Olvedo");
		assertTrue(this.practicante.actualizar());
	}
	
	@Test
	public void d_selectProject() {
		assertTrue(this.practicante.seleccionarProyecto("Hackear la nasa"));
	}
	
	@Test
	public void e_getSelections() {
		Proyecto proyecto = new DAOProyecto().cargarProyecto("Hackear la nasa");
		assertEquals(proyecto.getNombre(), this.practicante.getSeleccion()[0].getNombre());
	}
	
	@Test
	public void f_setProject() {
		try {
			assertTrue(this.practicante.setProyecto("Hackear la nasa"));
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void g_deleteSelectedProject() {
		for (Proyecto proyecto: this.practicante.getSeleccion()) {
			assertTrue(this.practicante.eliminarSeleccion(proyecto.getNombre()));
		}
	}
	
	@Test
	public void i_deleteReply() {
	
	}
	
	@Test
	public void j_removeProject() {
		try {
			assertTrue(this.practicante.eliminarProyecto());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void z_deleteStudent() {
		assertTrue(this.practicante.eliminar());
	}
}
