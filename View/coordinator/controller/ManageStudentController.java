package View.coordinator.controller;

import Exceptions.CustomException;
import Models.Practicante;
import Models.Professor;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {
	@FXML
	private TableView<Practicante> tblViewStudent;
	@FXML
	private TableColumn<Practicante, String> clmnName;
	@FXML
	private TableColumn<Practicante, String> clmnLastame;
	@FXML
	private TableColumn<Practicante, String> clmnRegno;
	@FXML
	private JFXComboBox<Professor> cmbProfessor;
	
	
	@FXML
	JFXTextField txtName;
	@FXML
	JFXTextField txtLastname;
	@FXML
	JFXTextField txtRegNo;
	@FXML
	JFXTextField txtEmail;
	@FXML
	JFXTextField txtPassword;
	
	@FXML
	JFXButton btnRegister;
	
	private ObservableList<Practicante> listPracticante;
	private ObservableList<Professor> listProfessor;
	
	private Practicante practicante;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listPracticante = FXCollections.observableArrayList();
		new Practicante().llenarTablaPracticantes(listPracticante);
		tblViewStudent.setItems(listPracticante);
		clmnName.setCellValueFactory(new PropertyValueFactory<Practicante, String>("names"));
		clmnLastame.setCellValueFactory(new PropertyValueFactory<Practicante, String>("lastnames"));
		clmnRegno.setCellValueFactory(new PropertyValueFactory<Practicante, String>("regNumber"));
		
		listProfessor = FXCollections.observableArrayList();
		new Professor().fillTableProfessor(listProfessor);
		cmbProfessor.setItems(listProfessor);
		eventManager();
		
		cmbProfessor.setConverter(new StringConverter<Professor>() {
			@Override
			public String toString(Professor professor) {
				return professor.getNombres();
			}
			
			@Override
			public Professor fromString(String string) {
				return null;
			}
		});
		
	}
	
	public void eventManager() {
		tblViewStudent.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<Practicante>() {
				@Override
				public void changed(ObservableValue<? extends Practicante> observable, Practicante oldValue,
				                    Practicante newValue) {
					if (newValue != null) {
						practicante = newValue;
						txtName.setText(newValue.getNombres());
						txtLastname.setText(newValue.getApellidos());
						txtEmail.setText(newValue.getEmail());
						txtRegNo.setText(newValue.getMatricula());
						txtPassword.setText(newValue.getContrasena());
						practicante = tblViewStudent.getSelectionModel().getSelectedItem();
						MainController.save("student", practicante);
						enableEdit();
					} else {
						cleanFormStudent();
						enableRegister();
					}
				}
			}
		);
	}
	
	public void enableEdit() {
	}
	
	public void enableRegister() {
	}
	
	public void cleanFormStudent() {
		txtName.setText(null);
		txtLastname.setText(null);
		txtRegNo.setText(null);
		txtEmail.setText(null);
	}
	
	@FXML
	public void signUp() {
		Practicante practicante = new Practicante();
		this.instanceStudent(practicante);
		if (practicante.estaCompleto()) {
			if (practicante.registrarse()) {
				listPracticante.add(practicante);
				MainController.alert(
					Alert.AlertType.INFORMATION,
					"Practicante registrado correctamente",
					"Pulse aceptar para continuar"
				);
			} else {
				MainController.alert(
					Alert.AlertType.WARNING,
					"Error al conectar con la base de datos",
					"Pulse aceptar para continuar"
				);
			}
		} else {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"Llene todos los campos correctamente",
				"Pulse aceptar para continuar"
			);
		}
	}
	
	private void instanceStudent(Practicante practicante) {
		practicante.setNombres(txtName.getText());
		practicante.setApellidos(txtLastname.getText());
		practicante.setMatricula(txtRegNo.getText());
		practicante.setEmail(txtEmail.getText());
		practicante.setContrasena(txtPassword.getText());
		practicante.setProfesor(cmbProfessor.getValue());
	}
	
	@FXML
	private void checkProject() {
		try {
			if (practicante.getProyecto() == null) {
				MainController.activate("AssignProject", "Asignar Proyecto", MainController.Sizes.MID);
			} else {
				MainController.save("Project", practicante.getProyecto());
				MainController.activate("ViewProject", "Ver Proyecto");
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	public void onClickBack(){
		MainController.activate("MainMenuCoordinator","Menu", MainController.Sizes.MID);
	}

	@FXML
	public void delete() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION,
				"Eliminar Practicante",
				"¿Está seguro que desea eliminar al Practicante seleccionado?")) {
			try {
				if (tblViewStudent.getSelectionModel().getSelectedItem().eliminar()) {
					listPracticante.remove(tblViewStudent.getSelectionModel().getSelectedIndex());
					MainController.alert(Alert.AlertType.INFORMATION,
						"Practicante eliminada",
						"Organización eliminada exitosamente");
				} else {
					MainController.alert(
						Alert.AlertType.INFORMATION,
						"DBError",
						"No se pudo establecer conexión con Base de Datos"
					);
				}
			} catch (AssertionError e) {
				new Logger().log(e.getMessage());
			}
		}
	}
	
}
