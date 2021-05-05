package View.practicante.controller;

import DAO.DAOPracticante;
import Exceptions.CustomException;
import Models.Practicante;
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
	public JFXButton btnSolicitarProyecto;
	
	@FXML
	public JFXButton btnGenerarReporte;
	
	@FXML
	public JFXButton btnSubirHorario;
	
	private Practicante practicante;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			this.practicante = DAOPracticante.get((Practicante) MainController.get("user"));
			if (practicante.getProyecto() != null) {
				btnSubirHorario.setText("Proyectos Seleccionados");
				btnSubirHorario.setOnMouseClicked(event -> proyectosSeleccionados());
			} else {
				btnSubirHorario.setText("Subir documento");
				btnSubirHorario.setOnMouseClicked(event -> subirDocumento());
			}
		} catch (SQLException | CustomException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void proyectosSeleccionados() {
		try {
			if (practicante.getSeleccion() != null) {
				MainController.activate(
					"VisualizarProyectosSeleccionados",
					"Proyectos Seleccionados",
					MainController.Sizes.MID);
			}
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void seleccionarProyecto(MouseEvent mouseEvent) {
		try {
			if (Proyecto.contarProyectos() > 0) {
				Practicante practicante = DAOPracticante.get(
					(Practicante) MainController.get("user"));
				Proyecto[] proyectosSeleccionados = practicante.getSeleccion();
				if (proyectosSeleccionados == null || proyectosSeleccionados.length < 3) {
					MainController.activate(
						"SeleccionarProyecto",
						"Solicitar Proyecto",
						MainController.Sizes.MID
					);
				} else {
					MainController.alert(
						Alert.AlertType.WARNING,
						"Limite de proyectos seleccionados",
						"Ya ha llegado a su límite de 3 proyectos"
					);
				}
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
	
	public void subirDocumento() {
		try {
			Practicante practicante = DAOPracticante.get((Practicante) MainController.get("user"));
			if (practicante.getProyecto() != null) {
				MainController.activate(
					"SubirHorario",
					"Añadir Plan de Actividades",
					MainController.Sizes.MID
				);
			} else {
				MainController.alert(
					Alert.AlertType.ERROR,
					"Sin proyecto",
					"No ha sido asignado a ningún proyecto"
				);
			}
		} catch (SQLException | CustomException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
}
