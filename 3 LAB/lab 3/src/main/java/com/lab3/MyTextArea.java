package com.lab3;

import javafx.scene.control.TextArea;

public class MyTextArea extends TextArea {

    public void showData(Data d){
        setText(getText() + '\n' + d.getS1() + "\n" + d.getS2());
    }

}
