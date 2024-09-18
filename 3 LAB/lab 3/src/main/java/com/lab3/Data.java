package com.lab3;

public class Data extends AbstractData {
    private String s2;

    @Override
    public String getS1() {
        return s1 + " " + info.getOS();
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }
    public void setOS(Info inf){
        info = inf;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }
    private Data(DataBuilder b, Info inf){
        super(inf);
        this.s1=b.s1;
        this.s2=b.s2;
    }

    public static class DataBuilder{
        private String s1;
        private String s2;
        public DataBuilder(String st1, String st2){
            s1=st1;
            s2=st2;
        }
        public DataBuilder setS1(String s1) {
            this.s1 = s1;
            return this;
        }
        public DataBuilder setS2(String s2) {
            this.s2 = s2;
            return this;
        }
        public Data build(){
            return new Data(this, null);
        }
    }
}
