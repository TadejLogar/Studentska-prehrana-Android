package com.sciget.studentmeals.service;

import java.util.Vector;

import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.RestaurantModel;

import android.test.AndroidTestCase;

public class UpdateDataTaskTest extends AndroidTestCase {

    public void setUp() throws Exception {
    }

    public void testUpdateRestaurants() {
        RestaurantModel restaurantModel1 = new RestaurantModel(getContext());
        restaurantModel1.create();
        restaurantModel1.close();
        
        UpdateDataTask updateDataTask = new UpdateDataTask(getContext());
        updateDataTask.updateRestaurants();
        updateDataTask.closeModel();
        
        RestaurantModel restaurantModel2 = new RestaurantModel(getContext());
        Vector<RestaurantData> list = restaurantModel2.getAllRestaurants();
        
        assertTrue(list.size() > 5);
        assertTrue(list.get(0).id != list.get(1).id);
        assertTrue(list.get(0).hash.length() > 0);
    }
    
    public void testAll() {
        UpdateDataTask updateDataTask = new UpdateDataTask(getContext());
        updateDataTask.all();
        
        RestaurantModel restaurantModel = new RestaurantModel(getContext());
        Vector<RestaurantData> list = restaurantModel.getAllRestaurants();
        assertTrue(list.size() > 20);
        
        RestaurantMenuModel restaurantMenuModel = new RestaurantMenuModel(getContext());
        int n = 0;
        for (int i = 0; i < 20; i++) {
            Vector<RestaurantMenuData> menusList = restaurantMenuModel.getMenusByHash(list.get(i).hash);
            if (menusList.size() > 0) {
                System.out.println(list.get(i).name);
                n++;
            }
        }
        assertTrue(n > 0);
    }

}
