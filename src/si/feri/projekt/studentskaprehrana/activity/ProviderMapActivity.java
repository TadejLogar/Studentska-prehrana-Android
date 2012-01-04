package si.feri.projekt.studentskaprehrana.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import si.feri.projekt.studentskaprehrana.MapOverlay;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.Road;
import si.feri.projekt.studentskaprehrana.RoadProvider;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import si.feri.projekt.studentskaprehrana.Settings;

/*
 * Zemljvid
 * Prikaži pot od trenutne lokacije do ponudnika
 */
public class ProviderMapActivity extends MapActivity {
    LinearLayout linearLayout;
    MapView mapView;
    private Road mRoad;
    
    //private boolean enableGps = false;
    double latitude;
    double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.maps_layout);

            Bundle extras = getIntent().getExtras();
    		if (extras == null) {
    			finish();
    			return;
    		} else {
    			latitude = extras.getDouble("latitude");
    			longitude = extras.getDouble("longitude");
    		}
            
            mapView = (MapView) findViewById(R.id.mapViewProvider);
            mapView.setBuiltInZoomControls(true);

        	/*try {
            	LocationManager locationManager;
            	String context = Context.LOCATION_SERVICE;
            	locationManager = (LocationManager)getSystemService(context);
            	
            	Criteria criteria = new Criteria();
            	criteria.setAccuracy(Criteria.ACCURACY_FINE);
            	criteria.setAltitudeRequired(false);
            	criteria.setBearingRequired(false);
            	criteria.setCostAllowed(true);
            	criteria.setPowerRequirement(Criteria.POWER_LOW);
            	String provider = locationManager.getBestProvider(criteria, true);

            	Location location = locationManager.getLastKnownLocation(provider);
            	
                locationManager.requestLocationUpdates(provider, 100000, 10, locationListener); // 100 sek, 10 m
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}*/
            
            changeMap(null);
    }
    
    private void changeMap(Location location) {
    	double fromLat = Settings.locationLat, fromLon = Settings.locationLon, toLat = latitude, toLon = longitude;
    	if (location != null) {
    		fromLat = location.getLatitude();
    		fromLon = location.getLongitude();
    		
    		Toast.makeText(this, "Koordinate " + fromLat + ", " + fromLon, Toast.LENGTH_LONG).show();
    	}
        String url = RoadProvider.getUrl(fromLat, fromLon, toLat, toLon);
        InputStream is = getConnection(url);
        mRoad = RoadProvider.getRoute(is);
        mHandler.sendEmptyMessage(0);
	}

	private final LocationListener locationListener = new LocationListener() {
    	public void onLocationChanged(Location location) {
    		if (location != null) {
	    		Settings.setLocation(location.getLatitude(), location.getLongitude());
				changeMap(location);
    		}
    	}

    	public void onProviderDisabled(String provider) {
    		//my_updateWithNewLocation(null);
    	}

    	public void onProviderEnabled(String provider) { }
    	public void onStatusChanged(String provider, int status, Bundle extras) { }
	};

    Handler mHandler = new Handler() {
	    public void handleMessage(android.os.Message msg) {
	        TextView textView = (TextView) findViewById(R.id.textViewMapMessage);
                String locationData = mRoad.mName + ", " + mRoad.mDescription;
                locationData = locationData.replaceAll("Distance", "Razdalja");
                locationData = locationData.replaceAll("about", "približno");
	        textView.setText(locationData);
	        MapOverlay mapOverlay = new MapOverlay(mRoad, mapView);
	        List<Overlay> listOfOverlays = mapView.getOverlays();
	        listOfOverlays.clear();
	        listOfOverlays.add(mapOverlay);
	        mapView.invalidate();
	    };
    };

    private InputStream getConnection(String url) {
        InputStream is = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
