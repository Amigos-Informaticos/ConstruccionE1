package View.coordinador.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuCoordinatorController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickStudents(MouseEvent clickEvent){
            MainController.activate("ManageStudents","Practicantes",MainController.Sizes.MID);
    }

    @FXML
    public void onClickProjects(MouseEvent clickEvent){
        MainController.activate("MenuProject","Menú de Proyectos",MainController.Sizes.MID);
    }

    @FXML
    public void onClickOrganizations(MouseEvent clickEvent){
        MainController.activate("AdministrarOrganizacion","Organizaciones",MainController.Sizes.MID);
    }
}