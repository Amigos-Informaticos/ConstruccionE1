package View.profesor.controller;

import Models.Documento;
import Models.Practicante;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sun.applet.Main;
import tools.Logger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExpedienteController implements Initializable {
    @FXML private TableView<Documento> tblViewDocuments;
    @FXML private TableColumn<Documento, String> clmnName;
    @FXML private TableColumn<Documento, String> clmnRuta;
    @FXML private TableColumn<Documento, String> clmnTipo;
    @FXML private JFXButton btnCalificar;
    public JFXButton btnRegresar;
    private Documento documento;
    private ObservableList<Documento> listaDocumentos;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCalificar.setDisable(true);
        listaDocumentos = FXCollections.observableArrayList();
        Practicante practicante;
        try {
            practicante = (Practicante) MainController.get("practicante");
            practicante.llenarTablaDocumentos(listaDocumentos);
            tblViewDocuments.setItems(listaDocumentos);
            clmnName.setCellValueFactory(new PropertyValueFactory<Documento, String>("nombre"));
            clmnTipo.setCellValueFactory(new PropertyValueFactory<Documento, String>("type"));
        }catch (NullPointerException nullPointerException){
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "No hay profesores registrados",
                    "Pulse aceptar para continuar"
            );
        } catch (SQLException sqlException) {
            Logger.staticLog(sqlException, true);
        }
        eventManager();
    }



    public void eventManager(){
        tblViewDocuments.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Documento>() {
                    @Override
                    public void changed(ObservableValue<? extends Documento> observable, Documento oldValue,
                                        Documento newValue) {
                        if(newValue != null) {
                            documento = newValue;
                            btnCalificar.setDisable(false);
                            System.out.println(documento);
                        } else {
                            documento = null;
                            btnCalificar.setDisable(true);
                        }
                    }
                }
        );
    }

    public void onClicRegresar(ActionEvent actionEvent) {
        MainController.activate("ListaPracticantes", "Lista de Practicantes", MainController.Sizes.MID);
    }

    public void onClicCalificar(ActionEvent actionEvent) {
        MainController.save("documento", documento);
        MainController.activate("EvaluarReporte", "Calificar Reporte", MainController.Sizes.LARGE);
    }
}
