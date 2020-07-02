package View.student.controller;

import Models.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseProjectController implements Initializable {
    @FXML
    private TableView<Project> tblViewStudent;

    @FXML
    private TableColumn<Project,String> clmnOrganization;
    @FXML
    private TableColumn<Project,String> clmnNomb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
