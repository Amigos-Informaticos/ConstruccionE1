package View.usuario.controller;

import Models.Administrador;
import Models.Coordinador;
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
import java.sql.SQLException;
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
				String type;
				try {
					type = usuario.tipo();
					if (!"null".equals(type)) {
						switch (type) {
							case "Practicante":
								usuario = new Practicante();
								break;
							case "Profesor":
								usuario = new Professor();
								break;
							case "Coordinador":
								usuario = new Coordinador();
								break;
							case "Administrador":
								usuario = new Administrador();
								break;
							default:
						}
						usuario.setEmail(emailField.getText().trim());
						usuario.setContrasena(passwordField.getText().trim());
						MainController.save("user", usuario);
						MainController.activate(
							"Menu" + type,
							"Menu Principal " + type,
							MainController.Sizes.MID);
					} else {
						MainController.alert(
							Alert.AlertType.ERROR,
							"No registrado",
							"Credenciales no registradas");
					}
				} catch (SQLException throwables) {
					MainController.alert(
						Alert.AlertType.ERROR,
						"ErrorBD",
						"No se pudo establecer conexión con la base de datos"
					);
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
