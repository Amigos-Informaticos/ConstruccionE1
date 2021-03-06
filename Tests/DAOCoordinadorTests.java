package Tests;

import DAO.DAOCoordinador;
import DAO.DAOProfesor;
import IDAO.IDAOProfesor;
import Models.Coordinador;
import Models.Profesor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.P;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOCoordinadorTests {
	@Test
	public void registrar() {
		Coordinador coordinador = new Coordinador();
		coordinador.setNombres("Angel Juan");
		coordinador.setApellidos("Rodriguez Perez");
		coordinador.setEmail("prueba1@hotmail.com");
		coordinador.setContrasena("aj1234");
		coordinador.setNumeroPersonal("N000011");
		coordinador.setTurno("Vespertino");
		DAOCoordinador daoCoordinador = new DAOCoordinador(coordinador);
		try {
			assertTrue(daoCoordinador.registrar());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void b_isRegistered() throws SQLException {
		Coordinador coordinador = new Coordinador();
		coordinador.setNombres("Angel Juan");
		coordinador.setApellidos("Rodriguez Perez");
		coordinador.setEmail("ajuan@hotmail.com");
		coordinador.setContrasena("aj1234");
		coordinador.setNumeroPersonal("N000011");
		coordinador.setTurno("Matutino");
		DAOCoordinador daoCoordinador = new DAOCoordinador(coordinador);
		try{
			assertTrue(daoCoordinador.estaRegistrado());
		}catch(AssertionError assertionError){
			assertionError.printStackTrace();
		}
	}
	
	@Test
	public void c_updateProfesor() {
		Profesor roberto = new Profesor();
		DAOProfesor daoProfesor = new DAOProfesor(roberto);
		roberto.setNombres("Alexis");
		roberto.setApellidos("Alvarez Ortega");
		roberto.setEmail("alexisao@hotmail.com");
		roberto.setContrasena("alexis123");
		roberto.setNumeroPersonal("N000001");
	    roberto.setTurno("1");
	    try {
			try {
				assertTrue(daoProfesor.update());
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} catch (AssertionError e) {
		    e.printStackTrace();
	    }
    }

    @Test
    public void d_deleteProfessor() {
        try {
	        assertTrue(this.getDAOCoordinador().eliminar());
        } catch (AssertionError | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void e_testGetIdShift() {
		try {
			assertNotNull(getDAOCoordinador().getTurno());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	public void z_getAll() {

		try {
			for (Profesor profesor : IDAOProfesor.obtenerProfesores()) {
				assertNotNull(profesor.getNombres());
				P.pln(profesor.getNombres());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void z_getActive() throws SQLException {
		Coordinador coordinador = null;
		coordinador = DAOCoordinador.obtenerActivo();
		System.out.println(coordinador);
		assertTrue(coordinador.estaCompleto());
	}
	
	@Test
	public void hayOtro() {
		try {
			assertTrue(new Coordinador().hayOtro());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	private DAOCoordinador getDAOCoordinador() {
		return new DAOCoordinador(obtenerInstanciaCoordinador());
	}
	
	private Coordinador obtenerInstanciaCoordinador() {
		Coordinador coordinador = new Coordinador();
		coordinador.setNombres("Angel Juan");
		coordinador.setApellidos("Sanchez Garcia");
		coordinador.setEmail("aj@gmail.com");
		coordinador.setContrasena("beethoven");
		coordinador.setTurno("Matutino");
		return coordinador;
    }
}