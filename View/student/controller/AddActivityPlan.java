package View.student.controller;

import View.MainController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tools.File;

import java.net.URL;
import java.util.ResourceBundle;

public class AddActivityPlan implements Initializable {
	@FXML
	private JFXTextField fileName;
	private File selectedFile;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}
	
	public void openFileExplorer() {
		this.selectedFile = new File(String.valueOf(MainController.fileExplorer()));
		if (this.selectedFile.getName() != null) {
			this.fileName.setText(this.selectedFile.getName());
		}
	}
	
	public void exit() {
		MainController.activate(
			"MainMenuStudent",
			"Menu Principal Practicante",
			MainController.Sizes.MID);
	}
	
	public void saveFile() {
	
	}
}
