package View.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCoordinatorController implements Initializable {
    @FXML private Label lblNombre;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNombre.setText("Hola mundo!");
    }
}
