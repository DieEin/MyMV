package com.elsyscoursework.mymv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;
import java.util.HashMap;

public class NotificationService extends Service {
    private HelperSQL db;

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = new HelperSQL(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HashMap<String, String> vehicleOil = db.getVehicleOilFromId(1);
        String nextChangeAt = vehicleOil.get("next_change_at");
        int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);

        if (nextChangeAtAsInteger <= 100) {
            Calendar calendar = Calendar.getInstance();

            Intent startNotificationIntent = new Intent(this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, startNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 120000, pendingIntent);
        } else {
            Intent stopNotificationIntent = new Intent(this, NotificationReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 100, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
