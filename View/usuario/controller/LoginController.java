package View.usuario.controller;

import Models.Admin;
import Models.Coordinator;
import Models.Practicante;
import Models.Professor;
import Models.Usuario;
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
			if (Usuario.esEmail(emailField.getText().trim())) {
				Usuario usuario = new Usuario();
				usuario.setEmail(emailField.getText().trim());
				usuario.setContrasena(passwordField.getText().trim());
				String type = usuario.tipo();
				if (!"null".equals(type)) {
					switch (type) {
						case "Student":
							usuario = new Practicante();
							break;
						case "Professor":
							usuario = new Professor();
							break;
						case "Coordinator":
							usuario = new Coordinator();
							break;
						case "Admin":
							usuario = new Admin();
							break;
						default:
					}
					usuario.setEmail(emailField.getText().trim());
					usuario.setContrasena(passwordField.getText().trim());
					MainController.save("user", usuario);
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
