package View.coordinador.controller;

import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VerProyectoController implements Initializable {
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
	
	private Proyecto proyecto ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		proyecto =  (Proyecto) MainController.get("project");
		this.inicializarCampos();

	}
	
	public void delete() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION,
			"Eliminar Proyecto",
			"¿Seguro que desea eliminar el Proyecto?")) {
			try {
				if (proyecto.eliminarProyecto()) {
					MainController.alert(Alert.AlertType.INFORMATION,
						"Proyecto eliminado",
						"Proyecto eliminado exitosamente");
				} else {
					MainController.alert(Alert.AlertType.ERROR,
						"Sin conexión con BD",
						"Sin conexión con Base de Datos");
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}


	public void inicializarCampos(){
		txtOrganization.setText(proyecto.getOrganization().getNombre());
		txtName.setText(proyecto.getNombre());
		txtDescription.setText(proyecto.getDescripcion());
		txtGeneralObjective.setText(proyecto.getObjetivoGeneral());
		txtMediateObjective.setText(proyecto.getObjetivoMediato());
		txtInmediateObjective.setText(proyecto.getObjetivoInmediato());
		txtMethodology.setText(proyecto.getMetodologia());
		txtResources.setText(proyecto.getRecursos());
		txtResponsibilities.setText(proyecto.getResponsabilidades());

		txtPositionResponsible.setText(proyecto.getResponsable().getPosicion());
		txtEmailResponsible.setText(proyecto.getResponsable().getEmail());
		txtNameResponsible.setText(proyecto.getResponsable().getNombres());
		txtLastnameResponsible.setText(proyecto.getResponsable().getApellidos());

		txtCapacity.setText(String.valueOf(proyecto.getCapacidad()));
		txtArea.setText(proyecto.getArea());
		txtPeriod.setText(proyecto.getPeriodo());
	}
}
