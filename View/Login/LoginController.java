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
	
	public void onIngresarButtonClick() {
		User user = new User();
		if (validateFields()) {
			if (isRegistered(emailField.getText().trim(), passwordField.getText().trim())) {
				user.setEmail(emailField.getText().trim());
				user.setPassword(passwordField.getText().trim());
				MainController.setUser(user);
				MainController.setType(user.getType());
				switch (user.getType()) {
					case "Student":
						MainController.activate("MainMenuStudent");
						break;
					case "Professor":
						MainController.activate("MainMenuProfessor");
						break;
					case "Coordinator":
						MainController.activate("MainMenuCoordinator");
						break;
					default:
				}
			} else {
				MainController.alert(
					Alert.AlertType.ERROR,
					"No registrado",
					"Credenciales no registradas");
				
			}
		}
	}
	
	public boolean validateFields() {
		return !emailField.getText().trim().equals("") && !passwordField.getText().trim().equals("");
	}
	
	public boolean isRegistered(String email, String password) {
		boolean isRegistered = false;
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		isRegistered = !user.getType().equals("null");
		return isRegistered;
	}
}
