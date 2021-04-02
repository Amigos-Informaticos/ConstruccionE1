package View.practicante.controller;

import Models.Documento;
import Models.Practicante;
import View.MainController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import tools.File;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddActivityPlan implements Initializable {
	@FXML
	private JFXTextField nombreArchivo;
	private File archivoSeleccionado;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nombreArchivo.setEditable(false);
		verificarPrecondicion();
	}
	
	private void verificarPrecondicion() {
		boolean archivoSubido = false;
		try {
			archivoSubido = ((Practicante) MainController.get("user")).tienePlanActividades();
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
		if (archivoSubido) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Ya ha subido su plan de actividades",
				"Pulse aceptar para continuar"
			);
			salir();
		}
	}
	
	@FXML
	private void abrirExploradorArchivos() {
		this.archivoSeleccionado = new File(String.valueOf(MainController.fileExplorer()));
		if (this.archivoSeleccionado.getName() != null) {
			if (this.extensionValida(this.archivoSeleccionado.getExt())) {
				this.nombreArchivo.setText(this.archivoSeleccionado.getName());
			} else {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Formato incorrecto",
					"Verifique que su archivo se encuentre en formato PDF o DOCX"
				);
			}
		}
	}
	
	@FXML
	private void salir() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	@FXML
	private void guardarArchivo() throws SQLException {
		Documento file = new Documento(
			archivoSeleccionado.getName(),
			"PlanActividades",
			archivoSeleccionado,
			(Practicante) MainController.get("user")
		);
		if (file.save()) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"Plan guardado exitosamente",
				"Pulse aceptar para continuar"
			);
			salir();
		} else {
			MainController.alert(
				Alert.AlertType.ERROR,
				"No se pudo guardar su plan",
				"Pulse aceptar para continuar"
			);
		}
	}
	
	private boolean extensionValida(String extension) {
		return extension.equals("pdf") ||
			extension.equals("docx") ||
			extension.equals("doc");
	}
}
