package View.MainMenuCoordinator;

import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuCoordinator implements Initializable {
	public JFXButton practicantes;
	public JFXButton proyectos;
	public JFXButton organizaciones;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void onClickPracticantes() {
		MainController.activate(
			"ManageStudents",
			"Administrar estudiantes",
			MainController.Sizes.MID);
	}
	
	public void onClickProyectos() {
	
	}
	
	public void onClickOrganizaciones() {
	
	}
}
