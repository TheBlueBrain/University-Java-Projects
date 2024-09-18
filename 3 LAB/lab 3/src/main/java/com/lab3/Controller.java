package com.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label welcomeText;
    String st1, st2;
    Data d;
    DataObserver dob;
    @FXML
    private TextField str1;
    @FXML
    private TextField str2;
    @FXML
    void getStr1(ActionEvent event) {
        st1=str1.getText();
    }
    @FXML
    void getStr2(ActionEvent event) {
        st2=str2.getText();
    }
    @FXML
    void passController(ActionEvent event) throws IOException {
        getStr1(event);
        getStr2(event);
        d = new Data.DataBuilder(st1, st2).build();
        dob = new DataObserver();
        dob.addData(d);
        newWindow(event, 2);
    }
    void newWindow(ActionEvent e, int type) throws IOException {
        dob.setOS(new AppendLinuxOS());
        Node n = (Node) e.getSource();
        Stage st =(Stage) n.getScene().getWindow();
        FXMLLoader l = new FXMLLoader(getClass().getResource("data2.fxml"));
        ResultControler rc = new ResultControler();
        if(type == 2){
            rc.setData(d);
        }
        l.setController(rc);
        ResultControler.Type = type;
        st.setTitle("Results");
        st.setScene(new Scene(l.load()));
        st.show();
    }
    @FXML
    void passSingleton(ActionEvent event) throws IOException {
        getStr1(event);
        getStr2(event);
        d = DataSingleton.getInstance();
        dob = new DataObserver();
        dob.addData(d);
        d.setS1(st1);
        d.setS2(st2);
        newWindow(event, 0);
    }
    @FXML
    void passUserData(ActionEvent event) throws IOException {
        getStr1(event);
        getStr2(event);
        d = new Data.DataBuilder(st1, st2).build();
        dob = new DataObserver();
        dob.addData(d);
        Stage st = (Stage) ((Node)event.getSource()).getScene().getWindow();
        st.setUserData(d);
        newWindow(event, 1);
    }
}