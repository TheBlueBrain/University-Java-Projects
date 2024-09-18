package com.lab4v2;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class MyChoiceBox extends ChoiceBox{

	void addSelection(Data d){
		 getItems().add(d);
	}

}
