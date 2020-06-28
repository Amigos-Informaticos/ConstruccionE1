package View.coordinator.controller;

import Models.Organization;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

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

    @FXML
    public void onClickCalendarization(MouseEvent clickEvent){
        MainController.activate("ProjectCalendarization",
                                "Calendarizacion de Proyecto",
                                MainController.Sizes.MID);
    }
}
