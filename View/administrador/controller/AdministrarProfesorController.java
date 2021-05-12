package View.administrador.controller;

import IDAO.Turno;
import Models.Profesor;
import Models.Usuario;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import tools.LimitadorTextfield;
import tools.Logger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministrarProfesorController implements Initializable {
    @FXML private TableView<Profesor> tblViewProfessor;
    @FXML private TableColumn<Profesor, String> clmnEmail;
    @FXML private TableColumn<Profesor, String> clmnNames;
    @FXML private TableColumn<Profesor, String> clmnLastNames;
    @FXML private TableColumn<Profesor, String> clmnPersonalNo;
    @FXML private TableColumn<Profesor, String> clmnShift;

    @FXML private JFXTextField txtEmail;
    @FXML private JFXPasswordField pwdPassword;
    @FXML private JFXTextField txtNames;
    @FXML private JFXTextField txtLastNames;
    @FXML private JFXTextField txtNoPersonal;
    @FXML private JFXComboBox<String> cmbShift;
    @FXML private JFXButton btnDelete;
    @FXML private JFXButton btnRegister;
    @FXML private JFXButton btnUpdate;
    @FXML private ImageView backArrow;

    private Profesor profesor;
    private ObservableList<String> listaTurnos;
    private ObservableList<Profesor> listaProfesores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaTurnos = FXCollections.observableArrayList();
        try {
            Turno.llenarTurno(listaTurnos);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        listaProfesores = FXCollections.observableArrayList();

        try {
            new Profesor().obtenerProfesores(listaProfesores);
            tblViewProfessor.setItems(listaProfesores);
        }catch (NullPointerException nullPointerException){
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "No hay profesores registrados",
                    "Pulse aceptar para continuar"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmbShift.setItems(listaTurnos);
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Profesor, String>("email"));
        clmnNames.setCellValueFactory(new PropertyValueFactory<Profesor, String>("nombres"));
        clmnLastNames.setCellValueFactory(new PropertyValueFactory<Profesor, String>("apellidos"));
        clmnPersonalNo.setCellValueFactory(new PropertyValueFactory<Profesor, String>("noPersonal"));
        clmnShift.setCellValueFactory(new PropertyValueFactory<Profesor, String>("turno"));
    
        txtNames.addEventFilter(KeyEvent.ANY, handleLetters);
        txtLastNames.addEventFilter(KeyEvent.ANY, handleLetters);
        LimitadorTextfield.soloNumeros(txtNoPersonal);
        LimitadorTextfield.limitarTamanio(txtNames, 50);
        LimitadorTextfield.limitarTamanio(txtLastNames, 60);
        LimitadorTextfield.limitarTamanio(pwdPassword, 32);
        LimitadorTextfield.limitarTamanio(txtNoPersonal, 13);
        eventManager();
    }

    @FXML
    public void registrar(){
        profesor = new Profesor();
        this.instanceProfessor(profesor);
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro que deseas agregar?",
                "Pulse aceptar para continuar")) {
            if (profesor.estaCompleto()) {
                try {
                    if (!profesor.estaRegistrado()) {
                        if (profesor.registrar()) {
                            MainController.alert(
                                    Alert.AlertType.INFORMATION,
                                    "Profesor registrado correctamente",
                                    "Pulse aceptar para continuar"
                            );
                            listaProfesores.add(profesor);
                        } else {
                            MainController.alert(
                                    Alert.AlertType.WARNING,
                                    "Ocurrio un error al conectarse a la base de datos",
                                    "Pulse aceptar para continuar"
                            );
                        }
                    } else {
                        MainController.alert(Alert.AlertType.WARNING,
                                "La cuenta ya se encuentra registrada",
                                "Pulse aceptar para continuar"
                        );
                    }
                } catch (SQLException sqlException) {
                    MainController.alert(
                            Alert.AlertType.ERROR,
                            "Ocurrio un error al conectarse a la base de datos",
                            "Pulse aceptar para continuar"
                    );
                    Logger.staticLog(sqlException, true);
                }
            } else {
                MainController.alert(
                        Alert.AlertType.WARNING,
                        "Campos incorrectos o incompletos",
                        "Pulse aceptar para continuar"
                );
                mostrarCamposErroneos();
            }
        }
    }

    @FXML
    public void update(){
        profesor = new Profesor();
        this.instanceProfessor(profesor);
        try {
            if (profesor.estaCompleto()) {
                if (profesor.update()) {
                    MainController.alert(
                        Alert.AlertType.INFORMATION,
                        "Profesor modificado satisfactoriamente",
                        "Pulse aceptar para continuar"
                    );
                    listaProfesores.set(tblViewProfessor.getSelectionModel().getSelectedIndex(), profesor);
                } else {
                    MainController.alert(
                        Alert.AlertType.ERROR,
                            "No se pudo actualizar al profesor",
                            "Pulse aceptar para continuar"
                    );
                }
            } else {
                MainController.alert(
                        Alert.AlertType.WARNING,
                        "LLene todos los campos correctamente",
                        "Pulse aceptar para continuar"
                );
            }
        } catch(AssertionError e){
            new Logger().log(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    public void eliminar(){
        if(MainController.alert(Alert.AlertType.CONFIRMATION,"¿Está seguro que desea eliminar?","")){
            try{
                if(tblViewProfessor.getSelectionModel().getSelectedItem().eliminar()){
                    listaProfesores.remove(tblViewProfessor.getSelectionModel().getSelectedIndex());
                } else{
                    MainController.alert(
                            Alert.AlertType.INFORMATION,
                            "No se pudo eliminar al profesor",
                            "Pulse aceptar para continuar"
                    );
                }
            }catch(AssertionError e){
                new Logger().log(e.getMessage());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void eventManager(){
        tblViewProfessor.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Profesor>() {
                    @Override
                    public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue,
                                        Profesor newValue) {
                        if(newValue != null) {
                            profesor = newValue;
                            txtEmail.setText(newValue.getEmail());
                            pwdPassword.setText(newValue.getContrasena());
                            txtNames.setText(newValue.getNombres());
                            txtLastNames.setText(newValue.getApellidos());
                            txtNoPersonal.setText(newValue.getNoPersonal());
                            cmbShift.setValue(newValue.getTurno());
                            enableEdit();
                        } else {
                            profesor = null;
                            cleanFormProfessor();
                            enableRegister();
                        }
                    }
                }
        );
    }
    @FXML
    public void onBackArrowClicked(MouseEvent event){
        MainController.activate("MenuAdministrador", "Menú Administrador", MainController.Sizes.MID);
    }

    EventHandler<KeyEvent> handleLetters = new EventHandler<KeyEvent>() {
        private boolean willConsume;
        @Override
        public void handle(KeyEvent event) {
            Object tempO = event.getSource();
            if(willConsume){
                event.consume();
            }
            String temp = event.getCode().toString();
            if(!event.getCode().toString().matches("[a-zA-Z]")
                    && event.getCode() != KeyCode.BACK_SPACE
                    && event.getCode() != KeyCode.SPACE
                    && event.getCode() != KeyCode.SHIFT){
                if(event.getEventType() == KeyEvent.KEY_PRESSED){
                    willConsume = true;
                }else if(event.getEventType() == KeyEvent.KEY_RELEASED){
                    willConsume = false;
                }
            }
        }
    };
    private void instanceProfessor(Profesor profesor) {
        profesor.setEmail(txtEmail.getText());
        profesor.setContrasena(pwdPassword.getText());
        profesor.setNombres(txtNames.getText());
        profesor.setApellidos(txtLastNames.getText());
        profesor.setNumeroPersonal(txtNoPersonal.getText());
        profesor.setTurno(cmbShift.getValue());
    }
    private void cleanFormProfessor(){
        txtEmail.setText(null);
        txtNames.setText(null);
        txtLastNames.setText(null);
        txtNoPersonal.setText(null);
    }
    private void enableRegister(){
        txtEmail.setDisable(false);
        pwdPassword.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnRegister.setDisable(false);
    }
    private void enableEdit(){
        txtEmail.setDisable(true);
        pwdPassword.setDisable(true);

        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        btnRegister.setDisable(true);
    }

    private void mostrarCamposErroneos() {
        if(!Usuario.esEmail(txtEmail.getText())){
            txtEmail.setUnFocusColor(Paint.valueOf("red"));
        } else {
            txtEmail.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!Usuario.esNombre(txtNames.getText())){
            txtNames.setUnFocusColor(Paint.valueOf("red"));
        } else {
            txtNames.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!Usuario.esNombre(txtLastNames.getText())){
            txtLastNames.setUnFocusColor(Paint.valueOf("red"));
        } else {
            txtLastNames.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!Usuario.esContrasena(pwdPassword.getText())){
            pwdPassword.setUnFocusColor(Paint.valueOf("red"));
        } else {
            pwdPassword.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!Profesor.esNoPersonal(txtNoPersonal.getText())){
            txtNoPersonal.setUnFocusColor(Paint.valueOf("red"));
        } else {
            txtNoPersonal.setUnFocusColor(Paint.valueOf("black"));
        }
        if(cmbShift.getValue()==null || cmbShift.getValue().equals("")){
            cmbShift.setUnFocusColor(Paint.valueOf("red"));
        } else {
            cmbShift.setUnFocusColor(Paint.valueOf("black"));
        }
    }
}
