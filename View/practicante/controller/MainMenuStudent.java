package View.practicante.controller;

import DAO.DAOPracticante;
import Models.Practicante;
import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

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
	public ImageView imagenActividad;
	
	private Practicante practicante;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			this.practicante = DAOPracticante.get((Practicante) MainController.get("user"));
			if (practicante.getProyecto() == null) {
				btnSubirHorario.setText("Proyectos Seleccionados");
				imagenActividad.setVisible(false);
				btnSubirHorario.setOnMouseClicked(event -> proyectosSeleccionados());
			} else {
				btnSubirHorario.setText("Subir Horario");
				imagenActividad.setVisible(true);
				btnSubirHorario.setOnMouseClicked(event -> subirDocumento());
			}
		} catch (SQLException throwable) {
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
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void seleccionarProyecto() {
		try {
			if (Proyecto.contarProyectos() > 0) {
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
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void generarReporte() {
		try {
			if (practicante.getProyecto() != null) {
				MainController.activate(
					"SubirReporte",
					"Generar Reporte",
					MainController.Sizes.LARGE
				);
			} else {
				MainController.alert(
					Alert.AlertType.WARNING,
					"No ha sido asignado a ningún proyecto",
					"Necesita estar asignado a un proyecto para poder generar reportes"
				);
			}
		} catch (SQLException e) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
	
	public void subirDocumento() {
		try {
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
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
	}
}
