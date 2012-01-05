package com.sciget.studentmeals.database.model;

import java.util.Vector;

import com.sciget.studentmeals.client.service.data.HistoryData;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.database.Database;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.util.Log;

public class RestaurantModel extends Model {

    public RestaurantModel(Context context) {
        super(context);
    }

    public void addRestaurants(Vector<com.sciget.studentmeals.client.service.data.RestaurantData> list) {
        addRestaurantsFast(list);
        
        /*int i = 0;
        for (com.sciget.studentmeals.client.service.data.RestaurantData restaurant : list) {
            if (i % 100 == 0) {
                Log.e("A", "ADD " + i);
            }
            i++;
            addRestaurant(new RestaurantData(0, restaurant.hash, restaurant.name, restaurant.address, restaurant.post, restaurant.country, restaurant.price, restaurant.phone, restaurant.openWorkdayFrom, restaurant.openWorkdayTo, restaurant.openSaturdayFrom, restaurant.openSaturdayTo, restaurant.openSundayFrom, restaurant.openSundayTo, restaurant.locationLatitude, restaurant.locationLongitude, restaurant.features.toString(), restaurant.message));
        }*/
    }
    
    public void addRestaurantsFast(Vector<com.sciget.studentmeals.client.service.data.RestaurantData> list) {
        if (list.isEmpty()) return;
        
        new RestaurantData().create(getDatabase());
        
        InsertHelper ih = new InsertHelper(getDatabase(), RestaurantData.NAME);

        final int id = ih.getColumnIndex("id");
        final int hash = ih.getColumnIndex("hash");
        final int name = ih.getColumnIndex("name");
        final int address = ih.getColumnIndex("address");
        final int post = ih.getColumnIndex("post");
        final int country = ih.getColumnIndex("country");
        final int price = ih.getColumnIndex("price");
        final int phone = ih.getColumnIndex("phone");
        final int openWorkdayFrom = ih.getColumnIndex("openWorkdayFrom");
        final int openWorkdayTo = ih.getColumnIndex("openWorkdayTo");
        final int openSaturdayFrom = ih.getColumnIndex("openSaturdayFrom");
        final int openSaturdayTo = ih.getColumnIndex("openSaturdayTo");
        final int openSundayFrom = ih.getColumnIndex("openSundayFrom");
        final int openSundayTo = ih.getColumnIndex("openSundayTo");
        final int locationLatitude = ih.getColumnIndex("locationLatitude");
        final int locationLongitude = ih.getColumnIndex("locationLongitude");
        final int features = ih.getColumnIndex("features");
        final int message = ih.getColumnIndex("message");
        final int imageSha1 = ih.getColumnIndex("imageSha1");
 
        int i = 0;
        getDatabase().beginTransaction();
        for (com.sciget.studentmeals.client.service.data.RestaurantData data : list) {
            ih.prepareForInsert();
            
            RestaurantData restaurant = new RestaurantData(data.id, data.hash, data.name, data.address, data.post, data.country, data.price, data.phone, data.openWorkdayFrom, data.openWorkdayTo, data.openSaturdayFrom, data.openSaturdayTo, data.openSundayFrom, data.openSundayTo, data.locationLatitude, data.locationLongitude, data.features, data.message, data.imageSha1);
            ih.bind(id, restaurant.id);
            ih.bind(hash, restaurant.hash);
            ih.bind(name, restaurant.name);
            ih.bind(address, restaurant.address);
            ih.bind(post, restaurant.post);
            ih.bind(country, restaurant.country);
            ih.bind(price, restaurant.price);
            ih.bind(phone, restaurant.phone);
            ih.bind(openWorkdayFrom, restaurant.getOpenWorkdayFrom());
            ih.bind(openWorkdayTo, restaurant.getOpenWorkdayTo());
            ih.bind(openSaturdayFrom, restaurant.getOpenSaturdayFrom());
            ih.bind(openSaturdayTo, restaurant.getOpenSaturdayTo());
            ih.bind(openSundayFrom, restaurant.getOpenSundayFrom());
            ih.bind(openSundayTo, restaurant.getOpenSundayTo());
            ih.bind(locationLatitude, restaurant.locationLatitude);
            ih.bind(locationLongitude, restaurant.locationLongitude);
            ih.bind(features, restaurant.features);
            ih.bind(message, restaurant.message);
            ih.bind(imageSha1, restaurant.imageSha1);
            
            ih.execute();
            
            if (i % 100 == 0) {
                Log.e("A", i + "");
            }
            i++;
        }
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
    }
    
    public int addRestaurant(RestaurantData restaurant) {
        return restaurant.add(this);
    }

