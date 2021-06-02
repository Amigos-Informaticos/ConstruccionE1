package View.profesor.controller;

import Models.Practicante;
import Models.Profesor;
import View.MainController;
import com.jfoenix.controls.JFXButton;
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
import sun.applet.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ListaPracticantesController implements Initializable {
    @FXML private TableView<Practicante> tblViewPracticantes;
    @FXML private TableColumn<Practicante, String> clmnEmail;
    @FXML private TableColumn<Practicante, String> clmnNombres;
    @FXML private TableColumn<Practicante, String> clmnApellidos;
    @FXML private TableColumn<Practicante, String> clmnMatricula;

    @FXML private JFXButton btnSeleccionar;
    @FXML private JFXButton btnCancelar;

    private Profesor profesor;
    private Practicante practicante;
    private ObservableList<Practicante> listaPracticantes;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        btnSeleccionar.setDisable(true);
        listaPracticantes = FXCollections.observableArrayList();
        profesor = (Profesor) MainController.get("user");
        try {
            new Practicante().llenarTablaPracticantes(listaPracticantes, profesor.getEmail());
            tblViewPracticantes.setItems(listaPracticantes);
        }catch (NullPointerException nullPointerException){
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "No hay profesores registrados",
                    "Pulse aceptar para continuar"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        clmnEmail.setCellValueFactory(new PropertyValueFactory<Practicante, String>("email"));
        clmnNombres.setCellValueFactory(new PropertyValueFactory<Practicante, String>("nombres"));
        clmnApellidos.setCellValueFactory(new PropertyValueFactory<Practicante, String>("apellidos"));
        clmnMatricula.setCellValueFactory(new PropertyValueFactory<Practicante, String>("matricula"));
        eventManager();
    }

    public void eventManager(){
        tblViewPracticantes.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Practicante>() {
                    @Override
                    public void changed(ObservableValue<? extends Practicante> observable, Practicante oldValue,
                                        Practicante newValue) {
                        if(newValue != null) {
                            practicante = newValue;
                            btnSeleccionar.setDisable(false);
                        } else {
                            practicante = null;
                            btnSeleccionar.setDisable(true);
                        }
                    }
                }
        );
    }
    @FXML
    public void onClicSeleccionPracticante(){
        Practicante practicante = tblViewPracticantes.getSelectionModel().getSelectedItem();
        MainController.save("practicante", practicante);
        MainController.activate("Expediente", "Expediente", MainController.Sizes.MID);
        System.out.println(((Practicante)MainController.get("practicante")).toString());
    }

    @FXML
    public void onClicCancelar(){
        MainController.activate("MenuProfesor", "Menu profesor", MainController.Sizes.MID);
    }

}
