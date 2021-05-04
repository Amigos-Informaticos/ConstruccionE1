package View.coordinador.controller;

import Models.Organizacion;
import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import tools.LimitadorTextfield;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VerProyectoV2Controller implements Initializable {
	@FXML
	private JFXComboBox<String> cmbOrganizations;
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
	private JFXComboBox<String> cmbArea;
	@FXML
	private JFXComboBox<String> cmbPeriod;

	@FXML
	private JFXDatePicker initialDate;
	@FXML
	private JFXDatePicker finalDate;

	private ObservableList<String> listOrganizations;
	private ObservableList<String> listAreas;
	private ObservableList<String> listPeriods;
	
	private Proyecto proyecto ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listOrganizations = FXCollections.observableArrayList();
		try {
			Organizacion.llenarNombres(listOrganizations);
		} catch (SQLException throwables) {
			System.out.println(throwables);;
		}
		cmbOrganizations.setItems(listOrganizations);

		listAreas = FXCollections.observableArrayList();
		try {
			Proyecto.fillAreaTable(listAreas);
		} catch (SQLException throwables) {
			System.out.println(throwables);;
		}
		cmbArea.setItems(listAreas);

		listPeriods = FXCollections.observableArrayList();
		listPeriods.add("FEB-JUL");
		listPeriods.add("AGO-ENE");
		cmbPeriod.setItems(listPeriods);
		limitarTextfields();

		proyecto =  (Proyecto) MainController.get("project");
		this.inicializarCampos();
	}

	public void limitarTextfields() {
		LimitadorTextfield.soloTexto(txtName);
		LimitadorTextfield.soloTextoArea(txtDescription);
		LimitadorTextfield.soloTexto(txtGeneralObjective);
		LimitadorTextfield.soloTexto(txtMediateObjective);
		LimitadorTextfield.soloTexto(txtInmediateObjective);
		LimitadorTextfield.soloTexto(txtMethodology);
		LimitadorTextfield.soloTextoArea(txtResources);
		LimitadorTextfield.soloTextoArea(txtResponsibilities);
		LimitadorTextfield.soloTexto(txtPositionResponsible);
		LimitadorTextfield.soloTexto(txtNameResponsible);
		LimitadorTextfield.soloTexto(txtLastnameResponsible);
		LimitadorTextfield.soloNumeros(txtCapacity);

		LimitadorTextfield.limitarTamanio(txtName, 50);
		LimitadorTextfield.limitarTamanio(txtGeneralObjective, 50);
		LimitadorTextfield.limitarTamanio(txtMediateObjective, 50);
		LimitadorTextfield.limitarTamanio(txtInmediateObjective, 50);
		LimitadorTextfield.limitarTamanio(txtMethodology, 50);
		LimitadorTextfield.limitarTamanio(txtPositionResponsible, 20);
		LimitadorTextfield.limitarTamanio(txtNameResponsible, 20);
		LimitadorTextfield.limitarTamanio(txtLastnameResponsible, 20);
		LimitadorTextfield.limitarTamanio(txtEmailResponsible, 20);
		LimitadorTextfield.limitarTamanio(txtCapacity, 2);

		LimitadorTextfield.limitarTamanioArea(txtDescription, 200);
		LimitadorTextfield.limitarTamanioArea(txtResources, 200);
		LimitadorTextfield.limitarTamanioArea(txtResponsibilities, 200);
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
		cmbOrganizations.setValue(proyecto.getOrganization().getNombre());
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
		cmbArea.setValue(proyecto.getArea());
		cmbPeriod.setValue(proyecto.getPeriodo());

		initialDate.setValue(LocalDate.parse(proyecto.getFechaInicio()));
		finalDate.setValue(LocalDate.parse(proyecto.getFechaFin()));

	}

	public void onClickBack() {
		MainController.activate("ListaProyectos", "Lista de Proyectos", MainController.Sizes.MID);
	}
}
