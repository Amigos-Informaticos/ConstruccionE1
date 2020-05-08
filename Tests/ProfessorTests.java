package Tests;

import DAO.DAOShift;
import Exceptions.CustomException;
import Models.Professor;
import Models.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import tools.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfessorTests {
    private Professor professor = new Professor(
            "Dr. Knucles",
            "Knowledge",
            "profesor1@hotmail.com",
            "profesor123",
            "N000003",
            "1"
    );

    @Test
    public void a_signUpProfessor() {
        try {
            assertTrue(this.professor.signUp());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void b_updateProfessor() {
        try {
            assertTrue(this.professor.update());
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
        }
    }

    @Test
    public void d_reactive() {
        try {
            assertTrue(this.professor.reactive());
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @Test
    public void d_logInProfessor() {
        try {
            assertTrue(this.professor.logIn());
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @Test
    public void f_listShift() {
        Shift shift = new Shift();
        DAOShift dao = new DAOShift(shift);
        ObservableList<String> listShift = FXCollections.observableArrayList();
        assertTrue(dao.fillShift(listShift));
    }

    @Test
    public void f_listShiftModel(){
        Shift shift = new Shift();
        ObservableList<String> listShift = FXCollections.observableArrayList();
        assertTrue(shift.fillShift(listShift));
    }
}
