package com.onesidedcabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.helper.RetrfitInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFormActivity extends AppCompatActivity {

    Toolbar toolbar;
    Custom_ProgressDialog loadingView;

    RadioGroup rg_que1, rg_que2, rg_que3, rg_que4, rg_que5;

    RadioButton rb_good1, rb_better1, rb_excellent1, rb_good2, rb_better2, rb_excellent2, rb_good3, rb_better3, rb_excellent3, rb_good4, rb_better4, rb_excellent4, rb_good5, rb_better5, rb_excellent5;


    String json = "";

    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String answer4 = "";
    String answer5 = "";

    Button btn_submit;

    RadioButton rbAnswer1;
    RadioButton rbAnswer2;
    RadioButton rbAnswer3;
    RadioButton rbAnswer4;
    RadioButton rbAnswer5;

    private ProgressDialog loading;

    String orderID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);


        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        rg_que1 = (RadioGroup) findViewById(R.id.rg_que1);
        rg_que2 = (RadioGroup) findViewById(R.id.rg_que2);
        rg_que3 = (RadioGroup) findViewById(R.id.rg_que3);
        rg_que4 = (RadioGroup) findViewById(R.id.rg_que4);
        rg_que5 = (RadioGroup) findViewById(R.id.rg_que5);


        rb_good1 = (RadioButton) findViewById(R.id.rb_good1);
        rb_good2 = (RadioButton) findViewById(R.id.rb_good2);
        rb_good3 = (RadioButton) findViewById(R.id.rb_good3);
        rb_good4 = (RadioButton) findViewById(R.id.rb_good4);
        rb_good5 = (RadioButton) findViewById(R.id.rb_good5);

        rb_better1 = (RadioButton) findViewById(R.id.rb_better1);
        rb_better2 = (RadioButton) findViewById(R.id.rb_better2);
        rb_better3 = (RadioButton) findViewById(R.id.rb_better3);
        rb_better4 = (RadioButton) findViewById(R.id.rb_better4);
        rb_better5 = (RadioButton) findViewById(R.id.rb_better5);

        rb_excellent1 = (RadioButton) findViewById(R.id.rb_excellent1);
        rb_excellent2 = (RadioButton) findViewById(R.id.rb_excellent2);
        rb_excellent3 = (RadioButton) findViewById(R.id.rb_excellent3);
        rb_excellent4 = (RadioButton) findViewById(R.id.rb_excellent4);
        rb_excellent5 = (RadioButton) findViewById(R.id.rb_excellent5);


        rb_better1.setChecked(true);
        rb_better2.setChecked(true);
        rb_better3.setChecked(true);
        rb_better4.setChecked(true);
        rb_better5.setChecked(true);

        btn_submit = (Button) findViewById(R.id.btn_done);



        rg_que1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                int pos = rg_que1.indexOfChild(findViewById(checkedId));
                int pos1 = rg_que1.indexOfChild(findViewById(rg_que1.getCheckedRadioButtonId()));

                switch (pos) {

                    case R.id.rb_good1:
                        break;
                    case R.id.rb_better1:
                        break;
                    case R.id.rb_excellent1:
                        break;
                    default:
                        break;

                }


            }
        });

        rg_que2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                int pos = rg_que2.indexOfChild(findViewById(checkedId));
                int pos1 = rg_que2.indexOfChild(findViewById(rg_que2.getCheckedRadioButtonId()));

                switch (pos) {

                    case R.id.rb_good2:
                        break;
                    case R.id.rb_better2:
                        break;
                    case R.id.rb_excellent2:
                        break;
                    default:
                        break;

                }


            }
        });

        rg_que3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                int pos = rg_que3.indexOfChild(findViewById(checkedId));
                int pos1 = rg_que3.indexOfChild(findViewById(rg_que3.getCheckedRadioButtonId()));

                switch (pos) {

                    case R.id.rb_good3:
                        break;
                    case R.id.rb_better3:
                        break;
                    case R.id.rb_excellent3:
                        break;
                    default:
                        break;

                }


            }
        });

        rg_que4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                int pos = rg_que1.indexOfChild(findViewById(checkedId));
                int pos1 = rg_que1.indexOfChild(findViewById(rg_que4.getCheckedRadioButtonId()));

                switch (pos) {

                    case R.id.rb_good4:
                        break;
                    case R.id.rb_better4:
                        break;
                    case R.id.rb_excellent4:
                        break;
                    default:
                        break;

                }


            }
        });

        rg_que5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                int pos = rg_que1.indexOfChild(findViewById(checkedId));
                int pos1 = rg_que1.indexOfChild(findViewById(rg_que5.getCheckedRadioButtonId()));

                switch (pos) {

                    case R.id.rb_good5:
                        break;
                    case R.id.rb_better5:
                        break;
                    case R.id.rb_excellent5:
                        break;
                    default:
                        break;

                }


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!Internet.isConnectingToInternet(getApplicationContext())) {

                    Internet.noInternet(getApplicationContext());


                } else {


                    RadioButton radioButton1 = (RadioButton) rg_que1.findViewById(rg_que1.getCheckedRadioButtonId());
                    answer1 =radioButton1.getText().toString();

                    RadioButton radioButton2 = (RadioButton) rg_que2.findViewById(rg_que2.getCheckedRadioButtonId());
                    answer2 =radioButton2.getText().toString();

                    RadioButton radioButton3 = (RadioButton) rg_que3.findViewById(rg_que3.getCheckedRadioButtonId());
                    answer3 =radioButton3.getText().toString();

                    RadioButton radioButton4 = (RadioButton) rg_que4.findViewById(rg_que4.getCheckedRadioButtonId());
                    answer4 =radioButton4.getText().toString();

                    RadioButton radioButton5 = (RadioButton) rg_que5.findViewById(rg_que5.getCheckedRadioButtonId());
                    answer5 =radioButton5.getText().toString();



                    new SendFeedBackDetails().execute();



                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class SendFeedBackDetails extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(FeedbackFormActivity.this, "");
                loadingView.setCancelable(false);
                loadingView.show();
//                loading = new ProgressDialog(FeedbackFormActivity.this);
//                loading.setMessage("Please Wait Loading data ....");
//                loading.show();
//                loading.setCancelable(false);


            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            String userID = PrefUtils.getUser(FeedbackFormActivity.this).getUser().get(0).getUserid();

            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("order_id", orderID));
            pairs.add(new BasicNameValuePair("feedback1", answer1));
            pairs.add(new BasicNameValuePair("feedback2", answer2));
            pairs.add(new BasicNameValuePair("feedback3", answer3));
            pairs.add(new BasicNameValuePair("feedback4", answer4));
            pairs.add(new BasicNameValuePair("feedback5", answer5));
            pairs.add(new BasicNameValuePair("userid", userID));
            pairs.add(new BasicNameValuePair("key", "go"));


            json = new ServiceHandler().makeServiceCall(RetrfitInterface.url + "feedback.php", ServiceHandler.POST, pairs);


            Log.e("parameters", "" + pairs);

            Log.e("json", json);


            return json;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loadingView.dismiss();


            try {

                JSONObject mainObject = new JSONObject(s);

                String status = "", message = "";

                status = mainObject.getString("status");
                message = mainObject.getString("message");




                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(FeedbackFormActivity.this, "Thank you for your valuable Feedback !" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }else {

                    Toast.makeText(FeedbackFormActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    }
}
