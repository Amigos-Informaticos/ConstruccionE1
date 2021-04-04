package View.coordinador.controller;

import Models.Asignacion;
import Models.Coordinador;
import Models.Practicante;
import Models.Proyecto;
import View.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class AsignarProyectoController implements Initializable {
	@FXML
	Label lbName;
	@FXML
	Label lbRegNo;
	@FXML
	Label lbEmail;
	
	public TableColumn<Proyecto, String> clmNameRequest;
	public TableColumn<Proyecto, String> clmName;
	
	@FXML
	public TableView<Proyecto> requestTable;
	@FXML
	public TableView<Proyecto> projectTable;
	
	private Practicante practicante = (Practicante) MainController.get("student");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Proyecto> proyectoObservableList = FXCollections.observableArrayList();
		try {
			Proyecto.llenarTabla(proyectoObservableList);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		projectTable.setItems(proyectoObservableList);
		clmName.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("name"));
		
		ObservableList<Proyecto> requestObservableList = FXCollections.observableArrayList();
		try {
			Collections.addAll(requestObservableList, practicante.getSeleccion());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		requestTable.setItems(requestObservableList);
		clmNameRequest.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("name"));
		
		lbName.setText(practicante.getNombres() + " " + practicante.getApellidos());
		lbRegNo.setText(practicante.getMatricula());
		lbEmail.setText(practicante.getEmail());
		
	}
	
	public void selectProject() {
		if (projectTable.isFocused()) {
			Proyecto proyecto = projectTable.getSelectionModel().getSelectedItem();
			MainController.save("project", proyecto);
		} else if (requestTable.isFocused()) {
			Proyecto proyecto = requestTable.getSelectionModel().getSelectedItem();
			MainController.save("project", proyecto);
		}
	}
	
	public void assign() throws FileNotFoundException {
		Proyecto proyecto = (Proyecto) MainController.get("project");
		Asignacion asignacion = null;
		try {
			asignacion = new Asignacion(
				practicante,
				proyecto,
				(Coordinador) MainController.get("user")
			);
		} catch (SQLException throwables) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
		}
		if (MainController.alert(Alert.AlertType.CONFIRMATION, "Confirmar Asignacion",
			"¿Esta seguro de que quiere asignar el practicante "
				+ practicante.getNombres() +
				" al proyecto " + proyecto.getNombre() + "?")) {
			try {
				if (asignacion.assignProject()) {
					MainController.alert(Alert.AlertType.INFORMATION,
						"Asignación registrada",
						"Asignacon realizada exitosamente");
				} else {
					MainController.alert(Alert.AlertType.ERROR, "DatabaseError", "No se pudo establecer conexión con la Base de Datos");
					exit();
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		
	}
	
	public void exit() {
		MainController.activate(
			"ManageStudents",
			"Administrar Practicantes",
			MainController.Sizes.MID);
	}
}
