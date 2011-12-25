package com.sciget.studentmeals;

import java.sql.Timestamp;

import android.content.Context;

import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.Main;

public class MyPerferences extends Perferences {
    private static MyPerferences instatnce;
    
    private Integer userId;
    private String userKey;
    private Double subsidy;
    private Timestamp lastRestaurantsUpdate;
    private Timestamp lastSubdidyUpdate;
    private Timestamp lastDailyMenusUpdate;
    private Timestamp lastPernamentMenusUpdate;
    private Timestamp lastUserHistoryUpdate;
    
    private static final String USER_ID = "userId";
    private static final String USER_KEY = "userKey";
    private static final String SUBSIDY = "subsidy";
    private static final String LAST_RESTAURANT_UPDATE = "lastRestaurantUpdate";
    private static final String LAST_SUBSIDY_UPDATE = "lastSubdidyUpdate";
    private static final String LAST_DAILY_UPDATE = "lastDailyMenusUpdate";
    private static final String LAST_PERMANENT_MENUS_UPDATE = "lastPernamentMenusUpdate";
    private static final String LAST_USER_HISTORY_UPDATE = "lastUserHistoryUpdate";
    
    public MyPerferences(Context context) {
        super(context);
        
        if (instatnce == null) {
            instatnce = this;

            StudentMealUserModel userModel = new StudentMealUserModel(context);
            StudentMealUserData user = userModel.getUser();
            if (user != null) {
                setUserId(user.userId);
                setUserKey(user.key);
            }
        }
    }
    
    public static MyPerferences getInstance() {
        return instatnce;
    }

    public int getUserId() {
        if (userId == null) {
            userId = getInt(USER_ID);
        }
        return userId;
    }

    public double getSubsidy() {
        if (subsidy == null) {
            subsidy = getDouble(SUBSIDY);
        }
        return subsidy;
    }

    public Timestamp getLastRestaurantsUpdate() {
        if (lastRestaurantsUpdate == null) {
            lastRestaurantsUpdate = getTimestamp(LAST_RESTAURANT_UPDATE);
        }
        return lastRestaurantsUpdate;
    }

    public Timestamp getLastSubdidyUpdate() {
        if (lastRestaurantsUpdate == null) {
            lastRestaurantsUpdate = getTimestamp(LAST_RESTAURANT_UPDATE);
        }
        return lastSubdidyUpdate;
    }

    public Timestamp getLastDailyMenusUpdate() {
        if (lastRestaurantsUpdate == null) {
            lastRestaurantsUpdate = getTimestamp(LAST_DAILY_UPDATE);
        }
        return lastDailyMenusUpdate;
    }

    public Timestamp getLastPernamentMenusUpdate() {
        if (lastRestaurantsUpdate == null) {
            lastRestaurantsUpdate = getTimestamp(LAST_PERMANENT_MENUS_UPDATE);
        }
        return lastPernamentMenusUpdate;
    }

    public Timestamp getLastUserHistoryUpdate() {
        if (lastRestaurantsUpdate == null) {
            lastRestaurantsUpdate = getTimestamp(LAST_USER_HISTORY_UPDATE);
        }
        return lastUserHistoryUpdate;
    }
    
    public String getUserKey() {
        if (userKey == null) {
            userKey = get(USER_KEY);
        }
        return userKey;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        set(USER_ID, userId);
    }

    public void setSubsidy(double subsidy) {
        this.subsidy = subsidy;
        set(SUBSIDY, subsidy);
    }

    public void setLastRestaurantsUpdate(Timestamp lastRestaurantsUpdate) {
        this.lastRestaurantsUpdate = lastRestaurantsUpdate;
        set(LAST_RESTAURANT_UPDATE, lastRestaurantsUpdate);
    }

    public void setLastSubdidyUpdate(Timestamp lastSubdidyUpdate) {
        this.lastSubdidyUpdate = lastSubdidyUpdate;
        set(LAST_SUBSIDY_UPDATE, lastSubdidyUpdate);
    }

    public void setLastDailyMenusUpdate(Timestamp lastDailyMenusUpdate) {
        this.lastDailyMenusUpdate = lastDailyMenusUpdate;
        set(LAST_DAILY_UPDATE, lastDailyMenusUpdate);
    }

    public void setLastPernamentMenusUpdate(Timestamp lastPernamentMenusUpdate) {
        this.lastPernamentMenusUpdate = lastPernamentMenusUpdate;
        set(LAST_PERMANENT_MENUS_UPDATE, lastPernamentMenusUpdate);
    }

    public void setLastUserHistoryUpdate(Timestamp lastUserHistoryUpdate) {
        this.lastUserHistoryUpdate = lastUserHistoryUpdate;
        set(LAST_USER_HISTORY_UPDATE, lastUserHistoryUpdate);
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
        set(USER_KEY, userKey);
    }
}
