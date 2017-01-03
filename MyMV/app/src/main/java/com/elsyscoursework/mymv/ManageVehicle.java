package com.elsyscoursework.mymv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageVehicle extends AppCompatActivity {

    private HelperSQL db;

    // restart activity in order to update on-screen information after back button was clicked
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicle);

        // the variable needed in this activity - the vehicle's id
        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        // default value if it wasn't passed
        final int DEFAULT_PASSED_INT_VALUE = 0;

        // receive the variable
        Intent receivedIntent = getIntent();
        final int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        // show what we received (the id)
        Toast toast = Toast.makeText(ManageVehicle.this, String.valueOf(idItemAtPosition), Toast.LENGTH_SHORT);
        toast.show();

        // instance of the database
        db = new HelperSQL(getApplicationContext());

        // get the information we need
        HashMap<String, String> vehicle = db.getVehicleFromId(idItemAtPosition);
        String type = vehicle.get("type");
        String manufacturer = vehicle.get("manufacturer");
        String model = vehicle.get("model");

        // find the right text views to show/display the information
        TextView typeTextView = (TextView) findViewById(R.id.manage_vehicle_type_textview);
        TextView manufacturerTextView = (TextView) findViewById(R.id.manage_vehicle_manufacturer_textview);
        TextView modelTextView = (TextView) findViewById(R.id.manage_vehicle_model_textview);

        // setting the values to the text views
        typeTextView.setText(type);
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);

        HashMap<String, String> vehicleHistory = db.getVehicleHistoryFromId(idItemAtPosition);
        String owner = vehicleHistory.get("owner");
        String productionYear = vehicleHistory.get("production_year");
        String previousOwners = vehicleHistory.get("previous_owners");
        String kilometerage = vehicleHistory.get("kilometerage");

        TextView ownerTextView = (TextView) findViewById(R.id.vehicle_owner_textview);
        TextView productionYearTextView = (TextView) findViewById(R.id.vehicle_production_year_textview);
        TextView previousOwnersTextView = (TextView) findViewById(R.id.vehicle_previous_owners_textview);
        TextView kilometerageTextView = (TextView) findViewById(R.id.vehicle_kilometerage_textview);

        ownerTextView.setText(owner);
        productionYearTextView.setText(productionYear);
        previousOwnersTextView.setText(previousOwners);
        kilometerageTextView.setText(kilometerage);

        HashMap<String, String> vehicleOil = db.getVehicleOilFromId(idItemAtPosition);
        String changedAt = vehicleOil.get("changed_at");
        String nextChangeAt = vehicleOil.get("next_change_at");

        TextView changedAtTextView = (TextView) findViewById(R.id.vehicle_last_oil_change_textview);
        TextView nextChangeAtTextView = (TextView) findViewById(R.id.vehicle_next_oil_change_textview);

        int kilometerageAsInteger = Integer.parseInt(kilometerage);
        int changedAtAsInteger = Integer.parseInt(changedAt);
        int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);

        int distanceWithoutOilChange = kilometerageAsInteger - changedAtAsInteger;
        int nextOilChangeAtAsInteger = 8000 - distanceWithoutOilChange;
        nextChangeAt = String.valueOf(nextOilChangeAtAsInteger);

        if (nextChangeAtAsInteger != nextOilChangeAtAsInteger) {
            db.update("oil", "next_change_at", nextChangeAt, idItemAtPosition);
        }

        changedAtTextView.setText(changedAt);
        nextChangeAtTextView.setText(nextChangeAt);

        // the update button and what happens when it gets clicked
        Button updateButton = (Button) findViewById(R.id.manage_vehicle_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUpdate = new Intent(ManageVehicle.this, UpdateActivity.class);
                goToUpdate.putExtra(PASSED_VARIABLE_NAME, idItemAtPosition);
                startActivity(goToUpdate);
            }
        });

        Button deleteButton = (Button) findViewById(R.id.manage_vehicle_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(idItemAtPosition);
                finish();
            }
        });

    }
}
