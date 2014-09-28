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
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;


public class editpro extends Activity 
{
	int flag=0;
	private static final int SELECT_PICTURE = 1;
	ImageView b1,b2,b3,b4,b6;
	Button b5;
	Bundle bl;
	final JSONObject jsonobj = new JSONObject();
	private String selectedImagePath;
	private ImageView img;
	String upLoadServerUri ;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,4})?$"

	);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);   
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		String email = sharedPreferences.getString("MEM1", "");
		TextView emailId=(TextView)findViewById(R.id.EmailId);
		emailId.setText(email);
		send();
		
		
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
					Toast.makeText(editpro.this, "USER", Toast.LENGTH_LONG).show();
				Intent i=new Intent ("PACK.MY.APPLICATION");
				startActivity(i);
				}
				else
				{
					Toast.makeText(editpro.this, "POLICE", Toast.LENGTH_LONG).show();
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
					new AlertDialog.Builder(editpro.this)

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
		
		

		
		
		
		
		img = (ImageView)findViewById(R.id.imageView1);
		final EditText fname=(EditText) findViewById(R.id.fname);
		final EditText lname=(EditText) findViewById(R.id.lname);
		final EditText city=(EditText) findViewById(R.id.city);
		
		final EditText add=(EditText) findViewById(R.id.add);
		final RadioButton male=(RadioButton) findViewById(R.id.gen1);
		final RadioButton female=(RadioButton) findViewById(R.id.gen2);
		final EditText pro=(EditText) findViewById(R.id.qua);
		final EditText dob=(EditText)findViewById(R.id.dob);
		Button submit = (Button) findViewById(R.id.submit);
		Button cancel = (Button) findViewById(R.id.cancel);
		final JSONObject jsonobj; 
		jsonobj = new JSONObject();	

		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		submit.setOnClickListener(new OnClickListener() {				
			public void onClick(View v)
			{
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					Toast.makeText(editpro.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
				} else
				{
					
					
					if(flag==0)
					{
						try {

							String gender="";
							jsonobj.put("fname", fname.getText().toString());
							jsonobj.put("lname", lname.getText().toString()); 
							jsonobj.put("city", city.getText().toString()); 
							 
							jsonobj.put("add", add.getText().toString()); 
							if(male.isChecked())
								gender="Male";
							else if(female.isChecked())
								gender="Female";
							jsonobj.put("gender", gender); 
							jsonobj.put("dob", dob.getText().toString());
							jsonobj.put("profession", pro.getText().toString());
							SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
							String email = sharedPreferences.getString("MEM1", "");
							String pass = sharedPreferences.getString("MEM2", "");
							Toast.makeText(editpro.this, "email:"+email,Toast.LENGTH_LONG).show();
							jsonobj.put("email", email);
							final String wurl = "http://training.artoonsolutions.com/crimetracking/email.php";
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
								ProgressDialog dialog = ProgressDialog.show(editpro.this, "", "Uploading..", true);
								//Toast.makeText(editpro.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
								if(j==0)
								{
									Toast.makeText(editpro.this, "Profile Updated", Toast.LENGTH_LONG).show();
									Intent intent=new Intent("PACK.MY.APPLICATION");
									startActivity(intent);
									finish();
								}

							}
							else
							{
								Toast.makeText(editpro.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
							}
						}
						catch (Exception e) {
							Toast.makeText(editpro.this, "Error:"+e, Toast.LENGTH_SHORT).show();
						}
					}	
				}	
			}
		});
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				img.setImageURI(selectedImageUri);
			}
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

	
	
	public void send()
	{

		try {
			Toast.makeText(editpro.this, "send called", Toast.LENGTH_LONG).show();

			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			Toast.makeText(editpro.this, "email:"+email,Toast.LENGTH_LONG).show();
			jsonobj.put("email", email);


			//final String wurl = "http://training.artoonsolutions.com/crimetracking/email.php";
			DefaultHttpClient httpclient = new DefaultHttpClient();
			SharedPreferences sharedPreference = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String user = sharedPreference.getString("MEM3", "");
			if(user.equals("police"))
			{
				upLoadServerUri= "http://training.artoonsolutions.com/crimetracking/editprofetch.php";
			}
			else
			{
				upLoadServerUri= "http://training.artoonsolutions.com/crimetracking/editprofetch1.php";
			}
			
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
				resultstring=new String(resultstring.substring(1, resultstring.length()-1));
				//Toast.makeText(editpro.this, "UPLOADED..."+resultstring, Toast.LENGTH_LONG).show();
				String []sep=resultstring.split(":");
				if(user.equals("police"))
				{
					//Toast.makeText(editpro.this, "array:"+sep[0]+","+sep[1]+","+sep[2]+","+sep[3]+","+sep[4], Toast.LENGTH_LONG).show();
					EditText fname=(EditText)findViewById(R.id.fname);
					fname.setText(sep[0]);
					EditText lname=(EditText)findViewById(R.id.lname);
					lname.setText(sep[1]);
					EditText addr=(EditText)findViewById(R.id.add);
					addr.setText(sep[2]);
					EditText prof=(EditText)findViewById(R.id.qua);
					prof.setText(sep[4]);
					if(sep[3].equals("female"))
					{
						 RadioButton rb=(RadioButton)findViewById(R.id.gen2);
						 rb.setChecked(true);
					}
					else
					{
						 RadioButton rb=(RadioButton)findViewById(R.id.gen1);
						 rb.setChecked(true);
					}
						
					
					
				}
				else
				{
					
					EditText fname=(EditText)findViewById(R.id.fname);
					fname.setText(sep[0]);
					EditText lname=(EditText)findViewById(R.id.lname);
					lname.setText(sep[1]);
					EditText addr=(EditText)findViewById(R.id.add);
					addr.setText(sep[2]);
					EditText city=(EditText)findViewById(R.id.city);
					city.setText(sep[3]);
					EditText prof=(EditText)findViewById(R.id.qua);
					prof.setText(sep[4]);
					if(sep[5].equals("female"))
					{
						 RadioButton rb=(RadioButton)findViewById(R.id.gen2);
						 rb.setChecked(true);
					}
					else
					{
						 RadioButton rb=(RadioButton)findViewById(R.id.gen1);
						 rb.setChecked(true);
					}
					EditText dob=(EditText)findViewById(R.id.dob);
					dob.setText(sep[6]);
				}
				//Toast.makeText(location.this, "Res from php:"+resultstring, Toast.LENGTH_LONG).show();
				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/
				//Toast.makeText(location.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
				/*int j;
	        		j=Integer.parseInt(resultstring);

	        	if(j==0)
	        	{
	        		Toast.makeText(location.this, "Location Updated", Toast.LENGTH_LONG).show();*/
				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/
				//}

			}
			else
			{
				Toast.makeText(editpro.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(editpro.this, "Error:"+e, Toast.LENGTH_SHORT).show();
		}
	}

}
