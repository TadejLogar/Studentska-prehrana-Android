package com.sciget.studentmeals.service;

import java.util.ArrayList;
import java.util.Vector;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.model.RestaurantMenuModel;
import com.sciget.studentmeals.database.model.RestaurantModel;

import android.test.AndroidTestCase;

public class UpdateDataTaskTest extends AndroidTestCase {

    public void setUp() throws Exception {
        new MyPerferences(getContext());
    }

    public void testUpdateRestaurants() {
        RestaurantModel restaurantModel1 = new RestaurantModel(getContext());
        restaurantModel1.create();
        restaurantModel1.close();
        
        UpdateDataTask updateDataTask = new UpdateDataTask(getContext());
        updateDataTask.updateRestaurants();
        updateDataTask.closeModel();
        
        RestaurantModel restaurantModel2 = new RestaurantModel(getContext());
        ArrayList<RestaurantData> list = restaurantModel2.getAllRestaurants();
        
        assertTrue(list.size() > 5);
        assertTrue(list.get(0).id != list.get(1).id);
        assertTrue(list.get(0).hash.length() > 0);
    }
    
    public void testAll() {
        UpdateDataTask updateDataTask = new UpdateDataTask(getContext());
        updateDataTask.all();
        
        RestaurantModel restaurantModel = new RestaurantModel(getContext());
        ArrayList<RestaurantData> list = restaurantModel.getAllRestaurants();
        assertTrue(list.size() > 20);
        
        RestaurantMenuModel restaurantMenuModel = new RestaurantMenuModel(getContext());
        
        Vector<RestaurantMenuData> allMenusList = restaurantMenuModel.getAllMenus();
        assertTrue(allMenusList.size() > 0);
        int nAllPermMenus = 0;
        for (int i = 0; i < allMenusList.size(); i++) {
            if (allMenusList.get(i).date == null) {
                nAllPermMenus++;
            }
        }
        assertTrue(nAllPermMenus > 0);
        
        int n = 0;
        int nTempMenus = 0; // število dnevnih menijev (date != null)
        int nPermMenus = 0; // število stalnih menijev (date == null)
        for (int i = 0; i < list.size(); i++) {
            Vector<RestaurantMenuData> menusList = restaurantMenuModel.getMenusByHash(list.get(i).hash);
            if (menusList.size() > 0) {
                n++;
                
                for (int j = 0; j < menusList.size(); j++) {
                    if (menusList.get(j).date == null) {
                        nPermMenus++;
                    } else {
                        nTempMenus++;
                    }
                }
            }
        }
        assertTrue(n > 0);
        assertTrue(nTempMenus > 0);
        assertTrue(nPermMenus > 0);
        restaurantModel.close();
        
        history();
    }
    
    public void testUpdateUserHistory() {
        UpdateDataTask updateDataTask = new UpdateDataTask(getContext());
        updateDataTask.updateUserHistory();
        updateDataTask.closeModel();
        
        history();
    }
    
    private void history() {
        RestaurantModel restaurantModel = new RestaurantModel(getContext());
        Vector<StudentMealHistoryData> list2 = restaurantModel.getHistory();
        assertTrue(list2.size() > 0);
        restaurantModel.close();
    }

}
