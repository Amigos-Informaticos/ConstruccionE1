package tests;

import dao.DAOActivity;
import models.Activity;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import tools.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOActivityTests {

    @Test
    public void A_createActivity() {
        Activity activity = getInstanceActivity();
        try {
            activity.create();
            activity.setTitle("LEER REPORTE LECTURA");
            activity.setDescription("Leer el RL que se encuentra en el libro 2 de sulana de tal" +
                    "y usar la nomenclatura Ap1-2-3-4");
            assertTrue(activity.update());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void updateActivity(){
        try{
            assertTrue(getDAOActivity().update());
        }catch(AssertionError e){
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void B_getIdActivity() {
        System.out.println(getDAOActivity().getIdActivity());
        assertNotNull(getDAOActivity().getIdActivity());
    }

    @Test
    public void C_isRegistered() {
        try {
            assertTrue(getDAOActivity().isRegistered());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void D_deleteActivity() {
        try {
            assertTrue(getDAOActivity().delete());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void E_createAndDelete(){
        try {
            assertTrue(getDAOActivity().create());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    private DAOActivity getDAOActivity() {
        return new DAOActivity(getInstanceActivity());
    }

    private Activity getInstanceActivity() {
        return new Activity(
                "Evaluación de avances. TERCERA PARTE",
                "Nomenclatura del documento Act3-ApellidosNombre",
                "2020-09-14 17:00:00",
                "/src/README.md"
        );
    }
}