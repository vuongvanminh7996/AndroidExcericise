package com.example.SelfieApp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShowNotification extends Service {
    private final static String TAG = "ShowNotification";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Error","Nobody call me");

        NotificationManager notificationManager
                = this.getSystemService(NotificationManager.class);

        String channelId = "Your_channel_id";
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Notification.Builder noti = new Notification.Builder(this)
                .setSmallIcon(R.drawable.camera)
                .setContentTitle("Let's take a selfie")
                .setContentText("It has been a day since you took one")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channelId)
                .setAutoCancel(true);
        notificationManager.notify(0, noti.build());
        Log.e(TAG, "Notification created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
