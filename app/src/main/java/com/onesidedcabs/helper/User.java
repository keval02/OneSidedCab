package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by one on 5/3/16.
 */
public class User {

    @SerializedName("user")
    private ArrayList<User_detail> user;

    public ArrayList<User_detail> getUser() {
        return user;
    }

    public void setUser(ArrayList<User_detail> user) {
        this.user = user;
    }
}
