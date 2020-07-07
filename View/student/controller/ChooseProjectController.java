package View.student.controller;

import Models.Assignment;
import Models.Project;
import Models.Student;
import View.MainController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseProjectController implements Initializable {
	
	@FXML
	public TableView<Project> projectTable;
	
	@FXML
	public TableColumn<Project, String> tableColumn;
	public Label name;
	public JFXTextArea generalObjective;
	public JFXTextArea resources;
	public JFXTextArea responsabilities;
	public JFXTextField area;
	public JFXTextField organization;
	
	private ObservableList<Project> projectObservableList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkConditions();
		projectObservableList = FXCollections.observableArrayList();
		Project.fillTable(projectObservableList);
		projectTable.setItems(projectObservableList);
		tableColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
		checkProject();
	}
	
	public void checkConditions() {
		Project[] projects = Assignment.requestedProjects((Student) MainController.get("user"));
		if (projects.length >= 3) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Limite de proyectos seleccionados",
				"Ya ha llegado a su límite de 3 proyectos"
			);
			MainController.activate(
				"MainMenuStudent",
				"Menu Principal Practicante",
				MainController.Sizes.MID
			);
		}
	}
	
	public void checkProject() {
		projectTable.getSelectionModel().selectedItemProperty().addListener(
			(observableValue, oldValue, newValue) -> {
				if (newValue != null) {
					name.setText(newValue.getName());
					generalObjective.setText(newValue.getGeneralObjective());
					resources.setText(newValue.getResources());
					responsabilities.setText(newValue.getResponsibilities());
					area.setText(newValue.getArea());
					organization.setText(newValue.getOrganization().getName());
				}
			});
	}
	
	public void selectProject(MouseEvent mouseEvent) {
		Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
		if (selectedProject != null) {
			Assignment.saveRequest((Student) MainController.get("user"), selectedProject);
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"Proyecto seleccionado exitosamente",
				"Pulse aceptar para continuar"
			);
			MainController.activate(
				"MainMenuStudent",
				"Menu Principal Practicante",
				MainController.Sizes.MID);
		} else {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No se ha seleccionado ningún proyecto",
				"Pulse aceptar para continuar"
			);
		}
	}
}
