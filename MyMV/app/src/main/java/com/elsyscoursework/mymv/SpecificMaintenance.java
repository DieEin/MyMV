package com.elsyscoursework.mymv;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tomi on 10.2.2017 г..
 */

public class SpecificMaintenance extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.specific_maintenance);

        // the variable needed in this activity - the vehicle's id
        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        // default value if it wasn't passed
        final int DEFAULT_PASSED_INT_VALUE = 0;

        Intent receivedIntent = getIntent();
        final int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        String specificValue = receivedIntent.getStringExtra(VehicleMaintenance.SPECIFIC_VALUE_NAME);

        List<Maintenance> specificMaintenanceList = Maintenance.find(Maintenance.class, "type = ? and vehicle_id = ?", specificValue, String.valueOf(idItemAtPosition));

        String[] specificMaintenance = new String[specificMaintenanceList.size()];
        int counter = 0;
        for (Maintenance m : specificMaintenanceList) {
            specificMaintenance[counter] = m.getDate() + " " + m.getName();

            counter++;
        }

        ListAdapter specificMaintenanceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specificMaintenance);
        ListView specificMaintenanceListView = (ListView) findViewById(R.id.specific_maintenance_list);
        specificMaintenanceListView.setAdapter(specificMaintenanceAdapter);
    }
}
