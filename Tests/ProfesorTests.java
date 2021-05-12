package Tests;

import Models.Profesor;
import org.junit.Test;
import tools.Logger;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfesorTests {
    private Profesor profesor = new Profesor(
            "Juan Carlos",
            "Rodriguez Perez",
            "test@hotmail.com",
            "profesor123",
            "N0000004",
            "Vespertino"
    );

    @Test
    public void a_registrar() {
        try {
            assertTrue(this.profesor.registrar());
        } catch (AssertionError | SQLException e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void b_estaRegistrado(){
        try {
            assertTrue(this.profesor.estaRegistrado());
        } catch (AssertionError | SQLException e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void c_deleteProfessor() {
        try {
            assertTrue(this.profesor.eliminar());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void d_iniciarSesion() {
        try {
            assertTrue(this.profesor.iniciarSesion());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
