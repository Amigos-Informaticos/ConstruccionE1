package View.profesor.controller;

import Models.Documento;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ExpedienteController implements Initializable {
    @FXML private TableView<Documento> tblViewDocuments;
    @FXML private TableColumn<Documento, String> clmnName;
    @FXML private TableColumn<Documento, String> clmnRuta;
    @FXML private TableColumn<Documento, String> clmnTipo;
    @FXML private JFXButton btnCalificar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