    public Vector<RestaurantData> getAllRestaurants() {
        Vector<RestaurantData> restaurants = new Vector<RestaurantData>();
        Cursor cursor = rawQuery("SELECT id, hash, name, address, post, country, price, phone, openWorkdayFrom, openWorkdayTo, openSaturdayFrom, openSaturdayTo, openSundayFrom, openSundayTo, locationLatitude, locationLongitude, features, message, imageSha1 FROM " + RestaurantData.NAME);
        while (cursor.moveToNext()) {
            Cursor resultSet = cursor;
            RestaurantData restaurant = new RestaurantData(resultSet.getInt(0), resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), resultSet.getString(7), toTime(resultSet.getString(8)), toTime(resultSet.getString(9)), toTime(resultSet.getString(10)), toTime(resultSet.getString(11)), toTime(resultSet.getString(12)), toTime(resultSet.getString(13)), resultSet.getDouble(14), resultSet.getDouble(15), resultSet.getString(16), resultSet.getString(17), resultSet.getString(18));
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public void addMenus(Vector<com.sciget.studentmeals.client.service.data.MenuData> list) {
        addMenusFast(list);
        /*int i = 0;
        for (com.sciget.studentmeals.client.service.data.MenuData menu : list) {
            if (i % 100 == 0) {
                Log.e("A", menu.menu);
            }
            i++;
            addMenu(new RestaurantMenuData(menu.restaurantId, menu.date, menu.menu));
        }*/
    }
    
    public int addMenu(RestaurantMenuData menu) {
        return menu.add(this);
    }
    
    public void addMenusFast(Vector<com.sciget.studentmeals.client.service.data.MenuData> list) {
        if (list.isEmpty()) return;
        
        InsertHelper ih = new InsertHelper(getDatabase(), RestaurantMenuData.NAME);

        final int restaurantIdPosition = ih.getColumnIndex("restaurantId");
        final int datePosition = ih.getColumnIndex("date");
        final int menuPosition = ih.getColumnIndex("menu");
 
        int i = 0;
        getDatabase().beginTransaction();
        for (com.sciget.studentmeals.client.service.data.MenuData menu : list) {
            ih.prepareForInsert();
            
            ih.bind(restaurantIdPosition, menu.restaurantId);
            ih.bind(datePosition, menu.date);
            ih.bind(menuPosition, menu.menu);
            
            ih.execute();
            
            if (i % 1000 == 0) {
                Log.e("A", menu.menu);
            }
            i++;
        }
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
    }

    public void create() {
        new RestaurantData().create(getDatabase());
    }

    public void addUserHistory(Vector<HistoryData> list) {
        //if (list.isEmpty()) return;
        
        new StudentMealHistoryData().create(getDatabase());
        
        InsertHelper ih = new InsertHelper(getDatabase(), StudentMealHistoryData.NAME);

        final int userId = ih.getColumnIndex("userId");
        final int time = ih.getColumnIndex("time");
        final int provider = ih.getColumnIndex("provider");
        final int company = ih.getColumnIndex("company");
        final int fee = ih.getColumnIndex("fee");
        final int fullPrice = ih.getColumnIndex("fullPrice");
        final int note = ih.getColumnIndex("note");

        getDatabase().beginTransaction();
        for (com.sciget.studentmeals.client.service.data.HistoryData history : list) {
            ih.prepareForInsert();
            
            ih.bind(userId, history.userId);
            ih.bind(time, history.time);
            ih.bind(provider, history.provider);
            ih.bind(company, history.company);
            ih.bind(fee, history.fee);
            ih.bind(fullPrice, history.fullPrice);
            ih.bind(note, history.note);
            
            ih.execute();
        }
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
    }
    
    public Vector<StudentMealHistoryData> getHistory() {
        boolean ok = false;
        String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + StudentMealHistoryData.NAME + "'";
        Cursor cursor0 = rawQuery(sql);
        if (cursor0.moveToNext()) {
            ok = true;
        }
        cursor0.close();
        
        if (ok) {
            Vector<StudentMealHistoryData> list = new Vector<StudentMealHistoryData>();
            Cursor cursor = rawQuery("SELECT id, userId, time, provider, company, fee, fullPrice, note FROM " + StudentMealHistoryData.NAME + " ORDER BY time DESC");
            while (cursor.moveToNext()) {
                StudentMealHistoryData history = new StudentMealHistoryData(cursor.getInt(0), cursor.getInt(1), toTimestamp(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getString(7));
                list.add(history);
            }
            cursor.close();
            return list;
        } else {
            return null;
        }
    }
}
