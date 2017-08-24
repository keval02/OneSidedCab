package com.onesidedcabs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesidedcabs.helper.Order_history;
import com.onesidedcabs.helper.Order_history_detail;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by manthanpatel on 27/02/2017.
 */

public class My_rides extends AppCompatActivity {

    JSONObject jObj;
    String status;
    private ProgressDialog loading;
    TravalListAdapter travalListAdapter;
    ListView listview;
    Typeface custom_font,custom_fontb,custom_fontl;

    ImageView back;

TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_rides);

        Order_list();


        custom_font = Typeface.createFromAsset(My_rides.this.getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(My_rides.this.getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(My_rides.this.getAssets(), "fonts/open-sans.semibold.ttf");

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");

        title =(TextView) findViewById(R.id.title);
        title.setTypeface(custom_fontb);


        listview = (ListView) findViewById(R.id.custom_listview_example);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PrefUtils.seth_id(""+i,My_rides.this);




                Intent intent = new Intent(My_rides.this, MyOrderdetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

    }




    private void Order_list(){

        String user_id = PrefUtils.getUser(My_rides.this).getUser().get(0).getUserid();
        //String  r_id  = PrefUtils.getr_id(My_rides.this);
        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(My_rides.this);
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");


        Log.e("Parameters" , "" + user_id);

        restInterface.get_usertraval(user_id,"go",new Callback<Response>()



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

                    PrefUtils.cleartravel(My_rides.this);

                    status = jObj.getString("status");

                    Log.e("Response", ""+status);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loading.dismiss();

                if(status.equals("1")) {


                    Order_history order_history = new GsonBuilder().create().fromJson(result, Order_history.class);
                    PrefUtils.setOrder_h(order_history, My_rides.this);



                    travalListAdapter = new TravalListAdapter(My_rides.this, order_history.getOrderhistory());
                    listview.setAdapter(travalListAdapter);

                }


                else{


                    Toast.makeText(My_rides.this, " No Cab Order ", Toast.LENGTH_SHORT).show();


                }

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

    public class TravalListAdapter extends BaseAdapter {



        private Context context;
        private ArrayList<Order_history_detail> listPojo;

        public TravalListAdapter(Context context, ArrayList<Order_history_detail> orderhistory) {


            this.context = context;
            this.listPojo = orderhistory;


        }



        @Override
        public int getCount() {
            return listPojo.size();


        }

        @Override
        public Object getItem(int position) {
            return listPojo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            TravalListAdapter.MyViewHolder holder = null;


            final Order_history_detail rowItem = (Order_history_detail) getItem(position);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.order_history_list_item_layout, null);

            holder = new TravalListAdapter.MyViewHolder();

            holder.ddd = (TextView) convertView.findViewById(R.id.ddd);

            holder.ddd.setTypeface(custom_font);
            holder.ddd.setText(""+rowItem.getPd());


            holder.price = (TextView) convertView.findViewById(R.id.from);
            holder.price.setText(rowItem.getOcity()+" to "+rowItem.getDcity());
            holder.price.setTypeface(custom_font);



            holder.from = (TextView) convertView.findViewById(R.id.cab);
            holder.from.setTypeface(custom_font);
            holder.from.setText("One Side Cab");

            holder.dd = (TextView) convertView.findViewById(R.id.dd);
            holder.dd.setTypeface(custom_font);




            holder.cancle = (LinearLayout) convertView.findViewById(R.id.cancle);
            holder.cancle.setTag(""+rowItem.getId());

            final MyViewHolder finalHolder = holder;
            holder.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String order_iid = ""+ finalHolder.cancle.getTag();

                    String pt = rowItem.getPt();
                    String pd = rowItem.getPd();

                    Log.e("pt" , pt);
                    Log.e("pd" , pd);










                    order_cancle(order_iid);

                }
            });


            return convertView;
        }

        class MyViewHolder {


            private TextView ddd,price,from,dd;
            LinearLayout cancle;
            String pickupTime;



        }


    }

    private void order_cancle(final String order_iid) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to cancle Cab");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                               Order_cancle(order_iid);

                            }


                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();


            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private void Order_cancle(String order_iid) {


        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(My_rides.this);
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");




        restInterface.user_login(order_iid,"go",new Callback<Response>()

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



                    Intent intent = new Intent(My_rides.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);



                }


                else{ Toast.makeText(My_rides.this, " No Cab Available ", Toast.LENGTH_SHORT).show(); }

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



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(My_rides.this, MenuActivity.class);
        startActivity(intent);


    }

}
