package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by manthanpatel on 03/04/2017.
 */

public class Travel {

    @SerializedName("travel")
    private ArrayList<Travel_detail> travel;

    public ArrayList<Travel_detail> getTravel() {
        return travel;
    }

    public void setTravel(ArrayList<Travel_detail> travel) {
        this.travel = travel;
    }
}
