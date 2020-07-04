package View.coordinator.controller;

import Exceptions.CustomException;
import Models.CalendarizedActivity;
import Models.Organization;
import Models.Project;
import Models.ProjectResponsible;
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
import tools.Logger;

import java.net.URL;
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
	private JFXTextField month1Activity = new JFXTextField();
	@FXML
	private JFXTextField month2Activity = new JFXTextField();
	@FXML
	private JFXTextField month3Activity = new JFXTextField();
	@FXML
	private JFXTextField month4Activity = new JFXTextField();
	@FXML
	private JFXTextField month5Activity = new JFXTextField();
	@FXML
	private JFXTextField month6Activity = new JFXTextField();

	@FXML
	private JFXDatePicker initialDate;
	@FXML
	private JFXDatePicker finalDate;
	
	ObservableList<String> listOrganizations;
	ObservableList<String> listAreas;

	private Project project = new Project();

	CalendarizedActivity[] calendarizedActivities = new CalendarizedActivity[6];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadValues();
	}
	
	public void loadValues() {
		if (MainController.getStageName().equals("CreateProject")) {
			listOrganizations = FXCollections.observableArrayList();
			Organization.fillOrganizationNames(listOrganizations);
			cmbOrganizations.setItems(listOrganizations);

			listAreas = FXCollections.observableArrayList();
			Project.fillAreaTable(listAreas);
			cmbArea.setItems(listAreas);
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
		}
	}
	
	@FXML
	public void onClickCalendarization(MouseEvent clickEvent) {
		MainController.clearMemory();
		MainController.save("name", txtName.getText());
		MainController.save("description",txtDescription.getText());
		MainController.save("generalObjective",txtGeneralObjective.getText());
		MainController.save("mediateObjective",txtMediateObjective.getText());
		MainController.save("inmediateObjective",txtInmediateObjective.getText());
		MainController.save("methodology",txtMethodology.getText());
		MainController.save("resources",txtResources.getText());
		MainController.save("responsibilities",txtResponsibilities.getText());
		MainController.save("capacity",txtCapacity.getText());
		MainController.save("positionResponsible",txtPositionResponsible.getText());
		MainController.save("emailResponsible",txtEmailResponsible.getText());
		MainController.save("nameResponsible",txtNameResponsible.getText());
		MainController.save("lastnameResponsible",txtLastnameResponsible.getText());
		MainController.activate("ProjectCalendarization",
			"Calendarizacion de Proyecto",
			MainController.Sizes.MID);
	}
	
	public CalendarizedActivity instanceCalendarizedActivity(int month) {
		CalendarizedActivity calendarizedActivity = new CalendarizedActivity();
		calendarizedActivity.setName(txtNamesOfActivity[month].getText());
		calendarizedActivity.setDate(txtDatesOfActivity[month].getText());
		return calendarizedActivity;
	}
	
	public void onClickOk(MouseEvent clickEvent) {
		calendarizedActivities = new CalendarizedActivity[6];
		for (int i = 0; i < 6; i++) {
			if (!txtNamesOfActivity[i].getText().equals("")) {
				calendarizedActivities[i] = instanceCalendarizedActivity(i);
			}
		}
		
	}
	
	public void onClickBack(MouseEvent clickEvent) {
		MainController.activate("CreateProject", "Crear Proyecto", MainController.Sizes.LARGE);
	}

	public void signUp(){
		instanceProject();
		project.setCalendarizedActivities(calendarizedActivities);
		try {
			if(project.isComplete()){
				if(project.register()){
					MainController.alert(Alert.AlertType.INFORMATION,
							"Proyecto registrado",
							"El Proyecto se registró exitosamente");
				}else{
					MainController.alert(Alert.AlertType.INFORMATION,
							"Error con Base de Datos",
							"No se pudo conectar con Base de Datos");
				}
			}else{
				MainController.alert(
						Alert.AlertType.INFORMATION,
						"IncorrectEntries",
						"Debe llenar los datos correctamente"
				);
			}
		} catch (CustomException e) {
			new Logger().log(e.getMessage());
		}
	}

	public void instanceProject(){
		project.setName(txtName.getText());
		project.setDescription(txtDescription.getText());
		project.setGeneralObjective(txtGeneralObjective.getText());
		project.setMediateObjective(txtMediateObjective.getText());
		project.setImmediateObjective(txtInmediateObjective.getText());
		project.setMethodology(txtMethodology.getText());
		project.setResources(txtResources.getText());
		project.setResponsibilities(txtResponsibilities.getText());
		project.setCapacity(txtCapacity.getText());
		project.setOrganization(Organization.getByName(cmbOrganizations.getValue()));

		ProjectResponsible projectResponsible = new ProjectResponsible();
		projectResponsible.setPosition(txtPositionResponsible.getText());
		projectResponsible.setEmail(txtEmailResponsible.getText());
		projectResponsible.setNames(txtNameResponsible.getText());
		projectResponsible.setLastNames(txtLastnameResponsible.getText());

		project.setResponsible(projectResponsible);
	}
}
