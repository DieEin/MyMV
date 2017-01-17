package com.elsyscoursework.mymv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NotificationService extends Service {

    final String VEHICLE_OIL_ID_TEXT = "vehicleOilId";
    final int UNIQUE_VALUE = 100;

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*new Thread() {
            @Override
            public void run() {
                handleNotification();
            }
        }.start();*/

        List<Oil> oil = Oil.listAll(Oil.class);
        for(Oil vehOil : oil) {

            // make unique request code for every vehicle (in order to support multiple notifications)
            String oilIdAsString = String.valueOf(vehOil.getId());
            int requestCode = UNIQUE_VALUE + Integer.parseInt(oilIdAsString);

            if (vehOil.getNextChangeAt() <= 100) {
                Calendar calendar = Calendar.getInstance();

                Intent startNotificationIntent = new Intent(NotificationService.this, NotificationReceiver.class);
                startNotificationIntent.putExtra(VEHICLE_OIL_ID_TEXT, vehOil.getId());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, startNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
            } else {
                Intent stopNotificationIntent = new Intent(this, NotificationReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(this, requestCode, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(sender);
            }
        }

        //Oil oil = Oil.findById(Oil.class, 1L);

        stopSelf();

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

//        while(true) {
//            HashMap<String, String> vehicleOil = db.getVehicleOilFromId(1);
//            String nextChangeAt = vehicleOil.get("next_change_at");
//            int nextChangeAtAsInteger = Integer.parseInt(nextChangeAt);
//
//            if (called) {
//                if (nextChangeAtAsInteger > 100) {
//                    Intent stopNotificationIntent = new Intent(this, NotificationReceiver.class);
//                    PendingIntent sender = PendingIntent.getBroadcast(this, 100, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//                    alarmManager.cancel(sender);
//
//                    called = false;
//                } else {
//                    continue;
//                }
//            } else {
//                if (nextChangeAtAsInteger <= 100) {
//
//                    Calendar calendar = Calendar.getInstance();
//
//                    Intent startNotificationIntent = new Intent(this, NotificationReceiver.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, startNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
//
//                    called = true;
//                } else {
//                    continue;
//                }
//            }
//        }
    }
}
