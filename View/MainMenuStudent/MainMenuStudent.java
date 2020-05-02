package View.MainMenuStudent;

import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuStudent implements Initializable {
	@FXML
	private JFXButton buttonPracticantes;
	@FXML
	private JFXButton buttonProyectos;
	@FXML
	private JFXButton buttonOrganizaciones;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void clickOnPracticantes() {
		MainController.alert(
			Alert.AlertType.WARNING,
			"No hay nada",
			"Te dije que no hay nada jaja soyun mensaje xDD"
		);
	}
}
