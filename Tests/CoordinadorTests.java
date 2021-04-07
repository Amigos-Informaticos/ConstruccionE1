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
            "aj@uv.com",
            "aj1234?!",
            "N030612",
            "Mixto"
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
    public void b_eliminar(){
        try {
            assertTrue(this.coordinador.eliminar());
        } catch (SQLException e) {
            Logger.staticLog(e, true);
        }
    }
}
