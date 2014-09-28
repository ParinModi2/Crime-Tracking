package pack.my;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

// import everything you need
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class uploadtxt extends Activity {

    Button sendButton;

    EditText msgTextField;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.m4);        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // make message text field object
        msgTextField = (EditText) findViewById(R.id.msgTextField);
        // make send button object
        sendButton = (Button) findViewById(R.id.sendButton);

    }

    // this is the function that gets called when you click the button
    public void send(View v)
    {
        // get the message from the message text box
        String msg = msgTextField.getText().toString();  

        // make sure the fields are not empty
        if (msg.length()>0)
        {
	  	    HttpClient httpclient = new DefaultHttpClient();
	   	    HttpPost httppost = new HttpPost("http://localhost:82/folder/uploadtxt.php");
	   	 try {//Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
	 	   
	   		BasicNameValuePair b1;
	   	   List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	       nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	       nameValuePairs.add(b1=new BasicNameValuePair("message", msg));
	       StringEntity se = new UrlEncodedFormEntity(nameValuePairs);
	       se.setContentEncoding("UTF-8");
           se.setContentType("text/html");
	       httppost.addHeader(se.getContentType());
	       httppost.setEntity(se);
	       //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	       Toast.makeText(getBaseContext(),b1.toString(),Toast.LENGTH_SHORT).show();
	   	   httpclient.execute(httppost);
	   	   msgTextField.setText(""); // clear text box
	   	
	     } catch (ClientProtocolException e) {
	         // TODO Auto-generated catch block
	     } catch (IOException e) {
	         // TODO Auto-generated catch block
	     }

        }
        else
        {
        	// display message if text fields are empty
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }

    }

}