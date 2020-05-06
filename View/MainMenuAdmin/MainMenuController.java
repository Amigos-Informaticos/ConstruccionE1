package View.MainMenuAdmin;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	@FXML
	public void onClickProfessor() {
		MainController.activate("RegistProfessor", "Registrar Profesor");
	}
}
