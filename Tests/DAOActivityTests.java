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

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class DAOActivityTests {

    @Test
    public void A_createActivity(){
        try{
        assertTrue(getDAOActivity().create());
        }catch(CustomException e){
            System.out.println(e.getCauseMessage());
            new Logger().log(e);
    }
    }

    private DAOActivity getDAOActivity() {
        return new DAOActivity(getInstanceActivity());
    }
    private Activity getInstanceActivity() {
        return new Activity(
                "2.- Reporte de lectura",
                "Entregar reporte de lectura del capitulo 2 del libro de Wieggers",
                "2020-09-14 17:00:00",
                "/src/README.md"
        );
    }
}
