package pack.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class locationpolice extends MapActivity {
	/** Called when the activity is first created. */
	MyPositionOverlay positionOverlay;
	MapController mapController;
	String addressString;
	Projection projection;
	private List mapOverlays;
	//private MyOverlay myoverlay;
	MapView myMapView;
	Canvas canvas= new Canvas();
	Boolean shadow=true;
	double lat=0, lng=0;
	double lat2=0,long2=0;
	int i=0;
	private final int mRadius = 5;
	MyLocationOverlay myLocationOverlay;
	final JSONObject jsonobj = new JSONObject();
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/locationupdate.php";
	String newUri = "http://training.artoonsolutions.com/crimetracking/inaction.php";
	int event_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		//Toast.makeText(locationpolice.this, "Msg:"+GCMIntentService.message,Toast.LENGTH_LONG).show();
		String msg=GCMIntentService.message;
		String lat1=new String(msg.substring(msg.indexOf(':')+1, msg.lastIndexOf("long")));
		String long1 = new String(msg.substring(msg.lastIndexOf(':')+1, msg.length()));
		String id= new String(msg.substring(msg.indexOf("#")+1, msg.indexOf("=")));
		lat2=Double.parseDouble(lat1);
		long2=Double.parseDouble(long1);
		event_id=Integer.parseInt(id);

		//Toast.makeText(locationpolice.this, "Lat::::: Long:::::::id::"+lat1+","+long1+","+id,Toast.LENGTH_LONG).show();
		// Get a reference to the MapView
		int y = 10;
		int x = 10;
		MapView.LayoutParams lp;
		lp = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT,
				x, y,
				MapView.LayoutParams.TOP_LEFT);
		myMapView = (MapView)findViewById(R.id.myMapView);
		View zoomControls = myMapView.getZoomControls();
		myMapView.addView(zoomControls, lp);
		myMapView.setBuiltInZoomControls(true);
		myMapView.displayZoomControls(true);
		// Get the Map View’s controller
		mapController = myMapView.getController();
		// Configure the map display options
		myMapView.setSatellite(false);
		myMapView.displayZoomControls(true);
		// Zoom in
		mapController.setZoom(15);
		mapOverlays = myMapView.getOverlays();
		projection = myMapView.getProjection();

		//myoverlay = new MyOverlay();
		//mapOverlays.add(myoverlay);
		// Add the MyPositionOverlay
		List<Overlay> overlays = myMapView.getOverlays();
		myLocationOverlay = new MyLocationOverlay(this, myMapView);
		overlays.add(myLocationOverlay);
		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		String provider = LocationManager.NETWORK_PROVIDER;
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider1 = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider1);
		updateWithNewLocation(location);
		final LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				float ac=location.getAccuracy();
				if(ac<200)
				{
					updateWithNewLocation(location);
				}
			}
			public void onProviderDisabled(String provider){
				updateWithNewLocation(null);
			}
			public void onProviderEnabled(String provider){ }
			public void onStatusChanged(String provider, int status,
					Bundle extras){ }
		};
		locationManager.requestLocationUpdates(provider, 60000, 10,
				locationListener);
		/*FOR POLICE (DRIVING DIRECTIONS)*/
		//finish();
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(locationpolice.this)
		.setTitle("Alert!")
		.setMessage("Take Action?")
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog,

		int which) {
			send();
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr="+lat2+","+long2));

			startActivity(intent);
			
			
		}

		});
		alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog,

			int which) {
				finish();
			}

			}).show();
		

		/*FOR POLICE (DRIVING DIRECTIONS) END*/

	}
	private void updateWithNewLocation(Location location) {
		String latLongString;
		//TextView myLocationText;
		//myLocationText = (TextView)findViewById(R.id.myLocationText);
		if (location != null) {
			//positionOverlay.setLocation(location);
			// Update the map location.
			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());
			myLocationOverlay.enableMyLocation();
			mapController.animateTo(point);
			lat = location.getLatitude();
			lng = location.getLongitude();
			latLongString = "Latitude:" + lat + "\nLongitude:" + lng;
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i)).append("\n");
					sb.append(address.getLocality()).append("\n");
					sb.append(address.getPostalCode()).append("\n");
					sb.append(address.getCountryName());
					List<Overlay> mapOverlays = myMapView.getOverlays();
					/*Drawable drawable = this.getResources().getDrawable(R.drawable.mm_20_red);
	        	AddItemizedOverlay itemizedOverlay =
	        	new AddItemizedOverlay(drawable, this);

	        	OverlayItem overlayitem = new OverlayItem(point, "Hello", "Sample Overlay item");

	        	itemizedOverlay.addOverlay(overlayitem);
	        	mapOverlays.add(itemizedOverlay);*/
					//send();
				}
				addressString = sb.toString();
			} catch (IOException e) {}
		} 
		else 
		{
			latLongString = "Location not found";
		}
		//myLocationText.setText("Your Current Position is:\n" +latLongString + "\n" + addressString);
		/*positionOverlay.setLocation(location);
        	positionOverlay.draw(canvas, myMapView, shadow);*/

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	public void send()
	{

		try {

			jsonobj.put("event_id", event_id);	
			jsonobj.put("lat", lat);	
			jsonobj.put("long", lng);

			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			//Toast.makeText(locationpolice.this, "email:"+email,Toast.LENGTH_LONG).show();
			jsonobj.put("email", email);
			Toast.makeText(locationpolice.this, "sending:"+email+", "+event_id,Toast.LENGTH_LONG).show();

			//final String wurl = "http://training.artoonsolutions.com/crimetracking/email.php";
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(newUri);
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
				//int j=Integer.parseInt(resultstring);
				//Toast.makeText(locationpolice.this, "UPLOADED... value of j="+j, Toast.LENGTH_LONG).show();



				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/
				//Toast.makeText(location.this, "Response:"+resultstring, Toast.LENGTH_LONG).show();
				/*int j;
	        		j=Integer.parseInt(resultstring);

	        	if(j==0)
	        	{
	        		Toast.makeText(location.this, "Location Updated", Toast.LENGTH_LONG).show();*/
				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/
				//}

			}
			else
			{
				Toast.makeText(locationpolice.this, "Could not load. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(locationpolice.this, "Error:"+e, Toast.LENGTH_SHORT).show();
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




		public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Toast.makeText(locationpolice.this, "back button pressed", Toast.LENGTH_LONG).show();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}