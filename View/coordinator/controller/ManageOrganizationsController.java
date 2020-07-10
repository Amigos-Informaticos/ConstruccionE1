package View.coordinator.controller;

import Models.Organization;
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
	private TableView<Organization> tblViewOrganization;
	@FXML
	private TableColumn<Organization, String> clmnName;
	
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
	
	ObservableList<Organization> listOrganization;
	ObservableList<String> listSector;
	
	private Organization organization;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listOrganization = FXCollections.observableArrayList();
		new Organization().fillTableOrganization(listOrganization);
		tblViewOrganization.setItems(listOrganization);
		clmnName.setCellValueFactory(new PropertyValueFactory<Organization, String>("name"));
		
		listSector = FXCollections.observableArrayList();
		new Organization().fillSector(listSector);
		cmbSector.setItems(listSector);
		
		eventManager();
	}
	
	public void eventManager() {
		tblViewOrganization.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<Organization>() {
				@Override
				public void changed(ObservableValue<? extends Organization> observable, Organization oldValue,
				                    Organization newValue) {
					if (newValue != null) {
						organization = newValue;
						txtName.setText(newValue.getName());
						txtStreet.setText(newValue.getAddress().get("street"));
						txtNo.setText(newValue.getAddress().get("number"));
						txtColony.setText(newValue.getAddress().get("colony"));
						txtLocality.setText(newValue.getAddress().get("locality"));
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
	public void signUp() {
		Organization organization = new Organization();
		this.instanceOrganization(organization);
		if (organization.isComplete()) {
			if (organization.signUp()) {
				listOrganization.add(organization);
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
	
	public void instanceOrganization(Organization organization) {
		organization.setName(txtName.getText());
		organization.setAddress(
			txtStreet.getText(),
			txtNo.getText(),
			txtColony.getText(),
			txtLocality.getText()
		);
		organization.setSector(cmbSector.getValue());
		if (!txtTel1.getText().equals("")) {
			organization.addPhoneNumber(txtTel1.getText());
		}
		if (!txtTel2.getText().equals("")) {
			organization.addPhoneNumber(txtTel2.getText());
		}
	}

	public void onClickBack(){
		MainController.activate("MainMenuCoordinator","Menu", MainController.Sizes.MID);
	}

	@FXML
	public void delete(){
		if(MainController.alert(Alert.AlertType.CONFIRMATION,"¿Está seguro que desea eliminar?","")){
			try{
				if(tblViewOrganization.getSelectionModel().getSelectedItem().delete()){
					listOrganization.remove(tblViewOrganization.getSelectionModel().getSelectedIndex());
					MainController.alert(Alert.AlertType.INFORMATION,
							"Organización eliminada",
							"Organización eliminada exitosamente");
				} else{
					MainController.alert(
							Alert.AlertType.INFORMATION,
							"DBError",
							"No se pudo establecer conexión con Base de Datos"
					);
				}
			}catch(AssertionError e){
				new Logger().log(e.getMessage());
			}
		}
	}
}
