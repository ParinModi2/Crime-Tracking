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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GprsCrimeTrackingActivity extends Activity {
	/** Called when the activity is first created. */
	Button submit, register,forgot;
	EditText id, pass;
	int flag=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		SharedPreferences Preferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		String email = Preferences.getString("MEM1", "");
		String passw = Preferences.getString("MEM2", "");
		id=(EditText)findViewById(R.id.id);
		pass=(EditText)findViewById(R.id.pass);
		id.setTextColor(Color.WHITE);
		pass.setTextColor(Color.WHITE);
		id.setText(email);
		pass.setText(passw);
		submit=(Button)findViewById(R.id.submit);
		register=(Button)findViewById(R.id.register);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				flag=0;

				if(id.getText().toString().length()==0)
				{
					id.setError( "Please Enter username" );
					flag=1;
				}
				if(pass.getText().toString().length()==0)
				{
					pass.setError( "Please Enter password" );
					flag=1;
				}
				if(flag==0)
				{
					//Save Values
					/*SavePreferences("MEM1", id.getText().toString());
					LoadPreferences();
					SavePreferences("MEM2", pass.getText().toString());*/
					LoadPreferences();
				}
			}

		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i=new Intent ("PACK.MY.REGISTRATION");
				startActivity(i);
			}
		});


	}
	private void SavePreferences(String key, String value){
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void LoadPreferences(){
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
		if(sharedPreferences.getString("MEM1", "").equals(""))
		{
			//Toast.makeText(GprsCrimeTrackingActivity.this, "Check from database", Toast.LENGTH_LONG).show();

			try {
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					Toast.makeText(GprsCrimeTrackingActivity.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
				} else
				{
					final JSONObject jsonobj; 
					jsonobj = new JSONObject();	
					//Toast.makeText(GprsCrimeTrackingActivity.this, "Id:"+id.getText().toString(), Toast.LENGTH_LONG).show();
					jsonobj.put("id", id.getText().toString());
					jsonobj.put("pass", pass.getText().toString()); 
					final String wurl = "http://training.artoonsolutions.com/crimetracking/login.php";
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
						//Toast.makeText(GprsCrimeTrackingActivity.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
						if(j==0)
						{
							//Toast.makeText(GprsCrimeTrackingActivity.this, "Welcome", Toast.LENGTH_LONG).show();
							SavePreferences("MEM1", id.getText().toString());
							SavePreferences("MEM2", pass.getText().toString());
							SavePreferences("MEM3", "user");
							String strSavedMem1 = sharedPreferences.getString("MEM1", "");
							String strSavedMem2 = sharedPreferences.getString("MEM2", "");
							String strSavedMem3 = sharedPreferences.getString("MEM3", "");
							//Toast.makeText(GprsCrimeTrackingActivity.this, "Values:"+strSavedMem1+" "+strSavedMem2+" "+strSavedMem3, Toast.LENGTH_LONG).show();
							Intent intent=new Intent("PACK.MY.APPLICATION");
							startActivity(intent);
							finish();
						}
						else if(j==2)
						{
							//Toast.makeText(GprsCrimeTrackingActivity.this, "Welcome POLICE", Toast.LENGTH_LONG).show();
							SavePreferences("MEM1", id.getText().toString());
							SavePreferences("MEM2", pass.getText().toString());
							SavePreferences("MEM3", "police");
							String strSavedMem1 = sharedPreferences.getString("MEM1", "");
							String strSavedMem2 = sharedPreferences.getString("MEM2", "");
							String strSavedMem3 = sharedPreferences.getString("MEM3", "");
							//Toast.makeText(GprsCrimeTrackingActivity.this, "Values:"+strSavedMem1+" "+strSavedMem2+" "+strSavedMem3, Toast.LENGTH_LONG).show();
							startService(new Intent(this,demoservice.class));
							Intent intent=new Intent("PACK.MY.POLICEAPP");
							startActivity(intent);
							finish();
						}
						else
						{
							Toast.makeText(GprsCrimeTrackingActivity.this, "This entry does not exist", Toast.LENGTH_LONG).show();
						}
					}
				}
			}
			catch (Exception e) {
				Toast.makeText(GprsCrimeTrackingActivity.this, "Error:"+e, Toast.LENGTH_SHORT).show();
				//Toast.makeText(registration.this, "This email address already exists. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			/*String strSavedMem1 = sharedPreferences.getString("MEM1", "");
    		String strSavedMem2 = sharedPreferences.getString("MEM2", "");
    		Toast.makeText(GprsCrimeTrackingActivity.this, "Values:"+strSavedMem1+" "+strSavedMem2, Toast.LENGTH_LONG).show();*/
			SharedPreferences Preferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = Preferences.getString("MEM1", "");
			String passw = Preferences.getString("MEM2", "");
			String type = Preferences.getString("MEM3", "");
			if(email.equals(id.getText().toString()) && passw.equals(pass.getText().toString()) && type.equals("user"))
			{
				//Toast.makeText(GprsCrimeTrackingActivity.this, "Welcome USER", Toast.LENGTH_LONG).show();
				Intent intent=new Intent("PACK.MY.APPLICATION");
				startActivity(intent);
				finish();
			}
			else if(email.equals(id.getText().toString()) && passw.equals(pass.getText().toString()) && type.equals("police"))
			{
				//Toast.makeText(GprsCrimeTrackingActivity.this, "Welcome POLICE..", Toast.LENGTH_LONG).show();
				Intent intent=new Intent("PACK.MY.POLICEAPP");
				startActivity(intent);
				finish();
			}
			else
				Toast.makeText(GprsCrimeTrackingActivity.this, "Wrong E-mail ID or Password", Toast.LENGTH_LONG).show();
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