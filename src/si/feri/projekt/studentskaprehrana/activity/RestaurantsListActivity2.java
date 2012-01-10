package si.feri.projekt.studentskaprehrana.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.Settings;
import si.feri.projekt.studentskaprehrana.db.ProvidersArrayAdapter;

import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.activity.RestaurantDetailsActivity;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.model.FavoritedRestaurantModel;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RestaurantsListActivity2 extends ListActivity implements OnItemClickListener {
    public class Type {
        public static final String KEY = "type";
        
        public static final int ALL = 1;
        public static final int NEAR = 2;
        public static final int FAVORITES = 3;
    }
    
    private MainApplication application;
    private ProvidersArrayAdapter restaurantsAdapter;
    private int type;
    private String query;
    
    private ListView restaurantsListView;
    private EditText searchEditText;
    private ImageButton allImageButton;
    private ImageButton nearImageButton;
    private ImageButton favoritesImageButton;
    private LinearLayout typesLinearLayout;
    private Comparator<? super RestaurantData> nearComparator;
    private boolean goToTop;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.providers_list_activity);
        application = (MainApplication) getApplication();
        setViews();
        setListeners();
        int type = 0;
        if (savedInstanceState != null) {
            type = savedInstanceState.getInt(Type.KEY);
        }
        setRestaurantsAdapter(type);
        application.setRestaurantsListActivity(this);
    }

    private void setListeners() {
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void afterTextChanged(Editable editable) {
                query = editable.toString();
                setRestaurantsAdapter(type);
            }
        });
        
        getListView().setOnItemClickListener(this);
    }
    
    private ArrayList<RestaurantData> search(ArrayList<RestaurantData> restaurants) {
        if (query == null || query.length() == 0) {
            return restaurants;
        } else {
            query = query.toLowerCase();
            ArrayList<RestaurantData> list = new ArrayList<RestaurantData>();
            for (RestaurantData restaurant : restaurants) {
                if (restaurant.contains(query)) {
                    list.add(restaurant);
                }
            }
            return list;
        }
    }

    private void setViews() {
        restaurantsListView = (ListView) findViewById(android.R.id.list);
        searchEditText = (EditText) findViewById(R.id.editTextSearch);
        allImageButton = (ImageButton) findViewById(R.id.all2);
        nearImageButton = (ImageButton) findViewById(R.id.near);
        favoritesImageButton = (ImageButton) findViewById(R.id.fav);
        typesLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutTypes);
        
        allImageButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setRestaurantsAdapter(Type.ALL);
            }
        });
        
        nearImageButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setRestaurantsAdapter(Type.NEAR);
            }
        });
        
        favoritesImageButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setRestaurantsAdapter(Type.FAVORITES);
            }
        });
        
        nearComparator = new NearComparator();
    }
    
    public void update() {
        runOnUiThread(new Runnable() {
            
            @Override
            public void run() {
                setRestaurantsAdapter(type);
            }
        });
    }

    private void setRestaurantsAdapter(int type) {
        if (type == 0) {
            type = Type.ALL;
        }
        
        if (type != this.type) {
            goToTop = true;
        }
        
        this.type = type;
        
        ArrayList<RestaurantData> restaurants = application.getCompleteRestaurantsList();
        ArrayList<RestaurantData> list = null;
        if (type == Type.ALL) {
            list = search(restaurants);
        } else if (type == Type.NEAR) {
            MyPerferences.Location location = MyPerferences.getInstance().getLocation();
            
            if (location == null) {
                list = search(restaurants);
            } else {
                list = new ArrayList<RestaurantData>();
                for (RestaurantData restaurant : restaurants) {
                    restaurant.setDistance(location);
                    if (restaurant.isNear(location.latitude, location.longitude)) {
                        list.add(restaurant);
                    }
                }
                
                list = search(list);
                
                Collections.sort(list, nearComparator);
            }
        } else if (type == Type.FAVORITES) {
            FavoritedRestaurantModel favoritesModel = new FavoritedRestaurantModel(this);
            Vector<Integer> favoritesList = favoritesModel.getFavorites(MyPerferences.getInstance().getUserId());
            favoritesModel.close();
            
            list = new ArrayList<RestaurantData>();
            if (!favoritesList.isEmpty()) {
                for (RestaurantData restaurant : restaurants) {
                    if (favoritesList.contains(restaurant.getId())) {
                        list.add(restaurant);
                    }
                }
            }
            
            list = search(list);
        } else {
            throw new RuntimeException("Unsported value: type = " + type);
        }
        
        updateRestaurantAdapter(list);
    }
    
    private void updateRestaurantAdapter(ArrayList<RestaurantData> list) {
        if (restaurantsAdapter == null) {
            restaurantsAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout2, list, this);
            setListAdapter(restaurantsAdapter);
        } else {
            ArrayList<RestaurantData> currentList = restaurantsAdapter.getList();
            currentList.clear();
            currentList.addAll(list);
            restaurantsAdapter.notifyDataSetChanged();
        }
        
        if (goToTop && list.size() > 0) {
            restaurantsListView.setSelection(0);
        }
        
        goToTop = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent details = new Intent(this, RestaurantDetailsActivity.class);
        details.putExtra(RestaurantDetailsActivity.RESTAURANT_ID_KEY, restaurantsAdapter.getItem(position).id);
        startActivity(details);
    }
    
    private void register() {
        MyPerferences.getInstance().registerRestaurantsListActivity(this);
    }
    
    private void unregister() {
        MyPerferences.getInstance().unregisterRestaurantsListActivity();
    }

    public void sortNearList(MyPerferences.Location location) {
        if (type == Type.FAVORITES) {
            setRestaurantsAdapter(Type.FAVORITES);
            //nearComparator = new NearComparator(location);
            //restaurantsAdapter.sort(nearComparator);
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

    public int getType() {
        return type;
    }
    
    @Override
    public boolean onSearchRequested() {
        searchEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        return false;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuForActivity.onOptionsItemSelected(item, this);
    }
    
    public boolean onCreateOptionsMenu(Menu mMenu) {
        return MenuForActivity.onCreateOptionsMenu(mMenu, this);
    }

}

class NearComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        RestaurantData p1 = (RestaurantData) o1;
        RestaurantData p2 = (RestaurantData) o2;
        Float r1 = p1.getDistance();
        Float r2 = p2.getDistance();
        return r1.compareTo(r2);
    }
}
