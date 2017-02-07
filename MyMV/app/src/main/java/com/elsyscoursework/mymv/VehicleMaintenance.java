package com.elsyscoursework.mymv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Wrapper;
import java.util.List;

/**
 * Created by Tomi on 6.2.2017 г..
 */

public class VehicleMaintenance extends AppCompatActivity {

    private AlertDialog.Builder dialog;
    private final int TEXT_SIZE = 20;
    private final int EDIT_TEXT_WIDTH = 80;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vehicle_maintenance_activity);

        // the variable needed in this activity - the vehicle's id
        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        // default value if it wasn't passed
        final int DEFAULT_PASSED_INT_VALUE = 0;

        Intent receivedIntent = getIntent();
        final int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        final List<Maintenance> maintenanceList = Maintenance.find(Maintenance.class, "vehicle_id = ?", String.valueOf(idItemAtPosition));
        final String[] vehicleMaintenanceList = new String[maintenanceList.size()];

        int counter = 0;
        for(Maintenance m : maintenanceList) {
            vehicleMaintenanceList[counter] = m.getType() + ": $" + m.getPrice();

            counter++;
        }

        ListAdapter maintenanceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicleMaintenanceList);
        final ListView maintenanceListView = (ListView) findViewById(R.id.maintenance_list_view);
        maintenanceListView.setAdapter(maintenanceAdapter);

        Button addButton = (Button) findViewById(R.id.maintenance_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // layout to fill the dialog
                LinearLayout layout = new LinearLayout(VehicleMaintenance.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER_VERTICAL);

                LinearLayout typeLayout = new LinearLayout(VehicleMaintenance.this);
                typeLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout dateLayout = new LinearLayout(VehicleMaintenance.this);
                typeLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout priceLayout = new LinearLayout(VehicleMaintenance.this);
                typeLayout.setOrientation(LinearLayout.HORIZONTAL);

                // the dialog
                dialog = new AlertDialog.Builder(VehicleMaintenance.this);

                TextView typeTextView = new TextView(VehicleMaintenance.this);
                typeTextView.setText("Type:");
                typeTextView.setTextSize(TEXT_SIZE);

                final Spinner setType = new Spinner(VehicleMaintenance.this);
                String[] options = {"Gas", "Fluids", "Tyres", "Other"};
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(VehicleMaintenance.this, android.R.layout.simple_spinner_dropdown_item, options);
                setType.setAdapter(typeAdapter);

                typeLayout.addView(typeTextView);
                typeLayout.addView(setType);

                layout.addView(typeLayout);

                TextView dateTextView = new TextView(VehicleMaintenance.this);
                dateTextView.setText("Date:");
                dateTextView.setTextSize(TEXT_SIZE);

                final EditText setDate = new EditText(VehicleMaintenance.this);
                setDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                setDate.setTextSize(TEXT_SIZE);
                setDate.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));

                dateLayout.addView(dateTextView);
                dateLayout.addView(setDate);

                layout.addView(dateLayout);

                TextView priceTextView = new TextView(VehicleMaintenance.this);
                priceTextView.setText("Price: $");
                priceTextView.setTextSize(TEXT_SIZE);

                final EditText setPrice = new EditText(VehicleMaintenance.this);
                setPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                setPrice.setTextSize(TEXT_SIZE);
                setPrice.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));

                priceLayout.addView(priceTextView);
                priceLayout.addView(setPrice);

                layout.addView(priceLayout);

                dialog.setView(layout);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Maintenance newMaintenance = new Maintenance(setType.getSelectedItem().toString(), setDate.getText().toString(),
                                Integer.valueOf(setPrice.getText().toString()), idItemAtPosition);

                        newMaintenance.save();

                        finish();
                        startActivity(getIntent());

                    }
                });

                AlertDialog dialogShow = dialog.create();
                dialogShow.show();
            }
        });

        Button statisticsButton = (Button) findViewById(R.id.maintenance_statistics_button);
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayStatistics = new Intent(VehicleMaintenance.this, StatisticsPieChart.class);
                displayStatistics.putExtra("maintenance values needed for pie chart", vehicleMaintenanceList);
                startActivity(displayStatistics);
            }
        });
    }
}
