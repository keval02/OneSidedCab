package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zero on 4/7/16.
 */
public class User_detail {

    @SerializedName("userid")
    private String userid;

    @SerializedName("email_id")
    private String email_id;

    @SerializedName("phone")
    private String phone;


    @SerializedName("otp")
    private String otp;


    @SerializedName("name")
    private String name;


    public String getUserid() {
        return userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
