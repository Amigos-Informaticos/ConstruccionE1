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
    @FXML private static JFXComboBox<String> cmbOrganizations;

    @FXML private static JFXTextField txtName;
    @FXML private static JFXTextArea txtDescription;
    @FXML private static JFXTextField txtGeneralObjective;
    @FXML private static JFXTextField txtMediateObjective;
    @FXML private static JFXTextField txtInmediateObjective;
    @FXML private static JFXTextField txtMethodology;
    @FXML private static JFXTextArea txtResources;
    @FXML private static JFXTextArea txtResponsibilities;
    @FXML private static JFXTextField txtCapacity;
    @FXML private static JFXTextField txtPositionResponsible;
    @FXML private static JFXTextField txtEmailResponsible;
    @FXML private static JFXTextField txtNameResponsible;
    @FXML private static JFXTextField txtLastnameResponsible;
    @FXML private static JFXComboBox<String> cmbArea;

    @FXML private JFXTextField[] txtNamesOfActivity = new JFXTextField[6];
    @FXML private JFXTextField[] txtDatesOfActivity = new JFXTextField[6];

    @FXML private static JFXDatePicker initialDate;
    @FXML private static JFXDatePicker finalDate;

    ObservableList<String> listOrganizations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    public void onClickCalendarization(MouseEvent clickEvent){
        MainController.activate("ProjectCalendarization",
                                "Calendarizacion de Proyecto",
                                MainController.Sizes.MID);
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
    }
}
