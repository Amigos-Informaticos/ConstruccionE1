package Tests;

import DAO.DAOProject;
import Exceptions.CustomException;
import Models.Project;
import Models.Student;
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
public class StudentTests {
	private static final TelegramBot bot = new TelegramBot("AITests");
	private final Student student = new Student(
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
		try {
			assertTrue(this.student.signUp());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void b_loginStudent() {
		assertTrue(this.student.login());
	}
	
	@Test
	public void c_updatePracticante() {
		this.student.setNames("Emilio Olvedo");
		assertTrue(this.student.update());
	}
	
	@Test
	public void d_selectProject() {
		try {
			assertTrue(this.student.selectProject("Hackear la nasa"));
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void e_getSelections() {
		Project project = new DAOProject().loadProject("Hackear la nasa");
		assertEquals(project.getName(), this.student.getSelection()[0].getName());
	}
	
	@Test
	public void f_setProject() {
		try {
			assertTrue(this.student.setProject("Hackear la nasa"));
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void g_deleteSelectedProject() {
		for (Project project: this.student.getSelection()) {
			assertTrue(this.student.removeSelection(project.getName()));
		}
	}
	
	@Test
	public void i_deleteReply() {
	
	}
	
	@Test
	public void j_removeProject() {
		try {
			assertTrue(this.student.deleteProject());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void k_deleteStudent() {
		assertTrue(this.student.delete());
	}
}
