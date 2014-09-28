package pack.my;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

// import everything you need
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class text extends Activity {

	Button sendButton;

	EditText msgTextField;
	ImageView b1,b2,b3,b4,b6;
	TextView emailId;
	Button b5;
	Bundle bl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// load the layout
		setContentView(R.layout.m4);        
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		String email = sharedPreferences.getString("MEM1", "");
		b1=(ImageView)findViewById(R.id.button1);
		b2=(ImageView)findViewById(R.id.button2);
		b3=(ImageView)findViewById(R.id.button3);
		b4=(ImageView)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		emailId=(TextView)findViewById(R.id.EmailId);
		emailId.setText(email);
		//b6=(Button)findViewById(R.id.button6);
		b6=(ImageView)findViewById(R.id.button6);
		b6.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				SharedPreferences Preferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				String type = Preferences.getString("MEM3", "");
				if(type.equals("user"))
				{
					//Toast.makeText(cam.this, "USER", Toast.LENGTH_LONG).show();
				Intent i=new Intent ("PACK.MY.APPLICATION");
				startActivity(i);
				}
				else
				{
					//Toast.makeText(cam.this, "POLICE", Toast.LENGTH_LONG).show();
					Intent i=new Intent ("PACK.MY.POLICEAPP");
					startActivity(i);
				}
				finish();
			}
		});
		b1.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i=new Intent ("PACK.MY.CAM");
				startActivity(i);
				finish();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i1=new Intent ("PACK.MY.VIDEO");
				startActivity(i1);
				finish();
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i2=new Intent ("PACK.MY.TEXT");
				startActivity(i2);
				finish();
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					//Toast.makeText(application.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
					new AlertDialog.Builder(text.this)

					.setTitle("Alert!")

					.setMessage("Check your internet connection. Try again.")

					.setNeutralButton("Ok",

					new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,

					int which) {
					}

					}).show();

				} else
				{
					Intent i3=new Intent ("PACK.MY.LOCATIONUPDATE");

					bl.putString("type", "map");
					i3.putExtras(bl);
					startActivity(i3);
					finish();
				}
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i4=new Intent ("PACK.MY.EDITPRO");
				startActivity(i4);
				finish();
			}
		});
		
		
		
		// make message text field object
		msgTextField = (EditText) findViewById(R.id.msgTextField);
		msgTextField.setTextColor(Color.WHITE);
		// make send button object
		sendButton = (Button) findViewById(R.id.sendButton);
		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					Toast.makeText(text.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
				} else
				{
					//Toast.makeText(sms.this, "msg:"+msgTextField.getText().toString(),Toast.LENGTH_SHORT).show();
					Bundle bl=new Bundle();
					bl.putString("type", "msg");
					bl.putString("text", msgTextField.getText().toString());
					Intent i=new Intent("PACK.MY.LOCATION");
					i.putExtras(bl);
					startActivity(i);
					finish();

					//database
					/*try
				{
				final JSONObject jsonobj; 
    	        jsonobj = new JSONObject();	
	        	jsonobj.put("text", msgTextField.getText().toString());
	        	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
	        	String email = sharedPreferences.getString("MEM1", "");
	        	jsonobj.put("email", email);
	        	final String wurl = "http://training.artoonsolutions.com/crimetracking/text.php";
	        	DefaultHttpClient httpclient = new DefaultHttpClient();
	        	HttpPost httppostreq = new HttpPost(wurl);
	        	StringEntity se = new StringEntity(jsonobj.toString());
	        	se.setContentType("application/json;charset=UTF-8");
	        	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
	        	httppostreq.setEntity(se);

	        	HttpResponse httpresponse = httpclient.execute(httppostreq);
	        	HttpEntity resultentity = httpresponse.getEntity();
	        	if(resultentity != null) {
	        		InputStream inputstream = resultentity.getContent();
	        		Header contentencoding = httpresponse.getFirstHeader("Content-Encoding");
	        		if(contentencoding != null && contentencoding.getValue().equalsIgnoreCase("gzip")) {
	        			inputstream = new GZIPInputStream(inputstream);
	        		}
	        		String resultstring = convertStreamToString(inputstream);
	        		inputstream.close();
	        		resultstring = resultstring.substring(0,resultstring.length());
	        		resultstring=resultstring.trim();
	        		int j;
	        		j=Integer.parseInt(resultstring);
	        		Toast.makeText(text.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
	        		if(j==0)
	        		{
	        			Toast.makeText(text.this, "Text sent", Toast.LENGTH_LONG).show();
	        			Intent i=new Intent("PACK.MY.APPLICATION");
	        			startActivity(i);
	        		}
	        	}

			}

			catch(Exception ex)
			{
				Toast.makeText(text.this, "Error:"+ex, Toast.LENGTH_LONG).show();
			}*/
				}
			}
		});

	}

	// this is the function that gets called when you click the button

	private String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (Exception e) {    e.printStackTrace();
		}
		return total.toString();
	}
}
