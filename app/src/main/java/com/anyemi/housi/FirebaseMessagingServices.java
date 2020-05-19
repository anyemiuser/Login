package com.anyemi.housi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Mohini on 14-04-2017.
 */

public class FirebaseMessagingServices extends FirebaseMessagingService {
    public static final String ACTION_RECEIVE="com.anyemi.housi.Service.action.RECEIVE";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

       //super.onMessageReceived(remoteMessage);
        String title = "News Meter",body="";
        if( remoteMessage.getNotification()!=null) {
            Log.e("notification", "Message Notification Body: " + remoteMessage.getNotification().getBody());
             title = remoteMessage.getNotification().getTitle();
             body = remoteMessage.getNotification().getBody();
        }
        if( remoteMessage.getData().size()>0) {
            Log.e("messaqge", "Message  Body: " + remoteMessage.getData());
            Log.e("messaqge", "Message : " +  remoteMessage.getData().get("type"));


          // String  type ="START_GAME";
            Intent broadcast = new Intent();
            broadcast.putExtra("type", remoteMessage.getData().get("type")+"");
            broadcast.putExtra("claim_type",remoteMessage.getData().get("claim_type"));
            broadcast.putExtra("game_id",remoteMessage.getData().get("game_id"));
            broadcast.putExtra("ticket_id",remoteMessage.getData().get("ticket_id"));
            broadcast.putExtra("status",remoteMessage.getData().get("status"));

            broadcast.setAction(ACTION_RECEIVE);
                    sendBroadcast(broadcast);

        }

/*

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("URL",""+body);

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            channel = new NotificationChannel("222", "my_channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        getApplicationContext(), "222")
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        //uma
                        .setLargeIcon(((BitmapDrawable)getApplicationContext().getDrawable(R.drawable.ic_launcher_background)).getBitmap())
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.electro))
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pi)
                ;

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        nm.notify(0, builder.build());
*/
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("token", "Refreshed token: " + token);
       // super.onNewToken(s);
    }

}
