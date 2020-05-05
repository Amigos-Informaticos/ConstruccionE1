package View.ManageStudents;

import Models.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {
    @FXML private TableView<Student> tblViewStudent;
    @FXML private TableColumn<Student, String> clmnName;
    @FXML private TableColumn<Student, String> clmnLastame;
    @FXML private TableColumn<Student, String> clmnRegno;


    @FXML JFXTextField txtName;
    @FXML JFXTextField txtLastname;
    @FXML JFXTextField txtRegNo;
    @FXML JFXTextField txtEmail;

    ObservableList<Student> listStudent;

    public void instanceStudent(Student student){
        student.setNames(txtName.getText());
        student.setLastnames(txtLastname.getText());
        student.setRegNumber(txtRegNo.getText());
        student.setEmail(txtEmail.getText());
    }

    public void cleanFormStudent(){
        txtName.setText(null);
        txtLastname.setText(null);
        txtRegNo.setText(null);
        txtEmail.setText(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listStudent = FXCollections.observableArrayList();
        new Student().fillTableStudent(listStudent);
        tblViewStudent.setItems(listStudent);
        clmnName.setCellValueFactory(new PropertyValueFactory<Student,String>("names"));
        clmnLastame.setCellValueFactory(new PropertyValueFactory<Student,String>("lastnames"));
        clmnRegno.setCellValueFactory(new PropertyValueFactory<Student,String>("regNumber"));


    }
}
