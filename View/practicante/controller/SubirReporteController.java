package View.practicante.controller;

import DAO.DAOPracticante;
import Models.Practicante;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SubirReporteController implements Initializable {
	public JFXComboBox<String> cmbTipoReporte;
	public JFXTextArea txtActividadesPlaneadas;
	public JFXTextArea txtActividadesRealizadas;
	public JFXDatePicker fechaInicial;
	public JFXDatePicker fechaFinal;
	public JFXTextArea txtResumen;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> tiposReporte = FXCollections.observableArrayList();
		tiposReporte.add("Reporte Parcial");
		tiposReporte.add("Reporte Mensual");
		cmbTipoReporte.setItems(tiposReporte);
		cmbTipoReporte.setValue("Reporte Parcial");
		txtActividadesPlaneadas.setOnMouseClicked(event -> {
			txtActividadesPlaneadas.setUnFocusColor(Paint.valueOf("black"));
		});
		txtActividadesRealizadas.setOnMouseClicked(event -> {
			txtActividadesRealizadas.setUnFocusColor(Paint.valueOf("black"));
		});
		txtResumen.setOnMouseClicked(event -> {
			txtResumen.setUnFocusColor(Paint.valueOf("black"));
		});
	}
	
	public void salir() {
		MainController.activate(
			"MenuPracticante",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	private boolean comprobarTextfields() {
		boolean valido = true;
		if (this.txtActividadesPlaneadas.getText().trim().equals("")) {
			this.txtActividadesPlaneadas.setUnFocusColor(Paint.valueOf("red"));
			valido = false;
		}
		if (this.txtActividadesRealizadas.getText().trim().equals("")) {
			this.txtActividadesRealizadas.setUnFocusColor(Paint.valueOf("red"));
			valido = false;
		}
		if (this.txtResumen.getText().trim().equals("")) {
			this.txtResumen.setUnFocusColor(Paint.valueOf("red"));
			valido = false;
		}
		if (!valido) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Campos incorrectos",
				"Favor de llenar los campos"
			);
		}
		return valido;
	}
	
	private boolean comprobarFechas() {
		boolean valido = true;
		LocalDate fIncial = this.fechaInicial.getValue();
		LocalDate fFinal = this.fechaFinal.getValue();
		if (fIncial == null) {
			this.fechaInicial.setPromptText("Fecha errónea");
			valido = false;
		}
		if (fFinal == null) {
			this.fechaFinal.setPromptText("Fecha errónea");
			valido = false;
		}
		if (fFinal != null && fIncial != null && fIncial.compareTo(fFinal) > 0) {
			valido = false;
			MainController.alert(
				Alert.AlertType.WARNING,
				"Fechas incorrectas",
				"La fecha inicial no puede ser posterior a la fecha final"
			);
		} else if (!valido) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Fechas incorrectas",
				"Favor de elegir fechas correctas"
			);
		}
		return valido;
	}
	
	public void guardar() {
		boolean camposCorrectos = this.comprobarTextfields();
		boolean fechasCorrectas = this.comprobarFechas();
		if (camposCorrectos && fechasCorrectas) {
			try {
				if (MainController.alert(
					Alert.AlertType.CONFIRMATION,
					"¿Desea registrar el reporte?", "")) {
					Practicante practicante = DAOPracticante.get(
						(Practicante) MainController.get("user"));
					if (practicante.registrarReporte(
						cmbTipoReporte.getValue(),
						txtActividadesPlaneadas.getText().trim(),
						txtActividadesRealizadas.getText().trim(),
						txtResumen.getText().trim(),
						fechaInicial.getValue(),
						fechaInicial.getValue())
					) {
						MainController.alert(
							Alert.AlertType.INFORMATION,
							"Reporte registrado",
							"El reporte se ha registrado exitosamente");
						this.salir();
					}
				}
			} catch (SQLException throwables) {
				MainController.alert(
					Alert.AlertType.ERROR,
					"ErrorBD",
					"No se pudo establecer conexión con la base de datos"
				);
			}
		}
	}
}
