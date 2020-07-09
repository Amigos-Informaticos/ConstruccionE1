package View.coordinator.controller;

import Models.Assignment;
import Models.Project;
import Models.Student;
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

import java.net.URL;
import java.util.ResourceBundle;

public class AssignProjectController implements Initializable {
    @FXML Label lbName;
    @FXML Label lbRegNo;
    @FXML Label lbEmail;
    public TableColumn<Project, String> tableColumn;

    @FXML public TableView<Project> projectTable;

    private Student student = new Student();
    private ObservableList<Project> projectObservableList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectObservableList = FXCollections.observableArrayList();
        Project.fillTable(projectObservableList);
        projectTable.setItems(projectObservableList);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
    }

    public void assign(){
        Project project = projectTable.getSelectionModel().getSelectedItem();
        Assignment assignment = new Assignment(student,project);
        if(MainController.alert(Alert.AlertType.CONFIRMATION,"Confirmar Asignacion",
                "¿Esta seguro de que quiere asignar el practicante "
                        + student.getNames() +
                        " al proyecto "+project.getName()+"?")) {
            if(assignment.assignProject()){
                MainController.alert(Alert.AlertType.INFORMATION,
                        "Asignación registrada",
                        "Asignacon realizada exitosamente");
            }else {
                MainController.alert(Alert.AlertType.ERROR,"DatabaseError","No se pudo establecer conexión con la Base de Datos");
                exit();
            }
        }

    }
    public void exit() {
        MainController.activate(
                "ManageStudents",
                "Administrar Practicantes",
                MainController.Sizes.MID);
    }
}
