package View.practicante.controller;

import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuStudent implements Initializable {
	
	@FXML
	public JFXButton solicitarProyecto;
	
	@FXML
	public JFXButton generarReporte;
	
	@FXML
	public JFXButton subirHorario;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	
	}
	
	public void seleccionarProyecto(MouseEvent mouseEvent) {
		try {
			if (Proyecto.contarProyectos() > 0) {
				MainController.activate(
					"SeleccionarProyecto",
					"Solicitar Proyecto",
					MainController.Sizes.MID
				);
			} else {
				MainController.alert(
					Alert.AlertType.INFORMATION,
					"Sin proyectos",
					"No hay proyectos registrados"
				);
			}
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void generarReporte(MouseEvent mouseEvent) {
		MainController.activate(
			"GenerarReporte",
			"Generar Reporte",
			MainController.Sizes.MID
		);
		
	}
	
	public void subirDocumento(MouseEvent mouseEvent) {
		
		MainController.activate(
			"SubirHorario",
			"Añadir Plan de Actividades",
			MainController.Sizes.MID
		);
	}
}
