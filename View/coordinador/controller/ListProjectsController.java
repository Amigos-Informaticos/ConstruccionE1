package View.coordinador.controller;

import Models.Proyecto;
import View.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListProjectsController implements Initializable {
	@FXML
	private TableView<Proyecto> tblProject;
	@FXML
	private TableColumn<Proyecto, String> clmNameProject;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Proyecto> listProyectos = FXCollections.observableArrayList();
		try {
			Proyecto.llenarTabla(listProyectos);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		tblProject.setItems(listProyectos);
		clmNameProject.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("name"));
	}
	
	public void selectProject() {
		MainController.save("project", tblProject.getSelectionModel().getSelectedItem());
		MainController.activate("ViewProject", "Ver Proyecto", MainController.Sizes.MID);
	}
	
	public void clickBack() {
		MainController.activate("MenuProject", "Proyectos", MainController.Sizes.MID);
	}
}
