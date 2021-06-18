package View.administrador.controller;

import IDAO.Turno;
import Models.Coordinador;
import Models.Usuario;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import tools.LimitadorTextfield;
import tools.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministrarCoordinadorController implements Initializable {
	@FXML
	private TableView<Coordinador> tableViewCoordinator;
	@FXML
	private TableColumn<Coordinador, String> clmnEmail;
	@FXML
	private TableColumn<Coordinador, String> clmnNames;
	@FXML
	private TableColumn<Coordinador, String> clmnLastNames;
	
	@FXML
	private Label lblNames;
	@FXML
	private Label lblLastnames;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblPersonalNo;
	@FXML
	private Label lblShift;
	@FXML
	private Label lblRegistrationDate;
	
	@FXML
	private JFXButton btnDelete;
	@FXML
	private JFXButton btnRegister;
	@FXML
	private JFXButton btnUpdate;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXPasswordField pwdPassword;
	@FXML
	private JFXTextField txtNames;
	@FXML
	private JFXTextField txtLastNames;
	@FXML
	private JFXTextField txtNoPersonal;
	@FXML
	private JFXComboBox<String> cmbShift;
	
	private Coordinador coordinador;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		coordinador = new Coordinador();
		try {
			if (coordinador.hayOtro()) {
				coordinador = coordinador.obtenerActivo();
				llenarDetallesCoordinador(coordinador);
				enableEdit();
			} else {
				habilitarRegistro();
			}
			ObservableList<String> listShift = FXCollections.observableArrayList();
			Turno.llenarTurno(listShift);
			cmbShift.setItems(listShift);
			
		} catch (SQLException e) {
			Logger.staticLog(e, true);
		}
		LimitadorTextfield.soloNumeros(txtNoPersonal);
		txtNames.addEventFilter(KeyEvent.ANY, handleLetters);
		txtLastNames.addEventFilter(KeyEvent.ANY, handleLetters);
		LimitadorTextfield.soloNumeros(txtNoPersonal);
		LimitadorTextfield.limitarTamanio(txtNames, 50);
		LimitadorTextfield.limitarTamanio(txtLastNames, 60);
		LimitadorTextfield.limitarTamanio(pwdPassword, 32);
		LimitadorTextfield.limitarTamanio(txtNoPersonal, 13);
	}
	
	@FXML
	public void registrar() {
		coordinador = new Coordinador();
		this.instanceCoordinator(coordinador);
		if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro que deseas agregar?",
			"Pulse aceptar para continuar")) {
			if (coordinador.estaCompleto()) {
				try {
					if (!coordinador.estaRegistrado()) {
						if (coordinador.registrar()) {
							MainController.alert(
								Alert.AlertType.INFORMATION,
								"Coordinador registrado correctamente",
								"Pulse aceptar para continuar"
							);
							llenarDetallesCoordinador(coordinador);
							enableEdit();
						} else {
							MainController.alert(
								Alert.AlertType.WARNING,
								"Ocurrio un error al conectarse a la base de datos",
								"Pulse aceptar para continuar"
							);
						}
					} else {
						MainController.alert(Alert.AlertType.WARNING,
							"La cuenta ya se encuentra registrada",
							"Pulse aceptar para continuar"
						);
					}
				} catch (SQLException sqlException) {
					MainController.alert(
						Alert.AlertType.ERROR,
						"Ocurrio un error al conectarse a la base de datos",
						"Pulse aceptar para continuar"
					);
					Logger.staticLog(sqlException, true);
				}
			} else {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Campos incorrectos o incompletos",
					"Pulse aceptar para continuar"
				);
				mostrarCamposErroneos();
			}
		}
	}
	
	@FXML
	public void actualizar() {
		this.instanceCoordinator(coordinador);
		try {
			if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea actualizar al Coordinador?", "")) {
				if (coordinador.estaCompleto()) {
					if (coordinador.actualizar()) {
						MainController.alert(
							Alert.AlertType.INFORMATION,
							"Coordinador modificado exitosamente",
							"Pulse aceptar para continua"
						);
						llenarDetallesCoordinador(coordinador);
						mostrarCamposErroneos();
					} else {
						MainController.alert(
							Alert.AlertType.ERROR,
							"No se pudo modificar al coordinador",
							"Pulse aceptar para continuar"
						);
					}
				} else {
					MainController.alert(
						Alert.AlertType.WARNING,
						"LLene todos los campos correctamente",
						"Pulse aceptar para continuar"
					);
					mostrarCamposErroneos();
				}
			}
		} catch (AssertionError e) {
			new Logger().log(e.getMessage());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			Logger.staticLog(sqlException, true);
		}
	}
	
	@FXML
	public void eliminar() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar este coordinador?", "")) {
			try {
				habilitarRegistro();
				limpiarFormularioCoordinador();
				limpiarDetallesCoordinador();
				if (coordinador.eliminar()) {
					MainController.alert(
						Alert.AlertType.INFORMATION,
						"Acción realizada exitosamente",
						"Pulse aceptar para continuar"
					);
				} else {
					MainController.alert(
						Alert.AlertType.ERROR,
						"Error al conectarse a la base de datos",
						"Pulse aceptar para continuar"
					);
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				Logger.staticLog(sqlException, true);
				MainController.alert(
					Alert.AlertType.WARNING,
					"Error al conectarse a la base de datos",
					"Pulse aceptar para continuar"
				);
			}
		}
	}
	
	@FXML
	public void onBackArrowClicked(MouseEvent event) {
		MainController.activate("MenuAdministrador", "Menú Administrador", MainController.Sizes.MID);
	}
	
	private void instanceCoordinator(Coordinador coordinador) {
		if (coordinador != null) {
			coordinador.setEmail(txtEmail.getText());
			coordinador.setContrasena(pwdPassword.getText());
			coordinador.setNombres(txtNames.getText());
			coordinador.setApellidos(txtLastNames.getText());
			coordinador.setNumeroPersonal(txtNoPersonal.getText());
			coordinador.setTurno(cmbShift.getValue());
		}
	}
	
	private void limpiarFormularioCoordinador() {
		txtEmail.setText(null);
		txtNames.setText(null);
		txtLastNames.setText(null);
		txtNoPersonal.setText(null);
	}
	
	private void mostrarCamposErroneos() {
		if (!Usuario.esEmail(txtEmail.getText())) {
			txtEmail.setUnFocusColor(Paint.valueOf("red"));
		} else {
			txtEmail.setUnFocusColor(Paint.valueOf("black"));
		}
		if (!Usuario.esNombre(txtNames.getText())) {
			txtNames.setUnFocusColor(Paint.valueOf("red"));
		} else {
			txtNames.setUnFocusColor(Paint.valueOf("black"));
		}
		if (!Usuario.esNombre(txtLastNames.getText())) {
			txtLastNames.setUnFocusColor(Paint.valueOf("red"));
		} else {
			txtLastNames.setUnFocusColor(Paint.valueOf("black"));
		}
		if (!Usuario.esContrasena(pwdPassword.getText())) {
			pwdPassword.setUnFocusColor(Paint.valueOf("red"));
		} else {
			pwdPassword.setUnFocusColor(Paint.valueOf("black"));
		}
		if (!Coordinador.esNoPersonal(txtNoPersonal.getText())) {
			txtNoPersonal.setUnFocusColor(Paint.valueOf("red"));
		} else {
			txtNoPersonal.setUnFocusColor(Paint.valueOf("black"));
		}
		if (cmbShift.getValue() == null || cmbShift.getValue().equals("")) {
			cmbShift.setUnFocusColor(Paint.valueOf("red"));
		} else {
			cmbShift.setUnFocusColor(Paint.valueOf("black"));
		}
	}
	
	private void llenarDetallesCoordinador(Coordinador coordinador) {
		lblNames.setText(coordinador.getNombres());
		lblLastnames.setText(coordinador.getApellidos());
		lblEmail.setText(coordinador.getEmail());
		lblPersonalNo.setText(coordinador.getNoPersonal());
		lblShift.setText(coordinador.getTurno());
		lblRegistrationDate.setText(coordinador.getFechaRegistro());
		txtEmail.setText(coordinador.getEmail());
		pwdPassword.setText(coordinador.getContrasena());
	}
	
	private void limpiarDetallesCoordinador() {
		lblNames.setText(null);
		lblLastnames.setText(null);
		lblEmail.setText(null);
		lblPersonalNo.setText(null);
		lblShift.setText(null);
		lblRegistrationDate.setText(null);
		txtEmail.setText(null);
		pwdPassword.setText(null);
	}
	
	private void habilitarRegistro() {
		txtEmail.setDisable(false);
		pwdPassword.setDisable(false);
		
		btnDelete.setDisable(true);
		btnUpdate.setDisable(true);
		btnRegister.setDisable(false);
	}
	
	private void enableEdit() {
		txtEmail.setDisable(true);
		pwdPassword.setDisable(true);
		
		btnDelete.setDisable(false);
		btnUpdate.setDisable(false);
		btnRegister.setDisable(true);
	}
	
	private EventHandler<KeyEvent> handleLetters = new EventHandler<KeyEvent>() {
		private boolean willConsume;
		
		@Override
		public void handle(KeyEvent event) {
			if (willConsume) {
				event.consume();
			}
			if (!event.getCode().toString().matches("[a-zA-Z]")
				&& event.getCode() != KeyCode.BACK_SPACE
				&& event.getCode() != KeyCode.SPACE
				&& event.getCode() != KeyCode.SHIFT) {
				if (event.getEventType() == KeyEvent.KEY_PRESSED) {
					willConsume = true;
				} else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
					willConsume = false;
				}
			}
		}
	};
}
