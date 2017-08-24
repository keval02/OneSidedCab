package com.onesidedcabs;

import android.app.Service;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NWSPL-17 on 10-Jul-17.
 */

public class MyAndroidFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyAndroidFCMIIDService";

    AppPrefs prefs;
    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token

        prefs = new AppPrefs(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);





    }
    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server
    }






}
