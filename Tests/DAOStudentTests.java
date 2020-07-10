package Tests;

import DAO.DAOStudent;
import Models.Student;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOStudentTests {
	
	@Test
	public void a_signUpPracticante() {
		assertTrue(getInstancePracticante().signUp());
	}
	
	@Test
	public void b_loginPracticante() { //assertTrue(getDAOPracticante().logIn());
	}
	
	@Test
	public void c_getAllPracticantes() {
		Student[] students = DAOStudent.getAll();
		for (Student student: students) {
			assertNotNull(student);
		}
	}
	
	@Test
	public void d_getPracticante() {
		//assertNotNull(DAOPracticante.get(getInstancePracticante()));
	}
	
	@Test
	public void e_updatePracticante() {
		Student student = getInstancePracticante();
		student.setNames("Jose Joaquin");
		//assertTrue(new DAOPracticante(practicante).update());
	}
	
	@Test
	public void f1_selectProyect() {
		//assertTrue(getDAOPracticante().selectProject("Hackear la nasa"));
	}
	
	@Test
	public void f2_getSelectedProyects() {
		/*for (Proyecto proyecto: getDAOPracticante().getProjects()) {
			assertNotNull(proyecto);
		}*/
	}
	
	@Test
	public void f3_deleteSelectedProyect() {
		//assertTrue(getDAOPracticante().deleteSelectedProject("Hackear la nasa"));
	}
	
	@Test
	public void g1_addReport() {
		/*assertTrue(getDAOPracticante().addReport("src/Configuration/Configuration.java",
			"Configuracion"));*/
	}
	
	@Test
	public void g2_deleteReport() {
		//assertTrue(getDAOPracticante().deleteReport("Configuracion"));
	}
	
	@Test
	public void h1_replyActivity() {
		/*assertTrue(
			getDAOPracticante().replyActivity(
				"Sin asignar",
				"src/Configuration/Configuration.java"
			)
		);*/
	}
	
	@Test
	public void z_deletePracticante() {
		assertTrue(getDAOPracticante().delete());
	}
	
	private DAOStudent getDAOPracticante() {
		return new DAOStudent(getInstancePracticante());
	}
	
	private Student getInstancePracticante() {
		return new Student(
			"Miguel Joaquin",
			"Lopez Doriga",
			"mjld@hotmail.com",
			"elmiguel123",
			"S18012150"
		);
	}
}
