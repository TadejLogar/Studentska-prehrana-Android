package com.sciget.studentmeals.database.model;

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
        if (cursor.moveToNext()) {
            StudentMealUserData data = new StudentMealUserData(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getInt(23));
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
}
