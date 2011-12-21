package com.sciget.studentmeals.service;

import java.util.Vector;

import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.RestaurantData;
import com.sciget.studentmeals.database.model.RestaurantModel;

import android.content.Context;

public class UpdateDataTask {
    private Context context;
    private StudentMealsService meals;
    private RestaurantModel restaurantModel;
    
    public UpdateDataTask(Context context) {
        this.context = context;
        this.meals = new StudentMealsService();
        this.restaurantModel = new RestaurantModel(context);
    }
    
    public void all() {
        updateRestaurants();
        updateDailyMenus();
        updatePermanentMenus();
        restaurantModel.close();
    }
    
    public void updateRestaurants() {
        Vector<RestaurantData> list = meals.restaurants();
        restaurantModel.addRestaurants(list);
    }
    
    public void updateDailyMenus() {
        Vector<MenuData> list = meals.allRestaurantsDailyMenus();
        restaurantModel.addMenus(list);
    }
    
    public void updatePermanentMenus() {
        Vector<MenuData> list = meals.allRestaurantsPermanentMenus();
        restaurantModel.addMenus(list);
    }
}
