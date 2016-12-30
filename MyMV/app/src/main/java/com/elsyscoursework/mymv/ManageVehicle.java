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

        TextView testt = (TextView) findViewById(R.id.testt);
        testt.setText(db.showTest(idItemAtPosition));

        // the update button and what happens when it gets clicked
        Button updateButton = (Button) findViewById(R.id.manage_vehicle_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText testttt = (EditText) findViewById(R.id.testttt);

                db.update(testttt.getText().toString(), idItemAtPosition);
                finish();
                startActivity(getIntent());
            }
        });

    }
}
