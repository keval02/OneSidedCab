package com.onesidedcabs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.onesidedcabs.helper.City;
import com.onesidedcabs.helper.City_detail;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;
import com.onesidedcabs.helper.Travel;
import com.onesidedcabs.helper.Travel_detail;
import com.onesidedcabs.residemenu.ResideMenu;
import com.google.gson.GsonBuilder;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class HomeFragment extends Fragment {

    TextView end_poi,from1,from2,from3,d_price1,d_price2,d_price3;
    LinearLayout view_l,city_l;
    private ListView lv,androidListView;
    TravalListAdapter  travalListAdapter;
    // Listview Adapter
   // ArrayAdapter<String> adapter;
    Travel travel;
    // Search EditText
    EditText inputSearch;
   // String r_id;
    CityListAdapter cityListAdapter;

    JSONObject jObj;
    String status ="";
    private ProgressDialog loading;
    private View parentView;
    private ResideMenu resideMenu;
    private TextView title_from,title,city;
    Typeface custom_font,custom_fontb,custom_fontl;
    int t;
    ImageView downarrow;
    TextView select_pic,pic_title;
    CardView no_cab;
    City city_list;

    AppPrefs prefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);

        prefs = new AppPrefs(getContext());
        prefs.setPickupfrom(false);
        prefs.setDropat(false);
        prefs.setFromPlaced(false);
        prefs.setToPlaced(false);


        PrefUtils.setCity_name("Vadodara",getActivity());

        PrefUtils.setr_id("1",getActivity());


        t = 0;

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String token = FirebaseInstanceId.getInstance().getToken();



        Log.e("userID" , PrefUtils.getUser(getActivity()).getUser().get(t).getUserid());

        setUpViews();

      //  City_list();

       // Travel_list();

        //Toast.makeText(getActivity(), " HI ", Toast.LENGTH_SHORT).show();

        custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open-sans.semibold.ttf");


        downarrow  = (ImageView) parentView.findViewById(R.id.downarrow);

        view_l = (LinearLayout)  parentView.findViewById(R.id.view_l);
        title_from = (TextView) parentView.findViewById(R.id.title_from);
        title       = (TextView) parentView.findViewById(R.id.title);
        city        = (TextView) parentView.findViewById(R.id.city);
        no_cab      = (CardView) parentView.findViewById(R.id.no_cab);
        city_l = (LinearLayout)  parentView.findViewById(R.id.city_l);


        lv = (ListView) parentView.findViewById(R.id.list_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               // Toast.makeText(getActivity(), ""+city_list.getCity().get(i).getCity_name(), Toast.LENGTH_SHORT).show();

                city.setText(city_list.getCity().get(i).getCity_name());

                PrefUtils.setCity_name(city_list.getCity().get(i).getCity_name(),getActivity());

                lv.setVisibility(View.GONE);
                androidListView.setVisibility(View.VISIBLE);
                pic_title.setVisibility(View.VISIBLE);
                downarrow.setRotation(0);
                select_pic.setVisibility(View.GONE);
                view_l.setVisibility(View.GONE);
                t = 0;

                Travel_list();

            }
        });







        androidListView = (ListView) parentView.findViewById(R.id.custom_listview_example);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               // Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();


                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dilog_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                end_poi = (TextView) dialog.findViewById(R.id.end_poi);
                end_poi.setTypeface(custom_font);
                end_poi.setText(travel.getTravel().get(i).getEndpoint());
                end_poi.setTag(travel.getTravel().get(i).getTravel_id());


                d_price1 = (TextView) dialog.findViewById(R.id.d_price1);
                d_price1.setTypeface(custom_font);
                d_price1.setText(travel.getTravel().get(i).getPrice1());

                d_price2 = (TextView) dialog.findViewById(R.id.d_price2);
                d_price2.setTypeface(custom_font);
                d_price2.setText(travel.getTravel().get(i).getPrice2());

                d_price3 = (TextView) dialog.findViewById(R.id.d_price3);
                d_price3.setTypeface(custom_font);
                d_price3.setText(travel.getTravel().get(i).getPrice3());


                TextView select1 = (TextView) dialog.findViewById(R.id.select1);
                select1.setTypeface(custom_font);

                TextView select2 = (TextView) dialog.findViewById(R.id.select2);
                select2.setTypeface(custom_font);

                TextView select3 = (TextView) dialog.findViewById(R.id.select3);
                select3.setTypeface(custom_font);



                from1 = (TextView) dialog.findViewById(R.id.from1);
                from1.setTypeface(custom_font);

                from2 = (TextView) dialog.findViewById(R.id.from2);
                from2.setTypeface(custom_font);



                from3 = (TextView) dialog.findViewById(R.id.from3);
                from3.setTypeface(custom_font);

                String tollTax = travel.getTravel().get(i).getToll_tax();
                PrefUtils.settollTax(tollTax , getActivity());

                Log.e("tollTax" , tollTax);



                select1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PrefUtils.setend_poi(end_poi.getText().toString(),getActivity());
                        PrefUtils.setfrom1("HATCHBACK",getActivity());

                        PrefUtils.setd_price1(d_price1.getText().toString(),getActivity());

                        PrefUtils.settravel_id(end_poi.getTag().toString(),getActivity());



                      GO_detail();

                    }
                });


                select2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();

                        PrefUtils.setend_poi(end_poi.getText().toString(),getActivity());
                        PrefUtils.settravel_id(end_poi.getTag().toString(),getActivity());

                        PrefUtils.setfrom1(from2.getText().toString(),getActivity());

                        PrefUtils.setd_price1(d_price2.getText().toString(),getActivity());



                        GO_detail();
                    }
                });



                select3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();

                        PrefUtils.setend_poi(end_poi.getText().toString(),getActivity());
                        PrefUtils.settravel_id(end_poi.getTag().toString(),getActivity());

                        PrefUtils.setfrom1(from3.getText().toString(),getActivity());

                        PrefUtils.setd_price1(d_price3.getText().toString(),getActivity());



                        GO_detail();
                    }
                });



                ImageView close = (ImageView) dialog.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });




                dialog.show();
            }
        });

        inputSearch = (EditText) parentView.findViewById(R.id.inputSearch);
        inputSearch.setTypeface(custom_font);
        inputSearch.setVisibility(View.GONE);

        select_pic = (TextView) parentView.findViewById(R.id.select_pic);
        select_pic.setTypeface(custom_fontb);
        pic_title  = (TextView) parentView.findViewById(R.id.pic_title);
        pic_title.setTypeface(custom_fontb);

