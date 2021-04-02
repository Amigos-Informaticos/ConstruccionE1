package Tests;

import DAO.DAOPracticante;
import Models.Practicante;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOPracticanteTests {
	
	@Test
	public void a_signUpPracticante() throws SQLException {
		assertTrue(getInstancePracticante().registrar());
	}
	
	@Test
	public void b_loginPracticante() { //assertTrue(getDAOPracticante().logIn());
	}
	
	@Test
	public void c_getAllPracticantes() throws SQLException {
		Practicante[] practicantes = DAOPracticante.obtenerTodos();
		for (Practicante practicante: practicantes) {
			assertNotNull(practicante);
		}
	}
	
	@Test
	public void d_getPracticante() {
		//assertNotNull(DAOPracticante.get(getInstancePracticante()));
	}
	
	@Test
	public void e_updatePracticante() {
		Practicante practicante = getInstancePracticante();
		practicante.setNombres("Jose Joaquin");
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
	public void z_deletePracticante() throws SQLException {
		assertTrue(getDAOPracticante().eliminar());
	}
	
	private DAOPracticante getDAOPracticante() {
		return new DAOPracticante(getInstancePracticante());
	}
	
	private Practicante getInstancePracticante() {
		return new Practicante(
			"Miguel Joaquin",
			"Lopez Doriga",
			"mjld@hotmail.com",
			"elmiguel123",
			"S18012150"
		);
	}
}
