package View.coordinador.controller;

import Models.Organizacion;
import View.MainController;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import tools.LimitadorTextfield;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministrarOrganizacionController implements Initializable {
    @FXML
    private TableView<Organizacion> tblViewOrganization;
    @FXML
    private TableColumn<Organizacion, String> clmnName;
    @FXML
    private JFXComboBox<String> cmbSector;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtTel;
    @FXML
    private JFXTextField txtStreet;
    @FXML
    private JFXTextField txtNo;
    @FXML
    private JFXTextField txtColony;
    @FXML
    private JFXTextField txtLocality;

    private ObservableList<Organizacion> listOrganizacion;

    private String tempNombreAntiguo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOrganizacion = FXCollections.observableArrayList();
        try {
            new Organizacion().llenarTablaOrganizacion(listOrganizacion);
        } catch (Exception e) {
            System.out.println(e);
        }
        tblViewOrganization.setItems(listOrganizacion);
        clmnName.setCellValueFactory(new PropertyValueFactory<Organizacion, String>("nombre"));

        ObservableList<String> listSector = FXCollections.observableArrayList();
        LimitadorTextfield.soloTexto(txtName);
        LimitadorTextfield.soloNumeros(txtTel);
        LimitadorTextfield.soloTexto(txtStreet);
        LimitadorTextfield.soloTexto(txtColony);
        LimitadorTextfield.soloTexto(txtLocality);
        LimitadorTextfield.limitarTamanio(txtTel,10);
        LimitadorTextfield.limitarTamanio(txtNo,5);

        try {
            new Organizacion().llenarSector(listSector);
        } catch (SQLException e) {
            System.out.println(e);
        }
        cmbSector.setItems(listSector);
        eventManager();
    }

    public void eventManager() {
        tblViewOrganization.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Organizacion>() {
                    @Override
                    public void changed(ObservableValue<? extends Organizacion> observable, Organizacion oldValue,
                                        Organizacion newValue) {
                        if (newValue != null) {
                            txtName.setText(newValue.getNombre());
                            txtTel.setText(newValue.getTelefono());
                            txtStreet.setText(newValue.getDireccion().get("calle"));
                            txtNo.setText(newValue.getDireccion().get("numero"));
                            txtColony.setText(newValue.getDireccion().get("colonia"));
                            txtLocality.setText(newValue.getDireccion().get("localidad"));
                            cmbSector.setValue(newValue.getSector());
                            enableEdit();
                            tempNombreAntiguo = newValue.getNombre();
                        } else {
                            cleanFormProfessor();
                            enableRegister();
                        }
                    }
                }
        );
    }

    public void enableEdit() {
    }

    public void cleanFormProfessor() {
    }

    public void enableRegister() {
    }

    @FXML
    public void registrar() {
        Organizacion organizacion = new Organizacion();
        this.instanciarOrganizacion(organizacion);
        if (organizacion.estaCompleto()) {
            try {
                if (organizacion.registrar()) {
                    listOrganizacion.add(organizacion);
                    MainController.alert(
                            Alert.AlertType.INFORMATION,
                            "Organizacion registrada exitosamente",
                            "Pulse aceptar para continuar"
                    );
                }
            } catch (SQLException throwables) {
                MainController.alert(
                        Alert.AlertType.WARNING,
                        "Error al conectar con la base de datos",
                        "Pulse aceptar para continuar"
                );
            }
        } else {
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "Llene todos los campos correctamente",
                    "Pulse aceptar para continuar"
            );
            mostrarCamposErroneos();
        }
    }

    public void instanciarOrganizacion(Organizacion organizacion) {
        organizacion.setNombre(txtName.getText());
        organizacion.setDireccion(
                txtStreet.getText(),
                txtNo.getText(),
                txtColony.getText(),
                txtLocality.getText()
        );
        organizacion.setSector(cmbSector.getValue());
        if (!txtTel.getText().equals("")) {
            organizacion.setTelefono(txtTel.getText());
        }
    }

    public void onClickBack() {
        MainController.activate("MenuCoordinador", "Menu", MainController.Sizes.MID);
    }

    @FXML
    public void delete() {
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar?", "")) {
            try {
                if (tblViewOrganization.getSelectionModel().getSelectedItem().eliminar()) {
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Organización eliminada",
                            "Organización eliminada exitosamente");
                    listOrganizacion.remove(tblViewOrganization.getSelectionModel().getSelectedIndex());
                }
            } catch (AssertionError | SQLException e) {
                MainController.alert(
                        Alert.AlertType.INFORMATION,
                        "DBError",
                        "No se pudo establecer conexión con Base de Datos"
                );
            }
        }
    }

    private void mostrarCamposErroneos() {
        if(txtName.getText().equals("")){
            txtName.setUnFocusColor(Paint.valueOf("red"));
        }
        if(txtTel.getText().equals("")){
            txtTel.setUnFocusColor(Paint.valueOf("red"));
        }
        if(txtStreet.getText().equals("")){
            txtStreet.setUnFocusColor(Paint.valueOf("red"));
        }
        if(txtColony.getText().equals("")){
            txtColony.setUnFocusColor(Paint.valueOf("red"));
        }
        if(txtLocality.getText().equals("")){
            txtLocality.setUnFocusColor(Paint.valueOf("red"));
        }
        if(txtNo.getText().equals("")){
            txtNo.setUnFocusColor(Paint.valueOf("red"));
        }
    }

    @FXML
    public void actualizar(){
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea actualizar?", "")){
            Organizacion organizacion = new Organizacion();
            this.instanciarOrganizacion(organizacion);
            if (organizacion.estaCompleto()) {
                try {
                    if (organizacion.actualizar(tempNombreAntiguo)) {
                        listOrganizacion.remove(tblViewOrganization.getSelectionModel().getSelectedIndex());
                        listOrganizacion.add(organizacion);
                        MainController.alert(
                                Alert.AlertType.INFORMATION,
                                "Organizacion actualizada exitosamente",
                                "Pulse aceptar para continuar"
                        );
                    }
                } catch (SQLException throwables) {
                    MainController.alert(
                            Alert.AlertType.WARNING,
                            "Error al conectar con la base de datos",
                            "Pulse aceptar para continuar"
                    );
                }
            } else {
                MainController.alert(
                        Alert.AlertType.INFORMATION,
                        "Llene todos los campos correctamente",
                        "Pulse aceptar para continuar"
                );
            }
        }
    }
}