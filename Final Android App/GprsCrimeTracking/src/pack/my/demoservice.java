package pack.my;

import static pack.my.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static pack.my.CommonUtilities.EXTRA_MESSAGE;
import static pack.my.CommonUtilities.SENDER_ID;

import com.google.android.gcm.GCMRegistrar;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class demoservice extends Service{
AsyncTask<Void, Void, Void> mRegisterTask;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Connection detector
	ConnectionDetector cd;
	public static String newMessage;
	public static String name="Ankita";
	public static String email;
	@Override
		public void onStart(Intent intent, int startId) {
			// TODO Auto-generated method stub
			super.onStart(intent, startId);
			Toast.makeText(demoservice.this, "In service", Toast.LENGTH_LONG).show();
			cd = new ConnectionDetector(getApplicationContext());

			// Check if Internet present
			if (!cd.isConnectingToInternet()) {
				// Internet Connection is not present
				alert.showAlertDialog(demoservice.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false);
				// stop executing code by return
				return;
			}
			
			// Getting name, email from intent
			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			email = sharedPreferences.getString("MEM1", "");		
			
			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(this);

			// Make sure the manifest was properly set - comment out this line
			// while developing the app, then uncomment it when it's ready.
			GCMRegistrar.checkManifest(this);

			
			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));
			
			// Get GCM registration id
			final String regId = GCMRegistrar.getRegistrationId(this);

			// Check if regid already presents
			if (regId.equals("")) {
				// Registration is not present, register now with GCM			
				GCMRegistrar.register(this, SENDER_ID);
			} else {
				// Device is already registered on GCM
				if (GCMRegistrar.isRegisteredOnServer(this)) {
					// Skips registration.				
					Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				} else {
					// Try to register again, but not in the UI thread.
					// It's also necessary to cancel the thread onDestroy(),
					// hence the use of AsyncTask instead of a raw thread.
					final Context context = this;
					mRegisterTask = new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							// Register on our server
							// On server creates a new user
							ServerUtilities.register(context, email, regId);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}

					};
					mRegisterTask.execute(null, null, null);
				}
			}
			
		}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message	
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			/*Bundle bl=new Bundle();
			bl.putString("Message",newMessage);
			Intent i=new Intent("PACK.MY.LOCATIONPOLICE");
			i.putExtras(bl);
			startActivity(i);*/
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	@Override
	public void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}


}
