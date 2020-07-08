package View.student.controller;

import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuStudent implements Initializable {
	
	@FXML
	public JFXButton solicitarProyecto;
	
	@FXML
	public JFXButton generarReporte;
	
	@FXML
	public JFXButton anadirActividad;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void chooseProject(MouseEvent mouseEvent) {
		MainController.activate(
			"ChooseProject",
			"Solicitar Proyecto",
			MainController.Sizes.MID
		);
	}
	
	public void generateProject(MouseEvent mouseEvent) {
		MainController.activate(
			"GenerateReport",
			"Generar Reporte",
			MainController.Sizes.MID
		);
		
	}
	
	public void addActivity(MouseEvent mouseEvent) {
		MainController.activate(
			"AddActivityPlan",
			"Añadir Plan de Actividades",
			MainController.Sizes.MID
		);
	}
}
