package com.onesidedcabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.onesidedcabs.helper.PrefUtils;


public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3*1000);

                    String p_id =  PrefUtils.getlogin(Splash.this);


                    if (p_id.equalsIgnoreCase("")||p_id.equalsIgnoreCase(null)){

                        PrefUtils.setlogin("", Splash.this);

                        Intent it = new Intent(Splash.this, Login.class);
                        startActivity(it);

                    }

                    else {

                        // User user = PrefUtils.getUser(LoginActivity1.this);

                        PrefUtils.setlogin("0", Splash.this);



                        Intent it = new Intent(Splash.this, MenuActivity.class);
                        startActivity(it);

                    }

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


//        next = (TextView)findViewById(R.id.next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(SplashtActivity.this, LoginActivity.class);
//                startActivity(it);
//            }
//        });




    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        finish();

    }
}
