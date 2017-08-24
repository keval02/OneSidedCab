package com.onesidedcabs.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by zero on 7/11/15.
 */
    public class PrefUtils {

    /*--------------------  City -------------------  */


    public static void setCity(City currentUser, Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_city", context.MODE_PRIVATE);
        complexPreferences.putObject("city_value", currentUser);
        complexPreferences.commit();
    }

    public static City getCity(Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_city", context.MODE_PRIVATE);
        City currentcity = complexPreferences.getObject("city_value", City.class);
        return currentcity;
    }

    public static void clearCity(Context context) {

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_city", context.MODE_PRIVATE);
        complexPreferences.clearObject();
        complexPreferences.commit();

    }






    /*--------------------  Travel -------------------  */


    public static void setTravel(Travel currentTravel, Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_travel", context.MODE_PRIVATE);
        complexPreferences.putObject("travel_value", currentTravel);
        complexPreferences.commit();
    }

    public static Travel getTravel(Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_travel", context.MODE_PRIVATE);
        Travel currenttravel = complexPreferences.getObject("travel_value", Travel.class);
        return currenttravel;
    }

    public static void cleartravel(Context context) {

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_travel", context.MODE_PRIVATE);
        complexPreferences.clearObject();
        complexPreferences.commit();

    }



/*-----------------  User and -----------------*/


    public static void setlogin(String login, Context ctx) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login", login);
        editor.commit();
    }


    public static String getlogin(Context ctx) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        String login = preferences.getString("login", "");
        return login;
    }

 /*--------------------  User_All_post   -------------------  */

//
//    public static void setuser_app_Post(User_all_post currentUserpost, Context context) {
//
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_Post", context.MODE_PRIVATE);
//        complexPreferences.putObject("user_app_Post_value", currentUserpost);
//        complexPreferences.commit();
//    }
//
//    public static User_all_post getuser_app_Post(Context context) {
//
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_Post", context.MODE_PRIVATE);
//        User_all_post user_app_Post_value = complexPreferences.getObject("user_app_Post_value", User_all_post.class);
//        return user_app_Post_value;
//    }
//
//    public static void clearuser_app_Post(Context context) {
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_Post", context.MODE_PRIVATE);
//        complexPreferences.clearObject();
//        complexPreferences.commit();
//
//    }
//
//
//    /*--------------------  User_1_post   -------------------  */
//
//
//    public static void setuser_one_Post(User_1post onecurrentUser, Context context) {
//
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_one_Post", context.MODE_PRIVATE);
//        complexPreferences.putObject("user_one_Post_value", onecurrentUser);
//        complexPreferences.commit();
//    }
//
//    public static User_1post getuser_one_Post(Context context) {
//
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_one_Post", context.MODE_PRIVATE);
//        User_1post onecurrentUser = complexPreferences.getObject("oneuser_value", User_1post.class);
//        return onecurrentUser;
//    }
//
//    public static void clearuser_one_Post(Context context) {
//
//        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user_one_Post", context.MODE_PRIVATE);
//        complexPreferences.clearObject();
//        complexPreferences.commit();
//
//    }
//
//
//
//
//


/*-----------------  User and -----------------*/


    public static void setpostID(String postID, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("postID", postID);
        editor.commit();
    }


    public static String getpostID(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String postID = preferences.getString("postID", "");
        return postID;
    }






    /*--------------------  User -------------------  */


    public static void setUser(User user, Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user", context.MODE_PRIVATE);
        complexPreferences.putObject("user_value", user);
        complexPreferences.commit();
    }

    public static User getUser(Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user", context.MODE_PRIVATE);
        User user = complexPreferences.getObject("user_value", User.class);
        return user;
    }

    public static void clearUser(Context context) {

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_user", context.MODE_PRIVATE);
        complexPreferences.clearObject();
        complexPreferences.commit();

    }


//    ---------  Login ---------------


    public static void setuID(String uID, Context uctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(uctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uID", uID);
        editor.commit();
    }


    public static String getuID(Context uctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(uctxe);

        String uID = preferences.getString("uID", "");
        return uID;
    }


//    ---------  City_name ---------------


    public static void setCity_name(String City_name, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("City_name", City_name);
        editor.commit();
    }


    public static String getCity_name(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String City_name = preferences.getString("City_name", "");
        return City_name;
    }


//    ---------  r_id ---------------


    public static void setr_id(String r_id, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("r_id", r_id);
        editor.commit();
    }


    public static String getr_id(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String r_id = preferences.getString("r_id", "");
        return r_id;
    }


//    ---------  end_poi ---------------


    public static void setend_poi(String end_poi, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("end_poi", end_poi);
        editor.commit();
    }


    public static String getend_poi(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String end_poi = preferences.getString("end_poi", "");
        return end_poi;
    }


//    ---------  d_price1 ---------------


    public static void setd_price1(String d_price1, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("d_price1", d_price1);
        editor.commit();
    }


    public static String getd_price1(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String d_price1 = preferences.getString("d_price1", "");
        return d_price1;
    }


    //-------------   toolTax ------------------

    public static void settollTax(String toolPrice, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tollPrice", toolPrice);
        editor.commit();
    }


    public static String gettollPrice(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String tollPrice = preferences.getString("tollPrice", "");
        return tollPrice;
    }






//    ---------  from1 ---------------


    public static void setfrom1(String from1, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("from1", from1);
        editor.commit();
    }


    public static String getfrom1(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String from1 = preferences.getString("from1", "");
        return from1;
    }


//    ---------  travel_id ---------------


    public static void settravel_id(String travel_id, Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("travel_id", travel_id);
        editor.commit();
    }


    public static String gettravel_id(Context ctxe) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxe);

        String travel_id = preferences.getString("travel_id", "");
        return travel_id;
    }


