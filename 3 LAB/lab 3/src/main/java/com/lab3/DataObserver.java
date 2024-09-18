package com.lab3;

import java.util.ArrayList;
import java.util.List;

public class DataObserver {
    private Info i;
    private List<Data> obs = new ArrayList<Data>();
    public void addData(Data d){
        obs.add(d);
    }
    public void removeData(Data d){
        obs.remove(d);
    }
    public void setOS(Info inf){
        i = inf;
        for(Data d : obs){
            d.setOS(inf);
        }
    }
}
