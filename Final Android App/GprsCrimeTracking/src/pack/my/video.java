package pack.my;
//video
//import android.R.string;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
//import android.database.Cursor;
import android.graphics.Bitmap;
//import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
//       import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Toast;
public class video extends Activity {
	protected static final int ACTION_TAKE_VIDEO = 0;
	private static final int SELECT_VIDEO = 1;
	/** Called when the activity is first created. */
	VideoView iv;
	Button b,playback,send,gallery;
	Bitmap bm;
	Uri videopath;
	String vp1="";
	Intent intent;
	Uri mUri=null;
	String finalpath=null;
	int fl=0;
	int flag=0;
	ProgressDialog dialog = null;
	String[] a;
	int serverResponseCode = 0;
	ImageView b1,b2,b3,b4,b6;
	Button b5;
	Bundle bl;
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/upload_media_test1.php";
	/*public void func(Intent i)
	{
		videopath = i.getData();
        iv.setVideoPath(videopath.getPath());
        iv.start();
        iv.requestFocus();
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m1);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		 SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			TextView emailId=(TextView)findViewById(R.id.EmailId);
			emailId.setText(email);
		b1=(ImageView)findViewById(R.id.button1);
		b2=(ImageView)findViewById(R.id.button2);
		b3=(ImageView)findViewById(R.id.button3);
		b4=(ImageView)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
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
					new AlertDialog.Builder(video.this)

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
		
		
		iv=(VideoView)findViewById(R.id.videoView1);
		b=(Button)findViewById(R.id.button11);
		playback=(Button)findViewById(R.id.button22);
		send =(Button)findViewById(R.id.send);
		gallery=(Button)findViewById(R.id.gal);

		gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("video/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,"Select Video"), SELECT_VIDEO);
			}
		});

		playback.setOnClickListener(new Button.OnClickListener(){


			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(videopath == null){
					Toast.makeText(video.this,"No Video",
							Toast.LENGTH_LONG)
							.show();
				}else{
					Toast.makeText(video.this,"Playback: " +videopath,
							Toast.LENGTH_LONG)
							.show();

					iv.setVideoURI(videopath);
					iv.setMediaController(new MediaController(video.this));
					iv.start();
				}
			}});
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					//Toast.makeText(video.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
					new AlertDialog.Builder(video.this)

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
					if(flag==0)
					{
						Toast.makeText(video.this, "No Video to upload.", Toast.LENGTH_LONG).show();
					}
					else
					{
						dialog = ProgressDialog.show(video.this, "", "Uploading file...", true);
						new Thread(new Runnable() {
							public void run() {
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(video.this, "Uploading...", Toast.LENGTH_LONG).show();  
									}
								});          
								try
								{
									/*Bundle bimg=getIntent().getExtras();
                        		String img=(String)bimg.get("ipath");*/
									int response= uploadFile(finalpath);
									System.out.println("RES : " + response);             
								}
								catch(Exception ex)
								{
									Toast.makeText(video.this, "Exception"+ex.toString(), Toast.LENGTH_SHORT).show();
								}
							}
						}).start();
					}
				}
			}
		});
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				try
				{
					Intent i=new Intent (MediaStore.ACTION_VIDEO_CAPTURE);

					startActivityForResult(i, 3);

				}
				catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(video.this, "Exception"+e, Toast.LENGTH_LONG).show();
				}
			}
		});

	}


	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) 
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, i);
		flag=1;

		if(resultCode==RESULT_OK)
		{
			/*if(requestCode == SELECT_VIDEO)
    		{
    			 videopath = i.getData();
    			 finalpath=getPath(videopath);
    	    }*/
			//if(requestCode == 3)
			//{
			videopath = i.getData();
			finalpath = getPath(videopath);
			//}
			a=finalpath.split("/");
			Toast.makeText(video.this, "Video Name:"+a[a.length-1], Toast.LENGTH_LONG).show();

			iv.setVideoPath(videopath.getPath());
			iv.setVideoURI(mUri);
			iv.start();
			iv.requestFocus();
			Toast.makeText(video.this, "Finalpath:"+finalpath, Toast.LENGTH_LONG).show();
			Toast.makeText(video.this, "Video captured. Press on playback button to play.", Toast.LENGTH_LONG).show();
		}
		else if(resultCode==RESULT_CANCELED)
		{
			Toast.makeText(video.this, "Cancelled", Toast.LENGTH_LONG).show();
		}

	}


	public int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri); 
		if (!sourceFile.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");
			return 0;
		}
		try { // open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName); 
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);  

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);               
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			String serverResponseMessage = conn.getResponseMessage();

			Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
			if(serverResponseCode == 200){
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(video.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
					}
				});  

				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();
				Bundle bl=new Bundle();
				bl.putString("videopath",a[a.length-1]);
				bl.putString("type", "video");

				/*txt=(EditText)findViewById(R.id.imgtxt);
					bl.putString("txt", txt.getText().toString());*/
				Intent i=new Intent("PACK.MY.LOCATION");
				i.putExtras(bl);
				startActivity(i);
				finish();
			}    



		} catch (MalformedURLException ex) {  
			dialog.dismiss();  
			ex.printStackTrace();
			Toast.makeText(video.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialog.dismiss();  
			e.printStackTrace();
			Toast.makeText(video.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
		}





		dialog.dismiss();       
		return serverResponseCode;  
	} 




}