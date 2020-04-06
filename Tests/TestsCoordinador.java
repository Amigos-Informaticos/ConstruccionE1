package Tests;

import Models.Coordinador;
import Models.Proyecto;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsCoordinador {
    Coordinador coordinador = new Coordinador();
    /*
    @Test
    public void registPracticante(){
        Practicante edson = new Practicante("Edson","Carballo","edson@edson.com","holajaja","s12345");
        assertTrue(coordinador.signUpPracticante(edson));
    }

     */
    @Test
    public void registProyecto(){
        Proyecto proyecto = new Proyecto("Hackear la nasa","A punta de ifs", "Ver aliens", "Entrar a sus servidores", "Jaquearlos muajaja","Dos computadpras","Despertarse a las 6","1","1","correoResponsable1@correo.com","1","1");
        assertTrue(coordinador.registerProyecto(proyecto));
    }
}
