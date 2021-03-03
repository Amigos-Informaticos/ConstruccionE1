package View.coordinador.controller;

import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProjectController implements Initializable {
	@FXML
	private JFXTextField txtOrganization;
	
	@FXML
	private JFXTextField txtName;
	@FXML
	private JFXTextArea txtDescription;
	@FXML
	private JFXTextField txtGeneralObjective;
	@FXML
	private JFXTextField txtMediateObjective;
	@FXML
	private JFXTextField txtInmediateObjective;
	@FXML
	private JFXTextField txtMethodology;
	@FXML
	private JFXTextArea txtResources;
	@FXML
	private JFXTextArea txtResponsibilities;
	@FXML
	private JFXTextField txtCapacity;
	@FXML
	private JFXTextField txtPositionResponsible;
	@FXML
	private JFXTextField txtEmailResponsible;
	@FXML
	private JFXTextField txtNameResponsible;
	@FXML
	private JFXTextField txtLastnameResponsible;
	@FXML
	private JFXTextField txtArea;
	@FXML
	private JFXTextField txtPeriod;
	
	private Proyecto proyecto = (Proyecto) MainController.get("Project");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtOrganization.setText(proyecto.getOrganization().getNombre());
		txtName.setText(proyecto.getNombre());
		txtDescription.setText(proyecto.getDescription());
		txtGeneralObjective.setText(proyecto.getGeneralObjective());
		txtMediateObjective.setText(proyecto.getMediateObjective());
		txtInmediateObjective.setText(proyecto.getImmediateObjective());
		txtMethodology.setText(proyecto.getMethodology());
		txtResources.setText(proyecto.getResources());
		txtResponsibilities.setText(proyecto.getResponsibilities());
		
		txtPositionResponsible.setText(proyecto.getResponsible().getPosicion());
		txtEmailResponsible.setText(proyecto.getResponsible().getEmail());
		txtNameResponsible.setText(proyecto.getResponsible().getNombres());
		txtLastnameResponsible.setText(proyecto.getResponsible().getApellidos());
		
		txtCapacity.setText(String.valueOf(proyecto.getCapacity()));
		txtArea.setText(proyecto.getArea());
		txtPeriod.setText(proyecto.getPeriod());
	}
	
	public void delete() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION,
			"Eliminar Proyecto",
			"¿Seguro que desea eliminar el Proyecto?")) {
			if (proyecto.eliminarProyecto()) {
				MainController.alert(Alert.AlertType.INFORMATION,
					"Proyecto eliminado",
					"Proyecto eliminado exitosamente");
			} else {
				MainController.alert(Alert.AlertType.ERROR,
					"Sin conexión con BD",
					"Sin conexión con Base de Datos");
			}
		}
	}
}
