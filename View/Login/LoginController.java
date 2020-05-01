package View.Login;

import Models.User;
import View.MainController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	
	}
	
	public void onIngresarButtonClick() {
		User user = new User();
		P.pln("1");
		if (validateFields()) {
			P.pln("2");
			if (isRegistered(emailField.getText().trim(), passwordField.getText().trim())) {
				P.pln("3");
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
				P.pln("No registrado");
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
		P.pln(user.getType());
		isRegistered = !user.getType().equals("null");
		return isRegistered;
	}
}
