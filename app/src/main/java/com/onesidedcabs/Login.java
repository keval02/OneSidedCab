package com.onesidedcabs;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;
import com.onesidedcabs.helper.User;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.provider.Settings.Secure;

/**
 * Created by manthanpatel on 27/02/2017.
 */

public class Login extends AppCompatActivity {


    boolean isMdevice;
    boolean pstatus;
    String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            android.Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WAKE_LOCK,
    Manifest.permission.RECEIVE_BOOT_COMPLETED};

    int code = 1;


    JSONObject jObj;
    String status;
    private ProgressDialog loading;
    private Custom_ProgressDialog loadingView;

    TextView login;
    EditText email, mobileno, name;
    TextInputLayout i_email, i_mobileno, i_name;
    Typeface custom_font, custom_fontb, custom_fontl;

    String name1, email1, mobileno1;


    String deviceId = "";

    String userId = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        isMdevice = isMarshmallowPlusDevice();
        pstatus = isPermissionRequestRequired(Login.this, perms, code);

        Log.e("final" , "-==>");



        String log_id = PrefUtils.getuID(Login.this);
        if (log_id.equalsIgnoreCase("a")) {

            Intent intent = new Intent(Login.this, MenuActivity.class);
            startActivity(intent);
        }

        custom_font = Typeface.createFromAsset(Login.this.getAssets(), "fonts/open-sans.regular.ttf");

        custom_fontb = Typeface.createFromAsset(Login.this.getAssets(), "fonts/open-sans.semibold.ttf");

        custom_fontl = Typeface.createFromAsset(Login.this.getAssets(), "fonts/open-sans.semibold.ttf");

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/open-sans.regular.ttf");
        email = (EditText) findViewById(R.id.email);
        email.setTypeface(type);

        mobileno = (EditText) findViewById(R.id.mobileno);
        mobileno.setTypeface(type);

        name = (EditText) findViewById(R.id.name);
        name.setTypeface(type);


        i_email = (TextInputLayout) findViewById(R.id.i_email);
        i_email.setTypeface(type);

        i_mobileno = (TextInputLayout) findViewById(R.id.i_mobileno);
        i_mobileno.setTypeface(type);

        i_name = (TextInputLayout) findViewById(R.id.i_name);
        i_name.setTypeface(type);


        login = (TextView) findViewById(R.id.login);
        login.setTypeface(custom_font);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(Login.this, MainActivity.class);
//                startActivity(intent);


                name1 = name.getText().toString();
                email1 = email.getText().toString();
                mobileno1 = mobileno.getText().toString();


                if (name1.equalsIgnoreCase("") || name1.equalsIgnoreCase(null)) {

                    Toast.makeText(Login.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    name.setFocusable(true);

                } else if (mobileno1.equalsIgnoreCase("") || mobileno1.equalsIgnoreCase(null)) {

                    Toast.makeText(Login.this, "Please enter your  Mobile Number", Toast.LENGTH_SHORT).show();
                    mobileno.setFocusable(true);

                } else if (mobileno1.length() != 10) {

                    Toast.makeText(Login.this, "Please enter 10 Digits Mobile Number", Toast.LENGTH_SHORT).show();
                    mobileno.setFocusable(true);

                } /*else if (email1.equalsIgnoreCase("") || email1.equalsIgnoreCase(null)) {
                    Toast.makeText(Login.this, "Please enter your Email Address", Toast.LENGTH_SHORT).show();

                    email.setFocusable(true);

                }*/ else if (!email1.isEmpty() && !isValidEmail(email1)) {
                    Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    email.setFocusable(true);
                } else {


                    User_Login();
                }


            }
        });


        deviceId = FirebaseInstanceId.getInstance().getToken();




    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void User_Login() {


        // Toast.makeText(getActivity(), " HI 1S", Toast.LENGTH_SHORT).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(RetrfitInterface.url).build();

        try {
            loadingView = new Custom_ProgressDialog(Login.this, "");
            loadingView.setCancelable(false);
            loadingView.show();
//            loading = new ProgressDialog(Login.this);
//            loading.setMessage("Please Wait Loading data ....");
//            loading.show();
//            loading.setCancelable(false);

        } catch (Exception e) {

        }

        //Creating Rest Services
        final RetrfitInterface restInterface = adapter.create(RetrfitInterface.class);
        Log.d("status&&&", "stat1" + restInterface);

        Log.d("status&&&", "stat1---------------------------------------------------");


        restInterface.user_login(mobileno1, email1, name1, "go", deviceId, "android" ,new Callback<Response>()



           /* restInterface.SearchResult("search","'Goa'",new Callback<SearchPropertyModel>()*/ {


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Response product, Response response) {
                //  Log.e("status&&&", "stat" + response.getReason() + "response=" + response.getUrl() + "status" + response.getStatus());

                try {

                    Log.e("parameters", "" + mobileno1 + email1 + name + "go" + deviceId);

                    Log.e("status", new String(((TypedByteArray) product.getBody()).getBytes()));

                    String result = new String(((TypedByteArray) product.getBody()).getBytes());

                    jObj = null;

                        jObj = new JSONObject(result);


                        status = jObj.getString("status");

                        Log.e("Response", "" + status);





                    loadingView.dismiss();

                    if (status.equals("1")) {


                        User user = new GsonBuilder().create().fromJson(result, User.class);
                        PrefUtils.setUser(user, Login.this);

                        JSONArray userArray = jObj.getJSONArray("user");

                        for(int i=0 ; i < userArray.length() ; i++) {



                            JSONObject object = userArray.getJSONObject(i);

                            name1 = object.getString("name");
                            email1 = object.getString("email_id");


                            userId = object.getString("userid");

                            mobileno1 = object.getString("phone");


                        }
                        Intent intent = new Intent(Login.this, Otp.class);
                        intent.putExtra("emailId", email1);
                        intent.putExtra("name" , name1);
                        intent.putExtra("userId" , userId);
                        intent.putExtra("mobileNo" , mobileno1);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    } else {
                        Toast.makeText(Login.this, " No Cab Available ", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){

                    e.printStackTrace();

                }

            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.toString();
                Log.d("error", merror);
                loadingView.dismiss();
            }
        });

    }


    public static boolean isMarshmallowPlusDevice() {

        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionRequestRequired(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (isMarshmallowPlusDevice() && permissions.length > 0) {
            List<String> newPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    newPermissionList.add(permission);
                }
            }
            if (newPermissionList.size() > 0) {
                activity.requestPermissions(newPermissionList.toArray(new String[newPermissionList.size()]), requestCode);
                return true;
            }
        }

        return false;
    }

}
