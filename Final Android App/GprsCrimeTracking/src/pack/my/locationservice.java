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

import android.app.Service;
import android.content.Context;
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
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class locationservice extends Service {
	/** Called when the activity is first created. */
	MyPositionOverlay positionOverlay;
	MapController mapController;
	String addressString;
	Projection projection;
	private List mapOverlays;
	//private MyOverlay myoverlay;
	//MapView myMapView;
	Canvas canvas= new Canvas();
	Boolean shadow=true;
	double lat=0, lng=0;
	int i=0;
	int flag=0;
	MyLocationOverlay myLocationOverlay;
	private final int mRadius = 5;
	final JSONObject jsonobj = new JSONObject();
	String upLoadServerUri = "http://training.artoonsolutions.com/crimetracking/locationupdate.php";
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// Get a reference to the MapView
		int y = 10;
		int x = 10;
		//MapView.LayoutParams lp;
		/*lp = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT,
				x, y,
				MapView.LayoutParams.TOP_LEFT);*/
		try
		{
			
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
				//updateWithNewLocation(null);
			}
			public void onProviderEnabled(String provider){ }
			public void onStatusChanged(String provider, int status,
					Bundle extras){ }
		};
		locationManager.requestLocationUpdates(provider, 2000, 10,
				locationListener);
		}catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(locationservice.this, "Exception:"+e,Toast.LENGTH_LONG).show();
		}
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
					//List<Overlay> mapOverlays = myMapView.getOverlays();
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
		

		/*positionOverlay.setLocation(location);
        	positionOverlay.draw(canvas, myMapView, shadow);*/
	}
	
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	public void send()
	{

		try {
			
			
			jsonobj.put("lat", lat);	
			jsonobj.put("long", lng);
			jsonobj.put("address",addressString);
			jsonobj.put("flag", flag);
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
				
				
			}
			else
			{
				Toast.makeText(locationservice.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(locationservice.this, "Error:"+e, Toast.LENGTH_SHORT).show();
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

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}