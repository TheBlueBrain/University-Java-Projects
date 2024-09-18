package com.example.lab7;

import android.content.Context;
import android.widget.TextView;

public class TextViewBuilder {
    TextView t;
    public TextViewBuilder(Context o, String text){
        t = new TextView(o);
        t.setText(" " + text + " ");
    };
    TextView build(){
        return t;
    }
}
