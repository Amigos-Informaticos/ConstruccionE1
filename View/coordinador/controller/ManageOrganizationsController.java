package View.coordinador.controller;

import Models.Organizacion;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageOrganizationsController implements Initializable {
	@FXML
	private TableView<Organizacion> tblViewOrganization;
	@FXML
	private TableColumn<Organizacion, String> clmnName;
	
	@FXML
	private JFXComboBox<String> cmbSector;
	
	@FXML
	private JFXTextField txtName;
	@FXML
	private JFXTextField txtTel1;
	@FXML
	private JFXTextField txtTel2;
	@FXML
	private JFXTextField txtStreet;
	@FXML
	private JFXTextField txtNo;
	@FXML
	private JFXTextField txtColony;
	@FXML
	private JFXTextField txtLocality;
	
	private ObservableList<Organizacion> listOrganizacion;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listOrganizacion = FXCollections.observableArrayList();
		new Organizacion().llenarTablaOrganizacion(listOrganizacion);
		tblViewOrganization.setItems(listOrganizacion);
		clmnName.setCellValueFactory(new PropertyValueFactory<Organizacion, String>("name"));
		
		ObservableList<String> listSector = FXCollections.observableArrayList();
		new Organizacion().llenarSector(listSector);
		cmbSector.setItems(listSector);
		
		eventManager();
	}
	
	public void eventManager() {
		tblViewOrganization.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<Organizacion>() {
				@Override
				public void changed(ObservableValue<? extends Organizacion> observable, Organizacion oldValue,
				                    Organizacion newValue) {
					if (newValue != null) {
						txtName.setText(newValue.getNombre());
						txtStreet.setText(newValue.getDireccion().get("street"));
						txtNo.setText(newValue.getDireccion().get("number"));
						txtColony.setText(newValue.getDireccion().get("colony"));
						txtLocality.setText(newValue.getDireccion().get("locality"));
						cmbSector.setValue(newValue.getSector());
						enableEdit();
					} else {
						cleanFormProfessor();
						enableRegister();
					}
				}
			}
		);
	}
	
	public void enableEdit() {
	}
	
	public void cleanFormProfessor() {
	}
	
	public void enableRegister() {
	}
	
	@FXML
	public void registrar() {
		Organizacion organizacion = new Organizacion();
		this.instanciarOrganizacion(organizacion);
		if (organizacion.estaCompleto()) {
			if (organizacion.registrar()) {
				listOrganizacion.add(organizacion);
				MainController.alert(
					Alert.AlertType.INFORMATION,
					"Organizacion registrada exitosamente",
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
				"Llene todos los campos correctamente",
				"Pulse aceptar para continuar"
			);
		}
	}
	
	public void instanciarOrganizacion(Organizacion organizacion) {
		organizacion.setNombre(txtName.getText());
		organizacion.setDireccion(
			txtStreet.getText(),
			txtNo.getText(),
			txtColony.getText(),
			txtLocality.getText()
		);
		organizacion.setSector(cmbSector.getValue());
		if (!txtTel1.getText().equals("")) {
			organizacion.setTelefono(txtTel1.getText());
		}
	}
	
	public void onClickBack() {
		MainController.activate("MainMenuCoordinator", "Menu", MainController.Sizes.MID);
	}
	
	@FXML
	public void delete() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar?", "")) {
			try {
				if (tblViewOrganization.getSelectionModel().getSelectedItem().eliminar()) {
					listOrganizacion.remove(tblViewOrganization.getSelectionModel().getSelectedIndex());
					MainController.alert(Alert.AlertType.INFORMATION,
						"Organización eliminada",
						"Organización eliminada exitosamente");
				} else {
					MainController.alert(
						Alert.AlertType.INFORMATION,
						"DBError",
						"No se pudo establecer conexión con Base de Datos"
					);
				}
			} catch (AssertionError e) {
				new Logger().log(e.getMessage());
			}
		}
	}
}
