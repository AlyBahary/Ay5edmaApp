package com.example.smatech.ay5edma.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.ClientHomeActivity;
import com.example.smatech.ay5edma.NotificationsActivity;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.hawk.Hawk;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    public void onCreate() {
        super.onCreate();
        if(!Hawk.isBuilt()){
            Hawk.init(this).build();
        }
        Log.d(TAG, "onToken: "+token);
        Hawk.put(Constants.TOKEN,token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
/*
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
*/
        if (remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        else
            showNotification(remoteMessage.getData());

      /*  Map<String, String> notificationMessage = remoteMessage.getData();
        if (notificationMessage.containsKey("targetScreen")){
            Log.d(TAG, "onMessageReceived: "+notificationMessage.get("chat_id"));
            Log.d(TAG, "onMessageReceived: "+notificationMessage.get("from_id"));
            Log.d(TAG, "onMessageReceived: "+notificationMessage.get("to_id"));
            Intent resultIntent = new Intent(this, ChatActivity.class).putExtra("to_id",notificationMessage.get("to_id"));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ClientHomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(resultPendingIntent);
            notificationBuilder.setAutoCancel(true);
        }else {
            Log.d(TAG, "onMessageReceived: notContained");

        }
*/
    }


    private void showNotification(Map<String, String> data) {

        String title = data.get("title").toString();
        String body = data.get("body").toString();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATIO_CHANEL_ID = "com.example.smatech.ay5edma";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIO_CHANEL_ID, "notification"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATIO_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.isplash)
                .setContentTitle(title)
                .setContentText(body)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentInfo("Info");
        Map<String, String> notificationMessage = data;
        if (notificationMessage.containsKey("targetScreen")){
            Intent resultIntent;
            if(notificationMessage.get("targetScreen").equals("message")) {
                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("chat_id"));
                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("from_id"));
                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("to_id"));
                 resultIntent = new Intent(this, ChatActivity.class).putExtra("to_id", notificationMessage.get("to_id"));
            }
            else {
                resultIntent = new Intent(this, NotificationsActivity.class);

            }
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ClientHomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(resultPendingIntent);
            notificationBuilder.setAutoCancel(true);


        }else {
            Log.d(TAG, "onMessageReceived: notContained");
            Intent resultIntent = new Intent(this, ClientHomeActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ClientHomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(resultPendingIntent);
            notificationBuilder.setAutoCancel(true);

        }

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATIO_CHANEL_ID = "com.smatech.rahmaapp";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIO_CHANEL_ID, "notification"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATIO_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.isplash)
                .setContentTitle(title)
                .setContentText(body)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: "+s);
        Hawk.put(Constants.TOKEN,s);
    }
}
