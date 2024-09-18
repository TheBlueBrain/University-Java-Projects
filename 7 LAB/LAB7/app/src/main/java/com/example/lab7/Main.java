package com.example.lab7;

import android.graphics.Color;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    ArrayList<LineDataSet> payments;
    TableLayout Table;
    TableRow Sum, LeftSum, Total, Perc, Left;
    EditText SumAmount, percentA, len, ATtr, ATpr, low, high;
    RadioButton sud, lin, an;
    Button CalcButton, filter;
    LineChart chart;
    double total, percent;
    int N;
    CheckBox atid;
    LineDataSet data;
    ArrayList<Entry> da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        da = new ArrayList<>();
        low = findViewById(R.id.FilterL);
        high = findViewById(R.id.FilterU);
        filter = findViewById(R.id.Filter);
        chart = findViewById(R.id.CHART);
        chart.setPinchZoom(true);
        chart.setTouchEnabled(true);
        percentA = findViewById(R.id.proc);
        ATpr = findViewById(R.id.ATN);
        ATtr = findViewById(R.id.ATT);
        len = findViewById(R.id.durat);
        sud = findViewById(R.id.sud);
        lin = findViewById(R.id.ties);
        an = findViewById(R.id.anu);
        CalcButton = findViewById(R.id.CalcButton);
        SumAmount = findViewById(R.id.SumAmount);
        Sum = findViewById(R.id.Sum);
        atid = findViewById(R.id.checkBox);
        //LeftSum = findViewById(R.id.LeftSum);
        Total = findViewById(R.id.Total);
        Perc = findViewById(R.id.Percent);
        Left = findViewById(R.id.Left);
        Table = findViewById(R.id.Table);


        CalcButton.setOnClickListener((v) -> {
            total = Double.parseDouble(SumAmount.getText().toString());
            percent = Double.parseDouble(percentA.getText().toString());
            N = Integer.parseInt(len.getText().toString());
            Total.removeAllViews();
            Perc.removeAllViews();
            Left.removeAllViews();
            Sum.removeAllViews();
            Total.addView(new TextViewBuilder(this, "moketi").build());
            Perc.addView(new TextViewBuilder(this, "proc sum.").build());
            Left.addView(new TextViewBuilder(this, "liko").build());
            Sum.addView(new TextViewBuilder(this, "sumoketa").build());
            XAxis xAxis = chart.getXAxis();
            xAxis.setAxisMaximum(1);

            int at = 0, atl = 0;
            if(atid.isChecked()){
                at=Integer.parseInt(ATpr.getText().toString());
                atl = Integer.parseInt(ATtr.getText().toString());
            }
            xAxis.setAxisMaximum(N+atl);
            chart.getAxisLeft().setAxisMinimum(0);
            chart.getAxisRight().setAxisMinimum(0);
            if(da!=null) {
                da.clear();
            }
            if(data!=null) {
                data.clear();
            }
            if(chart.getLineData()!=null){
                chart.getLineData().clearValues();
            }
            if(lin.isChecked()){
                double cons = total/N;
                System.out.println(cons);
                total*=(100+percent)/100;
                double sumt = total;
                total -= cons*N;//proc
                double delp = 2*total/((N) * (N+1));
                double firstpay = cons + N*delp;
                double sum = 0;

                for(int i = 0; i < N + atl; i++){
                    sum+=firstpay;
                    if(atid.isChecked()){
                        if(i>at && i<at+atl){
                            da.add(new Entry(i+1, 0));
                            Total.addView(new TextViewBuilder(this, String.valueOf(0)).build());
                        }else{
                            Total.addView(new TextViewBuilder(this, String.valueOf(firstpay)).build());
                            da.add(new Entry(i+1, (float)firstpay));
                        }
                        Perc.addView(new TextViewBuilder(this, String.valueOf(100 * sum / sumt)).build());
                        Left.addView(new TextViewBuilder(this, String.valueOf(sumt - sum)).build());
                        Sum.addView(new TextViewBuilder(this, String.valueOf(sum)).build());
                    }else {
                        Total.addView(new TextViewBuilder(this, String.valueOf(firstpay)).build());
                        Perc.addView(new TextViewBuilder(this, String.valueOf(100 * sum / sumt)).build());
                        Left.addView(new TextViewBuilder(this, String.valueOf(sumt - sum)).build());
                        Sum.addView(new TextViewBuilder(this, String.valueOf(sum)).build());
                        da.add(new Entry(i+1, (float) firstpay));
                    }
                    firstpay-=delp;
                }
                //chart.clear();
                ArrayList<ILineDataSet> d = new ArrayList<>();
                LineDataSet set = new LineDataSet(da, "Mokejimai");
                set.setColor(Color.DKGRAY);
                set.setDrawCircles(false);
                set.setDrawValues(false);
                set.setLineWidth(3);
                d.add(set);
                LineData dataaa = new LineData(d);
                chart.setData(dataaa);
                chart.invalidate();
                chart.notifyDataSetChanged();
            }else if(sud.isChecked()){
                percent/=100;
                double sumt = Math.pow(1+percent, N);
                sumt*=total;
                double sum = 0;
                double mok = sumt/N;
                for(int i = 0; i < N + atl; i++){
                    if(atid.isChecked() && i>at && i<at+atl){
                        Total.addView(new TextViewBuilder(this, String.valueOf(0)).build());
                        da.add(new Entry(i+1, 0));
                    }else{
                        Total.addView(new TextViewBuilder(this, String.valueOf(mok)).build());
                        da.add(new Entry(i+1, (float)mok));
                    }
                    sum+=mok;
                    Perc.addView(new TextViewBuilder(this, String.valueOf(100 * sum / sumt)).build());
                    Left.addView(new TextViewBuilder(this, String.valueOf(sumt - sum)).build());
                    Sum.addView(new TextViewBuilder(this, String.valueOf(sum)).build());
                }
                ArrayList<ILineDataSet> d = new ArrayList<>();
                LineDataSet set = new LineDataSet(da, "Mokejimai");
                set.setColor(Color.DKGRAY);
                set.setDrawCircles(false);
                set.setLineWidth(3);
                set.setDrawValues(false);
                d.add(set);
                LineData dataaa = new LineData(d);
                chart.setData(dataaa);
                chart.invalidate();
                chart.notifyDataSetChanged();
            }else{
                percent/=100;
                double TMP = Math.pow(1+percent, N);
                double pay = TMP*percent;
                pay/= TMP-1;
                double sum = 0;
                pay*=total;
                double sumt = total * (1+percent);
                for(int i = 0; i < N + atl; i++){
                    if(atid.isChecked()&&i>at && i<at+atl){
                        Total.addView(new TextViewBuilder(this, String.valueOf(0)).build());
                        da.add(new Entry(i+1, 0));
                    }else{
                        Total.addView(new TextViewBuilder(this, String.valueOf(pay)).build());
                        da.add(new Entry(i+1, (float)pay));
                    }

                    sum+=pay;
                    Perc.addView(new TextViewBuilder(this, String.valueOf(100 * sum / sumt)).build());
                    Left.addView(new TextViewBuilder(this, String.valueOf(sumt - sum)).build());
                    Sum.addView(new TextViewBuilder(this, String.valueOf(sum)).build());
                }
                ArrayList<ILineDataSet> d = new ArrayList<>();
                LineDataSet set = new LineDataSet(da, "Mokejimai");
                set.setColor(Color.DKGRAY);
                set.setDrawCircles(false);
                set.setDrawValues(false);
                set.setLineWidth(3);
                d.add(set);
                LineData dataaa = new LineData(d);
                chart.setData(dataaa);
                chart.invalidate();
                chart.notifyDataSetChanged();
            }
        });
        filter.setOnClickListener((v) ->{
            int L = Integer.parseInt(low.getText().toString());
            int H = Integer.parseInt(high.getText().toString());
            chart.getXAxis().setAxisMaximum(H);
            chart.getXAxis().setAxisMinimum(L);
            chart.setVisibleXRange(L, H);
            chart.moveViewToX(L);
            chart.setVisibleXRangeMinimum(H-L);
            chart.setVisibleXRangeMaximum(H-L);
            chart.invalidate();
            chart.notifyDataSetChanged();
        });
    }


}