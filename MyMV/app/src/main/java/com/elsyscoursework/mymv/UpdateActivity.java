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

        // get the information we need
        Vehicle vehicle = Vehicle.findById(Vehicle.class, Long.valueOf(idItemAtPosition));
        String type = vehicle.getType();
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();

        // find the right text views to show/display the information
        TextView typeTextView = (TextView) findViewById(R.id.update_type_textview);
        TextView manufacturerTextView = (TextView) findViewById(R.id.update_manufacturer_textview);
        TextView modelTextView = (TextView) findViewById(R.id.update_model_textview);

        // setting the values to the text views
        typeTextView.setText(type);
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);

        History vehicleHistory = History.findById(History.class, Long.valueOf(idItemAtPosition));
        String owner = vehicleHistory.getOwner();
        String productionYear = Integer.toString(vehicleHistory.getProductionYear());
        String previousOwners = Integer.toString(vehicleHistory.getPreviousOwners());
        String kilometerage = Integer.toString(vehicleHistory.getKilometerage());

        final EditText ownerTextView = (EditText) findViewById(R.id.update_owner_textview);
        final EditText productionYearTextView = (EditText) findViewById(R.id.update_production_year_textview);
        final EditText previousOwnersTextView = (EditText) findViewById(R.id.update_previous_owners_textview);
        final EditText kilometerageTextView = (EditText) findViewById(R.id.update_kilometerage_textview);

        ownerTextView.setText(owner);
        productionYearTextView.setText(productionYear);
        previousOwnersTextView.setText(previousOwners);
        kilometerageTextView.setText(kilometerage);

        Oil vehicleOil = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));
        String changedAt = Integer.toString(vehicleOil.getChangedAt());
        String nextChangeAt = Integer.toString(vehicleOil.getNextChangeAt());

        final EditText changedAtTextView = (EditText) findViewById(R.id.update_last_oil_change_textview);

        changedAtTextView.setText(changedAt);

        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History historyToUpdate = History.findById(History.class, Long.valueOf(idItemAtPosition));
                historyToUpdate.setOwner(ownerTextView.getText().toString());
                historyToUpdate.setProductionYear(Integer.parseInt(productionYearTextView.getText().toString()));
                historyToUpdate.setPreviousOwners(Integer.parseInt(previousOwnersTextView.getText().toString()));
                historyToUpdate.setKilometerage(Integer.parseInt(kilometerageTextView.getText().toString()));
                historyToUpdate.save();

                Oil oilToUpdate = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));
                oilToUpdate.setChangedAt(Integer.parseInt(changedAtTextView.getText().toString()));
                oilToUpdate.save();

                finish();
            }
        });

    }
}
