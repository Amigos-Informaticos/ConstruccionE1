package View.coordinator.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuProjectController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickCreate(MouseEvent clickEvent){
        MainController.activate("CreateProject","Crear Proyecto", MainController.Sizes.MID);
    }

    @FXML
    public void onClickProjects(MouseEvent clickEvent){
        MainController.activate("ListProjects","Lista de Proyectos", MainController.Sizes.MID);
    }
}
