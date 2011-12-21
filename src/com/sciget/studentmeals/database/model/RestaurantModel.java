package com.sciget.studentmeals.database.model;

import java.util.Vector;

import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.database.Database;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;

import android.content.Context;
import android.database.Cursor;

public class RestaurantModel extends Model {

    public RestaurantModel(Context context) {
        super(context);
    }

    public void addRestaurants(Vector<com.sciget.studentmeals.client.service.data.RestaurantData> list) {
        for (com.sciget.studentmeals.client.service.data.RestaurantData restaurant : list) {
            addRestaurant(new RestaurantData(0, restaurant.hash, restaurant.name, restaurant.address, restaurant.post, restaurant.country, restaurant.price, restaurant.phone, restaurant.openWorkdayFrom, restaurant.openWorkdayTo, restaurant.openSaturdayFrom, restaurant.openSaturdayTo, restaurant.openSundayFrom, restaurant.openSundayTo, restaurant.locationLatitude, restaurant.locationLongitude, restaurant.features.toString(), restaurant.message));
        }
    }
    
    public int addRestaurant(RestaurantData restaurant) {
        return restaurant.add(this);
    }

    public Vector<RestaurantData> getAllRestaurants() {
        Vector<RestaurantData> restaurants = new Vector<RestaurantData>();
        Cursor cursor = rawQuery("SELECT id, hash, name, address, post, country, price, phone, openWorkdayFrom, openWorkdayTo, openSaturdayFrom, openSaturdayTo, openSundayFrom, openSundayTo, locationLatitude, locationLongitude, features, message FROM " + RestaurantData.NAME);
        while (cursor.moveToNext()) {
            Cursor resultSet = cursor;
            RestaurantData restaurant = new RestaurantData(resultSet.getInt(0), resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), resultSet.getString(7), toTime(resultSet.getString(8)), toTime(resultSet.getString(9)), toTime(resultSet.getString(10)), toTime(resultSet.getString(11)), toTime(resultSet.getString(12)), toTime(resultSet.getString(13)), resultSet.getDouble(14), resultSet.getDouble(15), resultSet.getString(16), resultSet.getString(17));
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public void addMenus(Vector<com.sciget.studentmeals.client.service.data.MenuData> list) {
        for (com.sciget.studentmeals.client.service.data.MenuData menu : list) {
            addMenu(new RestaurantMenuData(menu.restaurantId, menu.date, menu.menu));
        }
    }
    
    public int addMenu(RestaurantMenuData menu) {
        return menu.add(this);
    }
}
