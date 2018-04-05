package com.onesidedcabs.helper;

import android.telecom.Call;

import com.onesidedcabs.CarTypeResponse;
import com.onesidedcabs.GlobalFile;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by gautam on 5/24/2016.
 */
public interface RetrfitInterface {
    String url = "http://onesidedcab.com/onesidedcabws/"; //"http://192.168.1.145/scrapy/android";


    @FormUrlEncoded
    @POST("/city.php")
    void city_list_web(@Field("key") String key, Callback<Response> cb);


    @FormUrlEncoded
    @POST("/oneway.php")
    void get_traval(@Field("city") String city, @Field("id") String id, @Field("key") String key, Callback<Response> cb);


    @FormUrlEncoded
    @POST("/signup.php")
    void user_login(@Field("phone") String phone, @Field("email_id") String email_id, @Field("name") String name, @Field("key") String key, @Field("fcm_id") String fcm_id , @Field("device") String device_selection ,  Callback<Response> cb);


    @FormUrlEncoded
    @POST("/order.php")
    void user_order(@Field("c_email") String c_email, @Field("c_type") String c_type, @Field("userid") String userid, @Field("address") String address, @Field("pick_addr") String pick_addr, @Field("pick_time") String pick_time, @Field("pick_date") String pick_date, @Field("price") String price, @Field("pickupcity") String pickupcity, @Field("travel_id") String travel_id, @Field("delivercity") String delivercity, @Field("car_type") String car_type, @Field("travel_code") String travel_code, @Field("phone") String phone, @Field("name") String name, @Field("key") String key, @Field("toll_tax") String toll_tax , Callback<Response> cb);


    @FormUrlEncoded
    @POST("/history.php")
    void get_usertraval(@Field("userid") String userid, @Field("key") String key, Callback<Response> cb);


    @FormUrlEncoded
    @POST("/updateorder.php")
    void get_order_ststus(@Field("order_id") String order_id, @Field("app_status") String app_status, @Field("order_type") String order_type, @Field("key") String key, Callback<Response> cb);


    @FormUrlEncoded
    @POST("/deletecustomerorder.php")
    void user_login(@Field("oid") String oid, @Field("key") String key,  Callback<Response> cb);




    @FormUrlEncoded
    @POST("/deletecustomerorder.php")
    void deleteOrder(@Field("oid") String oid, @Field("key") String key, @Field("userid") String userId , Callback<Response> cb);


    @FormUrlEncoded
    @POST("/cartype.php")
    void carType(Callback<Response> cb);


//
//
//    @FormUrlEncoded
//    @POST("/Approv_User_Post.php")
//    void aprove_user_posat_web(@Field("ID") String ID, Callback<Response> cb);
//
//
//
//    @FormUrlEncoded
//    @POST("/Delete_User_Post.php")
//    void delete_user_posat_web(@Field("ID") String ID, Callback<Response> cb);
//
//    @FormUrlEncoded
//    @POST("/po.php")
//    void search_user_web(@Field("q") String q, Callback<Response> cb);


}
