package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;
import java.util.Comparator;

import com.sciget.studentmeals.database.data.RestaurantData;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.Settings;
import si.feri.projekt.studentskaprehrana.R.layout;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ProvidersNearMyLocationActivity extends MyListActivity {
	ListApplication app;
	
	public ProvidersArrayAdapter nearProvidersAdapter;
	public ArrayList<RestaurantData> nearList = new ArrayList<RestaurantData>();
	public ArrayList<RestaurantData> completeNearList = new ArrayList<RestaurantData>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		setNearProvidersAdapter();
		setListAdapter(nearProvidersAdapter);
		setContentView(R.layout.providers_list_activity);
		setListeners(completeNearList, nearList, nearProvidersAdapter);
		getListView().setOnItemClickListener(this);
		Settings.arrayListProviders = nearList;
	}

	private void setNearProvidersAdapter() {
		double lat = Settings.locationLat;
		double lon = Settings.locationLon;
		for (RestaurantData provider : app.completeList) {
			if (provider.isNear(lat, lon)) {
				nearList.add(provider);
				completeNearList.add(provider);
			}
		}
		nearProvidersAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout2, nearList);
		nearProvidersAdapter.sort(new Comparator() {
			double lat = Settings.locationLat;
			double lon = Settings.locationLon;
			
            public int compare(Object o1, Object o2) {
                RestaurantData p1 = (RestaurantData) o1;
                RestaurantData p2 = (RestaurantData) o2;
            	Double r1 = p1.howNear(lat, lon);
            	Double r2 = p2.howNear(lat, lon);
                return r1.compareTo(r2);
            }
        });
	}

}
