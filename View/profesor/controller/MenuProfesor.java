package View.profesor.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuProfesor {
    @FXML
    public void onClickEvaluateReport(MouseEvent event){
        MainController.activate("ListaPracticante", "Administrar coordinador", MainController.Sizes.MID);
    }
    @FXML
    public void onClickListaPracticantes(MouseEvent event) {
        MainController.activate("ListaPracticantes", "Lista practicantes", MainController.Sizes.MID);
    }
}
