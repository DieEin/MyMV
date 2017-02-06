package com.elsyscoursework.mymv;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Tomi on 6.2.2017 г..
 */

public class ManageVehicleOnClickListener implements View.OnClickListener {

    Context context;
    int idItemAtPosition;

    private AlertDialog.Builder dialog;
    private final int TEXT_SIZE = 20;

    public ManageVehicleOnClickListener(Context context, int idItemAtPosition) {
        this.context = context;
        this.idItemAtPosition = idItemAtPosition;
    }

    @Override
    public void onClick(View v) {
        // layout to fill the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // the dialog
        dialog = new AlertDialog.Builder(context);

        TextView setDialogText = new TextView(context);
        final EditText setDialogEditText = new EditText(context);

        switch (v.getId()) {
            case R.id.vehicle_owner_textview:
                setDialogText.setText("Owner:");
                setDialogText.setTextSize(TEXT_SIZE);

                setDialogEditText.setText(getHistoryById(idItemAtPosition).getOwner());

                layout.addView(setDialogText);
                layout.addView(setDialogEditText);
                dialog.setView(layout);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        History history  = getHistoryById(idItemAtPosition);
                        history.setOwner(setDialogEditText.getText().toString());
                        history.save();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog ownerDialogShow = dialog.create();
                ownerDialogShow.show();

                break;
            case R.id.vehicle_production_year_textview:
                setDialogText.setText("Production year:");
                setDialogText.setTextSize(TEXT_SIZE);

                String textToSet = String.valueOf(getHistoryById(idItemAtPosition).getProductionYear());
                setDialogEditText.setText(textToSet);
                setDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(setDialogText);
                layout.addView(setDialogEditText);
                dialog.setView(layout);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        History history  = getHistoryById(idItemAtPosition);
                        history.setProductionYear(Integer.valueOf(setDialogEditText.getText().toString()));
                        history.save();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog productionYearDialogShow = dialog.create();
                productionYearDialogShow.show();

                break;
            case R.id.vehicle_previous_owners_textview:
                setDialogText.setText("Previous owners:");
                setDialogText.setTextSize(TEXT_SIZE);

                String textToSet2 = String.valueOf(getHistoryById(idItemAtPosition).getPreviousOwners());
                setDialogEditText.setText(textToSet2);
                setDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(setDialogText);
                layout.addView(setDialogEditText);
                dialog.setView(layout);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        History history  = getHistoryById(idItemAtPosition);
                        history.setPreviousOwners(Integer.valueOf(setDialogEditText.getText().toString()));
                        history.save();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog previousOwnersDialogShow = dialog.create();
                previousOwnersDialogShow.show();

                break;
            case R.id.vehicle_kilometerage_textview:
                setDialogText.setText("Kilometerage:");
                setDialogText.setTextSize(TEXT_SIZE);

                String textToSet3 = String.valueOf(getHistoryById(idItemAtPosition).getKilometerage());
                setDialogEditText.setText(textToSet3);
                setDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(setDialogText);
                layout.addView(setDialogEditText);
                dialog.setView(layout);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        History history  = getHistoryById(idItemAtPosition);
                        history.setKilometerage(Integer.valueOf(setDialogEditText.getText().toString()));
                        history.save();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog kilometerageDialogShow = dialog.create();
                kilometerageDialogShow.show();

                break;
            case R.id.vehicle_last_oil_change_textview:
                setDialogText.setText("Last oil change:");
                setDialogText.setTextSize(TEXT_SIZE);

                String textToSet4 = String.valueOf(getOilById(idItemAtPosition).getChangedAt());
                setDialogEditText.setText(textToSet4);

                layout.addView(setDialogText);
                layout.addView(setDialogEditText);
                dialog.setView(layout);

                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Oil oil = getOilById(idItemAtPosition);
                        oil.setChangedAt(Integer.valueOf(setDialogEditText.getText().toString()));
                        oil.save();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog lastOilChangeDialogShow = dialog.create();
                lastOilChangeDialogShow.show();

                break;
        }
    }

    private History getHistoryById(int id) {
        History history = History.findById(History.class, Long.valueOf(id));

        return history;
    }

    private Oil getOilById(int id) {
        Oil oil = Oil.findById(Oil.class, Long.valueOf(idItemAtPosition));

        return oil;
    }
}
