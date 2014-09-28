package pack.my;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class feedback extends Activity{
	EditText feedback;
	final JSONObject jsonobj = new JSONObject();
	String case_id,addr,uemail;
	ImageView b1,b2,b3,b4,b6;
	Button b5;
	Bundle bl;
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/feedback.php";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		 SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			TextView emailId=(TextView)findViewById(R.id.EmailId);
			emailId.setText(email);
		feedback=(EditText)findViewById(R.id.msgTextField);
		feedback.setTextColor(Color.WHITE);
		feedback.setSingleLine(false);
		
		 b1=(ImageView)findViewById(R.id.button1);
			b2=(ImageView)findViewById(R.id.button2);
			b3=(ImageView)findViewById(R.id.button3);
			b4=(ImageView)findViewById(R.id.button4);
			b5=(Button)findViewById(R.id.button5);
			b6=(ImageView)findViewById(R.id.button6);
			b6.setOnClickListener(new View.OnClickListener() 
			{

				public void onClick(View v) {
					SharedPreferences Preferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
					String type = Preferences.getString("MEM3", "");
					if(type.equals("user"))
					{
						Toast.makeText(feedback.this, "USER", Toast.LENGTH_LONG).show();
					Intent i=new Intent ("PACK.MY.APPLICATION");
					startActivity(i);
					}
					else
					{
						Toast.makeText(feedback.this, "POLICE", Toast.LENGTH_LONG).show();
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
						new AlertDialog.Builder(feedback.this)

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
			
		
		
		
		
		
		Bundle bd=getIntent().getExtras();
		/*String case_id=(String)bd.get("case_id");
		String addr=(String)bd.get("add");
		String uemail=(String)bd.get("uemail");*/
		case_id=history.caseid;
		addr=history.addr;
		uemail=history.email;
		Toast.makeText(feedback.this, "caseid:"+case_id, Toast.LENGTH_LONG).show();
		TextView tv=(TextView)findViewById(R.id.caseid);
		tv.setText(case_id);
		Button sendb=(Button)findViewById(R.id.sendButton);
		sendb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				feedback=(EditText)findViewById(R.id.msgTextField);
				if(feedback.getText().equals(""))
					Toast.makeText(feedback.this, "Enter some feedback", Toast.LENGTH_LONG).show();
				else
				{
					ProgressDialog dialog = ProgressDialog.show(feedback.this, "", "Uploading..", true);
					dialog.show();
					send();
				}
			}
		});
		
	}

	
	public void send()
	{

		try {
			Toast.makeText(feedback.this, "send called", Toast.LENGTH_LONG).show();
			
			

			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			//Toast.makeText(location.this, "address:"+addressString,Toast.LENGTH_LONG).show();
			jsonobj.put("email", email);
			jsonobj.put("msg", feedback.getText());
			jsonobj.put("case_id", case_id);

			//final String wurl = "http://training.artoonsolutions.com/crimetracking/email.php";
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(upLoadServerUri);
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
				Toast.makeText(feedback.this, "UPLOADED...", Toast.LENGTH_LONG).show();
				//Toast.makeText(feedback.this, "Res from php:"+resultstring, Toast.LENGTH_LONG).show();
				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/
				//Toast.makeText(location.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
				//receive response
				/*int j;
	        		j=Integer.parseInt(resultstring);
	        		Toast.makeText(location.this, "Value of response:"+j, Toast.LENGTH_LONG).show();
	        	if(j==0)
	        	{
	        		Toast.makeText(location.this, "Location Updated", Toast.LENGTH_LONG).show();
				Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);
				}*/
	        	//end
				finish();

			}
			else
			{
				Toast.makeText(feedback.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(feedback.this, "Error:"+e, Toast.LENGTH_SHORT).show();
		}
	}


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
