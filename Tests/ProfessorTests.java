package Tests;

import Exceptions.CustomException;
import Models.Professor;
import org.junit.Test;
import tools.Logger;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfessorTests {
    private Professor professor = new Professor(
            "",
            "Knowledge",
            "test@hotmail.com",
            "profesor123",
            "N0000004",
            "Vespertino"
    );

    @Test
    public void a_signUpProfessor() {
        try {
            assertTrue(this.professor.signUp());
        } catch (AssertionError | SQLException e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void b_updateProfessor() {
        try {
            try {
                assertTrue(this.professor.update());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void c_deleteProfessor() {
        try {
            assertTrue(this.professor.delete());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void d_reactive() {
        try {
            assertTrue(this.professor.reactive());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void d_logInProfessor() {
        try {
            assertTrue(this.professor.logIn());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
