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
	
	private static String nombre;
	
	@FXML private JFXTextField txtName;
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
	
	@FXML private JFXDatePicker initialDate;
	@FXML private JFXDatePicker finalDate;
	
	ObservableList<String> listOrganizations;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	@FXML
	public void onClickCalendarization(MouseEvent clickEvent){
		nombre = txtName.getText();
		MainController.activate("ProjectCalendarization",
			"Calendarizacion de Proyecto",
			MainController.Sizes.MID);
	}
	
	public CalendarizedActivity instanceCalendarizedActivity(int month){
		CalendarizedActivity calendarizedActivity = new CalendarizedActivity();
		calendarizedActivity.setName(txtDatesOfActivity[month].getText());
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
		txtName.setText(nombre);
		MainController.activate("CreateProject","Crear Proyecto", MainController.Sizes.LARGE);
	}
}
