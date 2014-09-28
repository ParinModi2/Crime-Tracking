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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

public class sendimgname extends Activity{
	final JSONObject jsonobj = new JSONObject();
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/upload_media_test.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		Toast.makeText(sendimgname.this, "Sending Json",Toast.LENGTH_LONG).show();
		send();
		
	}
	
	public void send()
	{
		 try {
			 	Bundle bd=getIntent().getExtras();
	    		String img =(String)bd.get("imgname");
	    		String txt =(String)bd.get("txt");
	    		String newstr = new String(img.substring(img.lastIndexOf('/')+1, img.length()));
           	 	Toast.makeText(sendimgname.this, "newstr:"+newstr, Toast.LENGTH_LONG).show();
	        	jsonobj.put("img", newstr);
	        	jsonobj.put("txt",txt);
	        	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
	        	String email = sharedPreferences.getString("MEM1", "");
	        	Toast.makeText(sendimgname.this, "email:"+email,Toast.LENGTH_LONG).show();
	        	jsonobj.put("email", email);
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
	        		Toast.makeText(sendimgname.this, "UPLOADED...", Toast.LENGTH_LONG).show();
	        		Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);
	        		/*int j;
	        		j=Integer.parseInt(resultstring);
	        		Toast.makeText(sendimgname.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
	        	if(j==0)
	        	{
	        		Toast.makeText(sendimgname.this, "Profile Updated", Toast.LENGTH_LONG).show();
	        		Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);
	        	}
	        	*/
	        	}
	        	else
	        	{
	        		Toast.makeText(sendimgname.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
	        	}
				}
				catch (Exception e) {
	        	Toast.makeText(sendimgname.this, "Error:"+e, Toast.LENGTH_SHORT).show();
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
