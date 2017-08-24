package com.onesidedcabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Ajay on 11/27/2016.
 */

public class Internet {


    public static boolean isConnectingToInternet(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void noInternet(Context context) {
        Toast.makeText(context, "Please check Your Internet", Toast.LENGTH_SHORT).show();

    }

    public static void serverError(Context context) {
        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();

    }

}
