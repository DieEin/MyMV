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
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Tomi on 7.2.2017 г..
 */

public class StatisticsPieChart extends AppCompatActivity {

    private PieChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the variable needed in this activity - the vehicle's id
        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        // default value if it wasn't passed
        final int DEFAULT_PASSED_INT_VALUE = 0;

        Intent receivedIntent = getIntent();
        final int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        final List<Maintenance> maintenanceList = Maintenance.find(Maintenance.class, "vehicle_id = ?", String.valueOf(idItemAtPosition));

        int gasSum = 0;
        int fluidsSum = 0;
        int tyresSum = 0;
        int otherSum = 0;
        for (Maintenance m : maintenanceList) {
            switch (m.getType()) {
                case "Gas":
                    gasSum += m.getPrice();
                    break;
                case "Fluids":
                    fluidsSum += m.getPrice();
                    break;
                case "Tyres":
                    tyresSum += m.getPrice();
                    break;
                case "Other":
                    otherSum += m.getPrice();
                    break;
            }
        }

        mChart = new PieChart(this);
        mChart.setUsePercentValues(true);
        setContentView(mChart);

        ArrayList<PieEntry> entries = new ArrayList<>();

        if (gasSum != 0) {
            entries.add(new PieEntry(gasSum, "Gas"));
        }

        if (fluidsSum != 0) {
            entries.add(new PieEntry(fluidsSum, "Fluids"));
        }

        if (tyresSum != 0) {
            entries.add(new PieEntry(tyresSum, "Tyres"));
        }

        if (otherSum != 0) {
            entries.add(new PieEntry(otherSum, "Other"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "type");
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
