package com.example.SelfieApp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.SelfieApp.Fragments.CameraFragment;

public class MainActivity extends AppCompatActivity {
    NotificationManager notificationManager;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Intent alarmIntent = new Intent(this,ShowNotification.class);
//        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE);
//
//        AlarmManager alarmManager =
//                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent);
//        }
//
//        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 1000,
//                60 * 1000,pendingIntent);

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

        loadFragment(new CameraFragment(), false);

    }

    private void startAlarm() {
        Log.e("Error","setAlarm");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        }


    }


    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public final void setAlarm(int seconds) {
    // create the pending intent
    Intent intent = new Intent(MainActivity.this, ShowNotification.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
            intent, 0);
    // get the alarm manager, and scedule an alarm that calls the receiver
    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(
            AlarmManager.RTC, System.currentTimeMillis() + seconds
                    * 1000, pendingIntent);
    Toast.makeText(MainActivity.this, "Timer set to " + seconds + " seconds.",
            Toast.LENGTH_SHORT).show();
}
}
