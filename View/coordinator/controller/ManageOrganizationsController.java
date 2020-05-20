package View.coordinator.controller;

import Exceptions.CustomException;
import Models.Address;
import Models.Organization;
import Models.Sector;
import Models.TelephoneNumber;
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
    private TableColumn<Organization,String> clmnName;

    @FXML private JFXComboBox<String> cmbSector;

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
    private TelephoneNumber telephoneNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOrganization = FXCollections.observableArrayList();
        new Organization().fillTableOrganization(listOrganization);
        tblViewOrganization.setItems(listOrganization);
        clmnName.setCellValueFactory(new PropertyValueFactory<Organization, String>("name"));

        listSector = FXCollections.observableArrayList();
        new Sector().fillSector(listSector);
        cmbSector.setItems(listSector);

        eventManager();
    }

    public void eventManager(){
        tblViewOrganization.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Organization>() {
                    @Override
                    public void changed(ObservableValue<? extends Organization> observable, Organization oldValue,
                                        Organization newValue) {
                        if(newValue != null){
                            organization = newValue;
                            txtName.setText(newValue.getName());
                            txtTel1.setText(newValue.getTel().getNumber());
                            txtTel2.setText(newValue.getTel().getNumber2());
                            txtStreet.setText(newValue.getAddress().getStreet());
                            txtNo.setText(newValue.getAddress().getNo());
                            txtColony.setText(newValue.getAddress().getColony());
                            txtLocality.setText(newValue.getAddress().getLocality());
                            cmbSector.setValue(newValue.getSector().getName());
                            enableEdit();
                        } else {
                            cleanFormProfessor();
                            enableRegister();
                        }
                    }
                }
        );
    }

    public void enableEdit(){}

    public void cleanFormProfessor(){}

    public void enableRegister(){}

    @FXML
    public void signUp(){
        Organization organization = new Organization();
        this.instanceOrganization(organization);
        try {
            if(organization.isComplete()){
                if(organization.signUp() &&
                        organization.getAddress().signUp(organization.getId()) &&
                        organization.getTel().signUp(organization.getId())){




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
        } catch (CustomException e) {
            new Logger().log(e.getCauseMessage());
        }
    }

    public Address instanceAddress(){
        Address address = new Address();
        address.setStreet(txtStreet.getText());
        address.setNo(txtNo.getText());
        address.setColony(txtColony.getText());
        address.setLocality(txtLocality.getText());
        return address;
    }

    public TelephoneNumber instanceTel(){
        TelephoneNumber tel = new TelephoneNumber();
        tel.setNumber(txtTel1.getText());
        tel.setNumber2(txtTel2.getText());
        return tel;
    }


    public void instanceOrganization(Organization organization){
        organization.setName(txtName.getText());
        organization.setAddress(instanceAddress());
        organization.setTel(instanceTel());
        organization.setSector(new Sector(cmbSector.getValue()));
    }
}
