package com.example.lab2;

import com.example.data.MyTableData;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class HelloController {

    @FXML
    private Button Calculate, file;
    @FXML
    private NumberAxis XAXIS, YAXIS;
    @FXML
    private TableColumn Suma, Like, Sumoketa, Procentai;
    @FXML
    private TableView Tab;
    @FXML
    private ToggleGroup a;
    @FXML
    private RadioButton lin, sud, at;
    @FXML
    private TextField suma, men, met, proc, start, dur, beginData, endData;
    @FXML
    private LineChart<Integer, Double> graph;
    @FXML
    private CheckBox atid;
    double sum, percent;
    int m, mh;
    MyTableData mtd;
    boolean[] atidet;
    ObservableList<MyTableData> data;
    @FXML
    private void Callculate(){
        setMonth();
        setYear();
        setPercent();
        setSum();
        graph.setCreateSymbols(false);
        XAXIS.setForceZeroInRange(false);
        Suma.setCellValueFactory(new PropertyValueFactory<MyTableData, Double>("payAmount"));
        Like.setCellValueFactory(new PropertyValueFactory<MyTableData, Double>("leftAmount"));
        Procentai.setCellValueFactory(new PropertyValueFactory<MyTableData, Double>("percentPayed"));
        Sumoketa.setCellValueFactory(new PropertyValueFactory<MyTableData, Double>("totalPayed"));

        data = FXCollections.observableArrayList();
        percent/=100;
        int i = m*12+mh;
        XAXIS.setAutoRanging(false);
        XAXIS.setUpperBound(i);
        XAXIS.setLowerBound(1);
        atidet = new boolean[i];
        int star =0;
        int d = 0;
        if(atid.isSelected()){
            star = Integer.parseInt(start.getText())-1;
            d = Integer.parseInt(dur.getText());
            for(int j = star; j<star+d; j++){
                atidet[j] = true;
            }
            i-=d;
        }

        XYChart.Series ser = new XYChart.Series();
        if(at.isSelected()){

            double TMP = Math.pow(1+percent,m*12+mh);
            double pay = TMP*percent;
            pay /= TMP - 1;
            pay*= sum;
            mtd = new MyTableData(pay * i);
            //Tab.getColumns().add(Col);
            //Col.setCellValueFactory(new PropertyValueFactory<>());
            for(int j = 0; j<i+d; j++){
                if(!atidet[j]) {
                    mtd.updateData(pay);
                    ser.getData().add(new XYChart.Data (j+1, pay));
                }else{
                    ser.getData().add(new XYChart.Data (j+1, 0));
                    mtd.updateData(0);
                }
                data.add(MyTableData.clone(mtd));


            }
            Tab.setItems(data);
            graph.getData().add(ser);
        }else if(lin.isSelected()){
            double paluk = sum * percent;
            double delp = 2*paluk/((i) * (i+1));
            double firstpay = sum/i + i*delp;

           // double delp = firstpay / i;
            mtd = new MyTableData(sum + paluk);
            for(int j = 0; j < i+d; j++){
                if(!atidet[j]) {
                    mtd.updateData(firstpay);
                    ser.getData().add(new XYChart.Data (j+1, firstpay));
                    firstpay-=delp;
                }else{
                    mtd.updateData(0);
                    ser.getData().add(new XYChart.Data (j+1, 0));
                }
                data.add(MyTableData.clone(mtd));


            }
            Tab.setItems(data);
            graph.getData().add(ser);
        }else if(sud.isSelected()){
            sum *= Math.pow(1+percent, i);
            mtd = new MyTableData(sum);
            double mok = sum/i;
            for(int j = 0; j<i+d; j++){
                if(!atidet[j]) {
                    mtd.updateData(mok);
                    ser.getData().add(new XYChart.Data (j+1, mok));
                }else{
                    mtd.updateData(0);
                    ser.getData().add(new XYChart.Data (j+1, 0));
                }
                data.add(MyTableData.clone(mtd));

            }
            Tab.setItems(data);
            graph.getData().add(ser);
        }
        graph.setTitle("Mokėjimai");

    }

    @FXML
    private void setMonth(){
        mh = Integer.parseInt(men.getText());
        if(mh>11){
            m = mh/12;
            met.setText(Integer.toString(m));
            mh = mh % 12;
            men.setText(Integer.toString(mh));
        }
    }
    @FXML
    private void setYear(){
        m = Integer.parseInt(met.getText());
    }
    @FXML
    private void setPercent(){
        percent = Double.parseDouble(proc.getText());
    }
    @FXML
    private void setSum(){
        sum = Double.parseDouble(suma.getText());
    }
    @FXML
    public void Write(){
        FileWriter out;
        try {
           out = new FileWriter("Payments.txt");
            out.write("Month of payment, payment amount\n");
            for(int i = 0; i < atidet.length; i++){
                MyTableData prdata = data.get(i);
                out.write((i + 1) + ", " + prdata.getPayAmount() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void lowerLimit(){
        int from = Integer.parseInt(beginData.getText());
        XAXIS.setAutoRanging(false);
        XAXIS.setLowerBound(from);
        XAXIS.setTickUnit((XAXIS.getUpperBound() + from)/10);
        Tab.getItems().removeAll();
        ObservableList<MyTableData> sdata = FXCollections.observableArrayList();
        for(int i = (int) XAXIS.getLowerBound() - 1; i<=(int)XAXIS.getUpperBound() - 1; i++){
            sdata.add(data.get(i));
        }
        Tab.setItems(sdata);
    }
    @FXML
    public void upperLimit(){
        int to = Integer.parseInt(endData.getText());
        XAXIS.setAutoRanging(false);
        XAXIS.setUpperBound(to);
        XAXIS.setTickUnit((XAXIS.getLowerBound() + to)/10);
        Tab.getItems().removeAll();
        ObservableList<MyTableData> sdata = FXCollections.observableArrayList();
        for(int i = (int) XAXIS.getLowerBound() - 1; i<=XAXIS.getUpperBound() - 1; i++){
            sdata.add(data.get(i));
        }

        Tab.setItems(sdata);

    }
}