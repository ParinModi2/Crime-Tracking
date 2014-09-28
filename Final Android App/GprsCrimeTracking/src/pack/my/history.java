package pack.my;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;
 
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
import pack.my.AlertDialogManager;
import pack.my.ConnectionDetector;
import pack.my.JSONParser;
 
public class history extends ListActivity {
    // Connection detector
    ConnectionDetector cd;
     Bundle bl;
     final JSONObject jsonobj = new JSONObject();
     public static String email,addr,caseid;
    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
     
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> albumsList;
 
    // albums JSONArray
    JSONArray albums = null;
 
    // albums JSON url
    private static final String URL_ALBUMS = "http://training.artoonsolutions.com/crimetracking/history.php";
    ImageView b1,b2,b3,b4,b6;
    Button b5;
    // ALL JSON node names
    private static final String TAG_ID = "uemail";
    private static final String TAG_NAME = "add";
    private static final String TAG_SONGS_COUNT = "case_id";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_activity);
         
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		String emai = sharedPreferences.getString("MEM1", "");
		TextView emailId=(TextView)findViewById(R.id.EmailId);
		emailId.setText(emai);
        b1=(ImageView)findViewById(R.id.button1);
		b2=(ImageView)findViewById(R.id.button2);
		b3=(ImageView)findViewById(R.id.button3);
		b4=(ImageView)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		b6=(ImageView)findViewById(R.id.button6);
		b6.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				SharedPreferences Preferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				String type = Preferences.getString("MEM3", "");
				if(type.equals("user"))
				{
					Toast.makeText(history.this, "USER", Toast.LENGTH_LONG).show();
				Intent i=new Intent ("PACK.MY.APPLICATION");
				startActivity(i);
				}
				else
				{
					Toast.makeText(history.this, "POLICE", Toast.LENGTH_LONG).show();
					Intent i=new Intent ("PACK.MY.POLICEAPP");
					startActivity(i);
				}
				finish();
			}
		});
		b1.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i=new Intent ("PACK.MY.CAM");
				startActivity(i);
				finish();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) {
				Intent i1=new Intent ("PACK.MY.VIDEO");
				startActivity(i1);
				finish();
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i2=new Intent ("PACK.MY.TEXT");
				startActivity(i2);
				finish();
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
					new AlertDialog.Builder(history.this)

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
					finish();
				}
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i4=new Intent ("PACK.MY.EDITPRO");
				startActivity(i4);
				finish();
			}
		});
		
		

        
        
        
        
        
        cd = new ConnectionDetector(getApplicationContext());
          
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(history.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
 
        //send();
        
        
        
        
        // Hashmap for ListView
        albumsList = new ArrayList<HashMap<String, String>>();
 
        // Loading Albums JSON in Background Thread
        new LoadAlbums().execute();
         
        // get listview
        ListView lv = getListView();
         
        /**
         * Listview item click listener
         * TrackListActivity will be lauched by passing album id
         * */
        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                    long arg3) {
                // on selecting a single album
                // TrackListActivity will be launched to show tracks inside the album
                Intent i = new Intent(getApplicationContext(), feedback.class);
            	//Object obj=arg0.getItemAtPosition(arg2);
            	caseid = ((TextView) view.findViewById(R.id.songs_count)).getText().toString();
            	addr = ((TextView) view.findViewById(R.id.album_name)).getText().toString();
            	email = ((TextView) view.findViewById(R.id.album_id)).getText().toString();
            	
            	//Toast.makeText(history.this, "Value in onclick:"+caseid+","+addr+","+email, Toast.LENGTH_LONG).show();        
                 
                startActivity(i);
                finish();
            }
        });     
    }
 
    /**
     * Background Async Task to Load all Albums by making http request
     * */
    class LoadAlbums extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(history.this);
            pDialog.setMessage("Listing all the cases ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting Albums JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add((NameValuePair) new BasicNameValuePair("email",email ));
            // getting JSON string from URL
            String json = jsonParser.makeHttpRequest(URL_ALBUMS, "POST",
                    params);
            
            // Check your log cat for JSON reponse
            Log.d("Albums JSON: ", "> " + json);
 
            try {               
                albums = new JSONArray(json);
                 
                if (albums != null) {
                    // looping through All albums
                    for (int i = 0; i < albums.length(); i++) {
                        JSONObject c = albums.getJSONObject(i);
 
                        // Storing each json item values in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String songs_count = c.getString(TAG_SONGS_COUNT);
                        
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_SONGS_COUNT, songs_count);
 
                        // adding HashList to ArrayList
                        albumsList.add(map);
                    }
                }else{
                    Log.d("Albums: ", "null");
                }
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	//Toast.makeText(history.this, "JSON:"+TAG_NAME, Toast.LENGTH_LONG).show();
                	
                    ListAdapter adapter = new SimpleAdapter(
                            history.this, albumsList,
                            R.layout.list_item_albums, new String[] { TAG_ID,
                                    TAG_NAME, TAG_SONGS_COUNT }, new int[] {
                                    R.id.album_id, R.id.album_name, R.id.songs_count });
                     
                    // updating listview
                    setListAdapter(adapter);
                    
                }
            });
 
        }
 
    }
    
    
    public void send()
	{

		try {
			
			SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			String email = sharedPreferences.getString("MEM1", "");
			Toast.makeText(history.this, "send"+email,Toast.LENGTH_LONG).show();
			jsonobj.put("email", email);


			//final String wurl = "http://training.artoonsolutions.com/crimetracking/email.php";
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(URL_ALBUMS);
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
				//Toast.makeText(history.this, "UPLOADED...", Toast.LENGTH_LONG).show();
				Toast.makeText(history.this, "Res from php:"+resultstring, Toast.LENGTH_LONG).show();
				/*Intent intent=new Intent("PACK.MY.APPLICATION");
	        		startActivity(intent);*/

			}
			else
			{
				Toast.makeText(history.this, "Could not register. Try again.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(history.this, "Error:"+e, Toast.LENGTH_SHORT).show();
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