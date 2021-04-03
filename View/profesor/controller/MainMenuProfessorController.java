package View.profesor.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MainMenuProfessorController {
    @FXML
    public void onClickGenerateActivity(MouseEvent event){
        MainController.activate("GenerateActivity", "Generar Actividad", MainController.Sizes.MID);
    }
    @FXML
    public void onClickEvaluateReport(MouseEvent event){
        MainController.activate("AdminCoordinator", "Administrar coordinador", MainController.Sizes.MID);
    }
}
