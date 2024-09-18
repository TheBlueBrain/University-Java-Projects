package com.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultControler {
    public static int Type;
    private Data da;

    public Data getData() {
        return da;
    }

    public void setData(Data da) {
        this.da = da;
    }

    @FXML
    MyTextArea text;
    @FXML
    void getData(ActionEvent e)throws IOException {
        Data d=null;
        if(Type==0){
            d = DataSingleton.getInstance();
            text.setText("Singleton");
        } else if (Type == 1) {
            Stage st = (Stage)((Node) e.getSource()).getScene().getWindow();
            d = (Data)st.getUserData();
            text.setText("UserData");
        } else if(Type == 2){
            text.setText("Controller");
            d = getData();
        }
        text.showData(d);
    }
}
