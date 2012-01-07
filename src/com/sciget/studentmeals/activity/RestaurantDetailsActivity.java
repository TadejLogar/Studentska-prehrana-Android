package com.sciget.studentmeals.activity;

import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.CommentActivity;
import si.feri.projekt.studentskaprehrana.activity.MainActivity;
import si.feri.projekt.studentskaprehrana.activity.MenuForActivity;
import si.feri.projekt.studentskaprehrana.activity.MenuListActivity;
import si.feri.projekt.studentskaprehrana.activity.ProviderMapActivity;

import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.camera.CameraActivity;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.task.RestaurantMapImageTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestaurantDetailsActivity extends MainActivity {
    public static final String RESTAURANT_ID_KEY = "restaurantId";
    
    private MainApplication application;
    private RestaurantData provider;
    
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView feeTextView;
    private TextView timeTextView;
    private ImageButton favoriteImageButton;
    private Button menuButton;
    private TextView commentsTextView;
    private LinearLayout imagesLinearLayout;
    private Button addButton;
    private TextView propertiesTextView;
    private ImageView mapImageView;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider);
        application = (MainApplication) getApplication();
        setViews();
        Bundle extras = getIntent().getExtras();
        setRestaurant(extras.getInt(RESTAURANT_ID_KEY));
        setData();
    }

    private void setData() {
        nameTextView.setText(provider.getName());
        addressTextView.setText(provider.getFullAddress());
        String providerStr = provider.getFullPhone();
        if (providerStr == null) {
            phoneTextView.setText(providerStr);
        } else {
            phoneTextView.setVisibility(View.GONE); 
        }
        feeTextView.setText("Doplačilo: " + provider.getEuroFee());
        timeTextView.setText(provider.getOpenTimes());
        setMapImage();
    }

    private void setMapImage() {
        RestaurantMapImageTask task = new RestaurantMapImageTask(application, provider.id, mapImageView, provider.mapImageSha1, provider.address);
        task.execute();
    }

    private void setRestaurant(int restaurantId) {
        provider = application.getRestaurantById(restaurantId);
    }

    private void setViews() {
        nameTextView = (TextView) findViewById(R.id.textViewProviderName);
        addressTextView = (TextView) findViewById(R.id.textViewAddress);
        phoneTextView = (TextView) findViewById(R.id.textViewPhone);
        feeTextView = (TextView) findViewById(R.id.textViewFee);
        timeTextView = (TextView) findViewById(R.id.textViewOpenTime);
        favoriteImageButton = (ImageButton) findViewById(R.id.imageButtonFav);
        menuButton = (Button) findViewById(R.id.buttonMenu);
        commentsTextView = (TextView) findViewById(R.id.textViewComments);
        addButton = (Button) findViewById(R.id.buttonAdd);
        imagesLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutImages);
        propertiesTextView = (TextView) findViewById(R.id.textViewProperties);
        mapImageView = (ImageView) findViewById(R.id.mapImage);
        
        registerForContextMenu(addButton);
        
        favoriteImageButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                setFavorite(provider.id);
            }
        });
        
        addButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View view) {
                openContextMenu(view);
            }
        });
        
        menuButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantMenuListActivity.class);
                intent.putExtra(RESTAURANT_ID_KEY, provider.getId());
                startActivity(intent);
            }
        });
        
        mapImageView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, RestaurantMapActivity.class);
                intent.putExtra(RestaurantMapActivity.LATITUDE_KEY, provider.locationLatitude);
                intent.putExtra(RestaurantMapActivity.LONGITUDE_KEY, provider.locationLongitude);
                startActivity(intent);
            }
        });
    }

    public static String getFileDownloadUrl() {
        return "http://" + MyPerferences.getInstance().getServer() + ":8080/StudentMealsWebService/restaurantFiles?hash=";
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Dodaj");
        menu.add(0, 1, 0, "Dodaj komentar");
        menu.add(0, 2, 0, "Dodaj zvočni posnetek");
        menu.add(0, 3, 0, "Dodaj sliko");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            Intent i = new Intent(this, CommentActivity.class);
            i.putExtra(RESTAURANT_ID_KEY, provider.getId());
            startActivity(i);
        } else if (item.getItemId() == 2) {
            
        } else if (item.getItemId() == 3) {
            Intent i = new Intent(this, CameraActivity.class);
            i.putExtra(RESTAURANT_ID_KEY, provider.getId());
            startActivity(i);
        } else {
            return false;
        }
        return true;
    }
    
    private void setFavorite(int restaurantId) {
        boolean favorited = application.isRestaurantFavorited(restaurantId);
        if (favorited) {
            favoriteImageButton.setBackgroundResource(R.drawable.unstarred48);
        } else {
            favoriteImageButton.setBackgroundResource(R.drawable.starred48);
        }
        application.setRestaurantFavorited(restaurantId, !favorited);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuForActivity.onOptionsItemSelected(item, this);
    }
    
    public boolean onCreateOptionsMenu(Menu mMenu) {
        return MenuForActivity.onCreateOptionsMenu(mMenu, this);
    }
}
