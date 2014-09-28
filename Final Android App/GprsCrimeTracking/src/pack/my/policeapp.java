package pack.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class policeapp extends Activity {
	Button b5;
	ImageView b1,b2,b3,b4;
	TextView history;
	Bundle bl=new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.policeapp);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toast.makeText(policeapp.this, "Welcome Police", Toast.LENGTH_LONG).show();
        startService(new Intent(this,locationservice.class));
        b1=(ImageView)findViewById(R.id.button1);
        b2=(ImageView)findViewById(R.id.button2);
        b3=(ImageView)findViewById(R.id.button3);
        b4=(ImageView)findViewById(R.id.button4);
        history=(TextView)findViewById(R.id.history);
        b5=(Button)findViewById(R.id.button5);
        
        
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		String email = sharedPreferences.getString("MEM1", "");
		TextView emailId=(TextView)findViewById(R.id.EmailId);
		emailId.setText(email);
        
        history.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent ("PACK.MY.HISTORY");
				startActivity(i);
			}
		});
        
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
				new AlertDialog.Builder(policeapp.this)

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
       
	}

	
	 public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
	     
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.sign_out:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	        	AlertDialog.Builder alertDialog=new AlertDialog.Builder(policeapp.this)
	    		.setTitle("Alert!")
	    		.setMessage("Your data will be removed. Are you sure you want to logout?")
	    		.setPositiveButton("Confirm",new DialogInterface.OnClickListener() {

	    		@SuppressWarnings("null")
				public void onClick(DialogInterface dialog,

	    		int which) {
	    			  //Toast.makeText(policeapp.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
	    			  SharedPreferences preferences = getSharedPreferences("MY_SHARED_PREF", 0);
	    			  preferences.edit().remove("MEM1").commit();
	    			  preferences.edit().remove("MEM2").commit();
	    			  preferences.edit().remove("MEM3").commit();
	    			  Intent i = new Intent(policeapp.this, GprsCrimeTrackingActivity.class);
	    			  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    			  startActivity(i);
	    			finish();
	    			
	    		}

	    		});
	    		alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {

	    			public void onClick(DialogInterface dialog,

	    			int which) {
	    			}

	    			}).show();
	          
	            return true;
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	 
}
