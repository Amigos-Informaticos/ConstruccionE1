package View.Login;

import View.MainController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void onIngresarButtonClick(){
        MainController mainController = new MainController();
        mainController.activate("Registrar Estudiante","RegistAStudent/RegistAStudent.fxml");
    }
}
