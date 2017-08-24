package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by manthanpatel on 02/04/2017.
 */

public class City {

    @SerializedName("city")
    private ArrayList<City_detail> city;

    public ArrayList<City_detail> getCity() {
        return city;
    }

    public void setCity(ArrayList<City_detail> city) {
        this.city = city;
    }
}
