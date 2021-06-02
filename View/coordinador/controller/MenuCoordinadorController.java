package View.coordinador.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuCoordinadorController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickStudents(MouseEvent clickEvent) {
        MainController.activate("AdministrarPracticante", "Practicantes", MainController.Sizes.MID);
    }

    @FXML
    public void onClickProjects(MouseEvent clickEvent) {
        MainController.activate("MenuProyecto", "Menú de Proyectos", MainController.Sizes.MID);
    }

    @FXML
    public void onClickOrganizations(MouseEvent clickEvent) {
        MainController.activate("AdministrarOrganizacion", "Organizaciones", MainController.Sizes.MID);
    }
}