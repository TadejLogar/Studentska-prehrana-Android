package com.sciget.studentmeals.activity;

import java.util.ArrayList;
import java.util.Vector;

import com.sciget.studentmeals.MainApplication;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;

import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.db.MenusArrayAdapter;
import android.app.ListActivity;
import android.os.Bundle;

public class RestaurantMenuListActivity extends ListActivity {
    private MainApplication application;
    private int restaurantId;
    private MenusArrayAdapter menusAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MainApplication) getApplication();
        setViews();
        setData();
    }

    private void setData() {
        Bundle extras = getIntent().getExtras(); 
        restaurantId = extras.getInt(RestaurantDetailsActivity.RESTAURANT_ID_KEY);

        RestaurantMenuModel menuModel = new RestaurantMenuModel(this);
        ArrayList<RestaurantMenuData> menuList = menuModel.getMenus(restaurantId);
        menuModel.close();
        
        menusAdapter = new MenusArrayAdapter(this, R.layout.menu_layout, menuList);
        setListAdapter(menusAdapter);
    }

    private void setViews() {
        setContentView(R.layout.menus_list_activity);
    }
}
