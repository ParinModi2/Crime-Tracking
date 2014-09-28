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
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class locationupdate extends MapActivity {
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
	int i=0;
	String id="";
	int flag=0;
	MyLocationOverlay myLocationOverlay;
	private final int mRadius = 5;
	final JSONObject jsonobj = new JSONObject();
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/ping.php";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		new AlertDialog.Builder(locationupdate.this)

		.setTitle("Alert!")

		.setMessage("Keep this screen active to be traced. Press on back button to exit.")

		.setNeutralButton("Ok",

				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog,

					int which) {
			}

		}).show();
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

					updateWithNewLocation(location);

			}
			public void onProviderDisabled(String provider){
				updateWithNewLocation(null);
			}
			public void onProviderEnabled(String provider){ }
			public void onStatusChanged(String provider, int status,
					Bundle extras){ }
		};
		locationManager.requestLocationUpdates(provider, 2000, 10,
				locationListener);
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

				}
				addressString = sb.toString();
				send();
			} catch (IOException e) {}
		} 
		else 
		{
			latLongString = "Location not found";
		}
		//myLocationText.setText("Your Current Position is:\n" +latLongString + "\n" + addressString);
		//Toast.makeText(locationupdate.this, "add:"+addressString,Toast.LENGTH_LONG).show();

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
			//Toast.makeText(locationupdate.this, "send called", Toast.LENGTH_LONG).show();
			//Toast.makeText(locationupdate.this, "Flag="+flag,Toast.LENGTH_LONG).show();
			jsonobj.put("lat", lat);	
			jsonobj.put("long", lng);
			jsonobj.put("address",addressString);
			jsonobj.put("flag", flag);
			jsonobj.put("event_id", id);
			flag=1;
			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
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
				Toast.makeText(locationupdate.this, "UPLOADED...", Toast.LENGTH_LONG).show();
				//Toast.makeText(locationupdate.this, "Result:"+resultstring, Toast.LENGTH_LONG).show();
				id= new String(resultstring.substring(0, resultstring.indexOf("<")));
				//Toast.makeText(locationupdate.this, "event id:"+id, Toast.LENGTH_LONG).show();
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
				Toast.makeText(locationupdate.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(locationupdate.this, "Error:"+e, Toast.LENGTH_SHORT).show();
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
			Toast.makeText(locationupdate.this, "back button pressed", Toast.LENGTH_LONG).show();
			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String user = sharedPreferences.getString("MEM3", "");
			if(user.equals("police"))
			{
				Intent i = new Intent(locationupdate.this, policeapp.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			else
			{
				Intent i = new Intent(locationupdate.this, application.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}