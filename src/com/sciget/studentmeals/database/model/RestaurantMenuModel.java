package com.sciget.studentmeals.database.model;

import java.util.Vector;

import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;

import android.content.Context;
import android.database.Cursor;

public class RestaurantMenuModel extends Model {
    public RestaurantMenuModel(Context context) {
        super(context);
    }

    public Vector<RestaurantMenuData> getMenusByHash(String hash) {
        int restaurantId = getValue("SELECT id FROM " + RestaurantData.NAME + " WHERE hash = ?", hash);
        Vector<RestaurantMenuData> menus = new Vector<RestaurantMenuData>();
        Cursor cursor = rawQuery("SELECT restaurantId, date, menu FROM " + RestaurantMenuData.NAME + " WHERE restaurantId = " + restaurantId);
        while (cursor.moveToNext()) {
            RestaurantMenuData menu = new RestaurantMenuData(cursor.getInt(0), toDate(cursor.getString(1)), cursor.getString(2));
            menus.add(menu);
        }
        return menus;
    }
    
    public void add() {
        
    }
}
