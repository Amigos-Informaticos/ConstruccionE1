package View.ManageOrganizations;

import Models.Organization;
import Models.Sector;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    Organization organization;

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
                            txtTel1.setText(newValue.getTel1());
                            txtTel2.setText(newValue.getTel2());
                            txtStreet.setText(newValue.getStreet());
                            txtNo.setText(newValue.getAdressNo());
                            txtColony.setText(newValue.getColony());
                            txtLocality.setText(newValue.getLocality());
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

    public void enableEdit(){}

    public void cleanFormProfessor(){}

    public void enableRegister(){}

}
