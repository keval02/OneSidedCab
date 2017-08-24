package com.onesidedcabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GetCurrentAddress extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private double dropedLocationLat;
    private double dropedLocationLong;

    private double pickedLocationLattitude;
    private double pickedLocationLongitude;

    ImageView img_refersh;
    TextView txt_lat_long;

    private GoogleMap googleMap;

    Toolbar toolbar;

    TextView txt_address;

    String strAdd = "";

    AppPrefs prefs;

    Button btn_submit;

    String date = "", time = "", pickAdd = "", dropAdd = "";

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private ProgressDialog loading;

    Circle myCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_address);
        txt_address = (TextView) findViewById(R.id.txt_address);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        prefs = new AppPrefs(getApplicationContext());


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setVisibility(View.VISIBLE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        currentLatitude = intent.getDoubleExtra("Lat", 0.0);
        currentLongitude = intent.getDoubleExtra("Long", 0.0);
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        pickAdd = intent.getStringExtra("pickAdd");
        dropAdd = intent.getStringExtra("dropAdd");

        dropedLocationLat = intent.getDoubleExtra("Lat", 0.0);
        dropedLocationLong = intent.getDoubleExtra("Long", 0.0);

        pickedLocationLattitude = intent.getDoubleExtra("Lat", 0.0);
        pickedLocationLongitude = intent.getDoubleExtra("Long", 0.0);



        img_refersh = (ImageView) findViewById(R.id.img_refresh);
        img_refersh.setVisibility(View.VISIBLE);


        txt_lat_long = (TextView) findViewById(R.id.txt_lat_long);
        txt_lat_long.setText(currentLatitude + " Lat " + currentLongitude + " Long ");


        getCompleteAddressString(currentLatitude, currentLongitude);

        img_refersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int off = 0;
                try {
                    off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (off == 0) {
                    displayLocationSettingRequest(getApplicationContext());
                } else {


                    new RefershLocation().execute();

                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getAddress = txt_address.getText().toString().trim();



                Intent intent = new Intent(GetCurrentAddress.this, Order.class);
                intent.putExtra("Address", getAddress);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("pickAdd", pickAdd);
                intent.putExtra("dropAdd", dropAdd);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });


        setUpMapIfNeeded();

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

    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();



        super.onResume();


        //Now lets connect to the API
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onPause() {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


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


    private void setUpMapIfNeeded() {

        if (googleMap == null) {
            SupportMapFragment supportMapFragment = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map));
            supportMapFragment.getMapAsync(this);

        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {



        googleMap.clear();

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (prefs.isDropat() == true) {


            final LatLng latLng = new LatLng(dropedLocationLat, dropedLocationLong);

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

/*


            CircleOptions circle = new CircleOptions()
                    .center(latLng)
                    .radius(500)
                    .fillColor(Color.RED)
                    .strokeColor(Color.BLUE)
                    .strokeWidth(5);



            myCircle = googleMap.addCircle(circle);

*/






        } else if (prefs.isPickupfrom() == true) {


            final LatLng latLng = new LatLng(pickedLocationLattitude, pickedLocationLongitude);

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));


/*
            CircleOptions circle = new CircleOptions()
                    .center(latLng)
                    .radius(500)
                    .fillColor(Color.RED)
                    .strokeColor(Color.BLUE)
                    .strokeWidth(5);



            myCircle = googleMap.addCircle(circle);

*/



        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.clear();

                currentLatitude = latLng.latitude;
                currentLongitude = latLng.longitude;



                txt_lat_long.setText(currentLatitude + " Lat " + currentLongitude + " Long ");
                getCompleteAddressString(currentLatitude, currentLongitude);



                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }
        });





    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Order.class);
        intent.putExtra("Address", "");
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("pickAdd", pickAdd);
        intent.putExtra("dropAdd", dropAdd);


        prefs.setDropat(false);
        prefs.setPickupfrom(false);
        startActivity(intent);
        finish();


        //super.onBackPressed();
    }


    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {



        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");

                }
                strAdd = strReturnedAddress.toString();

                txt_address.setText(strAdd);

            } else {

                Toast.makeText(this, "No Address returned!", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Canont get Address!", Toast.LENGTH_SHORT).show();


        }
        return strAdd;
    }

    public void displayLocationSettingRequest(Context context) {


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
                            status.startResolutionForResult(GetCurrentAddress.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });


    }




    public class RefershLocation extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                loading = new ProgressDialog(GetCurrentAddress.this);
                loading.setMessage("Please Wait Loading data ....");
                loading.show();
                loading.setCancelable(false);
            } catch (Exception e) {


            }


        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            try{

                if (prefs.isDropat() == true) {




                    getCompleteAddressString(dropedLocationLat, dropedLocationLong);

                    setUpMapIfNeeded();

                } else if (prefs.isPickupfrom() == true) {




                    getCompleteAddressString(pickedLocationLattitude, pickedLocationLongitude);
                    setUpMapIfNeeded();

                } else {







                    getCompleteAddressString(currentLatitude, currentLongitude);
                    setUpMapIfNeeded();


                }




            }catch (Exception e){



            }


        }
    }



}

