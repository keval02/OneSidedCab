package com.onesidedcabs.helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manthanpatel on 03/04/2017.
 */
public class Travel_detail {


    @SerializedName("travel_id")
    private String travel_id;

    @SerializedName("startpoint")
    private String startpoint;

    @SerializedName("endpoint")
    private String endpoint;

    @SerializedName("state")
    private String state;

    @SerializedName("hourl")
    private String hourl;

    @SerializedName("junction")
    private String junction;

    @SerializedName("car1")
    private String car1;

    @SerializedName("price1")
    private String price1;

    @SerializedName("car2")
    private String car2;

    @SerializedName("price2")
    private String price2;

    @SerializedName("car3")
    private String car3;

    @SerializedName("price3")
    private String price3;

    @SerializedName("toll_tax")
    private String toll_tax;


    public String getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(String travel_id) {
        this.travel_id = travel_id;
    }

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHourl() {
        return hourl;
    }

    public void setHourl(String hourl) {
        this.hourl = hourl;
    }

    public String getJunction() {
        return junction;
    }

    public void setJunction(String junction) {
        this.junction = junction;
    }

    public String getCar1() {
        return car1;
    }

    public void setCar1(String car1) {
        this.car1 = car1;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getCar2() {
        return car2;
    }

    public void setCar2(String car2) {
        this.car2 = car2;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getCar3() {
        return car3;
    }

    public void setCar3(String car3) {
        this.car3 = car3;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }


    public String getToll_tax() {
        return toll_tax;
    }

    public void setToll_tax(String toll_tax) {
        this.toll_tax = toll_tax;
    }
}
