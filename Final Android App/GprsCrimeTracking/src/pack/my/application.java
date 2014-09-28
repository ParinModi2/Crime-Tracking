package pack.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class application extends Activity {
	Button b5,b6;
	ImageView b1,b2,b3,b4;
	Bundle bl=new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Toast.makeText(application.this, "Welcome User", Toast.LENGTH_LONG).show();
		b1=(ImageView)findViewById(R.id.button1);
		b2=(ImageView)findViewById(R.id.button2);
		b3=(ImageView)findViewById(R.id.button3);
		b4=(ImageView)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		//b6=(Button)findViewById(R.id.button6);
		b1.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i=new Intent ("PACK.MY.CAM");
				startActivity(i);
			}
		});
		b2.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i1=new Intent ("PACK.MY.VIDEO");
				startActivity(i1);
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i2=new Intent ("PACK.MY.TEXT");
				startActivity(i2);
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
					new AlertDialog.Builder(application.this)

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
				}
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i4=new Intent ("PACK.MY.EDITPRO");
				startActivity(i4);
			}
		});
		/*b6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i5=new Intent ("PACK.MY.LOCATIONPOLICE");
				startActivity(i5);
			}
		});*/
	}

}
