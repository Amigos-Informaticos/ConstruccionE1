package View.coordinator.controller;

import Models.Project;
import View.MainController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProjectController implements Initializable {
    @FXML
    private JFXTextField txtOrganization;

    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextArea txtDescription;
    @FXML
    private JFXTextField txtGeneralObjective;
    @FXML
    private JFXTextField txtMediateObjective;
    @FXML
    private JFXTextField txtInmediateObjective;
    @FXML
    private JFXTextField txtMethodology;
    @FXML
    private JFXTextArea txtResources;
    @FXML
    private JFXTextArea txtResponsibilities;
    @FXML
    private JFXTextField txtCapacity;
    @FXML
    private JFXTextField txtPositionResponsible;
    @FXML
    private JFXTextField txtEmailResponsible;
    @FXML
    private JFXTextField txtNameResponsible;
    @FXML
    private JFXTextField txtLastnameResponsible;
    @FXML
    private JFXTextField txtArea;
    @FXML
    private JFXTextField txtPeriod;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Project project = (Project) MainController.get("Project");

        txtOrganization.setText(project.getOrganization().getName());
        txtName.setText(project.getName());
        txtDescription.setText(project.getDescription());
        txtGeneralObjective.setText(project.getGeneralObjective());
        txtMediateObjective.setText(project.getMediateObjective());
        txtInmediateObjective.setText(project.getImmediateObjective());
        txtMethodology.setText(project.getMethodology());
        txtResources.setText(project.getResources());
        txtResponsibilities.setText(project.getResponsibilities());

        txtPositionResponsible.setText(project.getResponsible().getPosition());
        txtEmailResponsible.setText(project.getResponsible().getEmail());
        txtNameResponsible.setText(project.getResponsible().getNames());
        txtLastnameResponsible.setText(project.getResponsible().getLastNames());

        txtCapacity.setText(String.valueOf(project.getCapacity()));
        txtArea.setText(project.getArea());
        txtPeriod.setText(project.getPeriod());
    }
}
