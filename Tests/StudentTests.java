package Tests;

import DAO.DAOProject;
import Exceptions.CustomException;
import Models.Project;
import Models.Student;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTests {
	private Student student = new Student(
		"Miguel Joaquin",
		"Lopez Doriga",
		"mjld@hotmail.com",
		"elmiguel123",
		"S18012150"
	);
	
	@Test
	public void a_signUpStudent() {
		try {
			assertTrue(this.student.register());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void b_loginStudent() {
		try {
			assertTrue(this.student.login());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void c_updatePracticante() {
		this.student.setNames("Emilio Olvedo");
		try {
			assertTrue(this.student.update());
		} catch (CustomException e) {
			new Logger().log(e);
		}
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
		try {
			assertEquals(project, this.student.getSelection()[0]);
		} catch (CustomException e) {
			new Logger().log(e);
		}
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
		try {
			assertTrue(this.student.removeSelection("Hackear la nasa"));
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
	
	@Test
	public void h_replyActivity() {
		//assertTrue(this.student.replyActivity())
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
		try {
			assertTrue(this.student.delete());
		} catch (CustomException e) {
			new Logger().log(e);
		}
	}
}
