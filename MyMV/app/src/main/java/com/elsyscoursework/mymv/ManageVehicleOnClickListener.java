package com.elsyscoursework.mymv;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

        switch (v.getId()) {
            case R.id.vehicle_owner_textview:
                setDialogText.setText("Owner:");
                setDialogText.setTextSize(TEXT_SIZE);
                final EditText setDialogEditText = new EditText(context);

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

                AlertDialog dialogShow = dialog.create();
                dialogShow.show();

                break;
        }
    }

    private History getHistoryById(int id) {
        History history = History.findById(History.class, Long.valueOf(id));

        return history;
    }
}
