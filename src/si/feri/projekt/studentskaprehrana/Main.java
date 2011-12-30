package si.feri.projekt.studentskaprehrana;

import java.util.Vector;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.RestaurantData;
import com.sciget.studentmeals.database.model.RestaurantModel;
import com.sciget.studentmeals.service.UpdateService;

import si.feri.projekt.studentskaprehrana.activity.RestaurantsListActivity;
import si.feri.projekt.studentskaprehrana.db.ProvidersDB;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.TextView;

/*
1. Naredite osnovne Activity
2. Shared Preferences (vnesite večino nastavitev, ki jih boste imeli).
3. Pripravite si bazo (če jo potrebujete).
4. Pripravite si testne primere (izračunov, kako boste testirali, itd...)
5. Začetek vnosa GUI
Na GitHub si odprite svoj projekt, kjer boste imeli kopijo in kljeb bomo spremljali napredek vašega projekta (lahko pod psevdonimom).
*/

public class Main extends Activity {
    private ListApplication app;
    private boolean done;
    
    ProgressDialog dialogWait;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyPerferences(this);
        Settings.setMain(this);
        app = (ListApplication) getApplication();
        //setContentView(R.layout.main);
        startUpdateService();
        //new NewThread().start();
        
        app.loadData();
        finish();
        Intent intent = new Intent(Main.this, RestaurantsListActivity.class);
        startActivityForResult(intent, 1);
        
        new LocationTask().execute();
    }

    private void startUpdateService() {
        if (!UpdateService.running) {
            startService(new Intent(this, UpdateService.class));
        }
    }
    
    /*Handler msgHandler = new Handler() {
    	public void handleMessage(Message m) {
    		dialogWait = ProgressDialog.show(Main.this, "", "Nalagam podatke o ponudnikih. Lahko traja dalj časa.", true);
    	}
	};*/
	
    /*Handler startHandler = new Handler() {
    	public void handleMessage(Message m) {
    		dialogWait.dismiss();
    		
    		app.loadData();
    		
    		finish();
    		Intent intent = new Intent(Main.this, RestaurantsListActivity.class);
    		startActivityForResult(intent, 1);
    	}
	};*/
    
    /*public class NewThread extends Thread {
    	public void run() {
            Message msg = new Message();
            msgHandler.sendMessage(msg);


            Message msg2 = new Message();
            startHandler.sendMessage(msg2);

    	}
    }*/
    
	private final LocationListener locationListener = new LocationListener() {
    	public void onLocationChanged(Location location) {
    		if (location != null) {
	    		Settings.setLocation(location.getLatitude(), location.getLongitude());
				//changeMap(location);
    		}
    	}

    	public void onProviderDisabled(String provider) {
    		//my_updateWithNewLocation(null);
    	}

    	public void onProviderEnabled(String provider) { }
    	public void onStatusChanged(String provider, int status, Bundle extras) { }
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
            		Settings.setLocation(location.getLatitude(), location.getLongitude());
            	}
            	
                return new LocationStuff(locationManager, provider);
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	return null;
        }

        protected void onPostExecute(LocationStuff stuff) {
        	if (stuff != null) {
        		stuff.locationManager.requestLocationUpdates(stuff.provider, 100000, 10, locationListener); // 100 sek, 10 m
        	}
        }
    }

}