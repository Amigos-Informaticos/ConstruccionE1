package View.practicante.controller;

import Exceptions.CustomException;
import Models.ActividadCalendarizada;
import Models.Practicante;
import Models.Proyecto;
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
	
	private Proyecto assignedProyecto;
	
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
		ActividadCalendarizada[] activities = assignedProyecto.getCalendarizedActivities();
		if (activities.length == 0) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No hay actividades calendarizadas",
				"Su proyecto no ha registrado ninguna actividad calendarizada"
			);
			exit();
		} else {
			JFXTextField[] names = {name1, name2, name3, name4, name5};
			for (int i = 0; i < activities.length; i++) {
				names[i].setText(activities[i].getNombre());
				names[i].setEditable(false);
			}
		}
	}
	
	private void preconditions() throws CustomException {
		Practicante practicante = (Practicante) MainController.get("user");
		this.assignedProyecto = practicante.getProyecto();
		if (this.assignedProyecto == null) {
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
		JFXTextField[] names = {name1, name2, name3, name4, name5};
		JFXTextField[] plannedDates = {planned1, planned2, planned3, planned4, planned5};
		JFXTextField[] realDates = {real1, real2, real3, real4, real5};
		JFXDatePicker[] pickedDates = {date1, date2, date3, date4, date5};
		if (!checkFields()) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Campos vacíos",
				"No se han llenado los campos"
			);
		}
	}
	
	private boolean checkFields() {
		boolean ok = true;
		JFXTextField[] fields = {name1, name2, name3, name4, name5,
			planned1, planned2, planned3, planned4, planned5,
			real1, real2, real3, real4, real5};
		JFXDatePicker[] pickedDates = {date1, date2, date3, date4, date5};
		int counter = 0;
		while (ok && counter < fields.length) {
			if (fields[counter].getText().equals("")) {
				ok = false;
			}
			counter++;
		}
		counter = 0;
		while (ok && counter < pickedDates.length) {
			if (pickedDates[counter].getValue() == null) {
				ok = false;
			}
			counter++;
		}
		return ok;
	}
}
