package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;

import si.feri.projekt.studentskaprehrana.ListApplication;
import si.feri.projekt.studentskaprehrana.Provider;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.Settings;
import si.feri.projekt.studentskaprehrana.R.id;
import si.feri.projekt.studentskaprehrana.R.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RestaurantsListActivity extends MyListActivity {
	ListApplication app;
	EditText etSearch;
	
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			app = (ListApplication) getApplication();
			setListAdapter(app.providersAdapter);
			setContentView(R.layout.providers_list_activity);
			setListeners(app.completeList, app.list, app.providersAdapter);
			getListView().setOnItemClickListener(this);
			Settings.arrayListProviders = app.list;
	}

}
