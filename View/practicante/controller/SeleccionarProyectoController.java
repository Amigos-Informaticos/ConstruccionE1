package View.practicante.controller;

import DAO.DAOPracticante;
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

public class SeleccionarProyectoController implements Initializable {
	
	@FXML
	public TableView<Proyecto> tablaProyectos;
	
	@FXML
	public TableColumn<Proyecto, String> columnaTabla;
	
	@FXML
	public JFXTextField area;
	
	@FXML
	public Label nombre;
	
	@FXML
	public JFXTextArea objetivoGeneral;
	
	@FXML
	public JFXTextArea recursos;
	
	@FXML
	public JFXTextArea responsabilidades;
	
	@FXML
	public JFXTextField organizacion;
	
	private Proyecto[] proyectosSeleccionados;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Practicante practicante = DAOPracticante.get((Practicante) MainController.get("user"));
			proyectosSeleccionados = practicante.getSeleccion();
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
		ObservableList<Proyecto> proyectoObservableList = FXCollections.observableArrayList();
		try {
			Proyecto.llenarTabla(proyectoObservableList);
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
		if (proyectoObservableList.size() > 0) {
			tablaProyectos.setItems(proyectoObservableList);
			columnaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			checkProject();
			objetivoGeneral.setEditable(false);
			recursos.setEditable(false);
			responsabilidades.setEditable(false);
			area.setEditable(false);
			organizacion.setEditable(false);
		}
	}
	
	public void checkProject() {
		tablaProyectos.getSelectionModel().selectedItemProperty().addListener(
			(observableValue, oldValue, newValue) -> {
				if (newValue != null) {
					nombre.setText(newValue.getNombre());
					objetivoGeneral.setText(newValue.getObjetivoGeneral());
					recursos.setText(newValue.getRecursos());
					responsabilidades.setText(newValue.getResponsabilidades());
					area.setText(newValue.getArea());
					organizacion.setText(newValue.getOrganization().getNombre());
				}
			});
	}
	
	public void selectProject() {
		Proyecto selectedProyecto = tablaProyectos.getSelectionModel().getSelectedItem();
		if (selectedProyecto != null) {
			if (isSelected(proyectosSeleccionados, selectedProyecto)) {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Proyecto ya seleccionado",
					"El proyecto que intenta seleccionado ya ha sido seleccionado previamente"
				);
			} else {
				try {
					Asignacion.guardarSolicitud((Practicante) MainController.get("user"), selectedProyecto);
				} catch (SQLException throwable) {
					MainController.alert(
						Alert.AlertType.ERROR,
						"ErrorBD",
						"No se pudo establecer conexión con la base de datos"
					);
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
		if (selectedProyectos != null) {
			for (Proyecto proyecto: selectedProyectos) {
				if (proyecto.getNombre().equals(toSelect.getNombre())) {
					selected = true;
					break;
				}
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
