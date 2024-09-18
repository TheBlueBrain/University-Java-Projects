package org.lab6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.Instant;
import java.util.Date;

public class DateController {


	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	void setLower(ActionEvent event) {
		HelloController.from = fromDate.getValue();
	}

	@FXML
	void setUpper(ActionEvent event) {
		HelloController.to = toDate.getValue();
	}

}
