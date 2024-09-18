package org.lab6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class SortTextController {

	@FXML
	private CheckBox atoz;

	@FXML
	private ComboBox<String> selectColumn;

	@FXML
	void initialize() {
		selectColumn.getItems().addAll("ID", "FIRST NAME", "LAST NAME", "EMAIL","GENDER","COUNTRY","DOMAIN","BIRTH DATE");

	}

	@FXML
	void selectColumn(ActionEvent event) {
		String s = selectColumn.getValue();
		switch (s) {
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
	void setDirection(ActionEvent event) {
		HelloController.isAsc = atoz.isSelected();
		//System.out.println("isAsc: " + HelloController.isAsc);
	}

}
