package com.onesidedcabs;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.icu.text.TimeZoneFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.onesidedcabs.ccavenuenonseamless.WebViewActivity;
import com.onesidedcabs.custom_font.MyRadioButton;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by manthanpatel on 06/04/2017.
 */
public class Order extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private double dropedLocationLattitude;
    private double dropedLocationLongitude;

    private double pickLocationLattitude;
    private double pickLocationLongitude;

    private double ahmedabadAirpotLattitude = 23.073426;
    private double ahmedabadAirpotLongitude = 72.626571;

    private double mumbaiAirpotLattitude = 19.089560;
    private double mumbaiAirpotlongitude = 72.865614;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    TextView price, ride, confirm, service;
    EditText name;
    EditText mobileno;
    static EditText picdate;
    static EditText pictime;
    TextView pic;
    TextView drop;
    String order_id;
    TextInputLayout i_name, i_mobileno, i_picdate, i_pictime, i_pic, i_drop;
    Typeface custom_font, custom_fontb, custom_fontl;
    LinearLayout confirm_l, pic_date, pic_time;
    double res;
    double res23, res24;
    JSONObject jObj;
    String result = "";
    String status = "";
    private ProgressDialog loading;
    private Custom_ProgressDialog loadingView;
    double amount;
    ImageView back_a;
    String f_name, f_mobileno, f_picdate, f_pictime, f_pic, f_drop;
    String car_type;


    private Calendar cal;
    private int day;
    private int month;
    private int year;

    static final int DATE_PICKER_ID = 1111;

    AppPrefs prefs;

    String date = "", time = "", pickAdd = "", dropAdd = "";

    LinearLayout layout_from, layout_drop;

    String tollTax = "";

    ImageView pic_image,drop_image;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        fetchIDs();
    }

    private void fetchIDs() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        prefs = new AppPrefs(getApplicationContext());

        back_a = (ImageView) findViewById(R.id.back_a);
        back_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        custom_font = Typeface.createFromAsset(Order.this.getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(Order.this.getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(Order.this.getAssets(), "fonts/open-sans.semibold.ttf");


        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");


        car_type = PrefUtils.getfrom1(Order.this);
        ImageView immg = (ImageView) findViewById(R.id.immg);


        service = (TextView) findViewById(R.id.service);

        confirm = (TextView) findViewById(R.id.confirm);
        confirm_l = (LinearLayout) findViewById(R.id.confirm_l);
        confirm.setTypeface(type);

        i_name = (TextInputLayout) findViewById(R.id.i_name);
        i_name.setTypeface(type);

        i_mobileno = (TextInputLayout) findViewById(R.id.i_mobileno);
        i_mobileno.setTypeface(type);

        i_picdate = (TextInputLayout) findViewById(R.id.i_picdate);
        i_picdate.setTypeface(type);

        i_pictime = (TextInputLayout) findViewById(R.id.i_pictime);
        i_pictime.setTypeface(type);

        i_pic = (TextInputLayout) findViewById(R.id.i_pic);
        i_pic.setTypeface(type);

        i_drop = (TextInputLayout) findViewById(R.id.i_drop);
        i_drop.setTypeface(type);


        price = (TextView) findViewById(R.id.price);
        ride = (TextView) findViewById(R.id.ride);


        price.setText(String.valueOf("\u20B9  "+PrefUtils.getd_price1(Order.this)));
        ride.setText(PrefUtils.getCity_name(Order.this) + " to " + PrefUtils.getend_poi(Order.this));


        String string = PrefUtils.getd_price1(Order.this).replace(",", "");

        tollTax = PrefUtils.gettollPrice(Order.this);
        Double finalTollTax = Double.parseDouble(tollTax);
        String tollFormat = decimalFormat.format(finalTollTax);
        finalTollTax = Double.valueOf(tollFormat);

        amount = Double.parseDouble(string);
        res = (((amount / 100.0f)) * 5 );
        String format = decimalFormat.format(res);
        res = Double.valueOf(format);


        res23 = ((amount / 100.0f) * 5) + amount + finalTollTax;
        String format1 = decimalFormat.format(res23);
        res23 = Double.valueOf(format1);



        res24 = ((amount / 100.0f) * 5);
        String format2 = decimalFormat.format(res24);
        res24 = Double.valueOf(format2);

        if(finalTollTax.equals(0.0)){


            service.setText(" + GST rate (5.0 %) : \u20B9" + res);

        }else {


            service.setText(String.valueOf(" + GST rate (5.0 %) : \u20B9 " + res + "\n" + " + Toll tax : \u20B9 " + PrefUtils.gettollPrice(Order.this)));


        }




        name = (EditText) findViewById(R.id.name);
        name.setTypeface(custom_font);
        name.setText(PrefUtils.getUser(Order.this).getUser().get(0).getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setCursorVisible(true);
            }
        });

        mobileno = (EditText) findViewById(R.id.mobileno);
        mobileno.setTypeface(custom_font);
        mobileno.setText(PrefUtils.getUser(Order.this).getUser().get(0).getPhone());
        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileno.setCursorVisible(true);
            }
        });

        picdate = (EditText) findViewById(R.id.picdate);
        picdate.setTypeface(custom_font);
        picdate.setInputType(InputType.TYPE_NULL);

        pic = (TextView) findViewById(R.id.pic);
        pic.setTypeface(custom_font);

        drop = (TextView) findViewById(R.id.drop);
        drop.setTypeface(custom_font);


        layout_from = (LinearLayout) findViewById(R.id.layout_from);
        layout_drop = (LinearLayout) findViewById(R.id.layout_drop);

        pic_date = (LinearLayout) findViewById(R.id.pic_date);

        pictime = (EditText) findViewById(R.id.pictime);
        pictime.setTypeface(custom_font);
        pictime.setInputType(InputType.TYPE_NULL);
        pic_time = (LinearLayout) findViewById(R.id.pic_time);


        Intent intent = getIntent();

        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        pickAdd = intent.getStringExtra("pickAdd");
        dropAdd = intent.getStringExtra("dropAdd");

        picdate.setText(date);
        pictime.setText(time);
        pic.setText(pickAdd);
        drop.setText(dropAdd);


        pic_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setCursorVisible(false);
                mobileno.setCursorVisible(false);

                cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                showDialog(DATE_PICKER_ID);


            }
        });


        confirm_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f_name = name.getText().toString();
                f_mobileno = mobileno.getText().toString();
                f_picdate = picdate.getText().toString();
                f_pictime = pictime.getText().toString();
                f_pic = pic.getText().toString();
                f_drop = drop.getText().toString();


                if (f_name.equalsIgnoreCase("") || f_name.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    name.setFocusable(true);

                } else if (f_mobileno.equalsIgnoreCase("") || f_mobileno.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    mobileno.setFocusable(true);

                } else if (f_mobileno.length() != 10) {

                    Toast.makeText(Order.this, "Please enter 10 Digits Mobile Number", Toast.LENGTH_SHORT).show();
                    mobileno.setFocusable(true);

                } else if (f_picdate.equalsIgnoreCase("") || f_picdate.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Pickup Date", Toast.LENGTH_SHORT).show();
                    picdate.setFocusable(true);

                } else if (f_pictime.equalsIgnoreCase("") || f_pictime.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Pickup Time", Toast.LENGTH_SHORT).show();
                    pictime.setFocusable(true);

                } else if (f_pic.equalsIgnoreCase("") || f_pic.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Pickup Address", Toast.LENGTH_SHORT).show();
                    pic.setFocusable(true);

                } else if (f_drop.equalsIgnoreCase("") || f_drop.equalsIgnoreCase(null)) {

                    Toast.makeText(Order.this, "Please enter your Drop Address", Toast.LENGTH_SHORT).show();
                    drop.setFocusable(true);

                } else {


                    Order();
                }


            }
        });


        pic_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setCursorVisible(false);
                mobileno.setCursorVisible(false);

                TimePicker mTimePicker = new TimePicker();
                DateFormat.is24HourFormat(getApplicationContext());
                mTimePicker.show(getFragmentManager(), "Select time");


            }
        });



        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = String.valueOf(R.string.google_maps_key);

                Log.e("DebugKey" ,""+ key);
                prefs.setFromPlaced(true);

                openAutocompleteActivity();

