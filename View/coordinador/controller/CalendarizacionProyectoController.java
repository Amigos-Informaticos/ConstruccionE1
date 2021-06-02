package View.coordinador.controller;

import Models.ActividadCalendarizada;
import View.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarizacionProyectoController implements Initializable {
	@FXML
	private JFXTextField[] txtNamesOfActivity = new JFXTextField[6];
	@FXML
	private JFXTextField[] txtDatesOfActivity = new JFXTextField[6];
	
	@FXML
	private JFXDatePicker initialDate;
	@FXML
	private JFXDatePicker finalDate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	public ActividadCalendarizada instanceCalendarizedActivity(int month) {
		ActividadCalendarizada actividadCalendarizada = new ActividadCalendarizada(
			txtDatesOfActivity[month].getText(),
			txtDatesOfActivity[month].getText()
		);
		return actividadCalendarizada;
	}
	
	public void onClickOk(MouseEvent clickEvent) {
		ActividadCalendarizada[] calendarizedActivities = new ActividadCalendarizada[6];
		for (int i = 0; i < 6; i++) {
			if (!txtNamesOfActivity[i].getText().equals("")) {
				calendarizedActivities[i] = instanceCalendarizedActivity(i);
			}
		}
		
	}
	
	public void onClickBack(MouseEvent clickEvent) {
		MainController.activate("CrearProyectoController", "Crear Proyecto", MainController.Sizes.LARGE);
	}
}
