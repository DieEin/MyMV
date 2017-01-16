package com.elsyscoursework.mymv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.StringTokenizer;

public class UpdateActivity extends AppCompatActivity {

    //private HelperSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // the variable needed in this activity - the vehicle's id
        final String PASSED_VARIABLE_NAME = "idItemAtPosition";
        // default value if it wasn't passed
        final int DEFAULT_PASSED_INT_VALUE = 0;

        // receive the variable
        Intent receivedIntent = getIntent();
        final int idItemAtPosition = receivedIntent.getIntExtra(PASSED_VARIABLE_NAME, DEFAULT_PASSED_INT_VALUE);

        // show what we received (the id)
        Toast toast = Toast.makeText(UpdateActivity.this, String.valueOf(idItemAtPosition), Toast.LENGTH_SHORT);
        toast.show();

        // instance of the database
        //db = new HelperSQL(getApplicationContext());

        // get the information we need
        /*HashMap<String, String> vehicle = db.getVehicleFromId(idItemAtPosition);
        String type = vehicle.get("type");
        String manufacturer = vehicle.get("manufacturer");
        String model = vehicle.get("model");*/
        Vehicle vehicle = Vehicle.findById(Vehicle.class, Long.valueOf(idItemAtPosition));
        String type = vehicle.type;
        String manufacturer = vehicle.manufacturer;
        String model = vehicle.model;

        // find the right text views to show/display the information
        TextView typeTextView = (TextView) findViewById(R.id.update_type_textview);
        TextView manufacturerTextView = (TextView) findViewById(R.id.update_manufacturer_textview);
        TextView modelTextView = (TextView) findViewById(R.id.update_model_textview);

        // setting the values to the text views
        typeTextView.setText(type);
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);

        /*
        HashMap<String, String> vehicleHistory = db.getVehicleHistoryFromId(idItemAtPosition);
        String owner = vehicleHistory.get("owner");
        String productionYear = vehicleHistory.get("production_year");
        String previousOwners = vehicleHistory.get("previous_owners");
        String kilometerage = vehicleHistory.get("kilometerage");

        final EditText ownerTextView = (EditText) findViewById(R.id.update_owner_textview);
        final EditText productionYearTextView = (EditText) findViewById(R.id.update_production_year_textview);
        final EditText previousOwnersTextView = (EditText) findViewById(R.id.update_previous_owners_textview);
        final EditText kilometerageTextView = (EditText) findViewById(R.id.update_kilometerage_textview);

        ownerTextView.setText(owner);
        productionYearTextView.setText(productionYear);
        previousOwnersTextView.setText(previousOwners);
        kilometerageTextView.setText(kilometerage);

        HashMap<String, String> vehicleOil = db.getVehicleOilFromId(idItemAtPosition);
        String changedAt = vehicleOil.get("changed_at");
        String nextChangeAt = vehicleOil.get("next_change_at");

        final EditText changedAtTextView = (EditText) findViewById(R.id.update_last_oil_change_textview);

        changedAtTextView.setText(changedAt);*/

        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*db.update("history", "owner", ownerTextView.getText().toString(), idItemAtPosition);
                db.update("history", "production_year", productionYearTextView.getText().toString(), idItemAtPosition);
                db.update("history", "previous_owners", previousOwnersTextView.getText().toString(), idItemAtPosition);
                db.update("history", "kilometerage", kilometerageTextView.getText().toString(), idItemAtPosition);

                db.update("oil", "changed_at", changedAtTextView.getText().toString(), idItemAtPosition);*/

                finish();
            }
        });

    }
}
