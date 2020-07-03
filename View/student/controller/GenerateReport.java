package View.student.controller;

import View.MainController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReport implements Initializable {
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void volver() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID
		);
	}
}
