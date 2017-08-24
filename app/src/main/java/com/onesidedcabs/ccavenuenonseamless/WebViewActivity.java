package com.onesidedcabs.ccavenuenonseamless;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.onesidedcabs.Confirm;
import com.onesidedcabs.R;
import com.onesidedcabs.helper.PrefUtils;
import com.onesidedcabs.utility.AvenuesParams;
import com.onesidedcabs.utility.Constants;
import com.onesidedcabs.utility.RSAUtility;
import com.onesidedcabs.utility.ServiceHandler;
import com.onesidedcabs.utility.ServiceUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



public class WebViewActivity extends Activity {
	private ProgressDialog dialog;
	String html, encVal="";

	private String access_code = "AVVO70ED04AF27OVFA";
	private String merchant_id = "129409";
	private String currency = "INR";
	private String redirect_url = "http://onesidedcab.com/merchant/ccavResponseHandler.php";
	private String cancel_url = "http://onesidedcab.com/merchant/ccavResponseHandler.php";
	private String rsa_url = "http://onesidedcab.com/merchant/GetRSA.php";
	private String billing_country = "India";
	private String amount = null;
	private String order_id = null;


	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_webview);
		amount = getIntent().getStringExtra("amount");
		
		// Calling async task to get display content
		new RenderView().execute();
	}
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class RenderView extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			dialog = new ProgressDialog(WebViewActivity.this);
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			Integer randomNum = ServiceUtility.randInt(0, 9999999);
			order_id = PrefUtils.geteOrder_ID(WebViewActivity.this);

			Log.e("orderId" , order_id);
	
			// Making a request to url and getting response
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE, access_code));
			params.add(new BasicNameValuePair(AvenuesParams.ORDER_ID, order_id));
			params.add(new BasicNameValuePair(AvenuesParams.BILLING_COUNTRY, billing_country));
	
			String vResponse = sh.makeServiceCall(rsa_url, ServiceHandler.POST, params);
			System.out.println(vResponse);
			if(!ServiceUtility.chkNull(vResponse).equals("") 
					&& ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR")==-1){
				StringBuffer vEncVal = new StringBuffer("");
				vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
				vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, currency));
				vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_COUNTRY, billing_country));
				encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
			}

			Log.e("parameters" , ""+params);


			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			class MyJavaScriptInterface
			{
				@JavascriptInterface
			    public void processHTML(String html)
			    {
			        // process the html as needed by the app
			    	String status = null;
			    	if(html.indexOf("Failure")!=-1){
			    		status = "Transaction Declined!";
			    	}else if(html.indexOf("Success")!=-1){
			    		status = "Transaction Successful!";
			    	}else if(html.indexOf("Aborted")!=-1){
			    		status = "Transaction Cancelled!";
			    	}else{
			    		status = "Status Not Known!";
			    	}

					if(status.equalsIgnoreCase("Transaction Successful!")){

						Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),Confirm.class);
						startActivity(intent);


					}

					else{


						Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
						intent.putExtra("transStatus", status);
						startActivity(intent);


					}



			    }
			}
			
			final WebView webview = (WebView) findViewById(R.id.webview);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
			webview.setWebViewClient(new WebViewClient(){
				@Override  
	    	    public void onPageFinished(WebView view, String url) {
	    	        super.onPageFinished(webview, url);
					// Dismiss the progress dialog
					if (dialog.isShowing())
						dialog.dismiss();
	    	        if(url.indexOf("/ccavResponseHandler.php")!=-1){
	    	        	webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
	    	        }
	    	    }  

	    	    @Override
	    	    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	    	        Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
	    	    }
			});
			
			/* An instance of this class will be registered as a JavaScript interface */
			StringBuffer params = new StringBuffer();
			params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE,access_code));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID,merchant_id));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID,order_id));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL,redirect_url));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL,cancel_url));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL,URLEncoder.encode(encVal)));
			
			String vPostParams = params.substring(0,params.length()-1);
			try {
				webview.postUrl(Constants.TRANS_URL, EncodingUtils.getBytes(vPostParams, "UTF-8"));
			} catch (Exception e) {
				showToast("Exception occured while opening webview.");
			}
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
} 