package View.profesor.controller;

import Models.Documento;
import Models.Practicante;
import Models.Profesor;
import Models.Reporte;
import View.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import sun.applet.Main;
import tools.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EvaluarReporte implements Initializable {
    public JFXDatePicker fechaInicial;
    public JFXDatePicker fechaFinal;
    public JFXTextArea txtRetroalimentacion;
    public Label lblActividadesPlaneadas;
    public Label lblActividadesRealizadas;
    public Label lblResumen;
    public Label lblTipoReporte;
    public JFXButton btnRegresar;
    public JFXButton btnCalificar;
    Reporte reporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Documento documento = (Documento) MainController.get("documento");
        reporte = new Reporte();
        try {
            reporte = new Practicante().obtenerReporte(documento.getId());
        } catch (SQLException sqlException) {
            Logger.staticLog(sqlException);
        }
        lblActividadesPlaneadas.setText(reporte.getActividadesPlaneadas());
        lblActividadesRealizadas.setText(reporte.getActividadesRealizadas());
        lblResumen.setText(reporte.getResumen());
        lblTipoReporte.setText(reporte.getTipoReporte());
        fechaFinal.setValue(reporte.getFechaFin());
        fechaInicial.setValue(reporte.getFechaInicio());
    }

    public void onClicCalificar(ActionEvent actionEvent) {
        System.out.println(reporte.getIdReporte());
        txtRetroalimentacion.setUnFocusColor(Paint.valueOf("black"));
        if (txtRetroalimentacion.getText() != null && !txtRetroalimentacion.getText().equals("")){
            try {
                if(new Profesor().calificarReporte(reporte, txtRetroalimentacion.getText())){
                    MainController.alert(Alert.AlertType.INFORMATION, "CalificaciónExitosa", "Se ha calificado el reporte exitosamente");
                } else {
                    MainController.alert(Alert.AlertType.ERROR, "ErrorBD", "Ocurrio un problema al calificar el reporte.");
                }
            } catch (SQLException sqlException) {
                Logger.staticLog(sqlException);
                MainController.alert(Alert.AlertType.ERROR, "ErrorBD", "Ocurrio un problema al calificar el reporte");
            }
        } else {
            txtRetroalimentacion.setUnFocusColor(Paint.valueOf("red"));
        }
    }

    public void onClicRegresar(ActionEvent actionEvent) {
        MainController.activate("Expediente","Expediente", MainController.Sizes.MID);
    }
}
