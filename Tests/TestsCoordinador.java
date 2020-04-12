package Tests;

import DAO.DAOPracticante;
import DAO.DAOProyecto;
import DAO.DAOrganizacion;
import Models.Coordinador;
import Models.Organizacion;
import Models.Practicante;
import Models.Proyecto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestsCoordinador {
    Coordinador coordinador = new Coordinador();
    Proyecto proyecto = new Proyecto();
    Practicante practicante = new Practicante();

    DAOProyecto daoProyecto = new DAOProyecto();


    @Test
    public void A_registPracticante(){
        practicante = new Practicante("Edson","Carballo","edson@edson.com","holajaja","s12345");
        assertTrue(coordinador.signUpPracticante(practicante));
    }

    @Test
    public void B_registrarOrganizacion(){
        Organizacion organizacion = new Organizacion("EfrainIndustries","La casa de Efrain","1","1");
        DAOrganizacion daoOrganizacion = new DAOrganizacion(organizacion);
        assertTrue(daoOrganizacion.signUp());
    }

    @Test
    public void C_registProyecto(){
        proyecto = new Proyecto("Facebook 2","Copiando y pegando", "Ganarle al Mark Zukaritas", "Hacernos ricos", "Dominar el mundo","Una nintendo switch","Echarle ganas","1","1","correoResponsable1@correo.com","1","1");
        assertTrue(coordinador.registerProyecto(proyecto));
    }

    @Test
    public void D_recuperarProyecto(){
        DAOProyecto daoProyecto = new DAOProyecto();
        assertNotNull(daoProyecto.loadProyecto("Facebook 2"));
    }

    @Test
    public void E_elegirProyecto(){
        DAOPracticante daoPracticante = new DAOPracticante(practicante);
        daoPracticante.selectProyect(proyecto);
    }

    @Test
    public void F_asignarProyecto(){
        DAOPracticante daoPracticante = new DAOPracticante(practicante);
        daoPracticante.setProyect("Facebook 2");
    }



    @Test
    public void G_eliminarPracticante(){
        DAOPracticante daoPracticante = new DAOPracticante(practicante);
        assertTrue(daoPracticante.delete());
    }

    @Test
    public void H_eliminarProyecto(){
        DAOProyecto daoProyecto = new DAOProyecto(proyecto);
        assertTrue(daoProyecto.delete());
    }
}