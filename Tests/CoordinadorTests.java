package Tests;

import Models.Coordinador;
import org.junit.Test;
import tools.Logger;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinadorTests {
    private Coordinador coordinador = new Coordinador(
            "Angel Juan",
            "Sanchez Garcia",
            "angeljsg@uv.com.mx",
            "beethoven",
            "4030612",
            "Matutino"
    );
    @Test
    public void a_registrar(){
        try {
            assertTrue(this.coordinador.registrar());
        } catch (SQLException e) {
            Logger.staticLog(e, true);
        }
    }

    @Test
    public void b_actualizar(){
        try{
            assertTrue(this.coordinador.actualizar());
        } catch (SQLException sqlException){
            Logger.staticLog(sqlException, true);
        }
    }

    @Test
    public void b_eliminar(){
        try {
            assertTrue(this.coordinador.eliminar());
        } catch (SQLException e) {
            Logger.staticLog(e, true);
        }
    }
}
