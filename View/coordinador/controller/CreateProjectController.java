package View.coordinador.controller;

import Models.ActividadCalendarizada;
import Models.Organizacion;
import Models.Proyecto;
import Models.ResponsableProyecto;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateProjectController implements Initializable {
	@FXML
	private JFXComboBox<String> cmbOrganizations;
	
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
	private JFXComboBox<String> cmbArea;
	@FXML
	private JFXComboBox<String> cmbPeriod;
	
	@FXML
	private JFXTextField month1Activity;
	@FXML
	private JFXTextField month2Activity;
	@FXML
	private JFXTextField month3Activity;
	@FXML
	private JFXTextField month4Activity;
	@FXML
	private JFXTextField month5Activity;
	@FXML
	private JFXTextField month6Activity;
	
	@FXML
	private JFXDatePicker month1DateActivity;
	@FXML
	private JFXDatePicker month2DateActivity;
	@FXML
	private JFXDatePicker month3DateActivity;
	@FXML
	private JFXDatePicker month4DateActivity;
	@FXML
	private JFXDatePicker month5DateActivity;
	@FXML
	private JFXDatePicker month6DateActivity;
	
	@FXML
	private JFXDatePicker initialDate;
	@FXML
	private JFXDatePicker finalDate;
	
	private ObservableList<String> listOrganizations;
	private ObservableList<String> listAreas;
	private ObservableList<String> listPeriods;
	
	private Proyecto proyecto = new Proyecto();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadValues();
	}
	
	public void loadValues() {
		if (MainController.getStageName().equals("CreateProject")) {
			listOrganizations = FXCollections.observableArrayList();
			Organizacion.llenarNombres(listOrganizations);
			cmbOrganizations.setItems(listOrganizations);
			
			listAreas = FXCollections.observableArrayList();
			Proyecto.fillAreaTable(listAreas);
			cmbArea.setItems(listAreas);
			
			listPeriods = FXCollections.observableArrayList();
			listPeriods.add("FEB-JUL");
			listPeriods.add("AGO-ENE");
			cmbPeriod.setItems(listPeriods);
			
			if (MainController.has("name")) {
				txtName.setText(MainController.get("name").toString());
				txtDescription.setText(MainController.get("description").toString());
				txtGeneralObjective.setText(MainController.get("generalObjective").toString());
				txtMediateObjective.setText(MainController.get("mediateObjective").toString());
				txtInmediateObjective.setText(MainController.get("inmediateObjective").toString());
				txtMethodology.setText(MainController.get("methodology").toString());
				txtResources.setText(MainController.get("resources").toString());
				txtResponsibilities.setText(MainController.get("responsibilities").toString());
				txtCapacity.setText(MainController.get("capacity").toString());
				txtPositionResponsible.setText(MainController.get("positionResponsible").toString());
				txtEmailResponsible.setText(MainController.get("emailResponsible").toString());
				txtNameResponsible.setText(MainController.get("nameResponsible").toString());
				txtLastnameResponsible.setText(MainController.get("lastnameResponsible").toString());
			}
		} else {
			if (MainController.has("initialDate")) {
				initialDate.setValue((LocalDate) MainController.get("initialDate"));
				finalDate.setValue((LocalDate) MainController.get("finalDate"));
				month1Activity.setText((String) MainController.get("month1Activity"));
				month2Activity.setText((String) MainController.get("month2Activity"));
				month3Activity.setText((String) MainController.get("month3Activity"));
				month4Activity.setText((String) MainController.get("month4Activity"));
				month5Activity.setText((String) MainController.get("month5Activity"));
				month6Activity.setText((String) MainController.get("month6Activity"));
				
				month1DateActivity.setValue((LocalDate) MainController.get("month1DateActivity"));
				month2DateActivity.setValue((LocalDate) MainController.get("month2DateActivity"));
				month3DateActivity.setValue((LocalDate) MainController.get("month3DateActivity"));
				month4DateActivity.setValue((LocalDate) MainController.get("month4DateActivity"));
				month5DateActivity.setValue((LocalDate) MainController.get("month5DateActivity"));
				month6DateActivity.setValue((LocalDate) MainController.get("month6DateActivity"));
			}
			
		}
	}
	
	@FXML
	public void onClickCalendarization(MouseEvent clickEvent) {
		MainController.save("name", txtName.getText());
		MainController.save("description", txtDescription.getText());
		MainController.save("generalObjective", txtGeneralObjective.getText());
		MainController.save("mediateObjective", txtMediateObjective.getText());
		MainController.save("inmediateObjective", txtInmediateObjective.getText());
		MainController.save("methodology", txtMethodology.getText());
		MainController.save("resources", txtResources.getText());
		MainController.save("responsibilities", txtResponsibilities.getText());
		MainController.save("capacity", txtCapacity.getText());
		MainController.save("positionResponsible", txtPositionResponsible.getText());
		MainController.save("emailResponsible", txtEmailResponsible.getText());
		MainController.save("nameResponsible", txtNameResponsible.getText());
		MainController.save("lastnameResponsible", txtLastnameResponsible.getText());
		MainController.activate("ProjectCalendarization",
			"Calendarizacion de Proyecto",
			MainController.Sizes.MID);
	}
	
	public void onClickOk(MouseEvent clickEvent) {
		MainController.save("initialDate", initialDate.getValue());
		MainController.save("finalDate", finalDate.getValue());
		
		MainController.save("month1Activity", month1Activity.getText());
		MainController.save("month2Activity", month2Activity.getText());
		MainController.save("month3Activity", month3Activity.getText());
		MainController.save("month4Activity", month4Activity.getText());
		MainController.save("month5Activity", month5Activity.getText());
		MainController.save("month6Activity", month6Activity.getText());
		
		MainController.save("month1DateActivity", month1DateActivity.getValue());
		MainController.save("month2DateActivity", month2DateActivity.getValue());
		MainController.save("month3DateActivity", month3DateActivity.getValue());
		MainController.save("month4DateActivity", month4DateActivity.getValue());
		MainController.save("month5DateActivity", month5DateActivity.getValue());
		MainController.save("month6DateActivity", month6DateActivity.getValue());
		MainController.activate("CreateProject", "Crear Proyecto", MainController.Sizes.LARGE);
	}
	
	public void onClickBack(MouseEvent clickEvent) {
		MainController.activate("CreateProject", "Crear Proyecto", MainController.Sizes.LARGE);
	}
	
	public void signUp() {
		instanceProject();
		if (proyecto.estaCompleto()) {
			if (proyecto.registrar()) {
				MainController.alert(Alert.AlertType.INFORMATION,
					"Proyecto registrado",
					"El Proyecto se registró exitosamente");
			} else {
				MainController.alert(Alert.AlertType.INFORMATION,
					"Error con Base de Datos",
					"No se pudo conectar con Base de Datos");
			}
		} else {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"IncorrectEntries",
				"Debe llenar los datos correctamente"
			);
		}
	}
	
	public void instanceProject() {
		proyecto.setNombre(txtName.getText());
		proyecto.setDescripcion(txtDescription.getText());
		proyecto.setObjetivoGeneral(txtGeneralObjective.getText());
		proyecto.setObjetivoMediato(txtMediateObjective.getText());
		proyecto.setObjetivoInmediato(txtInmediateObjective.getText());
		proyecto.setMetodologia(txtMethodology.getText());
		proyecto.setRecursos(txtResources.getText());
		proyecto.setResponsabilidades(txtResponsibilities.getText());
		proyecto.setCapacidad(Integer.parseInt(txtCapacity.getText()));
		proyecto.setOrganization(Organizacion.obtenerPorNombre(cmbOrganizations.getValue()));
		proyecto.setPeriodo(cmbPeriod.getValue());
		proyecto.setArea(cmbArea.getValue());
		proyecto.setFechaInicio(MainController.get("initialDate").toString());
		proyecto.setFechaFin(MainController.get("finalDate").toString());
		proyecto.setResponsable(this.instanceResponsible());
		proyecto.setActividaadCalendarizada(this.instanceActivities());
	}
	
	public ActividadCalendarizada[] instanceActivities() {
		ActividadCalendarizada[] activities = new ActividadCalendarizada[6];
		for (int i = 0; i < 6; i++) {
			activities[i] = new ActividadCalendarizada();
		}
		activities[0].setNombre((String) MainController.get("month1Activity"));
		activities[0].setFecha((String) MainController.get("month1DateActivty"));
		activities[1].setNombre((String) MainController.get("month2Activity"));
		activities[1].setFecha((String) MainController.get("month2DateActivty"));
		activities[2].setNombre((String) MainController.get("month3Activity"));
		activities[2].setFecha((String) MainController.get("month3DateActivty"));
		activities[3].setNombre((String) MainController.get("month4Activity"));
		activities[3].setFecha((String) MainController.get("month4DateActivty"));
		activities[4].setNombre((String) MainController.get("month5Activity"));
		activities[4].setFecha((String) MainController.get("month5DateActivty"));
		activities[5].setNombre((String) MainController.get("month6Activity"));
		activities[5].setFecha((String) MainController.get("month6DateActivty"));
		return activities;
	}
	
	public ResponsableProyecto instanceResponsible() {
		ResponsableProyecto responsableProyecto = new ResponsableProyecto();
		responsableProyecto.setPosicion(txtPositionResponsible.getText());
		responsableProyecto.setEmail(txtEmailResponsible.getText());
		responsableProyecto.setNombre(txtNameResponsible.getText());
		responsableProyecto.setApellido(txtLastnameResponsible.getText());
		responsableProyecto.setOrganizacion(Organizacion.obtenerPorNombre(cmbOrganizations.getValue()));
		return responsableProyecto;
	}
}
