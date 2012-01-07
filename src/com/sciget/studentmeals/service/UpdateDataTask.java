package com.sciget.studentmeals.service;

import java.io.IOException;
import java.util.Vector;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.client.service.StudentMealsService;
import com.sciget.studentmeals.client.service.data.HistoryData;
import com.sciget.studentmeals.client.service.data.MenuData;
import com.sciget.studentmeals.client.service.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.model.RestaurantModel;

import android.content.Context;
import android.util.Log;

public class UpdateDataTask {
    private Context context;
    private StudentMealsService meals;
    private RestaurantModel restaurantModel;
    
    public UpdateDataTask(Context context) {
        this.context = context;
        this.meals = new StudentMealsService();
        this.restaurantModel = new RestaurantModel(context);
    }
    
    public void all() {
        try {
            updateServerHost();
            updateSubsidy();
            updateRestaurants();
            new RestaurantMenuData().create(restaurantModel.getDatabase());
            /*new Thread() {
                public void run() {*/
                    updateDailyMenus();
                    updatePermanentMenus();
                /*}
            }.start();*/
        } catch (Exception e) {
            Log.e("update", e.toString());
        }
    }
    
    public static void updateServerHost() {
        String ip;
        try {
            ip = MVC.downloadToString("http://dl.dropbox.com/u/14013671/ip.txt");
            if (ip != null) {
                MyPerferences.getInstance().setServer(ip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSubsidy() {
        double subsidy = meals.getSubsidy();
        if (subsidy > 0) {
            MyPerferences.getInstance().setSubsidy(subsidy);
        }
    }

    public void updateRestaurants() {
        Vector<RestaurantData> list = meals.restaurants();
        Log.e("A", "DOWNLOADED");
        restaurantModel.addRestaurants(list);
    }
    
    public void updateDailyMenus() {
        Vector<MenuData> list = meals.allRestaurantsDailyMenus();
        restaurantModel.addMenus(list);
    }
    
    public void updatePermanentMenus() {
        Vector<MenuData> list = meals.allRestaurantsPermanentMenus();
        restaurantModel.addMenus(list);
    }
    
    public void updateUserHistory() {
        //String key = meals.getUserKey("tadej.logar.101@gmail.com", "studentskaprehrana.si");
        String key = MyPerferences.getInstance().getUserKey();
        Vector<HistoryData> list = meals.history(key);
        restaurantModel.addUserHistory(list);
    }
    
    public void closeModel() {
        restaurantModel.close();
    }
}
