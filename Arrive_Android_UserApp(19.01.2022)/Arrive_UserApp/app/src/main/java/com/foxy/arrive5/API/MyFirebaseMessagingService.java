package com.foxy.arrive5.API;

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

import com.foxy.arrive5.Home.FindingRideActivity;
import com.foxy.arrive5.Home.PaymentActivity;
import com.foxy.arrive5.Home.SplitFareActivity;
import com.foxy.arrive5.MainActivity;
import com.foxy.arrive5.R;
import com.foxy.arrive5.utils.ApplicationController;
import com.foxy.arrive5.utils.SavePref;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG = "MyFirebaseMsgService";
    MyFirebaseMessagingService context;
    SharedPreferences preferences;
    SavePref savePref;

    public MyFirebaseMessagingService() {
        super();
    }

    public MyFirebaseMessagingService(AppCompatActivity activity) {
        super();
    }

    /*
    {"content-available":"1","amount":"38.50","bookingId":"1",alert":"Split Fare","sound":"default","push_tag":"Split Fare"}
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        savePref = new SavePref(getApplicationContext());
        try {
            preferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            Log.d("dghjgfhaj", "" + remoteMessage.getData());
            Log.i("NumOFActivities", "" + ApplicationController.isAppInForeground());
            JSONObject obj = new JSONObject(remoteMessage.getData());
            String alert = obj.optString("alert");
            String pushTag = obj.optString("push_tag");
            String driverImage = obj.optString("driverImg");
            String driverName = obj.optString("driverName");
            String duration = obj.optString("duration");
            String driverUniqueId = obj.optString("driverUniqueId");
            String bookingUniqueId = obj.optString("bookingUniqueId");
            String startPoint = obj.optString("start_point");
            String endPoint = obj.optString("destination");
            String driverId = obj.optString("driverId");
            String distance = obj.optString("distance");
            String driverPhone = obj.optString("driverPhone");
            String bookingId = obj.optString("bookingId");
            String amount = obj.optString("amount");
            String driverLat = obj.optString("driverLat");
            String driverLong = obj.optString("driverLong");
            String rating = obj.optString("rating");
            String otp = obj.optString("otp");
            String carType = obj.optString("carType");
            String carNo = obj.optString("carNo");
            String userName = obj.optString("name");
            String share_userId = obj.optString("share_user_id");
            Intent intent = null;

            if (pushTag.equalsIgnoreCase("accept")) {
                intent = new Intent(this, FindingRideActivity.class);
                intent.putExtra("pushtag", pushTag);
                intent.putExtra("openActivity", true);
                intent.putExtra("driverImage", driverImage);
                intent.putExtra("driverName", driverName);
                intent.putExtra("duration", duration);
                intent.putExtra("driverPhone", driverPhone);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("amount", amount);
                intent.putExtra("driverId", driverId);
                intent.putExtra("startPoint", startPoint);
                intent.putExtra("endPoint", endPoint);
                intent.putExtra("driverLat", driverLat);
                intent.putExtra("driverLong", driverLong);
                intent.putExtra("rating", rating);
                intent.putExtra("otp", otp);
                intent.putExtra("carType", carType);
                intent.putExtra("carNo", carNo);
                savePref.saveDriverName(driverName);
                savePref.saveDriverMobile(driverPhone);
                savePref.saveBookingId(bookingId);
            } else if (pushTag.equalsIgnoreCase("arrived")) {
                intent = new Intent(this, FindingRideActivity.class);
                intent.putExtra("pushtag", pushTag);
                intent.putExtra("openActivity", true);
                intent.putExtra("driverImage", driverImage);
                intent.putExtra("driverName", driverName);
                intent.putExtra("duration", duration);
                intent.putExtra("driverPhone", driverPhone);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("amount", amount);
                intent.putExtra("driverId", driverId);
                intent.putExtra("startPoint", startPoint);
                intent.putExtra("endPoint", endPoint);
                intent.putExtra("driverLat", driverLat);
                intent.putExtra("driverLong", driverLong);
                intent.putExtra("rating", rating);
                intent.putExtra("otp", otp);
                intent.putExtra("carType", carType);
                intent.putExtra("carNo", carNo);
                savePref.saveBookingId(bookingId);
            } else if (pushTag.equalsIgnoreCase("finish_ride")) {
                intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("amount", amount);
            } else if (pushTag.equalsIgnoreCase("schedule_later_accept")) {
                intent = new Intent(this, MainActivity.class);
            } else if (pushTag.equalsIgnoreCase("Split Fare")) {
                intent = new Intent(this, SplitFareActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("booking_id", bookingId);
                intent.putExtra("name", userName);
                intent.putExtra("share_user_id", share_userId);
            } else if (pushTag.equalsIgnoreCase("accept_reject")) {
                intent = new Intent(this, MainActivity.class);
            }
            else {
                intent = new Intent(this, MainActivity.class);
            }

            intent.putExtra("Push", "Push");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            sendBroadcast(alert, pushTag, obj);
            if (ApplicationController.isAppInForeground()) {

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.e("yertywewer","InBack");
            }
//            if (!ApplicationController.isAppInForeground()) {
            displayNotification(pendingIntent, alert);
            //}
        } catch (Exception e) {
           Log.e("yertywewer",e.getMessage());
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

