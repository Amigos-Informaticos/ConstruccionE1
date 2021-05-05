package View.practicante.controller;

import Models.Practicante;
import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VisualizarProyectosSeleccionadosController implements Initializable {
	public JFXCheckBox checkUno;
	public JFXCheckBox checkDos;
	public JFXCheckBox checkTres;
	public Label objetivoGeneral;
	public Label recursos;
	public Label responsabilidades;
	public JFXTextArea objetivoTextArea;
	public JFXTextArea recursosTextArea;
	public JFXTextArea responsabilidadesTextArea;
	
	private Proyecto[] proyectos;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarProyectosSeleccionados();
	}
	
	private void cargarProyectosSeleccionados() {
		Practicante practicante = (Practicante) MainController.get("user");
		try {
			this.proyectos = practicante.getSeleccion();
			if (proyectos != null) {
				JFXCheckBox[] checkBoxes = new JFXCheckBox[] {checkUno, checkDos, checkTres};
				for (int i = 0; i < this.proyectos.length; i++) {
					Proyecto proyecto = this.proyectos[i];
					checkBoxes[i].setText(this.proyectos[i].getNombre());
				}
			}
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.ERROR,
				"ErrorBD",
				"No se pudo establecer conexión con la base de datos"
			);
			menuPrincipal();
		}
	}
	
	public void salir(MouseEvent mouseEvent) {
		menuPrincipal();
	}
	
	public void menuPrincipal() {
		MainController.activate(
			"MenuPracticante",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	public void checkUno(MouseEvent mouseEvent) {
		checkDos.setSelected(false);
		checkTres.setSelected(false);
		if (this.proyectos != null && this.proyectos.length >= 1) {
			objetivoTextArea.setText(proyectos[0].getObjetivoGeneral());
			recursosTextArea.setText(proyectos[0].getRecursos());
			responsabilidadesTextArea.setText(proyectos[0].getResponsabilidades());
		}
	}
	
	public void checkDos(MouseEvent mouseEvent) {
		checkUno.setSelected(false);
		checkTres.setSelected(false);
		if (this.proyectos != null && this.proyectos.length >= 2) {
			objetivoTextArea.setText(proyectos[1].getObjetivoGeneral());
			recursosTextArea.setText(proyectos[1].getRecursos());
			responsabilidadesTextArea.setText(proyectos[1].getResponsabilidades());
		}
	}
	
	public void checkTres(MouseEvent mouseEvent) {
		checkUno.setSelected(false);
		checkDos.setSelected(false);
		if (this.proyectos != null && this.proyectos.length == 3) {
			objetivoTextArea.setText(proyectos[2].getObjetivoGeneral());
			recursosTextArea.setText(proyectos[2].getRecursos());
			responsabilidadesTextArea.setText(proyectos[2].getResponsabilidades());
		}
	}
}
