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
	private JFXTextField fileName;
	private File selectedFile;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		fileName.setEditable(false);
		verifyPrecondition();
	}
	
	private void verifyPrecondition() {
		boolean has = false;
		try {
			has = ((Practicante) MainController.get("user")).tienePlanActividades();
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
		if (has) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Ya ha subido su plan de actividades",
				"Pulse aceptar para continuar"
			);
			exit();
		}
	}
	
	@FXML
	private void openFileExplorer() {
		this.selectedFile = new File(String.valueOf(MainController.fileExplorer()));
		if (this.selectedFile.getName() != null) {
			if (this.validExtension(this.selectedFile.getExt())) {
				this.fileName.setText(this.selectedFile.getName());
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
	private void exit() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	@FXML
	private void saveFile() throws SQLException {
		Documento file = new Documento(
			selectedFile.getName(),
			"PlanActividades",
			selectedFile,
			(Practicante) MainController.get("user")
		);
		if (file.save()) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"Plan guardado exitosamente",
				"Pulse aceptar para continuar"
			);
			exit();
		} else {
			MainController.alert(
				Alert.AlertType.ERROR,
				"No se pudo guardar su plan",
				"Pulse aceptar para continuar"
			);
		}
	}
	
	private boolean validExtension(String extension) {
		return extension.equals("pdf") || extension.equals("docx");
	}
}
