package com.onesidedcabs;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class SearchCities extends AppCompatActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    String getText = "";

    AppPrefs app;

    String date="" , time="" , pickAdd="" , dropAdd="";

    AutoCompleteTextView autoTextView ;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cities);



        app = new AppPrefs(getApplicationContext());


        Intent intent = getIntent();

        date=intent.getStringExtra("date");
        time=intent.getStringExtra("time");
        pickAdd=intent.getStringExtra("pickAdd");
        dropAdd=intent.getStringExtra("dropAdd");

        autoTextView = (AutoCompleteTextView) findViewById(R.id.autoTextView);




        openAutocompleteActivity();
        // Retrieve the TextViews that will display details about the selected place.

    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
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

                Log.e("Place Selected" , "" + place.getWebsiteUri());

                // Format the place's details and display them in the TextView.
                /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));


*/


                if(app.isFromPlaced()==true){



                    getText = place.getAddress().toString().trim();
                }

                if(app.isToPlaced() == true){



                    getText = place.getAddress().toString().trim();
                }






                Intent intent = new Intent(getApplicationContext() , Order.class);
                intent.putExtra("Place" , getText);
                intent.putExtra("date" , date);
                intent.putExtra("time" , time);
                intent.putExtra("pickAdd" , pickAdd);
                intent.putExtra("dropAdd" , dropAdd);
                app.setPlaceAddress(getText);
                startActivity(intent);

                Log.e("getText" , getText);
                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
  //                  mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
    //                mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
             //   Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        //Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
               // websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }


    @Override
    public void onBackPressed() {

        if(app.isFromPlaced()==true){

            app.setFromPlaced(false);
        }


        if(app.isToPlaced()==true){

            app.setToPlaced(false);
        }

        Intent intent = new Intent(getApplicationContext() , Order.class);
        startActivity(intent);
    }
}

