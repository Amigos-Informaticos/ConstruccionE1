package View.coordinador.controller;

import Exceptions.CustomException;
import Models.Practicante;
import Models.Profesor;
import Models.Usuario;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import tools.Logger;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministrarPracticanteController implements Initializable {
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
    private JFXComboBox<Profesor> cmbProfesor;


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
    private ObservableList<Professor> listProfesor;
    private  ObservableList<String> profesoresRecuperados;
    private ObservableList<Profesor> listProfesor;

    private Practicante practicante;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listPracticante = FXCollections.observableArrayList();
        profesoresRecuperados = FXCollections.observableArrayList();
        try {
            new Practicante().llenarTablaPracticantes(listPracticante);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblViewPracticante.setItems(listPracticante);
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Practicante, String>("nombres"));
        clmnApellido.setCellValueFactory(new PropertyValueFactory<Practicante, String>("apellidos"));
        clmnMatricula.setCellValueFactory(new PropertyValueFactory<Practicante, String>("matricula"));

        listProfesor = FXCollections.observableArrayList();
        try{
            new Profesor().obtenerProfesores(listProfesor);
        }catch (NullPointerException e){
            MainController.alert(
                    Alert.AlertType.WARNING,
                    "No hay profesores",
                    "No hay profesores registrados para asociar a los practicantes"
            );
            MainController.activate("MainMenuCoordinator");
        }catch (Exception e){
            MainController.alert(
                    Alert.AlertType.WARNING,
                    "ErrorBD",
                    "Error al conectar con la base de datos"
            );
            MainController.activate("MainMenuCoordinator");
        }

        this.llenarListaProfesores();
        cmbProfesor.setItems(this.profesoresRecuperados);
        eventManager();


    }

    private void llenarListaProfesores(){
        for (Professor profesor: this.listProfesor) {
            this.profesoresRecuperados.add(profesor.getNombres());
        }
    }

    private  Professor obtenerProfesorPorNombres(String nombres){
        Professor profesor = null;

        for (Professor profesorLista: this.listProfesor) {
            if(profesorLista.getNombres().equals(nombres)){
                profesor = profesorLista;
                break;
            }
        }

        return  profesor;
    }



    public void eventManager() {
        tblViewPracticante.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Practicante>() {
                    @Override
                    public void changed(ObservableValue<? extends Practicante> observable, Practicante oldValue,
                                        Practicante newValue) {
                        if (newValue != null) {
                            practicante = newValue;
                            txtNombre.setText(newValue.getNombres());
                            txtApellido.setText(newValue.getApellidos());
                            txtEmail.setText(newValue.getEmail());
                            txtMatricula.setText(newValue.getMatricula());
                            practicante = tblViewPracticante.getSelectionModel().getSelectedItem();

                            try {

                                Professor profesor = new Professor();
                                profesor = newValue.recuperarProfesor();
                                cmbProfesor.setValue(profesor.getNombres());
                                newValue.setProfesor(profesor);
                                txtContrasenia.setText("");



                            } catch (SQLException throwables) {
                                mostrarMensajeErrorBD();
                            }

                            MainController.save("student", practicante);
                            enableEdit();
                        } else {
                            enableRegister();
                        }
                    }
                }
        );
    }

    public void enableEdit() {
    }

    public void enableRegister() {
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


        if(this.validarCamposCompletos() && this.comprobarCamposValidos() ){

            this.instanceStudent(practicante);
            try {
                if(!practicante.estaRegistrado()){
                    if(practicante.registrar()){
                        this.mostrarMensajeRegistroExitoso();
                        this.actualizarTabla();
                        this.limpiarCampos();

                    }
                }else{
                    this.mostrarMensajePracticanteRegistradoPreviamente();

                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

        System.out.println(practicante.getNombres());
        System.out.println(practicante.getApellidos());
        System.out.println(practicante.getMatricula());
        System.out.println(practicante.getEmail());
        System.out.println(practicante.getContrasena());
        System.out.println(practicante.getProfesor().getEmail());
    }

    private  void instanciarEstudianteSinContrasenia(Practicante practicante){

        practicante.setNombres(txtNombre.getText());

        practicante.setApellidos(txtApellido.getText());
        practicante.setMatricula(txtMatricula.getText());
        practicante.setEmail(txtEmail.getText());
        practicante.setContrasenaLimpia(txtContrasenia.getText());
        practicante.setProfesor(this.obtenerProfesorPorNombres(cmbProfesor.getValue()));

        System.out.println(practicante.getNombres());
        System.out.println(practicante.getApellidos());
        System.out.println(practicante.getMatricula());
        System.out.println(practicante.getEmail());
        System.out.println(practicante.getContrasena());
        System.out.println(practicante.getProfesor().getEmail());
    }

    @FXML
    private void checkProject() {
        try {
            if (practicante.getProyecto() == null) {
                MainController.activate("AssignProject", "Asignar Proyecto", MainController.Sizes.MID);
            } else {
                MainController.save("Project", practicante.getProyecto());
                MainController.activate("ViewProject", "Ver Proyecto");
            }
        } catch (CustomException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            } catch (AssertionError e) {
                new Logger().log(e.getMessage());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @FXML
    public void actualizar(){
        Practicante practicanteAntiguo =  tblViewPracticante.getSelectionModel().getSelectedItem();

        Practicante practicanteNuevo =  new Practicante();

        try {
            if (this.txtContrasenia.getText().equals("")) {
                this.instanciarEstudianteSinContrasenia(practicanteNuevo);
                if (this.validarCamposCompletosSinContrasena() && this.comprobarCamposValidos()) {

                    if(!sonPracticantesIguales(practicanteAntiguo, practicanteNuevo)){
                        try {
                            if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Desea modificar la informacion del practicante?", "")) {

                                if (!practicanteNuevo.estaRegistradoActualizar(practicanteAntiguo.getEmail())) {
                                    practicanteNuevo.actualizarSinContrasenia(practicanteAntiguo.getEmail());
                                    practicanteNuevo.actualizarProfesor();
                                    this.mostrarMensajePracticanteActualizado();
                                    this.actualizarTabla();
                                    this.limpiarCampos();

                                } else {
                                    this.mostrarMensajePracticanteRegistradoPreviamente();
                                }
                            }

                        } catch (SQLException throwables) {
                            this.mostrarMensajeErrorBD();
                        }

                    }



                }

            } else {
                this.instanceStudent(practicanteNuevo);
                if (this.validarCamposCompletos() && this.comprobarCamposValidos()) {

                    if(!this.sonPracticantesIguales(practicanteAntiguo,practicanteNuevo)){
                        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Desea modificar la informacion del practicante?", "")) {
                            try {
                                if (!practicanteNuevo.estaRegistradoActualizar(practicanteAntiguo.getEmail())) {

                                    practicanteNuevo.actualizarConContrasenia(practicanteAntiguo.getEmail());
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

                    }


                }

            }
        }catch (Exception e){

        }
    }

    private boolean validarCamposCompletos(){
        boolean camposCompletos = false;

        if(
                !this.txtApellido.getText().isEmpty() &&
                !this.txtContrasenia.getText().isEmpty() &&
                !this.txtEmail.getText().isEmpty() &&
                !this.txtMatricula.getText().isEmpty() &&
                !this.txtNombre.getText().isEmpty() &&
                this.cmbProfesor.getValue() != null
        ){
            camposCompletos = true;
        }

        if(!camposCompletos){
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "ERROR",
                    "Campos vacíos. Por favor, ingrese toda la información"
            );
        }

        return  camposCompletos;
    }

    private  boolean validarCamposCompletosSinContrasena(){
        boolean camposCompletos = false;

        if(
                !this.txtApellido.getText().isEmpty() &&
                !this.txtEmail.getText().isEmpty() &&
                !this.txtMatricula.getText().isEmpty() &&
                !this.txtNombre.getText().isEmpty() &&
                this.cmbProfesor.getValue() != null

        ){
            camposCompletos = true;
        }

        if(!camposCompletos){
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "ERROR",
                    "Campos vacíos. Por favor, ingrese toda la información"
            );
        }

        return  camposCompletos;
    }




    private  boolean comprobarCamposValidos() {
        boolean camposValidos = true;

        boolean dialogoMostrado = false;

        if (!Usuario.esEmail(this.txtEmail.getText())) {
            camposValidos = false;


            if(!dialogoMostrado){
                mostrarMensajeInformacionIncorrecta();
                dialogoMostrado = true;
            }

            this.txtEmail.setUnFocusColor(Paint.valueOf("red"));
        }

        if(!Usuario.esNombre(this.txtNombre.getText())){
            camposValidos = false;

            if(!dialogoMostrado){
                mostrarMensajeInformacionIncorrecta();
                dialogoMostrado = true;
            }
            this.txtNombre.setUnFocusColor(Paint.valueOf("red"));
        }

        if(!Usuario.esNombre(this.txtApellido.getText())){
            camposValidos = false;
            if(!dialogoMostrado){
                mostrarMensajeInformacionIncorrecta();
                dialogoMostrado = true;
            }
            this.txtApellido.setUnFocusColor(Paint.valueOf("red"));
        }


        return  camposValidos;

    }


    private void mostrarMensajeInformacionIncorrecta(){
        MainController.alert(
                Alert.AlertType.INFORMATION,
                "InformacionIncorrecta",
                "Llene los campos correctamente"
        );
    }


    private  void mostrarMensajeErrorBD(){
        MainController.alert(
                Alert.AlertType.INFORMATION,
                "ErrorBD",
                "No se pudo establecer conexión con la base de datos"
        );
    }

    private  void mostrarMensajeRegistroExitoso(){
        MainController.alert(
                Alert.AlertType.INFORMATION,
                "Registro exitoso",
                "Practicante registrado exitosamente"
        );
    }

    private void mostrarMensajePracticanteRegistradoPreviamente(){
        MainController.alert(
                Alert.AlertType.INFORMATION,
                "Practicante registrado previamente",
                ""
        );
    }
    private  void mostrarMensajePracticanteActualizado(){
        MainController.alert(
                Alert.AlertType.INFORMATION,
                "Practicante actualizado exitosamente",
                ""
        );
    }


    private  void actualizarTabla(){
        listPracticante = FXCollections.observableArrayList();
        try {
            new Practicante().llenarTablaPracticantes(listPracticante);
            tblViewPracticante.setItems(listPracticante);
        } catch (SQLException throwables) {
            mostrarMensajeErrorBD();
        }
    }
    private boolean sonPracticantesIguales(Practicante practicanteAntiguo, Practicante practicanteNuevo){
        boolean sonIguales = false ;

        if(
                practicanteAntiguo.getEmail().equals(practicanteNuevo.getEmail())
                && practicanteAntiguo.getNombres().equals(practicanteNuevo.getNombres())
                && practicanteAntiguo.getApellidos().equals(practicanteNuevo.getApellidos())
                && practicanteAntiguo.getMatricula().equals(practicanteNuevo.getMatricula())
                && practicanteAntiguo.getProfesor().getPersonalNo().equals(practicanteNuevo.getProfesor().getPersonalNo())

        ){
            sonIguales = true;
        }


        return sonIguales;
    }




}
