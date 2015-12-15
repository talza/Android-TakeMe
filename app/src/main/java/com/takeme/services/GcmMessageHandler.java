package com.takeme.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.activities.MainTakeMeActivity;

/**
 * Created by tzamir on 11/30/2015.
 *
 * This class represent handler that get message from GCM and raise it via notification
 */
public class GcmMessageHandler extends IntentService {

    String mes;
    String title;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * Get gcm message and raise notification to user
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        mes = extras.getString("message");
        title = extras.getString("title");
        Log.i("GCM", "Received : (" + messageType + ")  " + extras.getString("title"));

        Intent notificationIntent = new Intent(this, MainTakeMeActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("TakeMe")
                .setContentText(mes)
                .setSmallIcon(R.drawable.ic_take_me)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;

        notif.notify(0, notify);

        GcmBroadcastReceiver.completeWakefulIntent(intent);


    }
}