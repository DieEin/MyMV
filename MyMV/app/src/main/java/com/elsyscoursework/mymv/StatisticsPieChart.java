package com.elsyscoursework.mymv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Tomi on 7.2.2017 г..
 */

public class StatisticsPieChart extends AppCompatActivity {

    private PieChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        String[] values = receivedIntent.getExtras().getStringArray("maintenance values needed for pie chart");

        mChart = new PieChart(this);
        mChart.setUsePercentValues(true);
        setContentView(mChart);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i = 0; i < values.length; i++) {
            String entryValueText = values[i].split(Pattern.quote(": $"))[0];
            values[i] = values[i].replaceAll("\\D+", "");
            int entryValue = Integer.valueOf(values[i]);

            entries.add(new PieEntry(entryValue, entryValueText));
        }
        /*entries.add(new PieEntry(60, "stuff"));
        entries.add(new PieEntry(80, "stuff2"));
        entries.add(new PieEntry(20, "stuff3"));*/

        PieDataSet dataSet = new PieDataSet(entries, "testing");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        mChart.setData(data);
        mChart.setTransparentCircleColor(Color.WHITE);
        Description chartDescription = new Description();
        chartDescription.setText("does it work?");
        chartDescription.setTextSize(20);
        mChart.setDescription(chartDescription);
        mChart.getLegend().setTextSize(15);
    }
}
