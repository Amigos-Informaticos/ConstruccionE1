package View.practicante.controller;

import Models.Assignment;
import Models.Practicante;
import Models.Project;
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
	
	private Project[] selectedProjects;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkConditions();
		projectObservableList = FXCollections.observableArrayList();
		Project.fillTable(projectObservableList);
		projectTable.setItems(projectObservableList);
		tableColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
		checkProject();
		generalObjective.setEditable(false);
		this.resources.setEditable(false);
		responsabilities.setEditable(false);
		area.setEditable(false);
		organization.setEditable(false);
	}
	
	public void checkConditions() {
		selectedProjects = Assignment.requestedProjects((Practicante) MainController.get("user"));
		if (selectedProjects.length >= 3) {
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
					name.setText(newValue.getNombre());
					generalObjective.setText(newValue.getGeneralObjective());
					resources.setText(newValue.getResources());
					responsabilities.setText(newValue.getResponsibilities());
					area.setText(newValue.getArea());
					organization.setText(newValue.getOrganization().getNombre());
				}
			});
	}
	
	public void selectProject() {
		Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
		if (selectedProject != null) {
			if (isSelected(selectedProjects, selectedProject)) {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Proyecto ya seleccionado",
					"El proyecto que intenta seleccionado ya ha sido seleccionado previamente"
				);
			} else {
				Assignment.saveRequest((Practicante) MainController.get("user"), selectedProject);
				MainController.alert(
					Alert.AlertType.INFORMATION,
					"Proyecto seleccionado exitosamente",
					"Pulse aceptar para continuar"
				);
				exit();
			}
		} else {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No se ha seleccionado ningún proyecto",
				"Pulse aceptar para continuar"
			);
		}
	}
	
	public boolean isSelected(Project[] selectedProjects, Project toSelect) {
		boolean selected = false;
		for (Project project: selectedProjects) {
			if (project.getNombre().equals(toSelect.getNombre())) {
				selected = true;
				break;
			}
		}
		return selected;
	}
	
	public void exit() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
}
