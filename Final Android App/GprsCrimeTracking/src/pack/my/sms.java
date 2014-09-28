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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sms extends Activity {

    Button sendButton;

    EditText msgTextField;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.m4);        

        // make message text field object
        msgTextField = (EditText) findViewById(R.id.msgTextField);
        // make send button object
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(sms.this, "msg:"+msgTextField.getText().toString(),Toast.LENGTH_SHORT).show();
				Bundle bl=new Bundle();
				bl.putString("text", msgTextField.getText().toString());
				Intent i=new Intent("PACK.MY.UPLOADDATAACTIVITY");
				i.putExtras(bl);
				startActivity(i);


			}
		});
        	
    }

    // this is the function that gets called when you click the button
   }