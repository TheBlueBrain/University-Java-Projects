package com.lab3;

public abstract class AbstractData {
    protected Info info;
    String s1;

    public AbstractData(Info inf) {
        info = inf;
    }

    abstract public String getS1();
}
