package si.feri.projekt.studentskaprehrana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.google.android.maps.MyLocationOverlay;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.FavoritedRestaurantModel;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.RestaurantModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.db.FavoritesDB;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;
import si.feri.projekt.studentskaprehrana.db.ProvidersDB;
import si.feri.projekt.studentskaprehrana.db.UsersDB;
import si.feri.projekt.studentskaprehrana.trash.Testing;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import si.feri.projekt.studentskaprehrana.activity.MyListActivity;

@Deprecated
public class ListApplication extends Application {
	
	public ArrayList<RestaurantData> list = new ArrayList<RestaurantData>();
	public ArrayList<RestaurantData> completeList = new ArrayList<RestaurantData>();
	public ProvidersArrayAdapter providersAdapter;

	public RestaurantModel providersDB;
	public RestaurantMenuModel menusDB;
	public FavoritedRestaurantModel favoritesDB;
	public StudentMealUserModel usersDB;
	
	private int currentProvider;
	
	//ProgressDialog dialogWait; // za nalaganje podatkov
	
	public void onCreate() {
		super.onCreate();
		loadData();
	}
	
	public void loadData() {
		providersDB = new RestaurantModel(this);
		menusDB = new RestaurantMenuModel(this);
		favoritesDB = new FavoritedRestaurantModel(this);
		usersDB = new StudentMealUserModel(this);
		
		//fillProvidersFromDB();
		providersAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout2, list);
	}
	
	public void newProvidersList(ArrayList<RestaurantData> list) {
		this.list = list;
		providersAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout2, list);
	}

	/*private void fillProvidersFromDB() {
		providersDB.open();
		Vector<RestaurantData> providers = providersDB.getAllRestaurants();
		list.clear();
		completeList.clear();
		for (RestaurantData provider : providers) {
			if (provider.getName() != null && !provider.getName().equals("")) {
				list.add(provider);
				completeList.add(provider);
			}
		}
		providersDB.close();
	}*/
	
	/*public void insertTest() {
		Testing.insertProviders(providersDB);
		Testing.insertMenus(new MenusDB(this));
	}*/
	
	public void updateProvidersAndMenus() {
	    //Testing.insertProviders(providersDB);
	}
	
	public void setCurrentProvider(int provider) {
		currentProvider = provider;
	}
	
	public int getCurrentProvider() {
		return currentProvider;
	}
	
	/*public RestaurantData getProviderById(int id) {
		return Settings.arrayListProviders.get(id);
	}*/
	
    public int getSubsidiesNumber() {
        StudentMealUserData user = new StudentMealUserModel(this).getUserAll();
        return user.remainingSubsidies;
    }

    public String getLastProvider() {
        return new StudentMealUserModel(this).getLastVisitedProvider();
    }
}
