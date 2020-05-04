package view.mainMenuAdmin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import view.MainController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickProfessor(MouseEvent event){
        MainController.activate("RegistProfessor");
    }
}
