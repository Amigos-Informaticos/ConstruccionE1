package View.Admin;

import Models.Coordinator;
import Models.Professor;
import Models.Shift;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tools.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCoordinatorController implements Initializable {
    @FXML private TableView<Coordinator> tableViewCoordinator;
    @FXML private TableColumn<Coordinator, String> clmnEmail;
    @FXML private TableColumn<Coordinator, String> clmnNames;
    @FXML private TableColumn<Coordinator, String> clmnLastNames;

    @FXML private Label lblNames;
    @FXML private Label lblLastnames;
    @FXML private Label lblEmail;
    @FXML private Label lblPersonalNo;
    @FXML private Label lblStatus;
    @FXML private Label lblRegistrationDate;
    @FXML private Label lblDischargeDate;

    @FXML private JFXButton btnDelete;
    @FXML private JFXButton btnRegister;
    @FXML private JFXButton btnUpdate;
    @FXML private JFXTextField txtEmail;
    @FXML private JFXPasswordField pwdPassword;
    @FXML private JFXTextField txtNames;
    @FXML private JFXTextField txtLastNames;
    @FXML private JFXTextField txtNoPersonal;
    @FXML private JFXComboBox<String> cmbShift;

    private ObservableList<Coordinator> listCoordinator;
    private ObservableList<String> listShift;
    private Coordinator coordinator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCoordinator = FXCollections.observableArrayList();
        listShift = FXCollections.observableArrayList();
        new Coordinator().fillTableCoordinator(listCoordinator);
        new Shift().fillShift(listShift);
        tableViewCoordinator.setItems(listCoordinator);
        cmbShift.setItems(listShift);
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Coordinator,String>("email"));
        clmnNames.setCellValueFactory(new PropertyValueFactory<Coordinator,String>("names"));
        clmnLastNames.setCellValueFactory(new PropertyValueFactory<Coordinator,String>("lastnames"));
        eventManager();

    }

    @FXML
    public void signUp(){
         coordinator = new Coordinator();
        this.instanceCoordinator(coordinator);
        try {
            if(coordinator.isComplete()){
                if(coordinator.signUp()){
                    listCoordinator.add(coordinator);
                    MainController.alert(
                            Alert.AlertType.INFORMATION,
                            "Coordinador registrado correctamente",
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
        coordinator = new Coordinator();
        this.instanceCoordinator(coordinator);
        try {
            if (coordinator.isComplete()) {
                Coordinator coordinator = tableViewCoordinator.getSelectionModel().getSelectedItem();
                instanceCoordinator(coordinator);
                if (tableViewCoordinator.getSelectionModel().getSelectedItem().update()) {
                    listCoordinator.set(tableViewCoordinator.getSelectionModel().getSelectedIndex(), coordinator);
                } else {
                    MainController.alert(
                            Alert.AlertType.WARNING,
                            "No se pudo actualizar al coordinador",
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
        } catch(AssertionError e){
            new Logger().log(e.getMessage());
        }
    }

    @FXML
    public void delete(){
        try{
            if(tableViewCoordinator.getSelectionModel().getSelectedItem().delete()){
                listCoordinator.remove(tableViewCoordinator.getSelectionModel().getSelectedIndex());
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


    public void eventManager(){
        tableViewCoordinator.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Coordinator>() {
                    @Override
                    public void changed(ObservableValue<? extends Coordinator> observable, Coordinator oldValue,
                                        Coordinator newValue) {
                        if(newValue != null){
                            coordinator = newValue;
                            txtEmail.setText(newValue.getEmail());
                            txtNames.setText(newValue.getNames());
                            txtLastNames.setText(newValue.getLastnames());
                            txtNoPersonal.setText(newValue.getPersonalNo());
                            fillDetailsCoordinator(newValue);
                            enableEdit();
                        } else {
                            coordinator = null;
                            cleanFormCoordinator();
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
    private void instanceCoordinator(Coordinator coordinator){
        coordinator.setEmail(txtEmail.getText());
        coordinator.setPassword(pwdPassword.getText());
        coordinator.setNames(txtNames.getText());
        coordinator.setLastnames(txtLastNames.getText());
        coordinator.setPersonalNo(txtNoPersonal.getText());
    }
    private void cleanFormCoordinator(){
        txtEmail.setText(null);
        txtNames.setText(null);
        txtLastNames.setText(null);
        txtNoPersonal.setText(null);
    }
    private void fillDetailsCoordinator(Coordinator coordinator) {
        lblNames.setText(coordinator.getNames());
        lblLastnames.setText(coordinator.getLastnames());
        lblEmail.setText(coordinator.getEmail());
        lblPersonalNo.setText(coordinator.getPersonalNo());
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
