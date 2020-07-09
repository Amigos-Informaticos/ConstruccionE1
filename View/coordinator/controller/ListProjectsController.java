package View.coordinator.controller;

import Models.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ListProjectsController implements Initializable {
	@FXML
	private TableView<Project> tblProject;
	@FXML
	private TableColumn<Project,String> clmNameProject;
	ObservableList<Project> listProjects;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listProjects = FXCollections.observableArrayList();
		Project.fillTable(listProjects);
		tblProject.setItems(listProjects);
		clmNameProject.setCellValueFactory(new PropertyValueFactory<Project,String>("name"));
	}
}
