package com.sciget.studentmeals.database.model;

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
    
    public void remove(int userId) {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE userId = " + userId);
    }
    
    public void removeOld() {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE date < '" + todayDate() + "'");
    }
}
