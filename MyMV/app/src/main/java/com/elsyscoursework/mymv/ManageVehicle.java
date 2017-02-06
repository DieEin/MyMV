package com.elsyscoursework.mymv;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageVehicle extends AppCompatActivity {

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

        // get the information we need
        Vehicle vehicle = Vehicle.findById(Vehicle.class, Long.valueOf(idItemAtPosition));
        String type = vehicle.getType();
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();

        // find the right text views to show/display the information
        TextView typeTextView = (TextView) findViewById(R.id.manage_vehicle_type_textview);
        TextView manufacturerTextView = (TextView) findViewById(R.id.manage_vehicle_manufacturer_textview);
        TextView modelTextView = (TextView) findViewById(R.id.manage_vehicle_model_textview);

        // setting the values to the text views
        typeTextView.setText(type);
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);

        History vehicleHistory = History.findById(History.class, Long.valueOf(idItemAtPosition));
        String owner = vehicleHistory.getOwner();
        String productionYear = Integer.toString(vehicleHistory.getProductionYear());
        String previousOwners = Integer.toString(vehicleHistory.getPreviousOwners());
        String kilometerage = Integer.toString(vehicleHistory.getKilometerage());

        TextView ownerTextView = (TextView) findViewById(R.id.vehicle_owner_textview);
        TextView productionYearTextView = (TextView) findViewById(R.id.vehicle_production_year_textview);
        TextView previousOwnersTextView = (TextView) findViewById(R.id.vehicle_previous_owners_textview);
        TextView kilometerageTextView = (TextView) findViewById(R.id.vehicle_kilometerage_textview);

        ownerTextView.setText(owner);
        productionYearTextView.setText(productionYear);
        previousOwnersTextView.setText(previousOwners);
        kilometerageTextView.setText(kilometerage);

        ownerTextView.setOnClickListener(new ManageVehicleOnClickListener(ManageVehicle.this, idItemAtPosition));

        Oil vehicleOil = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));
        String changedAt = Integer.toString(vehicleOil.getChangedAt());
        String nextChangeAt = Integer.toString(vehicleOil.getNextChangeAt());

        TextView changedAtTextView = (TextView) findViewById(R.id.vehicle_last_oil_change_textview);
        TextView nextChangeAtTextView = (TextView) findViewById(R.id.vehicle_next_oil_change_textview);

        int kilometerageAsInteger = Integer.parseInt(kilometerage);
        int changedAtAsInteger = Integer.parseInt(changedAt);
        int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);

        int distanceWithoutOilChange = kilometerageAsInteger - changedAtAsInteger;
        int nextOilChangeAtAsInteger = 8000 - distanceWithoutOilChange;
        nextChangeAt = String.valueOf(nextOilChangeAtAsInteger);

        if (nextChangeAtAsInteger != nextOilChangeAtAsInteger) {
            vehicleOil.setNextChangeAt(nextOilChangeAtAsInteger);
            vehicleOil.save();

            startService(new Intent(this, NotificationService.class));
        }

        changedAtTextView.setText(changedAt);
        nextChangeAtTextView.setText(nextChangeAt);

        TextView maintenanceTextView = (TextView) findViewById(R.id.vehicle_maintenance);
        maintenanceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToVehicleMaintenance = new Intent(ManageVehicle.this, VehicleMaintenance.class);
                goToVehicleMaintenance.putExtra(PASSED_VARIABLE_NAME, idItemAtPosition);
                startActivity(goToVehicleMaintenance);
            }
        });

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
                Vehicle vehicleToDelete = Vehicle.findById(Vehicle.class, Long.valueOf(idItemAtPosition));
                vehicleToDelete.delete();

                History historyToDelete = History.findById(History.class, Long.valueOf(idItemAtPosition));
                historyToDelete.delete();

                Oil oilToDelete = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));
                oilToDelete.delete();

                finish();
            }
        });

    }
}
