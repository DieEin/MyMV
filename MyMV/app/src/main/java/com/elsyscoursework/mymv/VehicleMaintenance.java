package com.elsyscoursework.mymv;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    public static String SPECIFIC_VALUE_NAME = "specific value name";

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

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

        final ListView maintenanceListView = (ListView) findViewById(R.id.maintenance_list_view);
        maintenanceListView.setAdapter(new VehicleMaintenanceArrayAdapter(this, vehicleMaintenanceList));

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

                LinearLayout nameLayout = new LinearLayout(VehicleMaintenance.this);
                nameLayout.setOrientation(LinearLayout.HORIZONTAL);

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

                TextView nameTextView = new TextView(VehicleMaintenance.this);
                nameTextView.setText("Name:");
                nameTextView.setTextSize(TEXT_SIZE);

                final EditText setName = new EditText(VehicleMaintenance.this);
                setName.setTextSize(TEXT_SIZE);
                setName.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));

                nameLayout.addView(nameTextView);
                nameLayout.addView(setName);

                layout.addView(nameLayout);

                dialog.setView(layout);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Maintenance newMaintenance = new Maintenance(setType.getSelectedItem().toString(), setName.getText().toString(), setDate.getText().toString(),
                                Integer.valueOf(setPrice.getText().toString()), idItemAtPosition);

                        newMaintenance.save();

                        if (setType.getSelectedItem().toString().equals("Gas")) {
                            dialog.dismiss();

                            AlertDialog.Builder innerDialog;
                            final int TEXT_SIZE = 20;

                            // layout to fill the dialog
                            LinearLayout innerLayout = new LinearLayout(VehicleMaintenance.this);
                            innerLayout.setOrientation(LinearLayout.VERTICAL);

                            // the dialog
                            innerDialog = new AlertDialog.Builder(VehicleMaintenance.this);

                            TextView setDialogText = new TextView(VehicleMaintenance.this);
                            final EditText setDialogEditText = new EditText(VehicleMaintenance.this);

                            setDialogText.setText("Kilometerage:");
                            setDialogText.setTextSize(TEXT_SIZE);

                            String textToSet3 = String.valueOf((History.findById(History.class, Long.valueOf(idItemAtPosition))).getKilometerage());
                            setDialogEditText.setText(textToSet3);
                            setDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                            innerLayout.addView(setDialogText);
                            innerLayout.addView(setDialogEditText);
                            innerDialog.setView(innerLayout);

                            innerDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    History history  = History.findById(History.class, Long.valueOf(idItemAtPosition));
                                    history.setKilometerage(Integer.valueOf(setDialogEditText.getText().toString()));
                                    history.save();

                                    History vehicleHistory = History.findById(History.class, Long.valueOf(idItemAtPosition));
                                    String kilometerage = Integer.toString(vehicleHistory.getKilometerage());

                                    Oil vehicleOil = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));
                                    String changedAt = Integer.toString(vehicleOil.getChangedAt());
                                    String nextChangeAt = Integer.toString(vehicleOil.getNextChangeAt());

                                    int kilometerageAsInteger = Integer.parseInt(kilometerage);
                                    int changedAtAsInteger = Integer.parseInt(changedAt);
                                    int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);

                                    int distanceWithoutOilChange = kilometerageAsInteger - changedAtAsInteger;
                                    int nextOilChangeAtAsInteger = 8000 - distanceWithoutOilChange;
                                    nextChangeAt = String.valueOf(nextOilChangeAtAsInteger);

                                    if (nextChangeAtAsInteger != nextOilChangeAtAsInteger) {
                                        vehicleOil.setNextChangeAt(nextOilChangeAtAsInteger);
                                        vehicleOil.save();

                                        startService(new Intent(VehicleMaintenance.this, NotificationService.class));
                                    }

                                    finish();
                                    startActivity(getIntent());
                                }
                            });

                            innerDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                @Override
                                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                                        finish();
                                        startActivity(getIntent());

                                        return true;
                                    }

                                    return false;
                                }
                            });

                            AlertDialog kilometerageDialogShow = innerDialog.create();
                            kilometerageDialogShow.show();
                        } else {
                            finish();
                            startActivity(getIntent());
                        }

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
                displayStatistics.putExtra(PASSED_VARIABLE_NAME, idItemAtPosition);
                startActivity(displayStatistics);
            }
        });

        ImageButton popupMenuButton = (ImageButton) findViewById(R.id.popup_menu_button);
        popupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(VehicleMaintenance.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent goToSpecificMaintenance = new Intent(VehicleMaintenance.this, SpecificMaintenance.class);
                        goToSpecificMaintenance.putExtra(PASSED_VARIABLE_NAME, idItemAtPosition);
                        goToSpecificMaintenance.putExtra(SPECIFIC_VALUE_NAME, item.toString());
                        startActivity(goToSpecificMaintenance);

                        return true;
                    }
                });
                inflater.inflate(R.menu.maintenance_popup_menu, popup.getMenu());
                popup.show();
            }
        });
    }
}
