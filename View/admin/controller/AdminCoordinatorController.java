package View.admin.controller;

import DAO.DAOCoordinator;
import IDAO.Shift;
import Models.Coordinator;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCoordinatorController implements Initializable {
	@FXML
	private TableView<Coordinator> tableViewCoordinator;
	@FXML
	private TableColumn<Coordinator, String> clmnEmail;
	@FXML
	private TableColumn<Coordinator, String> clmnNames;
	@FXML
	private TableColumn<Coordinator, String> clmnLastNames;

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

	private ObservableList<String> listShift;
	private Coordinator coordinator;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Coordinator coordinator = new Coordinator();
		if (coordinator.isAnother()) {
			coordinator = DAOCoordinator.getActive();
			fillDetailsCoordinator(coordinator);
			enableEdit();
		} else {
			enableRegister();
		}
		listShift = FXCollections.observableArrayList();
		Shift.fillShift(listShift);
		cmbShift.setItems(listShift);
	}

	@FXML
	public void signUp() {
		coordinator = new Coordinator();
		this.instanceCoordinator(coordinator);
		try {
			if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro?", "") && coordinator.isComplete()) {
				if (coordinator.signUp()) {
					MainController.alert(
							Alert.AlertType.INFORMATION,
							"Coordinador registrado correctamente",
							"Pulse aceptar para continuar"
					);
				} else {
					MainController.alert(
							Alert.AlertType.WARNING,
							"Error al conectar con la base de datos",
							"Pulse aceptar para continuar"
					);
				}
			} else {
				MainController.alert(
						Alert.AlertType.INFORMATION,
						"LLene todos los campos correctamente",
						"Pulse aceptar para continuar"
				);
			}
		} catch (AssertionError e) {
			new Logger().log(e.getMessage());
		}
	}

	@FXML
	public void update() {
		coordinator = new Coordinator();
		this.instanceCoordinator(coordinator);
		try {
			if (coordinator.isComplete()) {
				Coordinator coordinator = tableViewCoordinator.getSelectionModel().getSelectedItem();
				instanceCoordinator(coordinator);
				if (tableViewCoordinator.getSelectionModel().getSelectedItem().update()) {

				} else {
					MainController.alert(
							Alert.AlertType.WARNING,
							"No se pudo actualizar al coordinador",
							"Pulse aceptar para continuar"
					);
				}
			} else {
				MainController.alert(
						Alert.AlertType.INFORMATION,
						"LLene todos los campos correctamente",
						"Pulse aceptar para continuar"
				);
			}
		} catch (AssertionError e) {
			new Logger().log(e.getMessage());
		}
	}

	@FXML
	public void delete() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro?", "")) {
			try {
				if (coordinator.delete()) {
					enableRegister();
					cleanFormCoordinator();
				} else {
					MainController.alert(
							Alert.AlertType.INFORMATION,
							"No se pudo eliminar al profesor",
							"Pulse aceptar para continuar"
					);
				}
			} catch (AssertionError e) {
				new Logger().log(e.getMessage());
			}
		}

	}

	@FXML
	public void onBackArrowClicked(MouseEvent event) {
		MainController.activate("MainMenuAdmin", "Menú Administrador", MainController.Sizes.MID);
	}

	private void instanceCoordinator(Coordinator coordinator) {
		coordinator.setEmail(txtEmail.getText());
		coordinator.setPassword(pwdPassword.getText());
		coordinator.setNames(txtNames.getText());
		coordinator.setLastnames(txtLastNames.getText());
		coordinator.setPersonalNo(txtNoPersonal.getText());
	}

	private void cleanFormCoordinator() {
		txtEmail.setText(null);
		txtNames.setText(null);
		txtLastNames.setText(null);
		txtNoPersonal.setText(null);
	}

	private void fillDetailsCoordinator(Coordinator coordinator) {
		assert coordinator.isComplete() : "Coordinator isn't complete on AdminCoordinatorController.fillDetailsCoordinator()";
		lblNames.setText(coordinator.getNames());
		lblLastnames.setText(coordinator.getLastnames());
		lblEmail.setText(coordinator.getEmail());
		lblPersonalNo.setText(coordinator.getPersonalNo());
		lblShift.setText(coordinator.getShift());
		lblRegistrationDate.setText(coordinator.getRegistrationDate());
	}

	private void enableRegister() {
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

}
