package pack.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.view.View.OnCreateContextMenuListener;

public class upload1 extends Activity{
	 private void OnCreate() {
		 setContentView(R.layout.m3);
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		// TODO Auto-generated method stub
		String path = "http://192.168.1.104:82/folder/testmysql1.php";

		    HttpClient client = new DefaultHttpClient();
		    HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
		                                                                            // Limit
		    HttpResponse response;
		    JSONObject json = new JSONObject();
		    try {
		        HttpPost post = new HttpPost(path);
		        json.put("service", "GOOGLE");
		        Log.i("jason Object", json.toString());
		        post.setHeader("json", json.toString());
		        StringEntity se = new StringEntity(json.toString());
		        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
		                "application/json"));
		        post.setEntity(se);
		        response = client.execute(post);
		        /* Checking response */
		        if (response != null) {
		            InputStream in = response.getEntity().getContent(); // Get the
		                                                                // data in
		                                                                    // the
		                                                                    // entity
		            String a = convertStreamToString(in);
		            Log.i("Read from Server", a);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	 private static String convertStreamToString(InputStream is) {

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    try {
		        while ((line = reader.readLine()) != null) {
		            sb.append(line + "\n");
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            is.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    return sb.toString();
		}
}
