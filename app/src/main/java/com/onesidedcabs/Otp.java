package com.onesidedcabs;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manthanpatel on 27/02/2017.
 */

public class Otp extends AppCompatActivity {

    JSONObject jObj;
    String status;
    private ProgressDialog loading;

    TextView confirm,mob;
    EditText email,mobileno,otp;
    TextInputLayout i_otp;
    Typeface custom_font,custom_fontb,custom_fontl;

    String name1 ,email1,mobileno1;
    LinearLayout confirm_l;
    ImageView back_a;

    String json ="";

    String userId = "" , emailId = "" , name="" , mobileNo = "" , fcmDeviceID ="" , otpText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);


        Intent intent = getIntent();

        userId = intent.getStringExtra("userId");
        emailId = intent.getStringExtra("emailId");
        name = intent.getStringExtra("name");
        mobileNo = intent.getStringExtra("mobileNo");

        fcmDeviceID = FirebaseInstanceId.getInstance().getToken();

        back_a = (ImageView) findViewById(R.id.back_a);
        back_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        custom_font = Typeface.createFromAsset(Otp.this.getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(Otp.this.getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(Otp.this.getAssets(), "fonts/open-sans.semibold.ttf");

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");


        mob = (TextView)  findViewById(R.id.mob);
        mob.setText(PrefUtils.getUser(Otp.this).getUser().get(0).getPhone());

        i_otp = (TextInputLayout) findViewById(R.id.i_otp);
        i_otp.setTypeface(type);

        otp = (EditText)  findViewById(R.id.otp);
        otp.setTypeface(type);

        confirm = (TextView) findViewById(R.id.confirm);
        confirm.setTypeface(custom_font);

        confirm_l = (LinearLayout) findViewById(R.id.confirm_l);
        confirm_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                otpText = otp.getText().toString().trim();

                if(otp.getText().toString().equalsIgnoreCase("")||otp.getText().toString().equalsIgnoreCase(null)){


                    Toast.makeText(Otp.this, "Please enter your OTP", Toast.LENGTH_SHORT).show();


                }
                else if(otp.getText().toString().equalsIgnoreCase(PrefUtils.getUser(Otp.this).getUser().get(0).getOtp())){




                    new ConfrimOTP(otpText).execute();

                    /*PrefUtils.setlogin("0", Otp.this);

                    Intent intent = new Intent(Otp.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
*/



                }else{


                    Toast.makeText(Otp.this,"Wrong OTP Please Try Again",Toast.LENGTH_LONG).show();

                }



            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Otp.this, Login.class);
        startActivity(intent);


    }


    public  class  ConfrimOTP extends AsyncTask <Void , Void, String>{



        String confrimOTP = "";


        public ConfrimOTP(String confrimOTP) {
            this.confrimOTP = confrimOTP;
        }

        @Override
        protected String doInBackground(Void... params) {


            List<NameValuePair> pairList = new ArrayList<>();

            pairList.add(new BasicNameValuePair("phone" , mobileNo));
            pairList.add(new BasicNameValuePair("email_id" , emailId));
            pairList.add(new BasicNameValuePair("name" , name));
            pairList.add(new BasicNameValuePair("key" , "go"));
            pairList.add(new BasicNameValuePair("fcm_id" , fcmDeviceID));
            pairList.add(new BasicNameValuePair("device" , "android"));
            pairList.add(new BasicNameValuePair("userid" , userId));
            pairList.add(new BasicNameValuePair("otp" , confrimOTP));


            json = new ServiceHandler().makeServiceCall(RetrfitInterface.url + "signup.php" ,ServiceHandler.POST , pairList);



            Log.e("parameters" , "" + pairList);
            Log.e("json" , json);






            return json;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject mainObject = new JSONObject(s);



                String status = mainObject.getString("status");

                String message = mainObject.getString("message");
                if(status.equalsIgnoreCase("1")){


                    PrefUtils.setlogin("0", Otp.this);

                    Intent intent = new Intent(Otp.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {


                    Toast.makeText(Otp.this, ""+message, Toast.LENGTH_SHORT).show();



                }








            }catch (Exception e){

                e.printStackTrace();
            }





        }
    }

}