//    ---------  travel_id ---------------






    /*--------------------  order_history -------------------  */


    public static void setOrder_h(Order_history order_history, Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_order_history", context.MODE_PRIVATE);
        complexPreferences.putObject("order_history_value", order_history);
        complexPreferences.commit();
    }

    public static Order_history getOrder_h(Context context) {


        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_order_history", context.MODE_PRIVATE);
        Order_history order_history = complexPreferences.getObject("order_history_value", Order_history.class);
        return order_history;
    }

    public static void clearOrder_h(Context context) {

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "my_order_historyv", context.MODE_PRIVATE);
        complexPreferences.clearObject();
        complexPreferences.commit();

    }


    public static void setpp_id(String p_idd, Context p_iddc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(p_iddc);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("p_idd", p_idd);
        editor.commit();
    }


    public static String getpp_id(Context p_iddc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(p_iddc);

        String p_idd = preferences.getString("p_idd", "");
        return p_idd;
    }




     /* ------------ order ID ------------------------------- */

    public static void setOrder_ID(String Order_ID, Context ctxOrder_ID) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxOrder_ID);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Order_ID", Order_ID);
        editor.commit();
    }


    public static String geteOrder_ID(Context ctxOrder_ID) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxOrder_ID);

        String Order_ID = preferences.getString("Order_ID", "");
        return Order_ID;
    }






/*-----------------  User book_name -----------------*/


    public static void setbook_name(String book_name, Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_name", book_name);
        editor.commit();
    }


    public static String getbook_name(Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);

        String book_name = preferences.getString("book_name", "");
        return book_name;
    }


/*-----------------  User book_mob -----------------*/


    public static void setbook_mob(String book_mob, Context ctxm) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxm);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_mob", book_mob);
        editor.commit();
    }


    public static String getbook_mob(Context ctxm) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxm);

        String book_mob = preferences.getString("book_mob", "");
        return book_mob;
    }




/*-----------------  User book_pdate -----------------*/


    public static void setbook_pdate(String book_pdate, Context ctxmpd) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpd);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_pdate", book_pdate);
        editor.commit();
    }


    public static String getbook_pdate(Context ctxmpd) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpd);

        String book_pdate = preferences.getString("book_pdate", "");
        return book_pdate;
    }



/*-----------------  User book_ptime -----------------*/


    public static void setbook_ptime(String book_ptime, Context ctxmpt) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpt);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_ptime", book_ptime);
        editor.commit();
    }


    public static String getbook_ptime(Context ctxmpt) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpt);

        String book_ptime = preferences.getString("book_ptime", "");
        return book_ptime;
    }


/*-----------------  User book_pc -----------------*/


    public static void setbook_pc(String book_pc, Context ctxmpc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpc);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_pc", book_pc);
        editor.commit();
    }


    public static String getbook_pc(Context ctxmpc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmpc);

        String book_pc = preferences.getString("book_pc", "");
        return book_pc;
    }




/*-----------------  User book_dc -----------------*/


    public static void setbook_dc(String book_dc, Context ctxmdc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmdc);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_dc", book_dc);
        editor.commit();
    }


    public static String getbook_dc(Context ctxmdc) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmdc);

        String book_dc = preferences.getString("book_dc", "");
        return book_dc;
    }



/*-----------------  User book_price -----------------*/


    public static void setbook_price(String book_price, Context ctxmprice) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmprice);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_price", book_price);
        editor.commit();
    }


    public static String getbook_price(Context ctxmprice) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmprice);

        String book_price = preferences.getString("book_price", "");
        return book_price;
    }





/*-----------------  User book_tex -----------------*/


    public static void setbook_tex(String book_tex, Context ctxmtex) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmtex);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_tex", book_tex);
        editor.commit();
    }


    public static String getbook_tex(Context ctxmtex) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxmtex);

        String book_tex = preferences.getString("book_tex", "");
        return book_tex;
    }







/*-----------------  User book_type -----------------*/


    public static void setbook_type(String book_type, Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("book_type", book_type);
        editor.commit();
    }


    public static String getbook_type(Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);

        String book_type = preferences.getString("book_type", "");
        return book_type;
    }





/*-----------------  User h_id -----------------*/


    public static void seth_id(String h_id, Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("h_id", h_id);
        editor.commit();
    }


    public static String geth_id(Context ctxn) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctxn);

        String h_id = preferences.getString("h_id", "");
        return h_id;
    }

}





