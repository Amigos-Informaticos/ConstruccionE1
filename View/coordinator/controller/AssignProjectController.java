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
import java.util.Collections;
import java.util.ResourceBundle;

public class AssignProjectController implements Initializable {
    @FXML Label lbName;
    @FXML Label lbRegNo;
    @FXML Label lbEmail;

    public TableColumn<Project, String> clmNameRequest;
    public TableColumn<Project, String> clmName;


    @FXML public TableView<Project> requestTable;
    @FXML public TableView<Project> projectTable;

    private Student student = (Student) MainController.get("student");
    private ObservableList<Project> projectObservableList;
    private ObservableList<Project> requestObservableList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectObservableList = FXCollections.observableArrayList();
        Project.fillTable(projectObservableList);
        projectTable.setItems(projectObservableList);
        clmName.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));

        requestObservableList = FXCollections.observableArrayList();
        Collections.addAll(requestObservableList,student.getProjects());
        requestTable.setItems(requestObservableList);
        clmNameRequest.setCellValueFactory(new PropertyValueFactory<Project,String>("name"));

        lbName.setText(student.getNames() + " " + student.getLastnames());
        lbRegNo.setText(student.getRegNumber());
        lbEmail.setText(student.getEmail());

    }

    public void selectProject(){
    	if(projectTable.isFocused()){
    		Project project = projectTable.getSelectionModel().getSelectedItem();
    		MainController.save("project",project);
		}else if(requestTable.isFocused()){
			Project project = requestTable.getSelectionModel().getSelectedItem();
			MainController.save("project",project);
		}
	}

    public void assign(){
Project project = (Project) MainController.get("project");
			Assignment assignment = new Assignment(student, project);
			if (MainController.alert(Alert.AlertType.CONFIRMATION, "Confirmar Asignacion",
					"¿Esta seguro de que quiere asignar el practicante "
							+ student.getNames() +
							" al proyecto " + project.getName() + "?")) {
				if (assignment.assignProject()) {
					MainController.alert(Alert.AlertType.INFORMATION,
							"Asignación registrada",
							"Asignacon realizada exitosamente");
				} else {
					MainController.alert(Alert.AlertType.ERROR, "DatabaseError", "No se pudo establecer conexión con la Base de Datos");
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
