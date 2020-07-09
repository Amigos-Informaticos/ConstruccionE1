package View.professor.controller;

import Models.Activity;
import View.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import sun.applet.Main;

public class GenerateActivityController {
    @FXML
    private JFXTextField txtTitle;
    @FXML private JFXTextArea txtAreaDescription;
    @FXML private JFXTimePicker timeStartDate;
    @FXML private JFXDatePicker dateStartDate;

    @FXML
    private void createActivity(){
        Activity activity = new Activity();
        instanceActivity(activity);


    }
    private Activity instanceActivity(Activity activity){
        System.out.println(timeStartDate + " \n" +
                dateStartDate);
        return activity;
    }
    @FXML
    private void onBackArrowClicked(MouseEvent event){
        MainController.activate("MainMenuProfessor","Menú Principal Profesor", MainController.Sizes.MID);
    }
}
