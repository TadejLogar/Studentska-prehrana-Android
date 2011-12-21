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

public class FavProvidersActivity extends MyListActivity {
	ListApplication app;
	
	public ProvidersArrayAdapter favProvidersAdapter;
	public ArrayList<RestaurantData> favList = new ArrayList<RestaurantData>();
	public ArrayList<RestaurantData> completeFavList = new ArrayList<RestaurantData>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		setFavProvidersAdapter();
		setListAdapter(favProvidersAdapter);
		setContentView(R.layout.providers_list_activity);
		setListeners(completeFavList, favList, favProvidersAdapter);
		getListView().setOnItemClickListener(this);
		Settings.arrayListProviders = favList;
	}

	private void setFavProvidersAdapter() {
		for (RestaurantData provider : app.completeList) {
			if (provider.getFavorited()) {
				favList.add(provider);
				completeFavList.add(provider);
			}
		}
		favProvidersAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout, favList);
	}

}
