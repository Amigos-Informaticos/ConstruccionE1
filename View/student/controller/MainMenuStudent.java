package View.student.controller;

import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
		MainController.activate(
			"ChooseProject",
			"Solicitar Proyecto",
			MainController.Sizes.MID
		);
	}
	
	public void generar() {
		MainController.activate(
			"GenerateReport",
			"Generar Reporte",
			MainController.Sizes.MID
		);
	}
	
	public void aniadir() {
		MainController.activate(
			"AddActivityPlan",
			"Añadir Plan de Actividades",
			MainController.Sizes.MID
		);
	}
}
