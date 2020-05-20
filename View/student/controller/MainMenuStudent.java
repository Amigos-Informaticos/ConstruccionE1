package View.student.controller;

import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuStudent implements Initializable {
	@FXML
	private JFXButton solicitarProyecto;
	@FXML
	private JFXButton generarReporte;
	@FXML
	private JFXButton anadirActividad;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void solicitar() {
		MainController.alert(
			Alert.AlertType.WARNING,
			"No hay nada",
			"Te dije que no hay nada jaja soyun mensaje xDD"
		);
	}
}
