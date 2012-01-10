package com.sciget.studentmeals.database.model;

import java.util.Vector;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.database.data.Data;
import com.sciget.studentmeals.database.data.FavoritedRestaurantData;
import com.sciget.studentmeals.database.data.StudentMealUserData;

import android.content.Context;
import android.database.Cursor;

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
    
    public Vector<FavoritedRestaurantData> getAllFavorites() {
        Cursor cursor = rawQuery("SELECT id, userId, restaurantId, time FROM " + FavoritedRestaurantData.NAME);
        Vector<FavoritedRestaurantData> list = new Vector<FavoritedRestaurantData>();
        while (cursor.moveToNext()) {
            FavoritedRestaurantData data = new FavoritedRestaurantData(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), Data.toTimestamp(cursor.getString(3)));
            list.add(data);
        }
        cursor.close();
        return list;
    }
    
    public Vector<Integer> getFavorites(int userId) {
        Cursor cursor = rawQuery("SELECT restaurantId FROM " + FavoritedRestaurantData.NAME + " WHERE userId = -1 OR userId = " + userId);
        Vector<Integer> list = new Vector<Integer>();
        while (cursor.moveToNext()) {
            list.add(cursor.getInt(0));
        }
        cursor.close();
        return list;
    }
}
