package com.elsyscoursework.mymv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Tomi on 10.2.2017 г..
 */

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent checkOilOnPhoneBoot = new Intent(context, NotificationService.class);
        context.startService(checkOilOnPhoneBoot);
    }
}
