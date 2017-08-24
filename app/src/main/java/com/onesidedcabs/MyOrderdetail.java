package com.onesidedcabs;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onesidedcabs.helper.Order_history;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MyOrderdetail extends AppCompatActivity {

    ImageView back_a;
    TextView price_text, ride, price, cancelID;
    EditText name, mobileno, picdate, pic, drop;
    String car_type;
    Order_history order_history;
    int k;
    double res23;
    Typeface custom_font;
    LinearLayout Cancel;

    JSONObject jObj;
    String status;
    private ProgressDialog loading;

    Date time1, time2;

    Handler mHandler;
    Runnable mRunnable;

    public static final int DELAY_TIME = 20000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        back_a = (ImageView) findViewById(R.id.back_a);
        back_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");
        k = Integer.parseInt(PrefUtils.geth_id(MyOrderdetail.this));


        order_history = PrefUtils.getOrder_h(MyOrderdetail.this);


        //Toast.makeText(MyOrderdetail.this, ""+order_history.getOrderhistory().get(k).getOprice(), Toast.LENGTH_SHORT).show();

        car_type = PrefUtils.getfrom1(MyOrderdetail.this);
        ImageView immg = (ImageView) findViewById(R.id.immg);


        if (car_type.equalsIgnoreCase("HATCHBACK")) {

            immg.setImageResource(R.drawable.hatchback);

        } else if (car_type.equalsIgnoreCase("SEDAN")) {

            immg.setImageResource(R.drawable.sedan);

        } else if (car_type.equalsIgnoreCase("SUV")) {

            immg.setImageResource(R.drawable.suv);

        }


        price = (TextView) findViewById(R.id.price);
        ride = (TextView) findViewById(R.id.ride);
        price_text = (TextView) findViewById(R.id.price_text);

        cancelID = (TextView) findViewById(R.id.cancelID);
        cancelID.setVisibility(View.VISIBLE);


        Double amount = Double.parseDouble(order_history.getOrderhistory().get(k).getOprice());
        Double amount1 = Double.parseDouble(order_history.getOrderhistory().get(k).getTaxprice());
        Double tollTax = Double.parseDouble(order_history.getOrderhistory().get(k).getToll_tax());
        res23 = amount + amount1+tollTax;

        price.setText("Total Ride Amount: Rs. " + res23);
        ride.setText(order_history.getOrderhistory().get(k).getOcity() + " to " + order_history.getOrderhistory().get(k).getDcity());
        price_text.setText("Rs. " + order_history.getOrderhistory().get(k).getOprice() + "\n" + " + GST rate (5.0 %) : Rs. " + order_history.getOrderhistory().get(k).getTaxprice() + "\n" + " + Toll tax : Rs. " + order_history.getOrderhistory().get(k).getToll_tax());

        name = (EditText) findViewById(R.id.name);
        name.setTypeface(custom_font);
        name.setInputType(InputType.TYPE_NULL);
        name.setText(order_history.getOrderhistory().get(k).getName());


        mobileno = (EditText) findViewById(R.id.mobileno);
        mobileno.setTypeface(custom_font);
        mobileno.setInputType(InputType.TYPE_NULL);
        mobileno.setText(order_history.getOrderhistory().get(k).getPhone());

        picdate = (EditText) findViewById(R.id.picdate);
        picdate.setTypeface(custom_font);
        picdate.setInputType(InputType.TYPE_NULL);
        picdate.setText(order_history.getOrderhistory().get(k).getPd() + " , " + order_history.getOrderhistory().get(k).getPt());


        pic = (EditText) findViewById(R.id.pic);
        pic.setTypeface(custom_font);
        pic.setInputType(InputType.TYPE_NULL);
        pic.setText(order_history.getOrderhistory().get(k).getPick_add());


        drop = (EditText) findViewById(R.id.drop);
        drop.setTypeface(custom_font);
        drop.setInputType(InputType.TYPE_NULL);
        drop.setText(order_history.getOrderhistory().get(k).getDa());

        Cancel = (LinearLayout) findViewById(R.id.Cancel);
        Cancel.setVisibility(View.VISIBLE);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyOrderdetail.this);
                alertDialogBuilder.setMessage("Do You want to cancel the Cab ?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                Order_cancle();

                            }


                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();


                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        cancelID.setVisibility(View.VISIBLE);
        Cancel.setVisibility(View.VISIBLE);

        //  CancelOrderbeforeHalfanHour();

        //Check();

        checkOrder();

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {

                checkOrder();
                mHandler.postDelayed(this, DELAY_TIME);
            }
        };

        mHandler.postDelayed(mRunnable, DELAY_TIME);


    }

    private void Order_cancle() {


        String userId = PrefUtils.getUser(MyOrderdetail.this).getUser().get(0).getUserid();

        Log.e("userId" , "" +userId);

        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(MyOrderdetail.this);
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");


        restInterface.deleteOrder(order_history.getOrderhistory().get(k).getId(), "go", userId,new Callback<Response>()

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

                    Log.e("Response", "" + status);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loading.dismiss();

                if (status.equals("1")) {


                    Intent intent = new Intent(MyOrderdetail.this, My_rides.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                } else {
                    Toast.makeText(MyOrderdetail.this, " No Cab Available ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.toString();
                Log.d("error", merror);
                loading.dismiss();
            }
        });


    }

    public void onBackPressed() {
        Intent intent = new Intent(MyOrderdetail.this, My_rides.class);
        startActivity(intent);
    }

    private void checkOrder(){
        String getPickupTime = order_history.getOrderhistory().get(k).getPt();
        String getPickupDate = order_history.getOrderhistory().get(k).getPd();

        Log.e("PickUpDtTm",getPickupDate+" "+getPickupTime);

        String[] pickupDate=getPickupDate.split("-");
        String[] pickupTime=getPickupTime.split(":");
        Date pickupDateTime=new Date();

        Log.e("Date",pickupDate[0]+" "+pickupDate[1]+" "+pickupDate[2]);
        Calendar pickupCalender=Calendar.getInstance();
        pickupDateTime.setDate(Integer.parseInt(pickupDate[0]));
        pickupDateTime.setMonth(Integer.parseInt(pickupDate[1])-1);
        pickupDateTime.setHours(Integer.parseInt(pickupTime[0]));
        pickupDateTime.setMinutes(Integer.parseInt(pickupTime[1]));
        pickupCalender.setTime(pickupDateTime);
        pickupCalender.set(Calendar.YEAR, Integer.parseInt(pickupDate[2]));
        pickupCalender.add(Calendar.MINUTE,-30);

        Log.e("Pickup time",pickupCalender.getTime().toString());

        Calendar todayCalender=Calendar.getInstance();

        Log.e("Today Time",todayCalender.getTime().toString());

        if(todayCalender.after(pickupCalender) || todayCalender.equals(pickupCalender)){
            Log.e("Time","Over");
            Cancel.setVisibility(View.GONE);
            cancelID.setVisibility(View.GONE);
        }
    }


}
