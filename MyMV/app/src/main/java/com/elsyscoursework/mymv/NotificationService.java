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

        new Thread() {
            @Override
            public void run() {
                handleNotification();
            }
        }.start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void handleNotification() {
        boolean called = false;

        while(true) {
            HashMap<String, String> vehicleOil = db.getVehicleOilFromId(1);
            String nextChangeAt = vehicleOil.get("next_change_at");
            int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);

            if (called) {
                if (nextChangeAtAsInteger > 100) {
                    Intent stopNotificationIntent = new Intent(this, NotificationReceiver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(this, 100, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);

                    called = false;
                } else {
                    continue;
                }
            } else {
                if (nextChangeAtAsInteger <= 100) {

                    Calendar calendar = Calendar.getInstance();

                    Intent startNotificationIntent = new Intent(this, NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, startNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);

                    called = true;
                } else {
                    continue;
                }
            }
        }
    }
}
