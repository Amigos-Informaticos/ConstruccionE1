package View.ManageProjects;

import Models.Organization;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateProjectController implements Initializable {
    @FXML JFXComboBox<String> cmbOrganizations;
    ObservableList<String> listOrganizations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOrganizations = FXCollections.observableArrayList();
        new Organization().fillOrganizationNames(listOrganizations);
        cmbOrganizations.setItems(listOrganizations);

    }
}
