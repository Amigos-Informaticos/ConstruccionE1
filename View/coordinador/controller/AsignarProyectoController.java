package View.coordinador.controller;

import Models.Asignacion;
import Models.Coordinador;
import Models.Practicante;
import Models.Proyecto;
import View.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class AsignarProyectoController implements Initializable {
    @FXML
    Label lbName;
    @FXML
    Label lbRegNo;
    @FXML
    Label lbEmail;

    public TableColumn<Proyecto, String> clmNameRequest;
    public TableColumn<Proyecto, String> clmName;

    @FXML
    public TableView<Proyecto> requestTable;
    @FXML
    public TableView<Proyecto> projectTable;

    private Practicante practicante = (Practicante) MainController.get("practicante");

    private Proyecto proyecto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Proyecto> proyectoObservableList = FXCollections.observableArrayList();
        try {
            Proyecto.llenarTabla(proyectoObservableList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        projectTable.setItems(proyectoObservableList);
        clmName.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nombre"));

        ObservableList<Proyecto> seleccionObservableList = FXCollections.observableArrayList();

        try {
            Proyecto[] proyectos = practicante.getSeleccion();
            if (proyectos != null) {
                Collections.addAll(seleccionObservableList, practicante.getSeleccion());
            } else {
                System.out.println("No hay proyectos asignados");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        requestTable.setItems(seleccionObservableList);
        clmNameRequest.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nombre"));

        lbName.setText(practicante.getNombres() + " " + practicante.getApellidos());
        lbRegNo.setText(practicante.getMatricula());
        lbEmail.setText(practicante.getEmail());

    }

    public void selectProject() {
        if (projectTable.isFocused()) {
            proyecto = projectTable.getSelectionModel().getSelectedItem();
        } else if (requestTable.isFocused()) {
            proyecto = requestTable.getSelectionModel().getSelectedItem();
        }
    }

    public void assign() throws FileNotFoundException {
        if (proyecto != null){
            Asignacion asignacion = null;
            try {
                asignacion = new Asignacion(
                        practicante,
                        proyecto,
                        (Coordinador) MainController.get("user")
                );
            } catch (SQLException throwables) {
                MainController.alert(
                        Alert.AlertType.ERROR,
                        "ErrorBD",
                        "No se pudo establecer conexión con la base de datos"
                );
            }
            if (MainController.alert(Alert.AlertType.CONFIRMATION, "Confirmar Asignacion",
                    "¿Esta seguro de que quiere asignar este proyecto practicante?")) {
                try {
                    if (asignacion.assignProject()) {
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Asignación registrada",
                                "Asignacon realizada exitosamente");
                        MainController.activate("AdministrarPracticante", "Practicantes", MainController.Sizes.MID);

                    } else {
                        MainController.alert(Alert.AlertType.ERROR, "ErrorBD", "No se pudo establecer conexión con la Base de Datos");
                        exit();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            MainController.alert(
                    Alert.AlertType.ERROR,
                    "Proyecto no seleccionado",
                    "Debe seleccionar un royecto de las listas para poder asignarlo"
            );
        }
    }

    public void exit() {
        MainController.activate(
                "AdministrarPracticante",
                "Administrar Practicantes",
                MainController.Sizes.MID);
    }
}
