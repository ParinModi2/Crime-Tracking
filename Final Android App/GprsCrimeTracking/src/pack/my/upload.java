package pack.my;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class upload extends Activity {
    
    TextView tv;
    Button b;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        b = (Button)findViewById(R.id.but);
        tv = (TextView)findViewById(R.id.tv);
        //tv.setText("Uploading");
         
        b.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(upload.this, "", "Uploading file...", true);
                 new Thread(new Runnable() {
                        public void run() {
                             runOnUiThread(new Runnable() {
                                    public void run() {
                                        tv.setText("uploading started.....");
                                    }
                                });          
                             try
                             {
                            	 Bundle bimg=getIntent().getExtras();
                         		String img=(String)bimg.get("ipath");
                         int response= uploadFile(img);
                         System.out.println("RES : " + response);             
                             }
                             catch(Exception ex)
                             {
                            	 Toast.makeText(upload.this, "Exception"+ex.toString(), Toast.LENGTH_SHORT).show();
                             }
                        }
                      }).start();        
                }
        });
    }
     
    public int uploadFile(String sourceFileUri) {
          String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/upload_media_test.php";
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
                            tv.setText("File Upload Completed.");
                            Toast.makeText(upload.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });                
               }    
               
               //close the streams //
               fileInputStream.close();
               dos.flush();
               dos.close();
                
          } catch (MalformedURLException ex) {  
              dialog.dismiss();  
              ex.printStackTrace();
              Toast.makeText(upload.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
          } catch (Exception e) {
              dialog.dismiss();  
              e.printStackTrace();
              Toast.makeText(upload.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
              Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);  
          }
          dialog.dismiss();       
          return serverResponseCode;  
         } 
}