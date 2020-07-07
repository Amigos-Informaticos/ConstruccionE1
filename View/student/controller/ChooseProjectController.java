package View.student.controller;

import Models.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import tools.P;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseProjectController implements Initializable {
	@FXML
	private TableView<Project> tblViewStudent;
	
	@FXML
	private TableColumn<Project, String> clmnOrganization;
	@FXML
	private TableColumn<Project, String> clmnNomb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Project> projectObservableList = FXCollections.observableArrayList();
		Project.fillTable(projectObservableList);
		for (int i = 0; i < projectObservableList.size(); i++) {
			P.pln(projectObservableList.get(i).getName());
		}
	}
	
	
	public void checkProject(MouseEvent mouseEvent) {
		P.p(this.tblViewStudent.getSelectionModel().getSelectedItem().getName());
	}
}
