package View.Admin;

import DAO.DAOShift;
import Exceptions.CustomException;
import Models.Admin;
import Models.Professor;
import Models.Shift;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tools.Logger;

import javax.swing.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.ResourceBundle;

public class RegistProfessorController implements Initializable {
    @FXML private TableView<Professor> tblViewProfessor;
    @FXML private TableColumn<Professor, String> clmnEmail;
    @FXML private TableColumn<Professor, String> clmnNames;
    @FXML private TableColumn<Professor, String> clmnLastNames;
    @FXML private TableColumn<Professor, String> clmnPersonalNo;
    @FXML private TableColumn<Professor, String> clmnShift;
    //Components
    @FXML private JFXTextField txtEmail;
    @FXML private JFXPasswordField pwdPassword;
    @FXML private JFXTextField txtNames;
    @FXML private JFXTextField txtLastNames;
    @FXML private JFXTextField txtNoPersonal;
    @FXML private JFXComboBox<String> cmbShift;
    @FXML private JFXButton btnDelete;
    @FXML private JFXButton btnRegister;
    @FXML private JFXButton btnUpdate;

    //Collections
    ObservableList<String> listShift;
    ObservableList<Professor> listProfessor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listShift = FXCollections.observableArrayList();
        listProfessor = FXCollections.observableArrayList();
        new Shift().fillShift(listShift);
        new Admin().fillTableProfessor(listProfessor);
        cmbShift.setItems(listShift);
        tblViewProfessor.setItems(listProfessor);
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Professor,String>("email"));
        clmnNames.setCellValueFactory(new PropertyValueFactory<Professor,String>("names"));
        clmnLastNames.setCellValueFactory(new PropertyValueFactory<Professor,String>("lastnames"));
        clmnPersonalNo.setCellValueFactory(new PropertyValueFactory<Professor,String>("personalNo"));
        clmnShift.setCellValueFactory(new PropertyValueFactory<Professor,String>("shift"));
        eventManager();
    }

    @FXML
    public void signUp(){
        Professor professor = new Professor();
        professor.setEmail(txtEmail.getText());
        professor.setNames(txtNames.getText());
        professor.setLastnames(txtLastNames.getText());
        professor.setPersonalNo(txtNoPersonal.getText());
        switch (cmbShift.getValue()){
            case "Matutino":
                professor.setShift("1");
                break;
            case "Vespertino":
                professor.setShift("2");
                break;
            case "Mixto":
                professor.setShift("3");
                break;
        }
        professor.setPassword(pwdPassword.getText());

        try {
            if(professor.signUp()){
                listProfessor.add(professor);
                Alert message2 = new Alert(Alert.AlertType.INFORMATION);
                message2.setTitle("Registro agregado exitosamente");
                message2.setContentText("Felicidades!");
                message2.setHeaderText("Resultado:");
            }
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }

    @FXML
    public void update(){
        Professor professor = new Professor();
        this.instanceProfessor(professor);
        try {
            if(professor.update()){
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("Registro agregado exitosamente");
                message.setContentText("Felicidades!");
                message.setHeaderText("Resultado:");
            } else {
                Alert message2 = new Alert(Alert.AlertType.ERROR);
                message2 = new Alert(Alert.AlertType.ERROR);
                message2.setTitle("Error a registrar el profesor");
            }
        } catch (CustomException e) {
            new Logger().log(e);
        }
    }
    @FXML
    public void delete(){
        Professor professor = new Professor();
        this.instanceProfessor(professor);
        try{
            if(professor.delete()){
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
            } else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar al profesor");
            }
        }catch(CustomException e){
            new Logger().log(e);
        }
    }
    public void eventManager(){
        tblViewProfessor.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Professor>() {
                    @Override
                    public void changed(ObservableValue<? extends Professor> observable, Professor oldValue,
                                        Professor newValue) {
                        txtEmail.setText(newValue.getEmail());
                        txtNames.setText(newValue.getNames());
                        txtLastNames.setText(newValue.getLastnames());
                        txtNoPersonal.setText(newValue.getPersonalNo());
                        cmbShift.setValue(newValue.getShift());

                        btnDelete.setDisable(false);
                        btnUpdate.setDisable(false);
                        btnRegister.setDisable(true);
                    }
                }
        );
    }
    private void instanceProfessor(Professor professor){
        professor.setEmail(txtEmail.getText());
        professor.setPassword(pwdPassword.getText());
        professor.setNames(txtNames.getText());
        professor.setLastnames(txtLastNames.getText());
        professor.setPersonalNo(txtNoPersonal.getText());
        switch (cmbShift.getValue()){
            case "Matutino":
                professor.setShift("1");
                break;
            case "Vespertino":
                professor.setShift("2");
                break;
            case "Mixto":
                professor.setShift("3");
                break;
        }
    }
}
