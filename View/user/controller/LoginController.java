package View.user.controller;

import Models.Admin;
import Models.Coordinator;
import Models.Professor;
import Models.Student;
import Models.User;
import View.MainController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
	@FXML
	private JFXPasswordField passwordField;
	
	@FXML
	private JFXTextField emailField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void onClickLogIn() {
		if (checkEmptyFields()) {
			if (User.isEmail(emailField.getText().trim())) {
				User user = new User();
				user.setEmail(emailField.getText().trim());
				user.setPassword(passwordField.getText().trim());
				String type = user.getType();
				if (!"null".equals(type)) {
					switch (type) {
						case "Student":
							user = new Student();
							break;
						case "Professor":
							user = new Professor();
							break;
						case "Coordinator":
							user = new Coordinator();
						case "Admin":
							user = new Admin();
							break;
					}
					user.setEmail(emailField.getText().trim());
					user.setPassword(passwordField.getText().trim());
					MainController.save("user", user);
					MainController.activate(
						"MainMenu" + type,
						"Menu Principal " + type,
						MainController.Sizes.MID);
				} else {
					MainController.alert(
						Alert.AlertType.ERROR,
						"No registrado",
						"Credenciales no registradas");
				}
			} else {
				MainController.alert(
					Alert.AlertType.ERROR,
					"Correo erróneo",
					"Formato de correo electrónico inválido");
			}
		} else {
			MainController.alert(
				Alert.AlertType.WARNING,
				"Campos vacíos",
				"Algún campo se encuentra vacío");
		}
	}
	
	
	public boolean checkEmptyFields() {
		return emailField.getText().length() != 0 && passwordField.getText().length() != 0;
	}
}
