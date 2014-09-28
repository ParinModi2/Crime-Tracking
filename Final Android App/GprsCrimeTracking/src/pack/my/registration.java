package pack.my;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;


public class registration extends Activity 
{
	int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0;
	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath;
	private ImageView img;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,4})?$"

	);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);   
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		//img = (ImageView)findViewById(R.id.imageView1);
		final EditText uname=(EditText) findViewById(R.id.uname);
		final EditText email=(EditText) findViewById(R.id.email);
		final EditText pass=(EditText) findViewById(R.id.pass);
		final EditText cpass=(EditText) findViewById(R.id.cpass);
		final EditText mobile=(EditText) findViewById(R.id.mno);
		Button submit = (Button) findViewById(R.id.submit);
		final JSONObject jsonobj; 
		jsonobj = new JSONObject();	


		submit.setOnClickListener(new OnClickListener() {				
			public void onClick(View v)
			{ 
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					//Toast.makeText(registration.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
					new AlertDialog.Builder(registration.this)

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
					ProgressDialog dialog = ProgressDialog.show(registration.this, "", "Uploading..", true);
					int i= validmob(mobile.getText().toString()); 
					if(mobile.getText().toString().length()!=0)
					{
						if(mobile.getText().toString().length()==10)
						{

							if(i==0)
							{        	
								mobile.setError( "Please Enter Digits" );
								flag1=1;
							}
							else
							{
								flag1=0;
							}

						}
						else
						{
							mobile.setError( "Please Enter Valid number" );
							flag1=1;

						}
					}
					else
					{
						mobile.setError( "Please Enter Valid number" );
						flag1=1;
					}
					if(uname.getText().toString().length()==0)
					{        	
						uname.setError( "Please Enter User Name" );
						flag2=1;
					}
					else
						flag2=0;
					
					if(pass.getText().toString().length()==0)
					{        	
						pass.setError( "Please Enter Password" );
						flag3=1;
					}
					else
						flag3=0;
					
					if(email.getText().toString().length()==0 || !checkEmail(email.getText().toString()))
					{        	
						email.setError( "Please Enter valid Email address" );
						flag4=1;
					}	
					else
						flag4=0;
					
					if(cpass.getText().toString().length()==0 || !(cpass.getText().toString().equals(pass.getText().toString())))
					{
						cpass.setError( "Does not match with Password" );
						flag5=1;
					}
					else
						flag5=0;
					if(flag1==0 && flag2==0 && flag3==0 && flag4==0 && flag5==0)
					{
						try {


							jsonobj.put("uname", uname.getText().toString());
							jsonobj.put("pass", pass.getText().toString()); 
							jsonobj.put("email", email.getText().toString()); 
							jsonobj.put("cpass", cpass.getText().toString());
							jsonobj.put("mno", mobile.getText().toString());
							final String wurl = "http://training.artoonsolutions.com/crimetracking/reg.php";
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
								//Toast.makeText(registration.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
								if(j==0)
								{

									new AlertDialog.Builder(registration.this)

									.setTitle("Success!")

									.setMessage("Registered successfully. An email has been sent to your email address. Please verify your account. ")

									.setNeutralButton("Ok",

									new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,

									int which) {
										finish();
									}

									}).show();
									//Toast.makeText(registration.this, "Your entry has been entered successfully..", Toast.LENGTH_LONG).show();
									
									/*Intent intent=new Intent("PACK.MY.GPRSCRIMETRACKINGACTIVITY");
			        		startActivity(intent);*/
								}
								else
								{
									Toast.makeText(registration.this, "This email address already exists. Try again.", Toast.LENGTH_LONG).show();
								}
							}

						}
						catch (Exception e) {
							Toast.makeText(registration.this, "Error:"+e, Toast.LENGTH_SHORT).show();
							//Toast.makeText(registration.this, "This email address already exists. Try again.", Toast.LENGTH_LONG).show();
						}
					}	
				}
			}
		});
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
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	public int validmob(String number)
	{
		try
		{
			TextUtils.isDigitsOnly(number);
			//int chnum=Integer.parseInt(number);
			return 1;
		}
		catch(NumberFormatException ex)
		{
			return 0;
		}

	}

	/* public int validemail(String email)
    {
		int len=email.length();
    	int at1=email.indexOf("@");
		int at2=email.lastIndexOf("@");
		if(at1!=at2 || at1==-1)
			return 0;
		int d1=at1-1;
		int d2=at1+1;
		char dot=email.charAt(d1);
		if(dot.)
			return 0;
    	return flag;

    }*/
	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

}
