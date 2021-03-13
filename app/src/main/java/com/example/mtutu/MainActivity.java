package com.example.mtutu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BarChart stackedBar;
    int[] color = new int[] {Color.RED,Color.GREEN,Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stackedBar = findViewById(R.id.stacked_chartbar);

        BarDataSet barDataSet = new BarDataSet(dataValue1(),"bar_Set");
        barDataSet.setColors(color);
        BarData data = new BarData(barDataSet);
        data.setDrawValues(false);

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");

        stackedBar.setData(data);

        XAxis xAxis = stackedBar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));


        stackedBar.setTouchEnabled(false);
        stackedBar.setClickable(false);
        stackedBar.setDoubleTapToZoomEnabled(false);
        stackedBar.setDoubleTapToZoomEnabled(false);

        // border cả khung
        stackedBar.setDrawBorders(true);
        // màu bg
        stackedBar.setDrawGridBackground(true);
        // mô tả label
        stackedBar.getDescription().setEnabled(true);
        // note màu
        stackedBar.getLegend().setEnabled(true);

        // hàng ngang bên trong

        stackedBar.getAxisLeft().setDrawGridLines(true);
        // giá trị thanh dọc Right
        stackedBar.getAxisLeft().setDrawLabels(true);
        // thanh dọc ngoài left  (Đậm hơn tẹo)
        stackedBar.getAxisLeft().setDrawAxisLine(true);


        // thanh dọc
        stackedBar.getXAxis().setDrawGridLines(true);
        // giá trị thanh dọc top
        stackedBar.getXAxis().setDrawLabels(true);
        // thanh ngang trên cùng
        stackedBar.getXAxis().setDrawAxisLine(true);

        // thanh ngang trong
        stackedBar.getAxisRight().setDrawGridLines(true);
        // giá trị thanh dọc Right
        stackedBar.getAxisRight().setDrawLabels(true);
        // thanh dọc ngoài right (Đậm hơn tẹo)
        stackedBar.getAxisRight().setDrawAxisLine(true);


    }
    private ArrayList<BarEntry> dataValue1() {
         ArrayList<BarEntry> list = new ArrayList<>();
         BarEntry barEntry1 = new BarEntry(0,new float[] {2,5.5f,2});
         list.add(barEntry1);
         list.add(new BarEntry(1,new float[] {3,8.5f,2}));
         list.add(new BarEntry(2,new float[] {2,5,2}));
         return list;
    }
}