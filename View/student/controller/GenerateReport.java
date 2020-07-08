package View.student.controller;

import Exceptions.CustomException;
import Models.CalendarizedActivity;
import Models.Project;
import Models.Student;
import View.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReport implements Initializable {
	
	
	public JFXTextField name1;
	public JFXTextField name2;
	public JFXTextField name3;
	public JFXTextField name4;
	public JFXTextField name5;
	public JFXTextField planned1;
	public JFXTextField planned2;
	public JFXTextField planned3;
	public JFXTextField planned4;
	public JFXTextField planned5;
	public JFXTextField real1;
	public JFXTextField real2;
	public JFXTextField real3;
	public JFXTextField real4;
	public JFXTextField real5;
	public JFXDatePicker date1;
	public JFXDatePicker date2;
	public JFXDatePicker date3;
	public JFXDatePicker date4;
	public JFXDatePicker date5;
	
	private Project assignedProject;
	private CalendarizedActivity[] activities;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			preconditions();
		} catch (CustomException e) {
			Logger.staticLog(e);
		}
		loadActivities();
	}
	
	private void loadActivities() {
		this.activities = assignedProject.getCalendarizedActivities();
		if (this.activities.length == 0) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No hay actividades calendarizadas",
				"Su proyecto no ha registrado ninguna actividad calendarizada"
			);
			exit();
		}
	}
	
	private void preconditions() throws CustomException {
		Student student = (Student) MainController.get("user");
		this.assignedProject = student.getProject();
		if (this.assignedProject == null) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No ha sido asignado a ningún proyecto",
				"Necesita estar asignado a un proyecto para poder generar reportes"
			);
			exit();
		}
	}
	
	@FXML
	private void exit() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID
		);
	}
	
	public void accept(MouseEvent mouseEvent) {
	}
}
