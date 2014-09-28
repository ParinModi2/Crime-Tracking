package pack.my;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.LastOwnerException;
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
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class cam extends Activity {

	private static final int SELECT_PICTURE = 1;
	int flag=0;
	Uri selectedImage=null,uri=null,mUri=null;
	int serverResponseCode = 0;
	String finalpath=null;
	ProgressDialog dialog = null;
	Bitmap bm;
	private String selectedImagePath;
	private ImageView img;
	ImageButton ib;
	int fl=0;
	Bundle bl=null;
	EditText txt=null;
	Intent intent;
	Button b5;
	ImageView b1,b2,b3,b4,b6;
	String imagename=null;
	final JSONObject jsonobj = new JSONObject(); 
	 private static final int CAMERA_REQUEST = 1888; 
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/upload_media_test.php";	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		EditText text=(EditText)findViewById(R.id.imgtxt);
		text.setTextColor(Color.WHITE);
		 SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			TextView emailId=(TextView)findViewById(R.id.EmailId);
			emailId.setText(email);
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
					Toast.makeText(cam.this, "USER", Toast.LENGTH_LONG).show();
				Intent i=new Intent ("PACK.MY.APPLICATION");
				startActivity(i);
				}
				else
				{
					Toast.makeText(cam.this, "POLICE", Toast.LENGTH_LONG).show();
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
					new AlertDialog.Builder(cam.this)

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
		
		
		
		Button b=(Button)findViewById(R.id.button22);
		ib=(ImageButton)findViewById(R.id.imageButton1);
		ib.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//Intent i=new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				//startActivityForResult(i, 3);
				flag=1;
				/*intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File direct = new File(Environment.getExternalStorageDirectory() + "/gprs/Images");

				if(!direct.exists())
				{
					if(direct.mkdir()) //directory is created;
					{
						imagename="pic"+ String.valueOf(System.currentTimeMillis())+".jpg";
						mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/gprs/Images/", imagename));
						Toast.makeText(cam.this, ":::"+mUri, Toast.LENGTH_LONG).show();
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
						startActivityForResult(intent, 3);
					}

				}
				else
				{
					imagename="pic"+ String.valueOf(System.currentTimeMillis())+".jpg";
					mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/gprs/Images/", imagename));
					Toast.makeText(cam.this, ":::"+mUri, Toast.LENGTH_LONG).show();
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
					startActivityForResult(intent, 3);
				}*/
				 Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	                startActivityForResult(cameraIntent, CAMERA_REQUEST); 

			}
		});
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Bundle bl=new Bundle();
				bl.putString("ipath", selectedImagePath);*/
				/*Intent i1=new Intent("PACK.MY.UPLOAD");
				i1.putExtras(bl);
				startActivity(i1);*/
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					// There are no active networks.
					new AlertDialog.Builder(cam.this)

					.setTitle("Alert!")

					.setMessage("Check your internet connection. Try again.")

					.setNeutralButton("Ok",

					new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,

					int which) {
					}

					}).show();
					//Toast.makeText(cam.this, "Check your internet connection. Try again.", Toast.LENGTH_LONG).show();
				} else
				{
					if(flag==0)
					{
						Toast.makeText(cam.this, "No Image to upload.", Toast.LENGTH_LONG).show();
					}
					else
					{
						dialog = ProgressDialog.show(cam.this, "", "Uploading file...", true);
						new Thread(new Runnable() {
							public void run() {
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(cam.this, "Uploading...", Toast.LENGTH_LONG).show();  
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
									Toast.makeText(cam.this, "Exception"+ex.toString(), Toast.LENGTH_SHORT).show();
								}
							}
						}).start();
					}
				}
			}
		});

		img = (ImageView)findViewById(R.id.imageView1);

		((Button) findViewById(R.id.button21))
		.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				flag=1;
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			if (requestCode == SELECT_PICTURE) {
				try
				{
					 Uri selectedImageUri = data.getData();
		                finalpath = getPath(selectedImageUri);
		                System.out.println("Image Path : " + finalpath);
		                img.setImageURI(selectedImageUri);
					//Uri selectedImageUri = data.getData();
					fl=2;
					//selectedImagePath = getPath(selectedImageUri);
					/*bl=new Bundle();
				bl.putString("ipath", selectedImagePath);*/
					/*finalpath=selectedImagePath;
					System.out.println("Image Path : " + selectedImagePath);
					//Toast.makeText(ClicknuploadActivity.this, "Path:"+selectedImagePath, Toast.LENGTH_LONG).show();
					img.setImageURI(selectedImageUri);*/
					
				}
				catch(Exception ex)
				{
					Toast.makeText(cam.this, "Error:"+ex, Toast.LENGTH_LONG).show();
				}
			}
	        if (requestCode == CAMERA_REQUEST) { 
	        	try{
	        		fl=1;
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            Toast.makeText(cam.this, "Dataget:"+data.getData(), Toast.LENGTH_LONG).show();
	            finalpath=getPath(data.getData());
	            Toast.makeText(cam.this, "Path:"+finalpath, Toast.LENGTH_LONG).show();
	            img.setImageBitmap(photo);
	        	}catch (Exception e) {
					// TODO: handle exception
	        		 Toast.makeText(cam.this, "Excep:"+e, Toast.LENGTH_LONG).show();
				}
	        }  



		}
	}



	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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
						Toast.makeText(cam.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
					}
				});  

				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();
				Bundle bl=new Bundle();
				if(fl==1)
				{
					bl.putString("imgname",imagename);
					bl.putString("test", "camera");
				}
				if(fl==2)
				{
					//String newstr = new String(finalpath.substring(finalpath.lastIndexOf('/')+1, finalpath.length()));
					//Toast.makeText(cam.this, "newstr:"+newstr, Toast.LENGTH_LONG).show();
					bl.putString("imgname",finalpath);
					bl.putString("test", "gallery");
				}
				bl.putString("type", "cam");

				txt=(EditText)findViewById(R.id.imgtxt);
				bl.putString("txt", txt.getText().toString());
				Intent i=new Intent("PACK.MY.LOCATION");
				i.putExtras(bl);
				startActivity(i);
				finish();

			}    



		} 
		catch (MalformedURLException ex) {  
			dialog.dismiss();  
			ex.printStackTrace();
			Toast.makeText(cam.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
		} catch (Exception e) {
			dialog.dismiss();  
			e.printStackTrace();
			Toast.makeText(cam.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
		}





		dialog.dismiss();       
		return serverResponseCode;  
	} 


}