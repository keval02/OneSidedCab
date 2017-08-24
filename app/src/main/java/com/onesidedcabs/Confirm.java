package com.onesidedcabs;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by manthanpatel on 06/04/2017.
 */
public class Confirm extends AppCompatActivity {

    TextView price,ride,confirm,price_text;
    EditText name;
    EditText mobileno;
    EditText picdate;

    EditText pic;
    EditText drop;
    TextInputLayout i_name,i_mobileno,i_picdate,i_pic,i_drop;
    Typeface custom_font,custom_fontb,custom_fontl;
    ImageView back_a;

    JSONObject jObj;
    String status;
    private ProgressDialog loading;
    String car_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);


        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        car_type = PrefUtils.getfrom1(Confirm.this);
        ImageView immg = (ImageView)findViewById(R.id.immg);



        if(car_type.equalsIgnoreCase("HATCHBACK")){

            immg.setImageResource(R.drawable.hatchback);

        }

        else if(car_type.equalsIgnoreCase("SEDAN")){

            immg.setImageResource(R.drawable.sedan);

        }
        else if(car_type.equalsIgnoreCase("SUV")){

            immg.setImageResource(R.drawable.suv);

        }



        back_a = (ImageView)findViewById(R.id.back_a);
        back_a.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

        custom_font = Typeface.createFromAsset(Confirm.this.getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(Confirm.this.getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(Confirm.this.getAssets(), "fonts/open-sans.semibold.ttf");


        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");

        price_text  = (TextView) findViewById(R.id.price_text);


        i_name = (TextInputLayout) findViewById(R.id.i_name);
        i_name.setTypeface(type);

        i_mobileno = (TextInputLayout) findViewById(R.id.i_mobileno);
        i_mobileno.setTypeface(type);

        i_picdate = (TextInputLayout) findViewById(R.id.i_picdate);
        i_picdate.setTypeface(type);


        i_pic = (TextInputLayout) findViewById(R.id.i_pic);
        i_pic.setTypeface(type);

        i_drop = (TextInputLayout) findViewById(R.id.i_drop);
        i_drop.setTypeface(type);



        price = (TextView) findViewById(R.id.price);
        ride = (TextView) findViewById(R.id.ride);


        price.setText("Total Ride Amount: Rs. "+ PrefUtils.getbook_price(Confirm.this));

        ride.setText(PrefUtils.getCity_name(Confirm.this)+" to "+PrefUtils.getend_poi(Confirm.this));


        name = (EditText) findViewById(R.id.name);
        name.setTypeface(custom_font);
        name.setInputType(InputType.TYPE_NULL);
        name.setText(PrefUtils.getbook_name(Confirm.this));


        mobileno = (EditText) findViewById(R.id.mobileno);
        mobileno.setTypeface(custom_font);
        mobileno.setInputType(InputType.TYPE_NULL);
        mobileno.setText(PrefUtils.getbook_mob(Confirm.this));

        picdate = (EditText) findViewById(R.id.picdate);
        picdate.setTypeface(custom_font);
        picdate.setInputType(InputType.TYPE_NULL);
        picdate.setText(PrefUtils.getbook_pdate(Confirm.this)+" , "+PrefUtils.getbook_ptime(Confirm.this));

        String tollTax = PrefUtils.gettollPrice(Confirm.this);


        Double finalTollTax = Double.parseDouble(tollTax);
        String tollFormat = decimalFormat.format(finalTollTax);
        finalTollTax = Double.valueOf(tollFormat);

        Log.e("final" , "" + finalTollTax);


        if(finalTollTax.equals(0.0)){

            price_text.setText("Rs. "+PrefUtils.getd_price1(Confirm.this) + "\n"+" + GST rate (5.0 %) : Rs. "+PrefUtils.getbook_tex(Confirm.this));



        }else {


            price_text.setText("Rs. "+PrefUtils.getd_price1(Confirm.this) + "\n"+" + GST rate (5.0 %) : Rs. "+PrefUtils.getbook_tex(Confirm.this) + "\n" + " + Toll tax : Rs. " + PrefUtils.gettollPrice(Confirm.this));


        }




        pic = (EditText) findViewById(R.id.pic);
        pic.setTypeface(custom_font);
        pic.setInputType(InputType.TYPE_NULL);
        pic.setText(PrefUtils.getbook_pc(Confirm.this));


        drop = (EditText) findViewById(R.id.drop);
        drop.setTypeface(custom_font);
        drop.setInputType(InputType.TYPE_NULL);
        drop.setText(PrefUtils.getbook_dc(Confirm.this));


        User_Login();


    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Confirm.this, MenuActivity.class);
        startActivity(intent);



    }


    private void User_Login(){


        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(Confirm.this);
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");




        restInterface.get_order_ststus(PrefUtils.geteOrder_ID(Confirm.this),"1",PrefUtils.getbook_type(Confirm.this),"go",new Callback<Response>()

           /* restInterface.SearchResult("search","'Goa'",new Callback<SearchPropertyModel>()*/ {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Response product, Response response) {
                //  Log.e("status&&&", "stat" + response.getReason() + "response=" + response.getUrl() + "status" + response.getStatus());

                Log.e("status", new String(((TypedByteArray) product.getBody()).getBytes()));

                String result = new String(((TypedByteArray) product.getBody()).getBytes());

                jObj = null;
                try {
                    jObj = new JSONObject(result);


                    status = jObj.getString("status");

                    Log.e("Response", ""+status);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loading.dismiss();

                if(status.equals("1")) {


                    Toast.makeText(Confirm.this, "Thank you for booking a Cab ! Enjoy your ride", Toast.LENGTH_LONG).show();

                }


                else{ Toast.makeText(Confirm.this, " No Cab Available ", Toast.LENGTH_SHORT).show(); }

            }

            @Override
            public void failure(RetrofitError error)
            {

                String merror = error.toString();
                Log.d("error", merror);
                loading.dismiss();
            }
        });

    }


}
