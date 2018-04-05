package com.onesidedcabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;


/**
 * Created by prince on 9/5/2016.
 */
public class GlobalFile {

    public static final int POST= Request.Method.POST;

    public static String server_link ="http://onesidedcab.com/";
    public static String image_link =server_link+"onesidedcabws/";


    public static boolean isOnline(Context con) {
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

    public static void noInternet(Context context){
        Toast.makeText(context, "No Internet Connection Found\nPlease Check your Connection First!", Toast.LENGTH_SHORT).show();
    }

    public static void serverError(Context context){
        Toast.makeText(context, "Server Error Occured\nPlease Try After Sometime!", Toast.LENGTH_SHORT).show();
    }

}
