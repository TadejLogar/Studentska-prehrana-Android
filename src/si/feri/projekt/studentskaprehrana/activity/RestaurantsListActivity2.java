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
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

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
    
    private EditText searchEditText;
    private ImageButton allImageButton;
    private ImageButton nearImageButton;
    private ImageButton favoritesImageButton;
    private Comparator<? super RestaurantData> nearComparator;
    
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
    }

    private void setListeners() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                search(query);
            }
        });
        
        getListView().setOnItemClickListener(this);
    }
    
    private void search(String query) {
        ArrayList<RestaurantData> restaurants = application.getCompleteRestaurantsList();
        if (query.length() == 0) {
            updateRestaurantAdapter(restaurants);
        } else {
            query = query.toLowerCase();
            ArrayList<RestaurantData> list = new ArrayList<RestaurantData>();
            for (RestaurantData restaurant : restaurants) {
                if (restaurant.contains(query)) {
                    list.add(restaurant);
                }
            }
            updateRestaurantAdapter(list);
        }
    }

    private void setViews() {
        searchEditText = (EditText) findViewById(R.id.editTextSearch);
        allImageButton = (ImageButton) findViewById(R.id.all);
        nearImageButton = (ImageButton) findViewById(R.id.near);
        favoritesImageButton = (ImageButton) findViewById(R.id.fav);
        
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
        
        nearComparator = new NearComparator(MyPerferences.getInstance().getLocation());
    }

    private void setRestaurantsAdapter(int type) {
        if (type == 0) {
            type = Type.ALL;
        }
        
        this.type = type;
        
        ArrayList<RestaurantData> restaurants = application.getCompleteRestaurantsList();
        ArrayList<RestaurantData> list = null;
        if (type == Type.ALL) {
            list = restaurants;
        } else if (type == Type.NEAR) {
            MyPerferences.Location location = MyPerferences.getInstance().getLocation();
            
            if (location == null) {
                list = restaurants;
            } else {
                list = new ArrayList<RestaurantData>();
                for (RestaurantData restaurant : restaurants) {
                    restaurant.setDistance(location);
                    if (restaurant.isNear(location.latitude, location.longitude)) {
                        list.add(restaurant);
                    }
                }
                
                Collections.sort(list, nearComparator);
            }
        } else if (type == Type.FAVORITES) {
            StudentMealUserModel userModel = new StudentMealUserModel(this);
            Vector<Integer> favoritesList = userModel.getFavorites(MyPerferences.getInstance().getUserId());
            userModel.close();
            
            if (favoritesList.isEmpty()) {
                list = new ArrayList<RestaurantData>();
            } else {
                list = new ArrayList<RestaurantData>();
                for (RestaurantData restaurant : restaurants) {
                    if (list.contains(restaurant.getId())) {
                        list.add(restaurant);
                    }
                }
            }
        } else {
            throw new RuntimeException("Unsported value: type = " + type);
        }
        
        updateRestaurantAdapter(list);
        setListAdapter(restaurantsAdapter);
    }
    
    private void updateRestaurantAdapter(ArrayList<RestaurantData> list) {
        restaurantsAdapter = new ProvidersArrayAdapter(this, R.layout.provider_layout2, list);
        restaurantsAdapter.notifyDataSetChanged();
        setListAdapter(restaurantsAdapter);
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
    
}

class NearComparator implements Comparator {
    private double latitude;
    private double longitude;
    
    public NearComparator(MyPerferences.Location location) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }
    
    public int compare(Object o1, Object o2) {
        RestaurantData p1 = (RestaurantData) o1;
        RestaurantData p2 = (RestaurantData) o2;
        //Double r1 = p1.howNear(latitude, longitude);
        //Double r2 = p2.howNear(latitude, longitude);
        Float r1 = p1.getDistance();
        Float r2 = p2.getDistance();
        return r1.compareTo(r2);
    }
}