//                name.setCursorVisible(false);
//                mobileno.setCursorVisible(false);
//
//                int off = 0;
//                try {
//                    off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
//                } catch (Settings.SettingNotFoundException e) {
//                    e.printStackTrace();
//                }
//                if (off == 0) {
//                    displayLocationSettingRequest(getApplicationContext());
//                } else {
//
//
//                    date = picdate.getText().toString().trim();
//                    time = pictime.getText().toString().trim();
//                    pickAdd = pic.getText().toString().trim();
//                    dropAdd = drop.getText().toString().trim();
//
//
//                    Intent intent = new Intent(Order.this, GetCurrentAddress.class);
//                    intent.putExtra("date", date);
//                    intent.putExtra("time", time);
//                    intent.putExtra("pickAdd", pickAdd);
//                    intent.putExtra("dropAdd", dropAdd);
//                    intent.putExtra("Lat", pickLocationLattitude);
//                    intent.putExtra("Long", pickLocationLongitude);
//
//
//                    prefs.setPickupfrom(true);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
            }
        });


        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.setToPlaced(true);
                openAutocompleteActivity();
//                name.setCursorVisible(false);
//                mobileno.setCursorVisible(false);
//
//
//                date = picdate.getText().toString().trim();
//                time = pictime.getText().toString().trim();
//                pickAdd = pic.getText().toString().trim();
//                dropAdd = drop.getText().toString().trim();
//
//
//                Intent intent = new Intent(Order.this, GetCurrentAddress.class);
//
//                intent.putExtra("date", date);
//                intent.putExtra("time", time);
//                intent.putExtra("pickAdd", pickAdd);
//                intent.putExtra("dropAdd", dropAdd);
//                intent.putExtra("Lat", dropedLocationLattitude);
//                intent.putExtra("Long", dropedLocationLongitude);
//
//                prefs.setDropat(true);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);


            }
        });


        pic_image=(ImageView)findViewById(R.id.pic_image);
        drop_image=(ImageView)findViewById(R.id.drop_image);

        pic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setCursorVisible(false);
                mobileno.setCursorVisible(false);

                int off = 0;
                try {
                    off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (off == 0) {
                    displayLocationSettingRequest(getApplicationContext());
                }
//
// else {


                    date = picdate.getText().toString().trim();
                    time = pictime.getText().toString().trim();
                    pickAdd = pic.getText().toString().trim();
                    dropAdd = drop.getText().toString().trim();


                    Intent intent = new Intent(Order.this, GetCurrentAddress.class);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("pickAdd", pickAdd);
                    intent.putExtra("dropAdd", dropAdd);
                    intent.putExtra("Lat", pickLocationLattitude);
                    intent.putExtra("Long", pickLocationLongitude);


                    prefs.setPickupfrom(true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                }
            }
        });


        drop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setCursorVisible(false);
                mobileno.setCursorVisible(false);


                date = picdate.getText().toString().trim();
                time = pictime.getText().toString().trim();
                pickAdd = pic.getText().toString().trim();
                dropAdd = drop.getText().toString().trim();


                Intent intent = new Intent(Order.this, GetCurrentAddress.class);

                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("pickAdd", pickAdd);
                intent.putExtra("dropAdd", dropAdd);
                intent.putExtra("Lat", dropedLocationLattitude);
                intent.putExtra("Long", dropedLocationLongitude);

                prefs.setDropat(true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        if (car_type.equalsIgnoreCase("HATCHBACK")) {

            immg.setImageResource(R.drawable.hatchback);

        } else if (car_type.equalsIgnoreCase("SEDAN")) {

            immg.setImageResource(R.drawable.sedan);

        } else if (car_type.equalsIgnoreCase("SUV")) {

            immg.setImageResource(R.drawable.suv);

        }


        if (prefs.isPickupfrom() == true) {


            String address = intent.getStringExtra("Address");


            pic.setText(address);

            prefs.setPickupfrom(false);

        }


        if (prefs.isDropat() == true) {


            String address = intent.getStringExtra("Address");


            drop.setText(address);

            prefs.setDropat(false);

        }


        if (prefs.isFromPlaced() == true) {


            pic.setText(prefs.getPlaceAddress());
            prefs.setFromPlaced(false);

        }

        if (prefs.isToPlaced() == true) {


            drop.setText(prefs.getPlaceAddress());
            prefs.setToPlaced(false);


        }

        String getPickedLocation = PrefUtils.getCity_name(Order.this);
        getPickupLocationFromAddress(getPickedLocation);

        if (PrefUtils.getr_id(Order.this).equalsIgnoreCase("1") || PrefUtils.getr_id(Order.this).equalsIgnoreCase("2")) {


            String getDropedLocation = PrefUtils.getend_poi(Order.this);

            getLocationFromAddress(getDropedLocation);

        }
        if (PrefUtils.getr_id(Order.this).equalsIgnoreCase("3")) {


            getLocationFromAddress(getPickedLocation);


        }


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }

    public static class TimePicker extends android.app.DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);



            return new TimePickerDialog(getActivity(), R.style.AlertDialogCustom, this, hour + 2, minute ,DateFormat.is24HourFormat(getActivity()));


        }


        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutecurrent = c.get(Calendar.MINUTE);
            Log.e("SEL MINUTE",""+minute);
            Log.e("MINITE ",""+minutecurrent);

            hour = hour + 2;


            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentDate = simpleDateFormat.format(date);

            String getDate = picdate.getText().toString().trim();

            if (currentDate.equalsIgnoreCase(getDate) || getDate.isEmpty()) {

                if ((hourOfDay < hour) || (hour==hourOfDay && minute<minutecurrent)) {

                    Log.e("SEL MINUTE-----",""+minute);
                    Log.e("MINITE------",""+minutecurrent);

//                    if(minute < minutecurrent)
//                    {
//                        Toast.makeText(getActivity(), "Pick-up time should be at least 2 hrs ahead.", Toast.LENGTH_LONG).show();
//
//                        pictime.setText("");
//                    }
//                    else {
                        Toast.makeText(getActivity(), "Pick-up time should be at least 2 hrs ahead.", Toast.LENGTH_LONG).show();

                        pictime.setText("");
//                    }




                } else {


                    /*Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, hourOfDay, minute);
                    pictime.setText((String) DateFormat.format("hh:mm aaa", calendar));

//*/
                    String min_str="";
                 //   Log.e("MIN LOG",""+minute);

                    if(minute<10)
                    {
                        min_str= "0"+minute;
                    }
                    else {
                        min_str= String.valueOf(minute);
                    }

                    pictime.setText(String.valueOf(hourOfDay) + ":" + min_str + ":" + "00");



                }
            }

            else {


                /*Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hourOfDay, minute);
                pictime.setText((String) DateFormat.format("hh:mm aaa", calendar));
*/

                String min_str="";
               // Log.e("MIN LOG",""+minute);

                if(minute<10)
                {
                    min_str= "0"+minute;
                }
                else {
                    min_str= String.valueOf(minute);
                }

                pictime.setText(String.valueOf(hourOfDay) + ":" + min_str + ":" + "00");


            }


        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // create a new DatePickerDialog with values you want to show

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.AlertDialogCustom, datePickerListener, year, month, day);
                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
                Date newDate = calendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));
                return datePickerDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the Date in the
        // DatePickerDialog
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            picdate.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);

            int mon = selectedMonth + 1;

            picdate.setText("" + (selectedDay < 10 ? ("0" + selectedDay) : (selectedDay)) + "-" + (mon < 10 ? ("0" + mon) : (mon)) + "-" + selectedYear);
        }
    };

    private void Order() {


        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loadingView = new Custom_ProgressDialog(Order.this, "");
            loadingView.setCancelable(false);
            loadingView.show();
//            loading = new ProgressDialog(Order.this);
//            loading.setMessage("Please Wait Loading data ....");
//            loading.show();
//            loading.setCancelable(false);

        }
        catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);

        restInterface.user_order(PrefUtils.getUser(Order.this).getUser().get(0).getEmail_id(), PrefUtils.getfrom1(Order.this), PrefUtils.getUser(Order.this).getUser().get(0).getUserid(), f_drop, f_pic, f_pictime, f_picdate, "" + amount, PrefUtils.getCity_name(Order.this), PrefUtils.gettravel_id(Order.this), PrefUtils.getend_poi(Order.this), PrefUtils.getfrom1(Order.this), PrefUtils.getr_id(Order.this), f_mobileno, f_name, "go", tollTax, new Callback<Response>()

        {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Response product, Response response) {

                result = new String(((TypedByteArray) product.getBody()).getBytes());

                jObj = null;
                try {
                    jObj = new JSONObject(result);

                    PrefUtils.cleartravel(Order.this);

                    status = jObj.getString("status");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                loadingView.dismiss();

                if (status.equals("1")) {


                    try {
                        order_id = jObj.getString("id");
                        PrefUtils.setOrder_ID(order_id, Order.this);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    final Dialog dialog = new Dialog(Order.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.pament_option);

                    LinearLayout confirm_l = (LinearLayout) dialog.findViewById(R.id.confirm_l);
                    final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radGroup);
                    final MyRadioButton afterradio = (MyRadioButton) dialog.findViewById(R.id.aftertraval);
                    final MyRadioButton beforeradio = (MyRadioButton) dialog.findViewById(R.id.online);

                    confirm_l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int selectedId = radioGroup.getCheckedRadioButtonId();

                            // find the radiobutton by returned id
                            RadioButton radioSexButton = (RadioButton) dialog.findViewById(selectedId);


                            if(!afterradio.isChecked() && !beforeradio.isChecked())
                            {
                                Toast.makeText(getApplicationContext(), "Please Select Any One Payment Method", Toast.LENGTH_SHORT).show();
                            }


                           else if (radioSexButton.getText().toString().equalsIgnoreCase("Pay to Driver")) {
                                dialog.dismiss();


                                f_name = name.getText().toString();
                                f_mobileno = mobileno.getText().toString();
                                f_picdate = picdate.getText().toString();
                                f_pictime = pictime.getText().toString();
                                f_pic = pic.getText().toString();
                                f_drop = drop.getText().toString();

                                PrefUtils.setbook_name(f_name, Order.this);
                                PrefUtils.setbook_mob(f_mobileno, Order.this);
                                PrefUtils.setbook_pdate(f_picdate, Order.this);
                                PrefUtils.setbook_ptime(f_pictime, Order.this);
                                PrefUtils.setbook_pc(f_pic, Order.this);
                                PrefUtils.setbook_dc(f_drop, Order.this);
                                PrefUtils.setbook_price("" + res23, Order.this);
                                PrefUtils.setbook_tex("" + res24, Order.this);

                                PrefUtils.setbook_type("1", Order.this);
                                Intent intent = new Intent(Order.this, Confirm.class);
                                startActivity(intent);

                            } else if (radioSexButton.getText().toString().equalsIgnoreCase("Pay Now")) {
                                dialog.dismiss();

                                PrefUtils.setbook_type("2", Order.this);

                                Intent intent = new Intent(Order.this, WebViewActivity.class);
                                intent.putExtra("amount", "" + res23);
                                startActivity(intent);


                            }


                        }
                    });
                    dialog.show();


                } else {


                }

            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.toString();

                loadingView.dismiss();
            }
        });

    }


    public void onBackPressed() {

        Intent intent = new Intent(Order.this, MenuActivity.class);
        startActivity(intent);
        prefs.setPlaceAddress("");


    }


    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();


        super.onResume();

        prefs.setPlaceAddress("");
        prefs.setFromPlaced(false);
        prefs.setToPlaced(false);


        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStart() {

        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


        prefs.setPlaceAddress("");
        super.onPause();


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();


        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */

        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }


    private void displayLocationSettingRequest(Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:


                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:


                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Order.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });


    }


    public void getLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this


        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location = address.get(0);

            if (strAddress.equalsIgnoreCase("Ahmedabad")) {


                dropedLocationLattitude = ahmedabadAirpotLattitude;
                dropedLocationLongitude = ahmedabadAirpotLongitude;


            } else if (strAddress.equalsIgnoreCase("Mumbai")) {


                dropedLocationLattitude = mumbaiAirpotLattitude;
                dropedLocationLongitude = mumbaiAirpotlongitude;


            } else {

                dropedLocationLattitude = location.getLatitude();
                dropedLocationLongitude = location.getLongitude();


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getPickupLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this


        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //check for null
            if (address == null) {
                return;
            }

            Address location = address.get(0);

            if (strAddress.equalsIgnoreCase("Ahmedabad")) {

                pickLocationLattitude = ahmedabadAirpotLattitude;
                pickLocationLongitude = ahmedabadAirpotLongitude;


            } else if (strAddress.equalsIgnoreCase("Mumbai")) {


                pickLocationLattitude = mumbaiAirpotLattitude;
                pickLocationLongitude = mumbaiAirpotlongitude;


            } else {

                pickLocationLattitude = location.getLatitude();
                pickLocationLongitude = location.getLongitude();


            }
            //Put marker on map on that LatLng


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("IN")
                    .build();

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(typeFilter)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            // Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                // Log.i(TAG, "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.


                String getAddress = place.getAddress().toString().trim();

                Log.e("getAddress" , getAddress);

                if(prefs.isFromPlaced()==true){

                    pic.setText(getAddress);
                    prefs.setFromPlaced(false);

                }

                if(prefs.isToPlaced()==true){

                    drop.setText(getAddress);
                    prefs.setToPlaced(false);


                }


                /*pic.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));
*/




                // Display attributions if required.
                /*CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    pic.setText(Html.fromHtml(attributions.toString()));
                } else {
                    pic.setText("");
                }*/
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                //   Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }



}
