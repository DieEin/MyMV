package com.elsyscoursework.mymv;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Tomi on 3.1.2017 г..
 */

public class NotificationReceiver extends BroadcastReceiver {

    final String VEHICLE_OIL_ID_TEXT = "vehicleOilId";
    final long DEFAULT_PASSED_VALUE = 0L;
    final int UNIQUE_VALUE = 100;
    final String PASSED_VARIABLE_NAME = "idItemAtPosition";

    @Override
    public void onReceive(Context context, Intent intent) {

        long oilIdAsLong = intent.getLongExtra(VEHICLE_OIL_ID_TEXT, DEFAULT_PASSED_VALUE);
        String oilIdAsString = String.valueOf(oilIdAsLong);
        int requestCode = UNIQUE_VALUE + Integer.parseInt(oilIdAsString);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, ManageVehicle.class);
        repeatingIntent.putExtra(PASSED_VARIABLE_NAME, Integer.parseInt(oilIdAsString));

        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String notificationTitle = Vehicle.findById(Vehicle.class, oilIdAsLong).getManufacturer() + Vehicle.findById(Vehicle.class, oilIdAsLong).getModel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.oil_change_icon)
                .setContentTitle(notificationTitle)
                .setContentText("Oil needs changing")
                .setSound(alarmSound)
                .setAutoCancel(true);

        notificationManager.notify(requestCode, builder.build());
    }
}
