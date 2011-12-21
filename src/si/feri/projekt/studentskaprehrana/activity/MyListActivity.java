package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;

import com.sciget.studentmeals.database.data.RestaurantData;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;
import android.R.integer;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

public class MyListActivity extends MainListActivity implements OnItemClickListener {
	public static final int ALL_LIST_ACTIVITY = 1;
	public static final int NEAR_LIST_ACTIVITY = 2;
	public static final int FAV_LIST_ACTIVITY = 3;
	
	Intent allIntent;
	Intent nearIntent;
	Intent favoriteIntent;
	
	protected EditText etSearch;
	protected ListApplication app;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ListApplication) getApplication();
		//etSearch = (EditText) findViewById(R.id.editTextSearch);
	}
	
	public void allClick(View v) {
		finish();
		etSearch.setText("");
		allIntent = new Intent(this, RestaurantsListActivity.class);
		startActivity(allIntent);
	}
	
	public void nearClick(View v) {
		finish();
		nearIntent = new Intent(this, ProvidersNearMyLocationActivity.class);
		startActivity(nearIntent);
	}
	
	public void favoriteClick(View v) {
		finish();
		favoriteIntent = new Intent(this, FavProvidersActivity.class);
		startActivity(favoriteIntent);
	}
	
	public void searchClick(ArrayList<RestaurantData> completeList, ArrayList<RestaurantData> currentList, String query, ProvidersArrayAdapter adapter) {
		currentList.clear();
		
		for (RestaurantData provider : completeList) {
			if (provider.contains(query)) {
				currentList.add(provider);
			}
		}

		adapter.notifyDataSetChanged();
		
		//app.providersAdapter.notifyDataSetChanged();
		
		int a = 0;
		a = a + 5;
	}
	
	public void setListeners(final ArrayList<RestaurantData> completeList, final ArrayList<RestaurantData> currentList, final ProvidersArrayAdapter adapter) {
		etSearch = (EditText) findViewById(R.id.editTextSearch);
		
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				String query = arg0.toString();
				searchClick(completeList, currentList, query, adapter);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
		});
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//Toast.makeText(this, "Pritisnil si " + app.list.get(position).getName(), Toast.LENGTH_LONG).show();
		
		app.setCurrentProvider(position);
		Intent detailsActivity = new Intent(this, DetailsActivity.class);
		startActivityForResult(detailsActivity, 1);
	}
}
