package com.mobi.arrive5d.API;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public PreferenceModel pref;
    private Context mContext;

    public MyFirebaseInstanceIDService() {
        super();
    }

    public MyFirebaseInstanceIDService(Context context) {
        mContext = context;
    }

    @Override
    public void onTokenRefresh() {

        pref = new PreferenceModel(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        pref.saveStringValue("fcm_token", refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }
}