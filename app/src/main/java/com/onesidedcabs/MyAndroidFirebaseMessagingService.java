package com.onesidedcabs;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by NWSPL-17 on 10-Jul-17.
 */

public class MyAndroidFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyAndroidFCMService";
    public static final String NOTIFICATION = "com.nivida.user.boss";
    public static final String MESSAGETOSEND = "notification";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.e(TAG, "DATA Message Body: " + remoteMessage.getData().toString());

        String order_id = "";

        order_id = remoteMessage.getData().get("order_id");

        createNotification(remoteMessage.getData().get("title") , remoteMessage.getData().get("order_id"));





        Intent intent = new Intent(MyAndroidFirebaseMessagingService.NOTIFICATION);
        intent.putExtra(MyAndroidFirebaseMessagingService.MESSAGETOSEND, "updated");
        sendBroadcast(intent);

    }

    private void createNotification(String body, String order_id) {


        int uniqueNo = genrateRandom();


        if (order_id.equalsIgnoreCase("")||order_id.equalsIgnoreCase(null)) {




            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.launcher_final_icon);


            Intent intent = new Intent(this, NotificationDetails.class);
            intent.putExtra("content", body);


            Log.e("body", body);
            PendingIntent resultIntent = PendingIntent.getActivity(this, uniqueNo, intent, PendingIntent.FLAG_ONE_SHOT);


            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.launcher_final_icon)
                    .setLargeIcon(icon)
                    .setContentTitle("One Sided Cab")
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(uniqueNo, notificationBuilder.build());

        } else {


            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.launcher_final_icon);


            Intent intent = new Intent(this, FeedbackFormActivity.class);
            intent.putExtra("orderID", order_id);


            Log.e("orderId", order_id);
            Log.e("body", body);
            PendingIntent resultIntent = PendingIntent.getActivity(this, uniqueNo, intent, PendingIntent.FLAG_ONE_SHOT);


            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.launcher_final_icon)
                    .setLargeIcon(icon)
                    .setContentTitle("One Sided Cab")
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(uniqueNo, notificationBuilder.build());


        }

    }

    private int genrateRandom() {

        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;



    }
}
