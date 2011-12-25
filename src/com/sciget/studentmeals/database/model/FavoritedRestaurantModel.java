package com.sciget.studentmeals.database.model;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.database.data.FavoritedRestaurantData;

import android.content.Context;

public class FavoritedRestaurantModel extends Model {
    public FavoritedRestaurantModel(Context context) {
        super(context);
    }
    
    public boolean isFavorited(int restaurantId) {
        int val = getValue("SELECT id FROM " + FavoritedRestaurantData.NAME + " WHERE restaurantId = " + restaurantId + " AND userId = " + MyPerferences.getInstance().getUserId());
        return val != -1;
    }
    
    public int addFavorite(final int restaurantId) {
        new Thread() {
            public void run() {
                StudentMealsService meals = new StudentMealsService();
                meals.setFavoritedRestaurant(MyPerferences.getInstance().getUserKey(), restaurantId);
            }
        }.start();
        
        FavoritedRestaurantData data = new FavoritedRestaurantData(MyPerferences.getInstance().getUserId(), restaurantId, time());
        return data.add(this);
    }
    
    public void removeFavorite(final int restaurantId) {
        new Thread() {
            public void run() {
                StudentMealsService meals = new StudentMealsService();
                meals.removeFavoritedRestaurant(MyPerferences.getInstance().getUserKey(), restaurantId);
            }
        }.start();
        
        update("DELETE FROM " + FavoritedRestaurantData.NAME + " WHERE restaurantId = " + restaurantId + " AND userId = " + MyPerferences.getInstance().getUserId());
    }
}
