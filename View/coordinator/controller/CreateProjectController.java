package View.coordinator.controller;

import Models.CalendarizedActivity;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateProjectController implements Initializable {
    @FXML private JFXComboBox<String> cmbOrganizations;

    @FXML private static JFXTextField txtName;
    @FXML private JFXTextArea txtDescription;
    @FXML private JFXTextField txtGeneralObjective;
    @FXML private JFXTextField txtMediateObjective;
    @FXML private JFXTextField txtInmediateObjective;
    @FXML private JFXTextField txtMethodology;
    @FXML private JFXTextArea txtResources;
    @FXML private JFXTextArea txtResponsibilities;
    @FXML private JFXTextField txtCapacity;
    @FXML private JFXTextField txtPositionResponsible;
    @FXML private JFXTextField txtEmailResponsible;
    @FXML private JFXTextField txtNameResponsible;
    @FXML private JFXTextField txtLastnameResponsible;
    @FXML private JFXComboBox<String> cmbArea;

    @FXML private JFXTextField[] txtNamesOfActivity = new JFXTextField[6];
    @FXML private JFXTextField[] txtDatesOfActivity = new JFXTextField[6];

    @FXML private JFXDatePicker txtInitialDate;
    @FXML private JFXDatePicker txtFinalDate;

    private static String name;
    private static String description;
    private static String generalObjective;
    private static String mediateObjective;
    private static String inmediateObjective;
    private static String methodology;
    private static String resources;
    private static String responsibilities;
    private static String capacity;
    private static String positionResponsible;
    private static String emailResponsible;
    private static String nameResponsible;
    private static String lastnameResponsible;
    private static String area;

    private static String initialDate;
    private static String finalDate;


    ObservableList<String> listOrganizations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    public void onClickCalendarization(MouseEvent clickEvent){
        MainController.activate("ProjectCalendarization",
                                "Calendarizacion de Proyecto",
                                MainController.Sizes.MID);
        name = txtName.getText();
        description = txtDescription.getText();
        generalObjective = txtGeneralObjective.getText();
        mediateObjective = txtMediateObjective.getText();
        inmediateObjective = txtInmediateObjective.getText();
        methodology = txtMethodology.getText();
        resources = txtResources.getText();
        responsibilities = txtResponsibilities.getText();
        capacity = txtCapacity.getText();
        positionResponsible = txtPositionResponsible.getText();
        emailResponsible = txtEmailResponsible.getText();
        nameResponsible = txtNameResponsible.getText();
        lastnameResponsible = txtLastnameResponsible.getText();

    }

    public CalendarizedActivity instanceCalendarizedActivity(int month){
        CalendarizedActivity calendarizedActivity = new CalendarizedActivity();
        calendarizedActivity.setName(txtNamesOfActivity[month].getText());
        calendarizedActivity.setDate(txtDatesOfActivity[month].getText());
        return calendarizedActivity;
    }

    public void onClickOk(MouseEvent clickEvent){
        CalendarizedActivity[] calendarizedActivities = new CalendarizedActivity[6];
        for (int i = 0; i < 6; i++) {
            if(!txtNamesOfActivity[i].getText().equals("")){
                calendarizedActivities[i] = instanceCalendarizedActivity(i);
            }
        }

    }

    public void onClickBack(MouseEvent clickEvent){
        MainController.activate("CreateProject","Crear Proyecto", MainController.Sizes.LARGE);
        txtName.setText(name);
        txtDescription.setText(description);
        txtGeneralObjective.setText(generalObjective);
        txtMediateObjective.setText(mediateObjective);
    }
}
