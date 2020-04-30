package View.Login;

import View.MainController;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private JFXTextField nameText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameText.addEventFilter(KeyEvent.ANY, handleLetters);
    }

    EventHandler<KeyEvent> handleLetters = new EventHandler<KeyEvent>() {
        private boolean willConsume;
        @Override
        public void handle(KeyEvent event) {
            Object tempO = event.getSource();
            if(willConsume){
                event.consume();
            }
            String temp = event.getCode().toString();
            if(!event.getCode().toString().matches("[a-zA-Z]")
                    && event.getCode() != KeyCode.BACK_SPACE
                    && event.getCode() != KeyCode.SHIFT){
                if(event.getEventType() == KeyEvent.KEY_PRESSED){
                    willConsume = true;
                }else if(event.getEventType() == KeyEvent.KEY_RELEASED){
                    willConsume = false;
                }
            }
        }
    };

    public void onIngresarButtonClick(){
        MainController mainController = new MainController();
        mainController.activate("Registrar Estudiante","RegistAStudent/RegistAStudent.fxml");
    }
}
