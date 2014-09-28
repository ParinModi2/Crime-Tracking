package pack.my;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UploaddataActivity extends Activity {
    /** Called when the activity is first created. */
	Bundle bvdo,bmsg,bimg;
	String loc=null;
	 public EditText buildref;
	 public EditText recvdref;
	private static DefaultHttpClient getHttpclient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);	
		return httpclient;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m2);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        buildref = (EditText) findViewById(R.id.editTextbuild);
        buildref.setFocusable(false);
        buildref.setClickable(false);
        recvdref = (EditText) findViewById(R.id.editTextrecvd);
        recvdref.setFocusable(false);
        recvdref.setClickable(false);
        ImageView iv=(ImageView)findViewById(R.drawable.icon);
        JSONObject jsonobj; 
        jsonobj = new JSONObject();
      
        	bvdo=getIntent().getExtras();
    		String path =(String)bvdo.get("path");
    		bmsg=getIntent().getExtras();
    		String msg =(String)bmsg.get("text");
    		bimg=getIntent().getExtras();
    		String img=(String)bimg.get("ipath");
    		
        	
    		 String url = "http://training.artoonsolutions.com/crimetracking/image/upimage.php";
    	        HttpClient client = new DefaultHttpClient();
    	        HttpPost post = new HttpPost(url);
    	        MultipartEntity mpEntity = new MultipartEntity();
    	        //Path of the file to be uploaded
    	        String filepath = "";
    	        File file = new File(img);
    	        ContentBody cbFile = new FileBody(file, "image/jpeg");         
    	        try{
    	        //Add the data to the multipart entity
    	        mpEntity.addPart("image", cbFile);
    	        mpEntity.addPart("name", new StringBody("Test", Charset.forName("UTF-8")));
    	        mpEntity.addPart("data", new StringBody("This is test report", Charset.forName("UTF-8")));
    	        post.setEntity(mpEntity);
    	        //Execute the post request
    	        HttpResponse response1 = client.execute(post);
    	        //Get the response from the server
    	        HttpEntity resEntity = response1.getEntity();
    	        String Response=EntityUtils.toString(resEntity);
    	        //Generate the array from the response
    	        JSONArray jsonarray = new JSONArray("["+Response+"]");
    	        JSONObject jsonobject = jsonarray.getJSONObject(0);
    	        //Get the result variables from response 
    	        String result = (jsonobject.getString("result"));
    	        String message = (jsonobject.getString("msg"));
    	        //Close the connection
    	        client.getConnectionManager().shutdown();
    	        }
    	        catch(Exception e)
    	        {
    	        	e.printStackTrace();
    	        }
    	        
    
        }
   
    
}