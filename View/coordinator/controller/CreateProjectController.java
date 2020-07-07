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
	private JFXDatePicker month1DateActivity = new JFXDatePicker();
	@FXML
	private JFXDatePicker month2DateActivity = new JFXDatePicker();
	@FXML
	private JFXDatePicker month3DateActivity = new JFXDatePicker();
	@FXML
	private JFXDatePicker month4DateActivity = new JFXDatePicker();
	@FXML
	private JFXDatePicker month5DateActivity = new JFXDatePicker();
	@FXML
	private JFXDatePicker month6DateActivity = new JFXDatePicker();

	private @FXML JFXTextField[] namesOfActivities = {month1Activity,
							month2Activity,
							month3Activity,
							month4Activity,
							month5Activity,
							month6Activity};

	private @FXML JFXDatePicker[] dateOfActivities = {month1DateActivity,
										month2DateActivity,
										month3DateActivity,
										month4DateActivity,
										month5DateActivity,
										month6DateActivity};

	@FXML
	private JFXDatePicker initialDate;
	@FXML
	private JFXDatePicker finalDate;
	
	private ObservableList<String> listOrganizations;
	private ObservableList<String> listAreas;
	private ObservableList<String> listPeriods;

	private Project project = new Project();

	
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
		}else{
			if(MainController.has("initialDate")){
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
	
	public void onClickOk(MouseEvent clickEvent) {
		CalendarizedActivity[] calendarizedActivities = new CalendarizedActivity[6];

		MainController.save("initialDate",initialDate.getValue());
		MainController.save("finalDate",finalDate.getValue());

		MainController.save("month1Activity",month1Activity.getText());
		MainController.save("month2Activity",month2Activity.getText());
		MainController.save("month3Activity",month3Activity.getText());
		MainController.save("month4Activity",month4Activity.getText());
		MainController.save("month5Activity",month5Activity.getText());
		MainController.save("month6Activity",month6Activity.getText());

		MainController.save("month1DateActivity",month1DateActivity.getValue());
		MainController.save("month2DateActivity",month2DateActivity.getValue());
		MainController.save("month3DateActivity",month3DateActivity.getValue());
		MainController.save("month4DateActivity",month4DateActivity.getValue());
		MainController.save("month5DateActivity",month5DateActivity.getValue());
		MainController.save("month6DateActivity",month6DateActivity.getValue());
		MainController.activate("CreateProject","Crear Proyecto",MainController.Sizes.LARGE);
	}
	
	public void onClickBack(MouseEvent clickEvent) {
		MainController.activate("CreateProject", "Crear Proyecto", MainController.Sizes.LARGE);
	}

	public void signUp(){
		instanceProject();
		/*
		CalendarizedActivity[] activities = new CalendarizedActivity[6];
		activities[0].setName((String) MainController.get("Month1Activity"));
		activities[0].setDate((String) MainController.get("Month1DateActivty"));
		activities[1].setName((String) MainController.get("Month2Activity"));
		activities[1].setDate((String) MainController.get("Month2DateActivty"));
		activities[2].setName((String) MainController.get("Month3Activity"));
		activities[2].setDate((String) MainController.get("Month3DateActivty"));
		activities[3].setName((String) MainController.get("Month4Activity"));
		activities[3].setDate((String) MainController.get("Month4DateActivty"));
		activities[4].setName((String) MainController.get("Month5Activity"));
		activities[4].setDate((String) MainController.get("Month5DateActivty"));
		activities[5].setName((String) MainController.get("Month6Activity"));
		activities[5].setDate((String) MainController.get("Month6DateActivty"));
		 */
		project.setStartDate(MainController.get("initialDate").toString());
		System.out.println(project.getStartDate());
		project.setEndDate(MainController.get("finalDate").toString());
		System.out.println(project.getEndDate());
		//project.setCalendarizedActivities(activities);
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
		project.setCapacity(Integer.parseInt(txtCapacity.getText()));
		project.setOrganization(Organization.getByName(cmbOrganizations.getValue()));
		project.setPeriod(cmbPeriod.getValue());
		project.setArea(cmbArea.getValue());

		ProjectResponsible projectResponsible = new ProjectResponsible();
		projectResponsible.setPosition(txtPositionResponsible.getText());
		projectResponsible.setEmail(txtEmailResponsible.getText());
		projectResponsible.setNames(txtNameResponsible.getText());
		projectResponsible.setLastNames(txtLastnameResponsible.getText());
		projectResponsible.setOrganization(Organization.getByName(cmbOrganizations.getValue()));

		project.setResponsible(projectResponsible);
	}
}
