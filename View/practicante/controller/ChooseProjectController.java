package View.practicante.controller;

import Models.Asignacion;
import Models.Practicante;
import Models.Proyecto;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChooseProjectController implements Initializable {
	
	@FXML
	public TableView<Proyecto> projectTable;
	
	@FXML
	public TableColumn<Proyecto, String> tableColumn;
	public Label name;
	public JFXTextArea generalObjective;
	public JFXTextArea resources;
	public JFXTextArea responsabilities;
	public JFXTextField area;
	public JFXTextField organization;
	
	private Proyecto[] proyectosSeleccionados;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkConditions();
		ObservableList<Proyecto> proyectoObservableList = FXCollections.observableArrayList();
		try {
			Proyecto.llenarTabla(proyectoObservableList);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		if (proyectoObservableList.size() > 0) {
			projectTable.setItems(proyectoObservableList);
			tableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			checkProject();
			generalObjective.setEditable(false);
			this.resources.setEditable(false);
			responsabilities.setEditable(false);
			area.setEditable(false);
			organization.setEditable(false);
		}
	}
	
	public void checkConditions() {
		try {
			proyectosSeleccionados = Asignacion.proyectosSeleccionados(
				(Practicante) MainController.get("user"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		if (proyectosSeleccionados != null && proyectosSeleccionados.length >= 3) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Limite de proyectos seleccionados",
				"Ya ha llegado a su límite de 3 proyectos"
			);
			MainController.activate(
				"MenuPracticante",
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
					generalObjective.setText(newValue.getObjetivoGeneral());
					resources.setText(newValue.getRecursos());
					responsabilities.setText(newValue.getResponsabilidades());
					area.setText(newValue.getArea());
					organization.setText(newValue.getOrganization().getNombre());
				}
			});
	}
	
	public void selectProject() {
		Proyecto selectedProyecto = projectTable.getSelectionModel().getSelectedItem();
		if (selectedProyecto != null) {
			if (isSelected(proyectosSeleccionados, selectedProyecto)) {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Proyecto ya seleccionado",
					"El proyecto que intenta seleccionado ya ha sido seleccionado previamente"
				);
			} else {
				try {
					Asignacion.saveRequest((Practicante) MainController.get("user"), selectedProyecto);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
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
	
	public boolean isSelected(Proyecto[] selectedProyectos, Proyecto toSelect) {
		boolean selected = false;
		for (Proyecto proyecto: selectedProyectos) {
			if (proyecto.getNombre().equals(toSelect.getNombre())) {
				selected = true;
				break;
			}
		}
		return selected;
	}
	
	public void exit() {
		MainController.activate(
			"MenuPracticante",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
}
