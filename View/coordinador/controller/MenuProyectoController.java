package View.coordinador.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuProyectoController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickCreate(MouseEvent clickEvent){
        MainController.activate("CrearProyecto","Crear Proyecto", MainController.Sizes.LARGE);
    }

    @FXML
    public void onClickProjects(MouseEvent clickEvent){
        MainController.activate("ListProjects","Lista de Proyectos", MainController.Sizes.MID);
    }
}
