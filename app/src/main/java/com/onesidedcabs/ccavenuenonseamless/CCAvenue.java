package com.onesidedcabs.ccavenuenonseamless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onesidedcabs.R;


public class CCAvenue extends AppCompatActivity {

    Button b;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccavenu);

        b = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.amount);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CCAvenue.this,WebViewActivity.class);
                intent.putExtra("amount",editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
