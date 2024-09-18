package com.example.data;

import javafx.beans.property.SimpleDoubleProperty;

public class TableData {

    protected SimpleDoubleProperty payAmount, percentPayed, totalPayed, leftAmount;
    TableData(){
        this.payAmount = new SimpleDoubleProperty(0);
        this.percentPayed = new SimpleDoubleProperty(0);
        this.totalPayed = new SimpleDoubleProperty(0);
        this.leftAmount = new SimpleDoubleProperty(0);
    }

    public double getPayAmount() {
        return payAmount.get();
    }

    public void setPayAmount(double payAmount) {
        this.payAmount.set(payAmount);
    }

    public void setPercentPayed(double percentPayed) {
        this.percentPayed.set(percentPayed);
    }

    public void setTotalPayed(double totalPayed) {
        this.totalPayed.set(totalPayed);
    }

    public void setLeftAmount(double leftAmount) {
        this.leftAmount.set(leftAmount);
    }

    public double getLeftAmount() {
        return leftAmount.get();
    }

    public double getPercentPayed() {
        return percentPayed.get();
    }

    public double getTotalPayed() {
        return totalPayed.get();
    }
    public void updateData(double pA){
        payAmount.set(pA);
    }
}
