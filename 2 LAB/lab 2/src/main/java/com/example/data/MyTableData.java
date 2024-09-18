package com.example.data;

import javafx.beans.property.SimpleDoubleProperty;

public class MyTableData extends TableData{

    private final double total;
    public MyTableData(double total) {
        super();
        this.total = total;
    }
    @Override
    public void updateData(double payAmount){
        double left = total - totalPayed.get();
        double percent = payAmount*100/total;
        this.payAmount.set(payAmount);
        this.totalPayed.set(payAmount+totalPayed.get());
        this.percentPayed.set(percent);
        this.leftAmount.set(left);
    }
    public static MyTableData clone(MyTableData src){
        MyTableData dest = new MyTableData(src.total);
        dest.setLeftAmount(src.getLeftAmount());
        dest.setPayAmount(src.getPayAmount());
        dest.setPercentPayed(src.getPercentPayed());
        dest.setTotalPayed(src.getTotalPayed());
        return dest;
    }
}
