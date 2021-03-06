package View.profesor.controller;

import Models.Actividad;
import View.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class GenerateActivityController {
	@FXML
	private JFXTextField txtTitle;
	@FXML
	private JFXTextArea txtAreaDescription;
	@FXML
	private JFXTimePicker timeStartDate;
	@FXML
	private JFXDatePicker dateStartDate;
	
	@FXML
	private void createActivity() {
		Actividad actividad = new Actividad();
		instanceActivity(actividad);
		
	}
	
	private Actividad instanceActivity(Actividad actividad) {
		System.out.println(timeStartDate + " \n" + dateStartDate);
		return actividad;
	}
	
	@FXML
	private void onBackArrowClicked(MouseEvent event) {
		MainController.activate(
			"MainMenuProfessor",
			"Menú Principal Profesor",
			MainController.Sizes.MID
		);
	}
}
