package com.sciget.studentmeals.database.model;

import com.sciget.studentmeals.Perferences;
import com.sciget.studentmeals.database.data.FavoritedRestaurantData;

import android.content.Context;

public class FavoritedRestaurantModel extends Model {
    public FavoritedRestaurantModel(Context context) {
        super(context);
    }
    
    public int addFavorite(int restaurantId) throws Exception {
        FavoritedRestaurantData data = new FavoritedRestaurantData(0, Perferences.getUserId(), restaurantId, time());
        return data.add(this);
    }
    
    public void removeFavorite(int restaurantId) throws Exception {
        update("DELETE FROM " + FavoritedRestaurantData.NAME + " WHERE restaurantId = " + restaurantId + " AND userId = " + Perferences.getUserId());
    }
}
