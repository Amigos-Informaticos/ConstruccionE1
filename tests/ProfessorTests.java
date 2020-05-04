package tests;

import dao.DAOShift;
import exceptions.CustomException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Professor;
import models.Shift;
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
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @Test
    public void b_updateProfessor() {
        try {
            assertTrue(this.professor.update());
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @Test
    public void c_deleteProfessor() {
        try {
            assertTrue(this.professor.delete());
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @Test
    public void d_reactive() {
        try {
            assertTrue(this.professor.reactive());
        } catch (CustomException e) {
            new Logger().log(e);
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
