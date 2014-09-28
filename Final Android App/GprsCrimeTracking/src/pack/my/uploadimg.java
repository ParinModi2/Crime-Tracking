package pack.my;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
public class uploadimg extends Activity {
	EditText recvdref;

	 HttpURLConnection connection = null;
	 DataOutputStream outputStream = null;
	 DataInputStream inputStream = null;

	 String pathToOurFile = "";
	 String urlServer = "http://training.artoonsolutions.com/crimetracking/image/upimg.php";
	 String lineEnd = "\r\n";
	 String twoHyphens = "--";
	 String boundary =  "*****";

	 int bytesRead, bytesAvailable, bufferSize;
	 byte[] buffer;
	 int maxBufferSize = 1*1024*1024;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.up);
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        recvdref = (EditText) findViewById(R.id.editText1);
	 
	 try
	 {

 		Bundle bimg=getIntent().getExtras();
 		pathToOurFile=(String)bimg.get("ipath");
	 FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

	 URL url = new URL(urlServer);
	 connection = (HttpURLConnection) url.openConnection();

	 // Allow Inputs & Outputs
	 connection.setDoInput(true);
	 connection.setDoOutput(true);
	 connection.setUseCaches(false);

	 // Enable POST method
	 connection.setRequestMethod("POST");

	 connection.setRequestProperty("Connection", "Keep-Alive");
	 connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

	 outputStream = new DataOutputStream( connection.getOutputStream() );
	 outputStream.writeBytes(twoHyphens + boundary + lineEnd);
	 outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
	 outputStream.writeBytes(lineEnd);

	 bytesAvailable = fileInputStream.available();
	 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	 buffer = new byte[bufferSize];

	 // Read file
	 bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	 while (bytesRead > 0)
	 {
	 outputStream.write(buffer, 0, bufferSize);
	 bytesAvailable = fileInputStream.available();
	 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	 bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	 }

	 outputStream.writeBytes(lineEnd);
	 outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	 // Responses from the server (code and message)
	 int serverResponseCode = connection.getResponseCode();
	 String serverResponseMessage = connection.getResponseMessage();
     recvdref.setText("Response:"+serverResponseMessage);
	 fileInputStream.close();
	 outputStream.flush();
	 outputStream.close();
	 }
	 catch (Exception ex)
	 {
	 //Exception handling
	 }
}
}
