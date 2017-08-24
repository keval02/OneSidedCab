package com.onesidedcabs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NWSPL-17 on 22-Jun-17.
 */

public class AppPrefs {

    private static final String USER_PREFS = "USER";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;


    boolean pickupfrom = false;
    boolean dropat = false;

    String placeAddress = "";

    boolean fromPlaced = false;
    boolean toPlaced = false;

    String token = "";


    public AppPrefs(Context context){

        this.sharedPreferences = context.getSharedPreferences(USER_PREFS , Context.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();


        pickupfrom = sharedPreferences.getBoolean("pickupfrom" , false);
        dropat = sharedPreferences.getBoolean("dropat" , false);

        placeAddress = sharedPreferences.getString("placeAddress" , "");

        fromPlaced = sharedPreferences.getBoolean("fromPlaced" , false);
        toPlaced = sharedPreferences.getBoolean("toPlaced" , false);

        token = sharedPreferences.getString("token"  , "");





    }

    public boolean isPickupfrom() {
        return sharedPreferences.getBoolean("pickupfrom" , false);
    }

    public void setPickupfrom(boolean pickupfrom) {
        this.pickupfrom = pickupfrom;
        prefsEditor.putBoolean("pickupfrom" , pickupfrom).commit();
    }

    public boolean isDropat() {
        return sharedPreferences.getBoolean("dropat" , false);
    }

    public void setDropat(boolean dropat) {
        this.dropat = dropat;
        prefsEditor.putBoolean("dropat" , dropat).commit();
    }

    public String getPlaceAddress() {
        return sharedPreferences.getString("placeAddress" , "");
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
        prefsEditor.putString("placeAddress" , placeAddress).commit();
    }

    public boolean isFromPlaced() {
        return sharedPreferences.getBoolean("fromPlaced" , false);
    }

    public void setFromPlaced(boolean fromPlaced) {
        this.fromPlaced = fromPlaced;
        prefsEditor.putBoolean("fromPlaced" , fromPlaced).commit();
    }

    public boolean isToPlaced() {
        return sharedPreferences.getBoolean("toPlaced" , false);
    }

    public void setToPlaced(boolean toPlaced) {
        this.toPlaced = toPlaced;
        prefsEditor.putBoolean("toPlaced" , toPlaced).commit();
    }


    public String getToken() {
        return sharedPreferences.getString("token" , "");
    }

    public void setToken(String token) {
        this.token = token;
        prefsEditor.putString("token" , token).commit();
    }

}
