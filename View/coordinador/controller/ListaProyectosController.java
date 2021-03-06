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
import tools.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListaProyectosController implements Initializable {
	@FXML
	private TableView<Proyecto> tblProject;
	@FXML
	private TableColumn<Proyecto, String> clmNameProject;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Proyecto> listProyectos = FXCollections.observableArrayList();
		try {
			Proyecto.llenarTabla(listProyectos);
		} catch (SQLException throwable) {
			Logger.staticLog(throwable, true);
		}
		tblProject.setItems(listProyectos);
		clmNameProject.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	}
	
	public void selectProject() {
		MainController.save("pantallaAnterior" , "listaProyectos");
		Proyecto proyecto = tblProject.getSelectionModel().getSelectedItem();
		MainController.save("proyecto", proyecto);
		MainController.activate("VerProyecto", "Ver Proyecto", MainController.Sizes.LARGE);
	}
	
	public void clickBack() {
		MainController.activate("MenuProyecto", "Proyectos", MainController.Sizes.MID);
	}
}
