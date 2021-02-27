package com.example.medqx.Reminder;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.medqx.R;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class BroadcastReceiver extends android.content.BroadcastReceiver{

    private static final String CHANNEL_ID="SAMPLE CHANNEL";

   @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        int notificationId = intent.getIntExtra("notificationId", 0);
        String msg = intent.getStringExtra("todo");


        Intent intent1 = new Intent(context, Med_take.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent1,0);


        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 28){
            CharSequence channel_name = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
            myNotificationManager.createNotificationChannel(channel);
       }

       Intent snoozeIntent = new Intent(context, SimpleAlarm.class);
       snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
       snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId + 9876);
       PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, notificationId + 9876, snoozeIntent, notificationId);

       NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Time To Take Your Medicine!!")
                .setContentText(msg)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .addAction(R.drawable.notification_snooze, "Take",
                       contentIntent)
                       .addAction(R.drawable.notification_snooze, "Snooze",
                               snoozePendingIntent);

        myNotificationManager.notify(notificationId, builder.build());

    }


}
