package com.sciget.studentmeals.database.model;

import com.sciget.studentmeals.database.data.StudentMealUserData;

import android.content.Context;

public class StudentMealUserModel extends Model {
    public StudentMealUserModel(Context context) {
        super(context);
    }
    
    public int add(StudentMealUserData data) {
        return data.add(this);
    }
    
    public void remove(int userId) {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE userId = " + userId);
    }
    
    public void removeOld() {
        update("DELETE FROM " + StudentMealUserData.NAME + " WHERE date < '" + todayDate() + "'");
    }
}
