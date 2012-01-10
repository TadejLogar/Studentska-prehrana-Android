package com.sciget.studentmeals.database.model;

import java.util.Vector;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.database.data.FavoritedRestaurantData;
import com.sciget.studentmeals.database.data.StudentMealFileData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.data.StudentMealUserData;

import android.content.Context;
import android.database.Cursor;

public class StudentMealUserModel extends Model {
    public StudentMealUserModel(Context context) {
        super(context);
    }
    
    public int add(StudentMealUserData data) {
        return data.add(this);
    }
    
    public StudentMealUserData getUser() {
        Cursor cursor = rawQuery("SELECT userId, key FROM " + StudentMealUserData.NAME);
        if (cursor.moveToNext()) {
            StudentMealUserData data = new StudentMealUserData();
            data.userId = cursor.getInt(0);
            data.key = cursor.getString(1);
            return data;
        }
        return null;
    }
    
    public StudentMealUserData getUserAll() {
        Cursor cursor = rawQuery("SELECT id, userId, email, password, firstName, lastName, pin, addressStreet, addressPost, addressCountry, tempAddressStreet, tempAddressPost, tempAddressCountry, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, key, cookies, remainingSubsidies FROM " + StudentMealUserData.NAME);
        StudentMealUserData data = null;
        if (cursor.moveToNext()) {
            data = new StudentMealUserData(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getInt(23));
        }
        cursor.close();
        return data;
    }
    
    public void remove(int userId) {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE userId = " + userId);
    }
    
    public void removeOld() {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE date < '" + todayDate() + "'");
    }

    public String getLastVisitedProvider() {
        Cursor cursor = rawQuery("SELECT provider FROM " + StudentMealHistoryData.NAME);
        if (cursor.moveToNext()) {
            //StudentMealHistoryData data = new StudentMealHistoryData();
            //data.provider = cursor.getString(0);
            //return data;
            return cursor.getString(0);
        }
        return null;
    }

    public boolean isRestaurantFavorited(int restaurantId) {
        int val = getValue("SELECT id FROM " + FavoritedRestaurantData.NAME + " WHERE restaurantId = " + restaurantId + " AND userId = " + MyPerferences.getInstance().getUserId());
        return val != -1;
    }
    
    public void setRestaurantFavorited(int restaurantId, boolean favorited) {
        if (favorited) {
            addFavorite(restaurantId);
        } else {
            removeFavorite(restaurantId);
        }
    }
    
    private int addFavorite(final int restaurantId) {
        new Thread() {
            public void run() {
                StudentMealsService meals = new StudentMealsService();
                meals.setFavoritedRestaurant(MyPerferences.getInstance().getUserKey(), restaurantId);
            }
        }.start();
        
        FavoritedRestaurantData data = new FavoritedRestaurantData(MyPerferences.getInstance().getUserId(), restaurantId, time());
        return data.add(this);
    }
    
    private void removeFavorite(final int restaurantId) {
        new Thread() {
            public void run() {
                StudentMealsService meals = new StudentMealsService();
                meals.removeFavoritedRestaurant(MyPerferences.getInstance().getUserKey(), restaurantId);
            }
        }.start();
        
        update("DELETE FROM " + FavoritedRestaurantData.NAME + " WHERE restaurantId = " + restaurantId + " AND userId = " + MyPerferences.getInstance().getUserId());
    }
    
    public Vector<StudentMealFileData> getFilesData() {
        Cursor cursor = rawQuery("SELECT id, restaurantId, userId, type, smallHash, hash, smallDone, done, fileKey FROM " + StudentMealFileData.NAME + " WHERE done = 0");
        Vector<StudentMealFileData> list = new Vector<StudentMealFileData>();
        while (cursor.moveToNext()) {
            list.add(new StudentMealFileData(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8)));
        }
        cursor.close();
        return list;
    }
    
    public void setFileDone(int id) {
        update("UPDATE " + StudentMealFileData.NAME + " SET done = 1, smallDone = 1 WHERE id = " + id);
        //update("DELETE FROM " + StudentMealFileData.NAME + " WHERE id = " + id);
    }

    public void addImageFileData(int restaurantId, String fileKey) {
        new StudentMealFileData(restaurantId, 0, StudentMealFileData.FileType.IMAGE, null, null, false, false, fileKey).add(this);
    }

    public void setFileHashes(int id, String hash, String smallHash) {
        update("UPDATE " + StudentMealFileData.NAME + " SET hash = ?, smallHash = ? WHERE id = " + id, new Object[] { hash, smallHash });
    }
}
