package com.sciget.studentmeals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import si.feri.projekt.studentskaprehrana.R;

import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.RestaurantModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;
import com.sciget.studentmeals.service.UpdateService;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainApplication extends Application {
    private boolean first = true;
    private ArrayList<RestaurantData> completeRestaurantsList;
    
    public void onCreate() {
        super.onCreate();
        Log.i(MainApplication.class.getName(), "created");
        
        if (first) {
            first = false;
            initialization();
        }
    }
    
    private void initialization() {
        new MyPerferences(this);
        File dir = new File(MyPerferences.getInstance().getExternalStoragePath());
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        copyDatabase();
        MyPerferences.getInstance().setValues();
        startService(new Intent(this, UpdateService.class));
        new LocationTask().execute();
        loadRestaurantsList();
    }
    
    private void copyDatabase() {
        File file = new File(MyPerferences.getInstance().getDatabasePath());
        if (file.exists()) return;
        
        InputStream in = getResources().openRawResource(R.raw.database);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int read;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
             try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadRestaurantsList() {
        if (completeRestaurantsList != null) return;
        
        RestaurantModel restaurantModel = new RestaurantModel(this);
        completeRestaurantsList = restaurantModel.getAllRestaurants();
        restaurantModel.close();
    }
    
    public ArrayList<RestaurantData> getCompleteRestaurantsList() {
        loadRestaurantsList();
        return (ArrayList<RestaurantData>) completeRestaurantsList.clone();
    }

    public int getSubsidiesNumber() {
        StudentMealUserData user = new StudentMealUserModel(this).getUserAll();
        return user.remainingSubsidies;
    }

    public String getLastProvider() {
        return new StudentMealUserModel(this).getLastVisitedProvider();
    }
    
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                MyPerferences.getInstance().setLocation(new MyPerferences.Location(location.getLatitude(), location.getLongitude()));
                //changeMap(location);
            }
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    class LocationStuff {
        public LocationManager locationManager;
        public String provider;
        
        public LocationStuff(LocationManager locationManager, String provider) {
            this.locationManager = locationManager;
            this.provider = provider;
        }
    }
    
    private class LocationTask extends AsyncTask<Void, Void, LocationStuff> {
        protected LocationStuff doInBackground(Void... v) {
            LocationManager locationManager = null;
            try {
                String context = Context.LOCATION_SERVICE;
                locationManager = (LocationManager) getSystemService(context);
                
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String provider = locationManager.getBestProvider(criteria, true);

                Location location = locationManager.getLastKnownLocation(provider);
                
                if (location != null) {
                    MyPerferences.getInstance().setLocation(new MyPerferences.Location(location.getLatitude(), location.getLongitude()));
                }
                
                return new LocationStuff(locationManager, provider);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(LocationStuff stuff) {
            if (stuff != null) {
                stuff.locationManager.requestLocationUpdates(stuff.provider, 100000, 10, locationListener); // 100 sekund, 10 metrov
            }
        }
    }
    
    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }


    public RestaurantData getRestaurantById(int restaurantId) {
        for (RestaurantData restaurant : completeRestaurantsList) {
            if (restaurant.id == restaurantId) {
                return restaurant;
            }
        }
        return null;
    }

    public void setRestaurantMapImageHash(int restaurantId, String newHash) {
        getRestaurantById(restaurantId).mapImageSha1 = newHash;
        new RestaurantModel(this).setRestaurantMapImageHash(restaurantId, newHash);
    }

    public boolean isRestaurantFavorited(int restaurantId) {
        StudentMealUserModel userModel = new StudentMealUserModel(this);
        boolean result = userModel.isRestaurantFavorited(restaurantId);
        userModel.close();
        return result;
    }
    
    public void setRestaurantFavorited(int restaurantId, boolean favorited) {
        StudentMealUserModel userModel = new StudentMealUserModel(this);
        userModel.setRestaurantFavorited(restaurantId, favorited);
        userModel.close();
    }
}
