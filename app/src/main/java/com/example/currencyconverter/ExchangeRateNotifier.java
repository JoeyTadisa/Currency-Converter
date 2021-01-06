package com.example.currencyconverter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class ExchangeRateNotifier {
    private static final int NOTIFICATION_ID = 100;

    private static String CHANNEL_ID = "forex_channel";
    private static String CHANNEL_DESCRIPTION = "Display the result message after updating the rates";

    final NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ExchangeRateNotifier(Context context){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
        if(notificationChannel == null){
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_DESCRIPTION, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
                .setContentTitle("Currencies Updated")
                .setAutoCancel(false);

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultingPendingIntent = PendingIntent.getActivity(context, 0 , resultIntent, 0);
        notificationBuilder.setContentIntent(resultingPendingIntent);
    }

    /**
     *  Display the notification updating the user
     */
    public void showOrUpdateNotfication(){
        notificationBuilder.setContentText("Data was successfully retrieved and updated!");
        notificationBuilder.build();
        synchronized (notificationBuilder){
            notificationBuilder.notify();
        }
    }

    /**
     *  Remove the notification according to an ID
     */
    private void removeNotification(){
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
