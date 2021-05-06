package View.coordinador.controller;

import Models.Proyecto;
import View.MainController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
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
							"No se ha podido eliminar el proyecto",
							"El proyecto no ha sido eliminado");
				}
			} catch (SQLException throwables) {
				MainController.alert(Alert.AlertType.ERROR,
						"Sin conexión con BD",
						"Sin conexión con Base de Datos");
			}
		}
	}

	public void actualizar(){
		if(proyectoEstaModificado()){
			MainController.alert(Alert.AlertType.ERROR,
					"Proyecto si está modificado",
					"\"Proyecto si está modificado");
		}
	}

	public boolean responsableModificado(){
		boolean modificado = false;


		String posicionResponsable = proyecto.getResponsable().getPosicion();
		String emailResponsable = proyecto.getResponsable().getEmail();
		String nombreResponsable = proyecto.getResponsable().getNombres();
		String apellidoResponsable = proyecto.getResponsable().getApellidos();



		if(!posicionResponsable.equals(txtPositionResponsible.getText())){
			modificado = true;
		}else if (!emailResponsable.equals(txtEmailResponsible.getText())){
			modificado = true;
		}else if(!nombreResponsable.equals(txtNameResponsible.getText())){
			modificado = true;
		}else if(!apellidoResponsable.equals(txtLastnameResponsible.getText())){
			modificado = true;
		}


		return  modificado;
	}

	public boolean areaModificada(){
		boolean modificada = false;
		String area = proyecto.getArea();


		if(!area.equals(txtArea.getText())){
			modificada = true;
		}

		return modificada;

	}

	public  boolean periodoModificado(){
		boolean modificado = false;
		String periodo = proyecto.getPeriodo();

		if(!periodo.equals(txtPeriod.getText())){

			modificado = true;
		}

		return modificado;
	}






	public boolean proyectoEstaModificado(){
		boolean modificado = false;
		String organizacion = proyecto.getOrganization().getNombre();
		String nombreProyecto = proyecto.getNombre();
		String descripcion = proyecto.getDescripcion();
		String objetivoGenaral = proyecto.getObjetivoGeneral();
		String objetivoMediato = proyecto.getObjetivoMediato();
		String objetivoInmediato = proyecto.getObjetivoInmediato();
		String metodologia = proyecto.getMetodologia();
		String recursos = proyecto.getRecursos();
		String responsabilidades =proyecto.getResponsabilidades();
		String cupo =  String.valueOf( proyecto.getCapacidad());






		txtPositionResponsible.setText(proyecto.getResponsable().getPosicion());
		txtEmailResponsible.setText(proyecto.getResponsable().getEmail());
		txtNameResponsible.setText(proyecto.getResponsable().getNombres());
		txtLastnameResponsible.setText(proyecto.getResponsable().getApellidos());




		if (!nombreProyecto.equals(txtName.getText())){
			modificado = true;
		}else if(!descripcion.equals(txtDescription.getText())){
			modificado = true;
		}else if(!objetivoGenaral.equals(txtGeneralObjective.getText())){
			modificado = true;
		}else if(!objetivoMediato.equals(txtMediateObjective.getText()) ){
			modificado = true;
		}else if(!objetivoInmediato.equals(txtInmediateObjective.getText())){
			modificado = true;
		}else if(!metodologia.equals(txtMethodology.getText())){
			modificado = true;
		}else if(!recursos.equals(txtResources.getText())){
			modificado = true;
		}else if(!responsabilidades.equals(txtResponsibilities.getText())){
			modificado = true;
		}else if(! cupo.equals(txtCapacity.getText())){
			modificado = true;
		}



		return	modificado;
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

	public void eliminarClick(ActionEvent actionEvent) {
	}

	public void clicActualizar(ActionEvent actionEvent) {
		actualizar();
	}
}
