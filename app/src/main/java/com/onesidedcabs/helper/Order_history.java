package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by one on 5/3/16.
 */
public class Order_history {

    @SerializedName("orderhistory")
    private ArrayList<Order_history_detail> orderhistory;


    public ArrayList<Order_history_detail> getOrderhistory() {
        return orderhistory;
    }

    public void setOrderhistory(ArrayList<Order_history_detail> orderhistory) {
        this.orderhistory = orderhistory;
    }
}
