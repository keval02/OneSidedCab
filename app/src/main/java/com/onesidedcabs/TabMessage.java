package com.onesidedcabs;


/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "";

        switch (menuItemId) {

            case R.id.tab_favorites:
                message += "One Side";
                break;
            case R.id.tab_nearby:
                message += "Hourly Cab";
                break;
            case R.id.tab_friends:
                message += "Outstation (Return)";
                break;

        }

        if (isReselection) {
           // message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
