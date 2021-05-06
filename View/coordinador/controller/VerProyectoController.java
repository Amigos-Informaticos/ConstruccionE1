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

public class VerProyectoController implements Initializable {
	@FXML
	private JFXComboBox<String> cmbOrganizations;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextArea txtDescripcion;
	@FXML
	private JFXTextField txtObjetivoGeneral;
	@FXML
	private JFXTextField txtObjetivoMediato;
	@FXML
	private JFXTextField txtObjetivoInmediato;
	@FXML
	private JFXTextField txtMetodologia;
	@FXML
	private JFXTextArea txtRecursos;
	@FXML
	private JFXTextArea txtResponsabilidades;
	@FXML
	private JFXTextField txtCapacidad;
	@FXML
	private JFXTextField txtPosicionResponsable;
	@FXML
	private JFXTextField txtEmailResponsable;
	@FXML
	private JFXTextField txtNombreResponsable;
	@FXML
	private JFXTextField txtApellidosResponsable;
	@FXML
	private JFXComboBox<String> cmbArea;
	@FXML
	private JFXComboBox<String> cmbPeriodo;
	
	@FXML
	private JFXDatePicker fechaInicial;
	@FXML
	private JFXDatePicker fechaFinal;
	
	private ObservableList<String> listaOrganizaciones;
	private ObservableList<String> listaAreas;
	private ObservableList<String> listaPeriodos;
	
	private Proyecto proyecto;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaOrganizaciones = FXCollections.observableArrayList();
		try {
			Organizacion.llenarTodosNombres(listaOrganizaciones);
		} catch (SQLException throwable) {
			System.out.println(throwable.getMessage());
		}
		cmbOrganizations.setItems(listaOrganizaciones);
		
		listaAreas = FXCollections.observableArrayList();
		try {
			Proyecto.fillAreaTable(listaAreas);
		} catch (SQLException throwables) {
			System.out.println(throwables);
		}
		cmbArea.setItems(listaAreas);
		
		listaPeriodos = FXCollections.observableArrayList();
		listaPeriodos.add("FEB-JUL");
		listaPeriodos.add("AGO-ENE");
		cmbPeriodo.setItems(listaPeriodos);
		limitarTextfields();
		
		proyecto = (Proyecto) MainController.get("project");
		this.inicializarCampos();
	}
	
	public void limitarTextfields() {
		LimitadorTextfield.soloTexto(txtNombre);
		LimitadorTextfield.soloTextoArea(txtDescripcion);
		LimitadorTextfield.soloTexto(txtObjetivoGeneral);
		LimitadorTextfield.soloTexto(txtObjetivoMediato);
		LimitadorTextfield.soloTexto(txtObjetivoInmediato);
		LimitadorTextfield.soloTexto(txtMetodologia);
		LimitadorTextfield.soloTextoArea(txtRecursos);
		LimitadorTextfield.soloTextoArea(txtResponsabilidades);
		LimitadorTextfield.soloTexto(txtPosicionResponsable);
		LimitadorTextfield.soloTexto(txtNombreResponsable);
		LimitadorTextfield.soloTexto(txtApellidosResponsable);
		LimitadorTextfield.soloNumeros(txtCapacidad);
		
		LimitadorTextfield.limitarTamanio(txtNombre, 50);
		LimitadorTextfield.limitarTamanio(txtObjetivoGeneral, 50);
		LimitadorTextfield.limitarTamanio(txtObjetivoMediato, 50);
		LimitadorTextfield.limitarTamanio(txtObjetivoInmediato, 50);
		LimitadorTextfield.limitarTamanio(txtMetodologia, 50);
		LimitadorTextfield.limitarTamanio(txtPosicionResponsable, 20);
		LimitadorTextfield.limitarTamanio(txtNombreResponsable, 20);
		LimitadorTextfield.limitarTamanio(txtApellidosResponsable, 20);
		LimitadorTextfield.limitarTamanio(txtEmailResponsable, 20);
		LimitadorTextfield.limitarTamanio(txtCapacidad, 2);
		
		LimitadorTextfield.limitarTamanioArea(txtDescripcion, 200);
		LimitadorTextfield.limitarTamanioArea(txtRecursos, 200);
		LimitadorTextfield.limitarTamanioArea(txtResponsabilidades, 200);
	}
	
	public void eliminar() {
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
	
	public void inicializarCampos() {
		cmbOrganizations.setValue(proyecto.getOrganization().getNombre());
		txtNombre.setText(proyecto.getNombre());
		txtDescripcion.setText(proyecto.getDescripcion());
		txtObjetivoGeneral.setText(proyecto.getObjetivoGeneral());
		txtObjetivoMediato.setText(proyecto.getObjetivoMediato());
		txtObjetivoInmediato.setText(proyecto.getObjetivoInmediato());
		txtMetodologia.setText(proyecto.getMetodologia());
		txtRecursos.setText(proyecto.getRecursos());
		txtResponsabilidades.setText(proyecto.getResponsabilidades());
		
		txtPosicionResponsable.setText(proyecto.getResponsable().getPosicion());
		txtEmailResponsable.setText(proyecto.getResponsable().getEmail());
		txtNombreResponsable.setText(proyecto.getResponsable().getNombres());
		txtApellidosResponsable.setText(proyecto.getResponsable().getApellidos());
		
		txtCapacidad.setText(String.valueOf(proyecto.getCapacidad()));
		cmbArea.setValue(proyecto.getArea());
		cmbPeriodo.setValue(proyecto.getPeriodo());
		
		fechaInicial.setValue(LocalDate.parse(proyecto.getFechaInicio()));
		fechaFinal.setValue(LocalDate.parse(proyecto.getFechaFin()));
	}
	
	public void salir() {
		MainController.activate("ListaProyectos", "Lista de Proyectos", MainController.Sizes.MID);
	}
	
	public void actualizar() {
		this.proyecto.setNombre(txtNombre.getText());
		this.proyecto.setDescripcion(txtDescripcion.getText());
		this.proyecto.setObjetivoGeneral(txtObjetivoGeneral.getText());
		this.proyecto.setObjetivoMediato(txtObjetivoMediato.getText());
		this.proyecto.setObjetivoInmediato(txtObjetivoInmediato.getText());
		this.proyecto.setMetodologia(txtMetodologia.getText());
		this.proyecto.setRecursos(txtRecursos.getText());
		this.proyecto.setResponsabilidades(txtResponsabilidades.getText());
		this.proyecto.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
		this.proyecto.getResponsable().setPosicion(txtPosicionResponsable.getText());
		this.proyecto.getResponsable().setEmail(txtEmailResponsable.getText());
		this.proyecto.getResponsable().setNombre(txtNombreResponsable.getText());
		this.proyecto.getResponsable().setApellido(txtApellidosResponsable.getText());
		this.proyecto.setArea(cmbArea.getValue());
		this.proyecto.setPeriodo(cmbPeriodo.getValue());
		this.proyecto.setFechaInicio(fechaInicial.getValue().toString());
		this.proyecto.setFechaFin(fechaFinal.getValue().toString());
	}
}
