package com.sciget.studentmeals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.os.Environment;

import com.sciget.studentmeals.activity.RestaurantMapActivity;
import com.sciget.studentmeals.database.data.StudentMealUserData;
import com.sciget.studentmeals.database.model.StudentMealUserModel;

import si.feri.projekt.studentskaprehrana.Main;
import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.RestaurantsListActivity2;

public class MyPerferences extends Perferences {
    public static class Location {
        public double latitude;
        public double longitude;
        
        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    
    public static class Day {
        public static final int WORKDAY = 1;
        public static final int SATURDAY = 2;
        public static final int SUNDAY = 3;
    }
    
    //private static final String SERVER_ADDRESS = "164.8.221.136:8080";
    private static final String SERVER_ADDRESS = "sciget.com:80";
    
    private Context context;
    private static MyPerferences instatnce;
    
    private Integer userId;
    private String userKey;
    private Double subsidy;
    private Timestamp lastRestaurantsUpdate;
    private Timestamp lastSubdidyUpdate;
    private Timestamp lastDailyMenusUpdate;
    private Timestamp lastPernamentMenusUpdate;
    private Timestamp lastUserHistoryUpdate;
    private String server;
    private Location location;

    private RestaurantMapActivity restaurantMapActivity;
    private RestaurantsListActivity2 restaurantsListActivity;
    private Boolean first;
    private int openTimeType;
    
    private static final String USER_ID = "userId";
    private static final String USER_KEY = "userKey";
    private static final String SUBSIDY = "subsidy";
    private static final String LAST_RESTAURANT_UPDATE = "lastRestaurantUpdate";
    private static final String LAST_SUBSIDY_UPDATE = "lastSubdidyUpdate";
    private static final String LAST_DAILY_UPDATE = "lastDailyMenusUpdate";
    private static final String LAST_PERMANENT_MENUS_UPDATE = "lastPernamentMenusUpdate";
    private static final String LAST_USER_HISTORY_UPDATE = "lastUserHistoryUpdate";
    private static final String SERVER = "server";
    private static final String LOCATION_LATITUDE = "locationLatitude";
    private static final String LOCATION_LONGITUDE = "locationLongitude";
    private static final String FIRST = "first";
    
    public static final double LOCATION_LATITUDE_DEFAULT = 46.5575721;
    public static final double LOCATION_LONGITUDE_DEFAULT = 15.6375547;
    
    public MyPerferences() {
        super();
        instatnce = this;
        server = SERVER_ADDRESS;
    }
    
    public MyPerferences(Context context) {
        super(context);
        
        if (instatnce == null) {
            this.context = context;
            instatnce = this;
            if (isFirst()) {
                deleteDirectory(new File(getExternalStoragePath()));
            }
            setValues();
        }
    }
    
    public static boolean hasStorage(boolean requireWriteAccess) {  
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {  
            return true;
        }  
        return false;  
    }
    
    private void copyDatabase() {
        File file = new File(getDatabasePath());
        if (file.exists()) return;
        
        InputStream in = context.getResources().openRawResource(R.raw.database);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int read;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
             try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }
    
    private void setValues() {
        File dir = new File(MyPerferences.getExternalStoragePath());
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        copyDatabase();
        
        StudentMealUserModel userModel = new StudentMealUserModel(context);
        StudentMealUserData user = userModel.getUser();
        if (user != null) {
            setUserId(user.userId);
            setUserKey(user.key);
        }
        
        String server = getServer();
        if (server == null || server.length() == 0) {
            setServer(SERVER_ADDRESS);
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
    
    public String getServer() {
        if (server == null) {
            server = get(SERVER);
        }
        return server;
    }
    
    public Location getLocation() {
        if (location == null) {
            double latitude = getDouble(LOCATION_LATITUDE);
            double longitude = getDouble(LOCATION_LONGITUDE);
            if (latitude != -1 && longitude != -1) {
                location = new Location(latitude, longitude);
            } else {
                location = new Location(LOCATION_LATITUDE_DEFAULT, LOCATION_LONGITUDE_DEFAULT);
            }
        }
        return location;
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
    
    public void setServer(String server) {
        this.server = server;
        set(SERVER, server);
    }

    public void setLocation(Location location) {
        this.location = location;
        set(LOCATION_LATITUDE, location.latitude);
        set(LOCATION_LONGITUDE, location.longitude);
        if (restaurantMapActivity != null) {
            restaurantMapActivity.changeMap(location);
        }
        if (restaurantsListActivity != null) {
            restaurantsListActivity.sortNearList(location);
        }
    }
    
    public void registerRestaurantMapActivity(RestaurantMapActivity restaurantMapActivity) {
        this.restaurantMapActivity = restaurantMapActivity;
    }
    
    public void unregisterRestaurantMapActivity() {
        this.restaurantMapActivity = null;
    }
    
    public void registerRestaurantsListActivity(RestaurantsListActivity2 restaurantsListActivity) {
        this.restaurantsListActivity = restaurantsListActivity;
    }
    
    public void unregisterRestaurantsListActivity() {
        this.restaurantsListActivity = null;
    }

    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/StudentMeals/";
    }
    
    public static String getDatabasePath0() {
        return getExternalStoragePath() + "sp.sqlite";
    }
    
    public String getDatabasePath() {
        return context.getFilesDir() + File.separator + "sp.sqlite";
    }
    
    public boolean isFirst() {
        if (first == null) {
            first = false;
            int firstInt = getInt(FIRST);
            if (firstInt == -1) {
                set(FIRST, 1);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private void setOpenTimeType() {
        GregorianCalendar newCal = new GregorianCalendar();
        int day = newCal.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SUNDAY) {
            openTimeType = Day.SUNDAY;
        } else if (day == Calendar.SATURDAY) {
            openTimeType = Day.SATURDAY;
        } else {
            openTimeType = Day.WORKDAY;
        }
    }
    
    public int getOpenTimeType() {
        if (openTimeType == 0) {
            setOpenTimeType();
        }
        return openTimeType;
    }
}
