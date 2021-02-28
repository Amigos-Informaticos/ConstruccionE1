package View.administrador.controller;

import IDAO.Turno;
import Models.Professor;
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
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfessorController implements Initializable {
    @FXML private TableView<Professor> tblViewProfessor;
    @FXML private TableColumn<Professor, String> clmnEmail;
    @FXML private TableColumn<Professor, String> clmnNames;
    @FXML private TableColumn<Professor, String> clmnLastNames;
    @FXML private TableColumn<Professor, String> clmnPersonalNo;
    @FXML private TableColumn<Professor, String> clmnShift;

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

    private Professor professor;
    private ObservableList<String> listShift;
    private ObservableList<Professor> listProfessor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listShift = FXCollections.observableArrayList();
        listProfessor = FXCollections.observableArrayList();
        Turno.fillShift(listShift);
        new Professor().fillTableProfessor(listProfessor);
        cmbShift.setItems(listShift);
        tblViewProfessor.setItems(listProfessor);
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Professor, String>("email"));
        clmnNames.setCellValueFactory(new PropertyValueFactory<Professor, String>("names"));
        clmnLastNames.setCellValueFactory(new PropertyValueFactory<Professor, String>("lastnames"));
        clmnPersonalNo.setCellValueFactory(new PropertyValueFactory<Professor, String>("personalNo"));
        clmnShift.setCellValueFactory(new PropertyValueFactory<Professor, String>("shift"));
    
        txtNames.addEventFilter(KeyEvent.ANY, handleLetters);
        txtLastNames.addEventFilter(KeyEvent.ANY, handleLetters);
        eventManager();
    }

    @FXML
    public void signUp(){
        professor = new Professor();
        this.instanceProfessor(professor);
        try {
            if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea registrar?", "") && professor.estaCompleto()) {
                if (professor.signUp()) {
                    listProfessor.add(professor);
                    MainController.alert(
                        Alert.AlertType.INFORMATION,
                        "Profesor registrado correctamente",
                        "Pulse aceptar para continuar"
                    );
                } else {
                    MainController.alert(
                        Alert.AlertType.WARNING,
                            "Error al conectar con la base de datos",
                            "Pulse aceptar para continuar"
                    );
                }
            } else {
                    MainController.alert(
                            Alert.AlertType.INFORMATION,
                            "LLene todos los campos correctamente",
                            "Pulse aceptar para continuar"
                    );
            }
        } catch (AssertionError e) {
            new Logger().log(e.getMessage());
        }
    }

    @FXML
    public void update(){
        professor = new Professor();
        this.instanceProfessor(professor);
        try {
            if (professor.estaCompleto()) {
                if (professor.update()) {
                    MainController.alert(
                        Alert.AlertType.INFORMATION,
                        "Profesor modificado satisfactoriamente",
                        "Pulse aceptar para continuar"
                    );
                    listProfessor.set(tblViewProfessor.getSelectionModel().getSelectedIndex(), professor);
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
        }
    }
    @FXML
    public void delete(){
        if(MainController.alert(Alert.AlertType.CONFIRMATION,"¿Está seguro que desea eliminar?","")){
            try{
                if(tblViewProfessor.getSelectionModel().getSelectedItem().delete()){
                    listProfessor.remove(tblViewProfessor.getSelectionModel().getSelectedIndex());
                } else{
                    MainController.alert(
                            Alert.AlertType.INFORMATION,
                            "No se pudo eliminar al profesor",
                            "Pulse aceptar para continuar"
                    );
                }
            }catch(AssertionError e){
                new Logger().log(e.getMessage());
            }
        }
    }
    public void eventManager(){
        tblViewProfessor.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Professor>() {
                    @Override
                    public void changed(ObservableValue<? extends Professor> observable, Professor oldValue,
                                        Professor newValue) {
                        if(newValue != null) {
                            professor = newValue;
                            txtEmail.setText(newValue.getEmail());
                            txtNames.setText(newValue.getNombres());
                            txtLastNames.setText(newValue.getApellidos());
                            txtNoPersonal.setText(newValue.getPersonalNo());
                            cmbShift.setValue(newValue.getShift());
                            enableEdit();
                        } else {
                            professor = null;
                            cleanFormProfessor();
                            enableRegister();
                        }
                    }
                }
        );
    }
    @FXML
    public void onBackArrowClicked(MouseEvent event){
        MainController.activate("MainMenuAdmin", "Menú Administrador", MainController.Sizes.MID);
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
    private void instanceProfessor(Professor professor) {
        professor.setEmail(txtEmail.getText());
        professor.setContrasena(pwdPassword.getText());
        professor.setNombres(txtNames.getText());
        professor.setApellidos(txtLastNames.getText());
        professor.setPersonalNo(txtNoPersonal.getText());
        professor.setShift(cmbShift.getValue());
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
}
