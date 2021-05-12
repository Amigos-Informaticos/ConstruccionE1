package View.administrador.controller;

import View.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuAdministradorController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
	public void onClickProfessor(MouseEvent event) {
		MainController.activate(
			"AdministrarProfesor",
			"Administrar Profesor",
			MainController.Sizes.MID);
	}
	
	@FXML
	public void onClickCoordinator(MouseEvent event) {
		MainController.activate(
			"AdministrarCoordinador",
			"Administrar coordinador",
			MainController.Sizes.MID);
	}

	@FXML
	public void onClickCerrarSesion(MouseEvent event){
		MainController.clearMemory();
		MainController.activate(
				"Login",
				"Login",
				MainController.Sizes.SMALL);
	}
}
