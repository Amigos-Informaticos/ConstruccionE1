package View.coordinador.controller;

import Models.Practicante;
import Models.Profesor;
import Models.Proyecto;
import Models.Usuario;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import org.apache.commons.validator.routines.EmailValidator;
import tools.LimitadorTextfield;
import tools.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministrarPracticanteController implements Initializable {
	@FXML
	public JFXButton btnEliminar;
	@FXML
	public JFXButton btnActualizar;
	@FXML
	public JFXButton verProyecto;
	@FXML
	private TableView<Practicante> tblViewPracticante;
	@FXML
	private TableColumn<Practicante, String> clmnNombre;
	@FXML
	private TableColumn<Practicante, String> clmnApellido;
	@FXML
	private TableColumn<Practicante, String> clmnMatricula;
	@FXML
	private JFXComboBox<String> cmbProfesor;
	
	@FXML
	JFXTextField txtNombre;
	@FXML
	JFXTextField txtApellido;
	@FXML
	JFXTextField txtMatricula;
	@FXML
	JFXTextField txtEmail;
	@FXML
	JFXTextField txtContrasenia;
	
	@FXML
	JFXButton btnRegistrar;
	
	private ObservableList<Practicante> listPracticante;
	private ObservableList<String> profesoresRecuperados;
	private ObservableList<Profesor> listProfesor;
	
	private Practicante practicante;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listPracticante = FXCollections.observableArrayList();
		profesoresRecuperados = FXCollections.observableArrayList();
		try {
			new Practicante().llenarTablaPracticantes(listPracticante);
		} catch (NullPointerException pointerException) {
			pointerException.printStackTrace();
		} catch (SQLException throwable) {
			this.mostrarMensajeErrorBD();
		}
		
		tblViewPracticante.setItems(listPracticante);
		clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombres"));
		clmnApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		clmnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
		
		listProfesor = FXCollections.observableArrayList();
		try {
			new Profesor().obtenerProfesores(listProfesor);
		} catch (NullPointerException e) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"No hay profesores",
				"No hay profesores registrados para asociar a los practicantes"
			);
			MainController.activate("MainMenuCoordinator");
		} catch (Exception e) {
			MainController.alert(
				Alert.AlertType.WARNING,
				"ErrorBD",
				"Error al conectar con la base de datos"
			);
			MainController.activate("MainMenuCoordinator");
		}
		
		this.llenarListaProfesores();
		cmbProfesor.setItems(this.profesoresRecuperados);
		reiniciarColorTxtbox();
		this.limitarTextFields();
		eventManager();
	}
	
	private void llenarListaProfesores() {
		for (Profesor profesor: this.listProfesor) {
			this.profesoresRecuperados.add(profesor.getNombres());
		}
	}
	
	private Profesor obtenerProfesorPorNombres(String nombres) {
		Profesor profesor = null;
		for (Profesor profesorLista: this.listProfesor) {
			if (profesorLista.getNombres().equals(nombres)) {
				profesor = profesorLista;
				break;
			}
		}
		return profesor;
	}
	
	public void eventManager() {
		tblViewPracticante.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> {
				if (newValue != null) {
					practicante = newValue;
					txtNombre.setText(newValue.getNombres());
					txtApellido.setText(newValue.getApellidos());
					txtEmail.setText(newValue.getEmail());
					txtMatricula.setText(newValue.getMatricula());
					practicante = tblViewPracticante.getSelectionModel().getSelectedItem();
					try {
						Profesor profesor = newValue.recuperarProfesor();
						cmbProfesor.setValue(profesor.getNombres());
						newValue.setProfesor(profesor);
						txtContrasenia.setText("");
						btnRegistrar.setDisable(true);
						btnEliminar.setDisable(false);
						btnActualizar.setDisable(false);
						
					} catch (SQLException throwable) {
						mostrarMensajeErrorBD();
					}
					
					MainController.save("student", practicante);
					enableEdit();
				} else {
					enableRegister();
				}
			}
		);
	}
	
	public void enableEdit() {
		this.btnEliminar.setDisable(false);
		this.btnActualizar.setDisable(false);
	}
	
	public void enableRegister() {
		this.limpiarCampos();
		this.btnRegistrar.setDisable(false);
		this.btnEliminar.setDisable(true);
		this.btnActualizar.setDisable(true);
	}
	
	public void limpiarCampos() {
		this.txtNombre.setText(null);
		this.txtApellido.setText(null);
		this.txtMatricula.setText(null);
		this.txtEmail.setText(null);
		this.txtContrasenia.setText(null);
		this.cmbProfesor.getSelectionModel().clearSelection();
	}
	
	@FXML
	public void registrarPracticante() {
		Practicante practicante = new Practicante();
		if (this.validarCamposCompletos() && this.comprobarCamposValidos()) {
			this.instanceStudent(practicante);
			try {
				if (!practicante.estaRegistrado()) {
					if (practicante.registrar()) {
						this.mostrarMensajeRegistroExitoso();
						this.actualizarTabla();
						this.limpiarCampos();
					}
				} else {
					this.mostrarMensajePracticanteRegistradoPreviamente();
				}
			} catch (SQLException throwable) {
				this.mostrarMensajeErrorBD();
			}
		}
	}
	
	private void instanceStudent(Practicante practicante) {
		practicante.setNombres(txtNombre.getText());
		practicante.setApellidos(txtApellido.getText());
		practicante.setMatricula(txtMatricula.getText());
		practicante.setEmail(txtEmail.getText());
		practicante.setContrasena(txtContrasenia.getText());
		practicante.setProfesor(this.obtenerProfesorPorNombres(cmbProfesor.getValue()));
	}
	
	private void instanciarEstudianteSinContrasenia(Practicante practicante) {
		practicante.setNombres(txtNombre.getText());
		practicante.setApellidos(txtApellido.getText());
		practicante.setMatricula(txtMatricula.getText());
		practicante.setEmail(txtEmail.getText());
		practicante.setContrasenaLimpia(txtContrasenia.getText());
		practicante.setProfesor(this.obtenerProfesorPorNombres(cmbProfesor.getValue()));
	}
	
	@FXML
	private void checkProject() {
		try {
			Proyecto proyecto = practicante.getProyecto();
			if (proyecto != null) {
				MainController.save("proyecto", proyecto);
				MainController.activate("VerProyecto", "Ver Proyecto", MainController.Sizes.LARGE);
			} else {
				System.out.println("PROYECTO NULLO");
				MainController.save("practicante", this.practicante);
				MainController.activate("AsignarProyecto", "Asignar Proyecto", MainController.Sizes.MID);
			}
		} catch (SQLException throwable) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"DBError",
				"No se pudo establecer conexión con Base de Datos"
			);
		}
	}
	
	public void onClickBack() {
		MainController.activate("MenuCoordinador", "Menu", MainController.Sizes.MID);
	}
	
	@FXML
	public void eliminarPracticante() {
		if (MainController.alert(Alert.AlertType.CONFIRMATION,
			"Eliminar Practicante",
			"¿Está seguro que desea eliminar al Practicante seleccionado?")) {
			try {
				if(tblViewPracticante.getSelectionModel().getSelectedItem() != null){
					if (tblViewPracticante.getSelectionModel().getSelectedItem().eliminar()) {
						listPracticante.remove(tblViewPracticante.getSelectionModel().getSelectedIndex());
						MainController.alert(Alert.AlertType.INFORMATION,
								"Practicante eliminado",
								"Organización eliminada exitosamente");
					} else {
						MainController.alert(
								Alert.AlertType.INFORMATION,
								"DBError",
								"No se pudo establecer conexión con Base de Datos"
						);
					}
				}else{
					MainController.alert(Alert.AlertType.ERROR, "No ha seleccionado practicante", "Se" +
							"leccione un practicante por favor");
				}

			} catch (AssertionError assertionError) {
				new Logger().log(assertionError.getMessage());
			} catch (SQLException throwable) {
				Logger.staticLog(throwable, true);
			}
		}
	}
	
	@FXML
	public void actualizar() {
		Practicante practicanteAntiguo = tblViewPracticante.getSelectionModel().getSelectedItem();
		Practicante practicanteNuevo = new Practicante();
		try {
			if (this.txtContrasenia.getText().equals("")) {
				this.instanciarEstudianteSinContrasenia(practicanteNuevo);
				if (!sonPracticantesIguales(practicanteAntiguo, practicanteNuevo) &&
					this.validarCamposCompletosSinContrasena() &&
					this.comprobarCamposValidos() &&
					MainController.alert(Alert.AlertType.CONFIRMATION,
						"¿Desea modificar la informacion del practicante?",
						"")) {
					try {
						if (!practicanteNuevo.estaRegistradoActualizar(
							practicanteAntiguo.getEmail())) {
							practicanteNuevo.actualizarSinContrasenia(
								practicanteAntiguo.getEmail());
							practicanteNuevo.actualizarProfesor();
							this.mostrarMensajePracticanteActualizado();
							this.actualizarTabla();
							this.limpiarCampos();
						} else {
							this.mostrarMensajePracticanteRegistradoPreviamente();
						}
						
					} catch (SQLException throwables) {
						this.mostrarMensajeErrorBD();
					}
				}
			} else {
				this.instanceStudent(practicanteNuevo);
				if (!this.sonPracticantesIguales(practicanteAntiguo, practicanteNuevo) &&
					this.validarCamposCompletos() &&
					this.comprobarCamposValidos() &&
					MainController.alert(Alert.AlertType.CONFIRMATION,
						"¿Desea modificar la informacion del practicante?",
						"")) {
					try {
						if (!practicanteNuevo.estaRegistradoActualizar(
							practicanteAntiguo.getEmail())) {
							practicanteNuevo.actualizarConContrasenia(
								practicanteAntiguo.getEmail());
							
							practicanteNuevo.actualizarProfesor();
							this.mostrarMensajePracticanteActualizado();
							this.actualizarTabla();
							this.limpiarCampos();
						} else {
							this.mostrarMensajePracticanteRegistradoPreviamente();
						}
					} catch (SQLException throwable) {
						this.mostrarMensajeErrorBD();
					}
				}
			}
		} catch (Exception exception) {
			Logger.staticLog(exception, true);
		}
	}
	
	private boolean validarCamposCompletos() {
		boolean camposCompletos = !this.txtApellido.getText().isEmpty() &&
			!this.txtContrasenia.getText().isEmpty() &&
			!this.txtEmail.getText().isEmpty() &&
			!this.txtMatricula.getText().isEmpty() &&
			!this.txtNombre.getText().isEmpty() &&
			this.cmbProfesor.getValue() != null;
		
		if (!camposCompletos) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"ERROR",
				"Campos vacíos. Por favor, ingrese toda la información"
			);
		}
		return camposCompletos;
	}
	
	private boolean validarCamposCompletosSinContrasena() {
		boolean camposCompletos = !this.txtApellido.getText().isEmpty() &&
			!this.txtEmail.getText().isEmpty() &&
			!this.txtMatricula.getText().isEmpty() &&
			!this.txtNombre.getText().isEmpty() &&
			this.cmbProfesor.getValue() != null;
		
		if (!camposCompletos) {
			MainController.alert(
				Alert.AlertType.INFORMATION,
				"ERROR",
				"Campos vacíos. Por favor, ingrese toda la información"
			);
		}
		return camposCompletos;
	}
	
	private boolean comprobarCamposValidos() {
		boolean camposValidos = true;
		boolean dialogoMostrado = false;
		if (!this.emailValido(this.txtEmail.getText())) {
			camposValidos = false;
			dialogoMostrado = true;
		}
		if (!Usuario.esNombre(this.txtNombre.getText())) {
			camposValidos = false;
			if (!dialogoMostrado) {
				mostrarMensajeInformacionIncorrecta();
				dialogoMostrado = true;
			}
			this.txtNombre.setUnFocusColor(Paint.valueOf("red"));
		}
		if (!Usuario.esNombre(this.txtApellido.getText())) {
			camposValidos = false;
			if (!dialogoMostrado) {
				mostrarMensajeInformacionIncorrecta();
				dialogoMostrado = true;
			}
			this.txtApellido.setUnFocusColor(Paint.valueOf("red"));
		}
		if (!this.esMatriculaValida(this.txtMatricula.getText())) {
			camposValidos = false;
			if (!dialogoMostrado) {
				mostrarMensajeInformacionIncorrecta();
				
			}
			this.txtMatricula.setUnFocusColor(Paint.valueOf("red"));
		}
		return camposValidos;
	}
	
	private void mostrarMensajeInformacionIncorrecta() {
		MainController.alert(
			Alert.AlertType.INFORMATION,
			"InformacionIncorrecta",
			"Llene los campos correctamente"
		);
	}
	
	private void mostrarMensajeErrorBD() {
		MainController.alert(
			Alert.AlertType.INFORMATION,
			"ErrorBD",
			"No se pudo establecer conexión con la base de datos"
		);
	}
	
	private void mostrarMensajeRegistroExitoso() {
		MainController.alert(
			Alert.AlertType.INFORMATION,
			"Registro exitoso",
			"Practicante registrado exitosamente"
		);
	}
	
	private void mostrarMensajePracticanteRegistradoPreviamente() {
		MainController.alert(
			Alert.AlertType.INFORMATION,
			"Practicante registrado previamente",
			""
		);
	}
	
	private void mostrarMensajePracticanteActualizado() {
		MainController.alert(
			Alert.AlertType.INFORMATION,
			"Practicante actualizado exitosamente",
			""
		);
	}
	
	private void actualizarTabla() {
		listPracticante = FXCollections.observableArrayList();
		try {
			new Practicante().llenarTablaPracticantes(listPracticante);
			tblViewPracticante.setItems(listPracticante);
		} catch (SQLException throwable) {
			mostrarMensajeErrorBD();
		}
	}
	
	private boolean sonPracticantesIguales(Practicante practicanteAntiguo,
	                                       Practicante practicanteNuevo) {
		return practicanteAntiguo.getEmail().equals(practicanteNuevo.getEmail())
			&& practicanteAntiguo.getNombres().equals(practicanteNuevo.getNombres())
			&& practicanteAntiguo.getApellidos().equals(practicanteNuevo.getApellidos())
			&& practicanteAntiguo.getMatricula().equals(practicanteNuevo.getMatricula())
			&& practicanteAntiguo.getProfesor().getNoPersonal().equals(
			practicanteNuevo.getProfesor().getNoPersonal());
	}
	
	private boolean esMatriculaValida(String matricula) {
		boolean matriculaValida = true;
		if (matricula.length() == 9) {
			if (matricula.charAt(0) != 'S' && matricula.charAt(0) != 's') {
				matriculaValida = false;
			}
		} else {
			matriculaValida = false;
		}
		
		if (matriculaValida) {
			matriculaValida = sonNumeros(matricula);
		}
		
		return matriculaValida;
	}
	
	private boolean sonNumeros(String matricula) {
		boolean sonNumeros = true;
		int index = 1;
		while (index < matricula.length()) {
			if (!Character.isDigit(matricula.charAt(index))) {
				sonNumeros = false;
				break;
			}
			index++;
			
		}
		
		return sonNumeros;
		
	}
	
	public void onChangeTxtNombre(InputMethodEvent inputMethodEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onChangeTxtApellido(InputMethodEvent inputMethodEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onChangeTxtMatricula(InputMethodEvent inputMethodEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onChangeTxtEmail(InputMethodEvent inputMethodEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onChangeTxtContrasenia(InputMethodEvent inputMethodEvent) {
		reiniciarColorTxtbox();
	}
	
	private void reiniciarColorTxtbox() {
		this.txtMatricula.setUnFocusColor(Paint.valueOf("blue"));
		this.txtContrasenia.setUnFocusColor(Paint.valueOf("blue"));
		this.txtEmail.setUnFocusColor(Paint.valueOf("blue"));
		this.txtApellido.setUnFocusColor(Paint.valueOf("blue"));
		this.txtNombre.setUnFocusColor(Paint.valueOf("blue"));
	}
	
	public void onClicTxtNombre(MouseEvent mouseEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onClicTxtApellido(MouseEvent mouseEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onClicTxtMatricula(MouseEvent mouseEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onClicTxtEmail(MouseEvent mouseEvent) {
		reiniciarColorTxtbox();
	}
	
	public void onClicTxtContrasenia(MouseEvent mouseEvent) {
		reiniciarColorTxtbox();
	}
	
	private boolean emailValido(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		boolean emailValido = validator.isValid(email);
		if (!emailValido) {
			this.mostrarMensajeInformacionIncorrecta();
			this.txtEmail.setUnFocusColor(Paint.valueOf("red"));
		}
		return emailValido;
	}
	
	private void limitarTextFields() {
		LimitadorTextfield.soloTexto(this.txtNombre);
		LimitadorTextfield.soloTexto(this.txtApellido);
		LimitadorTextfield.soloTexto(this.txtMatricula);
	}
}
