package com.sciget.studentmeals.database.model;

import java.util.ArrayList;
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
    
    public Vector<RestaurantMenuData> getAllMenus() {
        Vector<RestaurantMenuData> menus = new Vector<RestaurantMenuData>();
        Cursor cursor = rawQuery("SELECT restaurantId, date, menu FROM " + RestaurantMenuData.NAME);
        while (cursor.moveToNext()) {
            RestaurantMenuData menu = new RestaurantMenuData(cursor.getInt(0), toDate(cursor.getString(1)), cursor.getString(2));
            menus.add(menu);
        }
        return menus;
    }
    
    public void add() {
        
    }

    public ArrayList<RestaurantMenuData> getMenus(int restaurantId) {
        ArrayList<RestaurantMenuData> menus = new ArrayList<RestaurantMenuData>();
        Cursor cursor = rawQuery("SELECT restaurantId, date, menu FROM " + RestaurantMenuData.NAME + " WHERE restaurantId = " + restaurantId);
        while (cursor.moveToNext()) {
            RestaurantMenuData menu = new RestaurantMenuData(cursor.getInt(0), toDate(cursor.getString(1)), cursor.getString(2));
            menus.add(menu);
        }
        cursor.close();
        return menus;
    }

    public boolean hasMenus(int restaurantId) {
        int value = getValue("SELECT id FROM " + RestaurantMenuData.NAME + " WHERE restaurantId = " + restaurantId + " LIMIT 1");
        return value != -1;
    }
}
