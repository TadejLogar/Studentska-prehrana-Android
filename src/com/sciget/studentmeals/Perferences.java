package com.sciget.studentmeals;

import java.sql.Timestamp;

import si.feri.projekt.studentskaprehrana.Main;

import com.sciget.studentmeals.database.data.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class Perferences {
    public static final String SERVER = "192.168.1.100";
    public static final String SERVER2 = "192.168.1.101";
    
    private Context context;
    
    public Perferences(Context context) {
        this.context = context;
    }
    
    protected void set(String key, String value) {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    protected void set(String key, int value) {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    
    protected void set(String key, double value) {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, (float) value);
        editor.commit();
    }
    
    protected String get(String key) {
        return getSharedPreferences().getString(key, "");
    }
    
    protected int getInt(String key) {
        return getSharedPreferences().getInt(key, -1);
    }
    
    protected double getDouble(String key) {
        return getSharedPreferences().getFloat(key, -1);
    }
    
    protected Timestamp getTimestamp(String key) {
        return Data.toTimestamp(get(key));
    }
    
    protected void set(String key, Timestamp value) {
        set(key, Data.toString(value));
    }
    
    protected SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("Values", 0);
    }
}