//        // Adding items to listview
//        cityListAdapter = new CityListAdapter(getActivity(), androidListViewStrings);
//        lv.setAdapter(cityListAdapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               // cityListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });



        city.setTypeface(custom_font);

        city_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(t == 0) {
                    lv.setVisibility(View.VISIBLE);
                    androidListView.setVisibility(View.GONE);
                    pic_title.setVisibility(View.GONE);
                    downarrow.setRotation(180);
                    select_pic.setVisibility(View.VISIBLE);
                    view_l.setVisibility(View.VISIBLE);
                    t = 1;
                }
                else if(t == 1){

                    lv.setVisibility(View.GONE);
                    androidListView.setVisibility(View.VISIBLE);
                    pic_title.setVisibility(View.VISIBLE);
                    downarrow.setRotation(0);
                    select_pic.setVisibility(View.GONE);
                    view_l.setVisibility(View.GONE);
                    t = 0;

                }

            }
        });

        BottomBar bottomBar = (BottomBar) parentView.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                title.setText(TabMessage.get(tabId, false));
                title.setTypeface(custom_fontb);

                String t = TabMessage.get(tabId, false);

                //Toast.makeText(getActivity(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();

                 if(t.equalsIgnoreCase("One Side")) {

                     PrefUtils.setr_id("1",getActivity());
                    title_from.setText("ONE WAY FROM");
                    title_from.setTypeface(custom_fontl);

                     PrefUtils.setCity_name("Vadodara",getActivity());
                   //Travel_list();
                     city.setText("Vadodara");


                     City_list();

                 }
                else if(t.equalsIgnoreCase("Hourly Cab")) {

                     PrefUtils.setr_id("3",getActivity());
                    title_from.setText("SELECT CITY");
                    title_from.setTypeface(custom_fontl);

                     PrefUtils.setCity_name("Vadodara",getActivity());

                     city.setText("Vadodara");

                     Travel_list();

                }
                else if(t.equalsIgnoreCase("Outstation (Return)")) {

                     PrefUtils.setr_id("2",getActivity());
                    title_from.setText("SELECT PICKUP CITY");
                    title_from.setTypeface(custom_fontl);

                     PrefUtils.setCity_name("Vadodara",getActivity());
                     city.setText("Vadodara");

                     Travel_list();

                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toast.makeText(getActivity(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });


        return parentView;
    }

    private void GO_detail() {


        String city_name = PrefUtils.getCity_name(getActivity());

        Intent intent = new Intent(getActivity(), Order.class);
        startActivity(intent);


    }


    private void setUpViews() {


        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        LinearLayout ignored_view = (LinearLayout) parentView.findViewById(R.id.homel);
        resideMenu.addIgnoredView(ignored_view);




    }



    private void City_list(){


       // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(getActivity());
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");




        restInterface.city_list_web("go",new Callback<Response>()

           /* restInterface.SearchResult("search","'Goa'",new Callback<SearchPropertyModel>()*/ {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Response product, Response response) {
                //  Log.e("status&&&", "stat" + response.getReason() + "response=" + response.getUrl() + "status" + response.getStatus());

                if (Internet.isConnectingToInternet(getActivity())) {

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


                        city_list = new GsonBuilder().create().fromJson(result, City.class);
                        PrefUtils.setCity(city_list, getActivity());

                        cityListAdapter = new CityListAdapter(getActivity(), city_list.getCity());
                        lv.setAdapter(cityListAdapter);


                        // Toast.makeText(getActivity(), " Login successfully ", Toast.LENGTH_SHORT).show();

                        Travel_list();
                    } else {
                        Toast.makeText(getActivity(), " No cabs Found ", Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Internet.noInternet(getActivity());
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







    public class CityListAdapter extends BaseAdapter {



        private Context context;
        private ArrayList<City_detail> listPojo;

        public CityListAdapter(Context context, ArrayList<City_detail> city_list) {


            this.context = context;
            this.listPojo = city_list;


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


            MyViewHolder holder = null;


            City_detail rowItem = (City_detail) getItem(position);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.custom_list_city_layout, null);

            holder = new MyViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.text_view);
            holder.title.setText(""+rowItem.getCity_name());
            holder.title.setTypeface(custom_font);
            holder.title.setTag(""+rowItem.getCity_id());


            return convertView;
        }

        class MyViewHolder {


            private TextView title;



        }


    }




    private void Travel_list(){

        String city_name = PrefUtils.getCity_name(getActivity());
        String  r_id  = PrefUtils.getr_id(getActivity());
        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loading = new ProgressDialog(getActivity());
            loading.setMessage("Please Wait Loading data ....");
            loading.show();
            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");




        restInterface.get_traval(city_name,r_id,"go",new Callback<Response>()

           /* restInterface.SearchResult("search","'Goa'",new Callback<SearchPropertyModel>()*/ {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Response product, Response response) {
                //  Log.e("status&&&", "stat" + response.getReason() + "response=" + response.getUrl() + "status" + response.getStatus());

                if(Internet.isConnectingToInternet(getActivity())) {


                Log.e("status", new String(((TypedByteArray) product.getBody()).getBytes()));

                String result = new String(((TypedByteArray) product.getBody()).getBytes());

                jObj = null;
                try {
                    jObj = new JSONObject(result);

                    PrefUtils.cleartravel(getActivity());

                    status = jObj.getString("status");

                    Log.e("Response", ""+status);

                    no_cab.setVisibility(View.GONE);
                    androidListView.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loading.dismiss();




                    if (status.equals("1")) {


                        no_cab.setVisibility(View.GONE);

                        travel = new GsonBuilder().create().fromJson(result, Travel.class);
                        PrefUtils.setTravel(travel, getActivity());

                        androidListView.setVisibility(View.VISIBLE);

                        travalListAdapter = new TravalListAdapter(getActivity(), travel.getTravel());
                        androidListView.setAdapter(travalListAdapter);


                    } else {

                        androidListView.setVisibility(View.GONE);
                        no_cab.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), " No Cab Available ", Toast.LENGTH_SHORT).show();


                    }
                }else {


                    Internet.noInternet(getActivity());

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
        private ArrayList<Travel_detail> listPojo;

        public TravalListAdapter(Context context, ArrayList<Travel_detail> travel_detail) {


            this.context = context;
            this.listPojo = travel_detail;


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


            Travel_detail rowItem = (Travel_detail) getItem(position);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.custom_list_item_layout, null);

            holder = new TravalListAdapter.MyViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.text_view);
            holder.title.setText(""+rowItem.getEndpoint());
            holder.title.setTypeface(custom_font);
            holder.title.setTag(""+rowItem.getTravel_id());


            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.price.setText("Rs. "+rowItem.getPrice1());
            holder.price.setTypeface(custom_font);

            holder.from = (TextView) convertView.findViewById(R.id.from);
            holder.from.setTypeface(custom_font);

            holder.button = (TextView) convertView.findViewById(R.id.button);
            holder.button.setTypeface(custom_font);


            return convertView;
        }

        class MyViewHolder {


            private TextView title,price,from,button;



        }


    }

}
