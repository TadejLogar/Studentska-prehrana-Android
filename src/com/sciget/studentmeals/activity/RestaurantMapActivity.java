package com.sciget.studentmeals.activity;

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
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.MyPerferences;

public class RestaurantMapActivity extends MapActivity {
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    
    private MainApplication application;
    private MapView mapView;
    
    private double latitude;
    private double longitude;
    private Road mapRoad;
    private Handler mapHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String locationData = mapRoad.mName + ", " + mapRoad.mDescription;
            locationData = locationData.replaceAll("Distance", "Razdalja");
            locationData = locationData.replaceAll("about", "približno");
            locationData = locationData.replaceAll(" to ", " do ");
            locationData = locationData.replaceAll("mins", "minute");
            locationData = locationData.replaceAll("secs", "sekund");
            mapMessageTextView.setText(locationData);
            MapOverlay mapOverlay = new MapOverlay(mapRoad, mapView);
            List<Overlay> listOfOverlays = mapView.getOverlays();
            listOfOverlays.clear();
            listOfOverlays.add(mapOverlay);
            mapView.invalidate();
        };
    };
    private TextView mapMessageTextView;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MainApplication) getApplication();
        setContentView(R.layout.maps_layout);
        setViews();
        setData();
        try {
            changeMap(MyPerferences.getInstance().getLocation());
        } catch (Exception e) {
            finish();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        register();
    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregister();
    }

    private void setViews() {
        mapView = (MapView) findViewById(R.id.mapViewProvider);
        mapView.setBuiltInZoomControls(true);
        mapMessageTextView = (TextView) findViewById(R.id.textViewMapMessage);
    }
    
    private void setData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble(LATITUDE_KEY);
            longitude = extras.getDouble(LONGITUDE_KEY);
        }
        register();
    }
    
    private void register() {
        MyPerferences.getInstance().registerRestaurantMapActivity(this);
    }
    
    private void unregister() {
        MyPerferences.getInstance().unregisterRestaurantMapActivity();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public void changeMap(MyPerferences.Location location) {
        String url = RoadProvider.getUrl(location.latitude, location.longitude, latitude, longitude);
        InputStream inputStream = getConnection(url);
        mapRoad = RoadProvider.getRoute(inputStream);
        mapHandler.sendEmptyMessage(0);
    }
    
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

}
