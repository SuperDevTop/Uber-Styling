package com.mobi.arrive5d.API;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobi.arrive5d.MainActivity;
import com.mobi.arrive5d.R;
import com.mobi.arrive5d.SideMenu.BookingDialogActivity;
import com.mobi.arrive5d.utils.ApplicationController;
import com.mobi.arrive5d.utils.SavePref;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;
    SavePref savePref;

    public MyFirebaseMessagingService() {
        super();
    }

    public MyFirebaseMessagingService(AppCompatActivity activity) {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            preferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            Log.d("dghjgfhaj", "" + remoteMessage.getData());
            Log.i("NumOFActivities", "" + ApplicationController.isAppInForeground());
            JSONObject obj = new JSONObject(remoteMessage.getData());
            String alert = obj.optString("alert");
            String userId = obj.optString("user_id");
            String pushTag = obj.optString("push_tag");
            String bookingId = obj.optString("booking_id");
            String endPoint = obj.optString("end_point");
            String userRating = obj.optString("user_rating");
            String type = obj.optString("vehicleSubTypeName");
            String time = obj.optString("duration");
            String startPoint = obj.optString("start_point");
            String userName = obj.optString("userName");
            String userImg = obj.optString("userImg");
            String amount = obj.optString("amount");
            savePref = new SavePref(getApplicationContext());
            Intent intent = null;
            if (pushTag.equalsIgnoreCase("booking")) {
                intent = new Intent(this, BookingDialogActivity.class);
                intent.putExtra("pushtag", pushTag);
                intent.putExtra("openActivity", true);
                savePref.saveUserId(userId);
                savePref.saveName(userName);
                savePref.saveImage(userImg);
                savePref.saveStartPoint(startPoint);
                savePref.saveEndPoint(endPoint);
                savePref.saveUserRating(userRating);
                savePref.saveBookingId(bookingId);
                savePref.saveVehicleSubType(type);
                savePref.saveDuration(time);
                savePref.saveBookingAmount(amount);
            }
            if (pushTag.equalsIgnoreCase("user_cancel_booking")) {
                intent = new Intent(this, MainActivity.class);
            }
            if (pushTag.equalsIgnoreCase("Schedule Booking")) {
                intent = new Intent(this, BookingDialogActivity.class);
                intent.putExtra("pushtag", pushTag);
                intent.putExtra("openActivity", true);
                savePref.saveUserId(userId);
                savePref.saveName(userName);
                savePref.saveImage(userImg);
                savePref.saveStartPoint(startPoint);
                savePref.saveEndPoint(endPoint);
                savePref.saveUserRating(userRating);
                savePref.saveBookingId(bookingId);
                savePref.saveVehicleSubType(type);
                savePref.saveDuration(time);
            }
            intent.putExtra("Push", "Push");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            if (ApplicationController.isAppInForeground()) {
               // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
               // displayNotification(pendingIntent, alert);
                Log.e("dfjhdgdfgdfg","isAppInBack");
            }
            if (!ApplicationController.isAppInForeground()) {

                displayNotification(pendingIntent, alert);
                Log.e("dfjhdgdfgdfg","NotInBack");
            }
            sendBroadcast(alert, pushTag, obj);
        }

        catch (Exception e) {
            Log.e("dfjhdgdfgdfg",e.getMessage());
        }
    }

    private void displayNotification(PendingIntent pendingIntent, String messageBody) {
        String channelId = "Notification";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
        notificationBuilder.setContentTitle(getString(R.string.app_name)).setContentText(messageBody).setAutoCancel(true).setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_ALL).setSound(defaultSoundUri).setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void sendBroadcast(String str, String push, JSONObject obj) {
        Intent intent = new Intent("send");
        intent.putExtra("message", push);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}

