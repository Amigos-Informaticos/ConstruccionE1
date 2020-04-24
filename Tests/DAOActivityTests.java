package Tests;

import DAO.DAOActivity;
import DAO.DAOProfessor;
import Exceptions.CustomException;
import Models.Activity;
import Models.Professor;
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
        try {
            assertTrue(getDAOActivity().create());
        } catch (CustomException e) {
            System.out.println(e.getCauseMessage());
            new Logger().log(e);
        }
    }

    @Test
    public void updateActivity(){
        try{
            assertTrue(getDAOActivity().update());
        }catch(CustomException e){
            new Logger().log(e);
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
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @Test
    public void D_deleteActivity() {
        try {
            assertTrue(getDAOActivity().delete());
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    private DAOActivity getDAOActivity() {
        return new DAOActivity(getInstanceActivity());
    }

    private Activity getInstanceActivity() {
        return new Activity(
                "Holaaaa",
                "ADiossss",
                "2020-09-14 17:00:00",
                "/src/README.md"
        );
    }
}
