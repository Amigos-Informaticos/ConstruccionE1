package View.ManageStudents;

import Exceptions.CustomException;
import Models.Student;
import View.MainController;
import com.jfoenix.controls.JFXButton;
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
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {
	@FXML private TableView<Student> tblViewStudent;
	@FXML private TableColumn<Student, String> clmnName;
	@FXML private TableColumn<Student, String> clmnLastame;
	@FXML private TableColumn<Student, String> clmnRegno;


	
	@FXML JFXTextField txtName;
	@FXML JFXTextField txtLastname;
	@FXML JFXTextField txtRegNo;
	@FXML JFXTextField txtEmail;
	@FXML JFXTextField txtPassword;

	@FXML JFXButton btnRegister;
	
	ObservableList<Student> listStudent;

	private Student student;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listStudent = FXCollections.observableArrayList();
		new Student().fillTableStudent(listStudent);
		tblViewStudent.setItems(listStudent);
		clmnName.setCellValueFactory(new PropertyValueFactory<Student, String>("names"));
		clmnLastame.setCellValueFactory(new PropertyValueFactory<Student, String>("lastnames"));
		clmnRegno.setCellValueFactory(new PropertyValueFactory<Student, String>("regNumber"));

		eventManager();
	}

	public void eventManager(){
		tblViewStudent.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Student>() {
					@Override
					public void changed(ObservableValue<? extends Student> observable, Student oldValue,
										Student newValue) {
						if(newValue != null){
							student = newValue;
							txtName.setText(newValue.getNames());
							txtLastname.setText(newValue.getLastnames());
							txtEmail.setText(newValue.getEmail());
							txtRegNo.setText(newValue.getPassword());
							txtPassword.setText(newValue.getPassword());
							enableEdit();
						} else {
							cleanFormStudent();
							enableRegister();
						}
					}
				}
		);
	}

	public void enableEdit(){}

	public void enableRegister(){}
	
	public void cleanFormStudent() {
		txtName.setText(null);
		txtLastname.setText(null);
		txtRegNo.setText(null);
		txtEmail.setText(null);
	}

	@FXML
	public void signUp(){
		Student student = new Student();
		this.instanceStudent(student);
		try {
			if(student.isComplete()){
				if(student.signUp()){
					listStudent.add(student);
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
		} catch (CustomException e) {
			new Logger().log(e.getCauseMessage());
		}
	}

	private void instanceStudent(Student student){
		student.setNames(txtName.getText());
		student.setLastnames(txtLastname.getText());
		student.setRegNumber(txtRegNo.getText());
		student.setEmail(txtEmail.getText());
		student.setPassword(txtPassword.getText());
	}

}
