package View.Login;

import Models.User;
import View.MainController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
						//Ventana para studiante
						break;
					case "Professor":
						//Ventana para profesor
						break;
					case "Coordinator":
						//
						break;
				}
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
		isRegistered = !user.getType().equals("");
		return isRegistered;
	}
}
