package com.onesidedcabs.ccavenuenonseamless;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onesidedcabs.MenuActivity;
import com.onesidedcabs.R;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.User;

import org.json.JSONObject;


public class StatusActivity extends Activity {

	TextView thanks,shoping;
	ProgressDialog loading;
	String status;
	String pric,user_ID;
	JSONObject jObj;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);




		User user = PrefUtils.getUser(StatusActivity.this);
		user_ID = user.getUser().get(0).getUserid();



		thanks = (TextView) findViewById(R.id.thanks);
        shoping  = (TextView) findViewById(R.id.shoping);
		 shoping.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				onBackPressed();
			}
		});

		Intent mainIntent = getIntent();

		if(mainIntent.getStringExtra("transStatus").equalsIgnoreCase("Transaction Successful!")){

			thanks.setVisibility(View.VISIBLE);






		}
		else{thanks.setVisibility(View.INVISIBLE);}



		TextView tv4 = (TextView) findViewById(R.id.status);
		tv4.setText(mainIntent.getStringExtra("transStatus"));




//		CustomToast(mainIntent.getStringExtra("transStatus"));
//



	}

	public void onBackPressed() {

		Intent intent = new Intent(StatusActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}






} 