package com.lab3;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DataSingleton {
   private static Data d;

   public static Data getInstance(){
        if(d == null){
            d  = new Data.DataBuilder(null, null).build();
        }
        return d;
    }
}
