package com.onesidedcabs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesidedcabs.helper.PrefUtils;

/**
 * Created by manthanpatel on 11/04/2017.
 */
public class User_Profile extends AppCompatActivity {


    EditText email,mobileno,name;
    TextInputLayout i_email,i_mobileno,i_name;
    Typeface custom_font,custom_fontb,custom_fontl;
    ImageView back;
TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profil);


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        custom_fontb = Typeface.createFromAsset(getAssets(), "fonts/open-sans.semibold.ttf");


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/open-sans.regular.ttf");
        email = (EditText) findViewById(R.id.email);
        email.setTypeface(type);
        email.setInputType(InputType.TYPE_NULL);

        email.setText(PrefUtils.getUser(User_Profile.this).getUser().get(0).getEmail_id());

        mobileno = (EditText) findViewById(R.id.mobileno);
        mobileno.setTypeface(type);
        mobileno.setInputType(InputType.TYPE_NULL);

        mobileno.setText(PrefUtils.getUser(User_Profile.this).getUser().get(0).getPhone());

        name = (EditText)  findViewById(R.id.name);
        name.setTypeface(type);
        name.setInputType(InputType.TYPE_NULL);
        name.setText(PrefUtils.getUser(User_Profile.this).getUser().get(0).getName());

        title =(TextView) findViewById(R.id.title);
        title.setTypeface(custom_fontb);

        i_email = (TextInputLayout) findViewById(R.id.i_email);
        i_email.setTypeface(type);

        i_mobileno = (TextInputLayout) findViewById(R.id.i_mobileno);
        i_mobileno.setTypeface(type);

        i_name = (TextInputLayout)  findViewById(R.id.i_name);
        i_name.setTypeface(type);

    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(User_Profile.this, MenuActivity.class);
        startActivity(intent);


    }
}
