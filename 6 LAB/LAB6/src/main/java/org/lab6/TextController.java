package org.lab6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TextController {

	@FXML
	private ComboBox<String> columnSelect;

	@FXML
	private TextField startText;

	@FXML
	void setColumn(ActionEvent event){
		String s = columnSelect.getValue();
		switch(s){
			case "ID":
				HelloController.selectedColumn = 0;
				break;
			case "FIRST NAME":
				HelloController.selectedColumn = 1;
				break;
			case "LAST NAME":
				HelloController.selectedColumn = 2;
				break;
			case "EMAIL":
				HelloController.selectedColumn = 3;
				break;
			case "GENDER":
				HelloController.selectedColumn = 4;
				break;
			case "COUNTRY":
				HelloController.selectedColumn = 5;
				break;
			case "DOMAIN":
				HelloController.selectedColumn = 6;
				break;
			case "BIRTH DATE":
				HelloController.selectedColumn = 7;
				break;

		}
	}

	@FXML
	void setStartText(ActionEvent event) {
		HelloController.startss = startText.getText();
	}
	@FXML
	void initialize() {
		columnSelect.getItems().addAll("ID", "FIRST NAME", "LAST NAME", "EMAIL","GENDER","COUNTRY","DOMAIN","BIRTH DATE");
	}
}
