package View.Login;

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
	
	public void onClick() {
		User user = new User();
		if (checkEmptyFields()) {
			if (User.isEmail(emailField.getText().trim())) {
				String type =
					isRegistered(emailField.getText().trim(), passwordField.getText().trim());
				if (!type.equals("null")) {
					user.setEmail(emailField.getText().trim());
					user.setPassword(passwordField.getText().trim());
					MainController.setUser(user);
					MainController.setType(type);
					MainController.activate("MainMenu" + type);
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
				"Algún campo se encuentra vacío"
			);
		}
	}
	
	public boolean checkEmptyFields() {
		return !(emailField.getText().length() == 0) && !(passwordField.getText().length() == 0);
	}
	
	public String isRegistered(String email, String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		return user.getType();
	}
}
