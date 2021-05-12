package View.coordinador.controller;

import Models.Organizacion;
import Models.Proyecto;
import Models.ResponsableProyecto;
import View.MainController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import tools.LimitadorTextfield;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CrearProyectoController implements Initializable {
    @FXML
    private JFXComboBox<String> cmbOrganizations;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextArea txtDescription;
    @FXML
    private JFXTextField txtGeneralObjective;
    @FXML
    private JFXTextField txtMediateObjective;
    @FXML
    private JFXTextField txtInmediateObjective;
    @FXML
    private JFXTextField txtMethodology;
    @FXML
    private JFXTextArea txtResources;
    @FXML
    private JFXTextArea txtResponsibilities;
    @FXML
    private JFXTextField txtCapacity;
    @FXML
    private JFXTextField txtPositionResponsible;
    @FXML
    private JFXTextField txtEmailResponsible;
    @FXML
    private JFXTextField txtNameResponsible;
    @FXML
    private JFXTextField txtLastnameResponsible;
    @FXML
    private JFXComboBox<String> cmbArea;
    @FXML
    private JFXComboBox<String> cmbPeriod;

    @FXML
    private JFXDatePicker initialDate;
    @FXML
    private JFXDatePicker finalDate;

    private ObservableList<String> listOrganizations;
    private ObservableList<String> listAreas;
    private ObservableList<String> listPeriods;

    private Proyecto proyecto = new Proyecto();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadValues();
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        limitarTextfields();
    }

    public void limitarTextfields() {
        LimitadorTextfield.soloTexto(txtName);
        LimitadorTextfield.soloTexto(txtGeneralObjective);
        LimitadorTextfield.soloTexto(txtMediateObjective);
        LimitadorTextfield.soloTexto(txtInmediateObjective);
        LimitadorTextfield.soloTexto(txtMethodology);
        LimitadorTextfield.soloTexto(txtPositionResponsible);
        LimitadorTextfield.soloTexto(txtNameResponsible);
        LimitadorTextfield.soloTexto(txtLastnameResponsible);
        LimitadorTextfield.soloNumeros(txtCapacity);

        LimitadorTextfield.limitarTamanio(txtName, 50);
        LimitadorTextfield.limitarTamanio(txtGeneralObjective, 50);
        LimitadorTextfield.limitarTamanio(txtMediateObjective, 50);
        LimitadorTextfield.limitarTamanio(txtInmediateObjective, 50);
        LimitadorTextfield.limitarTamanio(txtMethodology, 50);
        LimitadorTextfield.limitarTamanio(txtPositionResponsible, 20);
        LimitadorTextfield.limitarTamanio(txtNameResponsible, 20);
        LimitadorTextfield.limitarTamanio(txtLastnameResponsible, 20);
        LimitadorTextfield.limitarTamanio(txtEmailResponsible, 50);
        LimitadorTextfield.limitarTamanio(txtCapacity, 2);

        LimitadorTextfield.limitarTamanioArea(txtDescription, 200);
        LimitadorTextfield.limitarTamanioArea(txtResources, 200);
        LimitadorTextfield.limitarTamanioArea(txtResponsibilities, 200);
    }

    public void loadValues() throws SQLException {
        if (MainController.getStageName().equals("CrearProyecto")) {
            listOrganizations = FXCollections.observableArrayList();
            Organizacion.llenarNombres(listOrganizations);
            cmbOrganizations.setItems(listOrganizations);

            listAreas = FXCollections.observableArrayList();
            Proyecto.fillAreaTable(listAreas);
            cmbArea.setItems(listAreas);

            listPeriods = FXCollections.observableArrayList();
            listPeriods.add("FEB-JUL");
            listPeriods.add("AGO-ENE");
            cmbPeriod.setItems(listPeriods);

            if (MainController.has("name")) {
                txtName.setText(MainController.get("name").toString());
                txtDescription.setText(MainController.get("description").toString());
                txtGeneralObjective.setText(MainController.get("generalObjective").toString());
                txtMediateObjective.setText(MainController.get("mediateObjective").toString());
                txtInmediateObjective.setText(MainController.get("inmediateObjective").toString());
                txtMethodology.setText(MainController.get("methodology").toString());
                txtResources.setText(MainController.get("resources").toString());
                txtResponsibilities.setText(MainController.get("responsibilities").toString());
                txtCapacity.setText(MainController.get("capacity").toString());
                txtPositionResponsible.setText(MainController.get("positionResponsible").toString());
                txtEmailResponsible.setText(MainController.get("emailResponsible").toString());
                txtNameResponsible.setText(MainController.get("nameResponsible").toString());
                txtLastnameResponsible.setText(MainController.get("lastnameResponsible").toString());
            }
        } else {
            if (MainController.has("initialDate")) {
                initialDate.setValue((LocalDate) MainController.get("initialDate"));
                finalDate.setValue((LocalDate) MainController.get("finalDate"));
            }

        }
    }

    public void onClickBack(MouseEvent clickEvent) {
        MainController.activate("MenuProyecto", "Proyectos", MainController.Sizes.MID);
    }

    public void signUp() throws SQLException {
        instanceProject();
        if (proyecto.estaCompleto()) {
            if (proyecto.validarFechas()) {
                if (proyecto.registrar()) {
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Proyecto registrado",
                            "El Proyecto se registró exitosamente");
                } else {
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Error con Base de Datos",
                            "No se pudo conectar con Base de Datos");
                }
            } else {
                MainController.alert(Alert.AlertType.WARNING,
                        "Fechas incorrectas", "Las fechas que proporcionó son incorectas.");
            }
        } else {
            MainController.alert(
                    Alert.AlertType.INFORMATION,
                    "IncorrectEntries",
                    "Debe llenar los datos correctamente"
            );
            this.mostrarErrores();
        }
    }

    public void instanceProject() throws SQLException {
        proyecto.setNombre(txtName.getText());
        proyecto.setDescripcion(txtDescription.getText());
        proyecto.setObjetivoGeneral(txtGeneralObjective.getText());
        proyecto.setObjetivoMediato(txtMediateObjective.getText());
        proyecto.setObjetivoInmediato(txtInmediateObjective.getText());
        proyecto.setMetodologia(txtMethodology.getText());
        proyecto.setRecursos(txtResources.getText());
        proyecto.setResponsabilidades(txtResponsibilities.getText());
        try {
            if (!txtCapacity.getText().equals("") || (Integer.parseInt(txtCapacity.getText()) < 1)) {
                proyecto.setCapacidad(Integer.parseInt(txtCapacity.getText()));
            }
        } catch (NumberFormatException e) {
            System.out.printf(e.getMessage());;
        }
        proyecto.setOrganization(Organizacion.obtenerPorNombre(cmbOrganizations.getValue()));
        proyecto.setPeriodo(cmbPeriod.getValue());
        proyecto.setArea(cmbArea.getValue());
        if ((initialDate.getValue() != null)) {
            proyecto.setFechaInicio(initialDate.getValue().toString());
        }
        if ((finalDate.getValue() != null)) {
            proyecto.setFechaFin(finalDate.getValue().toString());
        }
        proyecto.setResponsable(this.instanceResponsible());
    }

    public ResponsableProyecto instanceResponsible() throws SQLException {
        ResponsableProyecto responsableProyecto = new ResponsableProyecto();
        responsableProyecto.setPosicion(txtPositionResponsible.getText());
        responsableProyecto.setEmail(txtEmailResponsible.getText());
        responsableProyecto.setNombre(txtNameResponsible.getText());
        responsableProyecto.setApellido(txtLastnameResponsible.getText());
        responsableProyecto.setOrganizacion(Organizacion.obtenerPorNombre(cmbOrganizations.getValue()));
        return responsableProyecto;
    }

    public void mostrarErrores() {
        marcarCampoErroneo(txtName);
        if (txtDescription.getText().equals("")) {
            txtDescription.setUnFocusColor(Paint.valueOf("red"));
        }
        marcarCampoErroneo(txtMediateObjective);
        marcarCampoErroneo(txtInmediateObjective);
        marcarCampoErroneo(txtGeneralObjective);
        marcarCampoErroneo(txtMethodology);
        if (txtResources.getText().equals("")) {
            txtResources.setUnFocusColor(Paint.valueOf("red"));
        }
        if (txtResponsibilities.getText().equals("")) {
            txtResponsibilities.setUnFocusColor(Paint.valueOf("red"));
        }
        marcarCampoErroneo(txtNameResponsible);
        marcarCampoErroneo(txtLastnameResponsible);
        marcarCampoErroneo(txtPositionResponsible);
        marcarCampoErroneo(txtEmailResponsible);
        if (txtCapacity.getText().equals("") || proyecto.getCapacidad() < 1) {
            txtCapacity.setUnFocusColor(Paint.valueOf("red"));
        }
        this.mostrarFechasErroneas();
        this.mostarCombosErroeos();
    }

    public void marcarCampoErroneo(JFXTextField textField) {
        if (textField.getText().equals("")) {
            textField.setUnFocusColor(Paint.valueOf("red"));
        }
    }

    public void mostrarFechasErroneas() {
        if (initialDate.getValue() == null) {
            initialDate.setPromptText("ERROR:Fecha Inicial");
        }
        if (finalDate.getValue() == null) {
            finalDate.setPromptText("ERROR: Fecha Final");
        }
    }

    public void mostarCombosErroeos() {
        if (proyecto.getArea() == null) {
            cmbArea.setUnFocusColor(Paint.valueOf("red"));
        }
        if (proyecto.getPeriodo() == null) {
            cmbPeriod.setUnFocusColor(Paint.valueOf("red"));
        }
    }
}
