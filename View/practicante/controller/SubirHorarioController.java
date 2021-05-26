package View.practicante.controller;

import DAO.DAOPracticante;
import Models.Practicante;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import tools.File;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SubirHorarioController implements Initializable {
	@FXML
	public JFXButton aceptar;
	@FXML
	private JFXTextField nombreArchivo;
	
	private File archivoSeleccionado;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nombreArchivo.setEditable(false);
		aceptar.setDisable(true);
	}
	
	@FXML
	private void abrirExploradorArchivos() {
		this.archivoSeleccionado = new File(String.valueOf(MainController.fileExplorer()));
		if (this.archivoSeleccionado.getName() != null) {
			if (this.extensionValida(this.archivoSeleccionado.getExt())) {
				this.nombreArchivo.setText(this.archivoSeleccionado.getName());
				aceptar.setDisable(false);
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
			"MenuPracticante",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	@FXML
	private void guardarArchivo() throws SQLException {
		Practicante practicante = DAOPracticante.get((Practicante) MainController.get("user"));
		if (practicante.guardarDocumento(archivoSeleccionado)) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"Documento guardado exitosamente",
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
