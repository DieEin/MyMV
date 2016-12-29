package com.elsyscoursework.mymv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageVehicle extends AppCompatActivity {

    private HelperSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicle);

        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        final int DEFAULT_PASSED_INT_VALUE = 0;

        Intent receivedIntent = getIntent();
        int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        Toast toast = Toast.makeText(ManageVehicle.this, String.valueOf(idItemAtPosition), Toast.LENGTH_SHORT);
        toast.show();

        db = new HelperSQL(getApplicationContext());

        HashMap<String, String> vehicle = db.getVehicleFromId(idItemAtPosition);
        String type = vehicle.get("type");
        String manufacturer = vehicle.get("manufacturer");
        String model = vehicle.get("model");

        TextView typeTextView = (TextView) findViewById(R.id.manage_vehicle_type_textview);
        TextView manufacturerTextView = (TextView) findViewById(R.id.manage_vehicle_manufacturer_textview);
        TextView modelTextView = (TextView) findViewById(R.id.manage_vehicle_model_textview);

        typeTextView.setText(type);
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);

    }
}
