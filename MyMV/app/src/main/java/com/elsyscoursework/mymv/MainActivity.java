package com.elsyscoursework.mymv;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private HelperSQL db;
    private AlertDialog.Builder dialog;
    private final int TEXT_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get db information for every vehicle
        db = new HelperSQL(getApplicationContext());
        ArrayList<String> vehicle = db.getVehicleForList();
        String[] vehicleInformation = new String[vehicle.size()];
        int counter = 0;
        for(String str : vehicle) {
            vehicleInformation[counter] = str;
            counter++;
        }

        // display vehicles as list view
        ListAdapter vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicleInformation);
        final ListView vehicleListView = (ListView) findViewById(R.id.vehicle_list);
        vehicleListView.setAdapter(vehicleAdapter);

        // add_new button and what happens when it gets clicked
        Button add_vehicle_button = (Button) findViewById(R.id.add_vehicle_button);
        add_vehicle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // layout to fill the dialog
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // the dialog
                dialog = new AlertDialog.Builder(MainActivity.this);

                // set type text view
                TextView setTypeText = new TextView(MainActivity.this);
                setTypeText.setText("Set vehicle type:");
                setTypeText.setTextSize(TEXT_SIZE);

                // set type dropdown
                final Spinner setType = new Spinner(MainActivity.this);
                String[] options = new String[]{"Car", "Bike", "Bus", "Truck"};
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
                setType.setAdapter(typeAdapter);

                // set manufacturer text view
                TextView setManufacturerText = new TextView(MainActivity.this);
                setManufacturerText.setText("Set vehicle manufacturer:");
                setManufacturerText.setTextSize(TEXT_SIZE);

                // set manufacturer
                final EditText setManufacturer = new EditText(MainActivity.this);

                // set model text view
                TextView setModelText = new TextView(MainActivity.this);
                setModelText.setText("Set vehicle model:");
                setModelText.setTextSize(TEXT_SIZE);

                // set model
                final EditText setModel = new EditText(MainActivity.this);

                // adding it add to the layout
                layout.addView(setTypeText);
                layout.addView(setType);
                layout.addView(setManufacturerText);
                layout.addView(setManufacturer);
                layout.addView(setModelText);
                layout.addView(setModel);

                // set the layout for the dialog
                dialog.setView(layout);
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.addVehicle(setType.getSelectedItem().toString(), setManufacturer.getText().toString(), setModel.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                });

                AlertDialog dialogShow = dialog.create();
                dialogShow.show();
            }
        });
    }
}
