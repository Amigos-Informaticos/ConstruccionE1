package View.user.controller;

import Models.User;
import View.MainController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import tools.P;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
	@FXML
	private JFXPasswordField passwordField;
	
	@FXML
	private JFXTextField emailField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		P.p(
			location.toString().substring(location.toString().lastIndexOf('/') + 1)
		);
	}
	
	public void onClickLogIn() {
		if (checkEmptyFields()) {
			if (User.isEmail(emailField.getText().trim())) {
				User user = new User();
				user.setEmail(emailField.getText().trim());
				user.setPassword(passwordField.getText().trim());
				String type = user.getType();
				if (!"null".equals(type)) {
					MainController.setUser(user);
					MainController.setType(type);
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
