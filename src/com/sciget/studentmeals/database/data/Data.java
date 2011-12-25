package com.sciget.studentmeals.database.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.database.sqlite.SQLiteDatabase;
import com.sciget.studentmeals.database.Database;

public abstract class Data {
    public static final String FORMAT_SECONDS = "d.M.yyyy H:mm:ss";
    public static final String FORMAT = "d.M.yyyy H:mm";
    public static final String FORMAT_DATE = "d.M.yyyy";
    public static final String FORMAT_DATE_SQL = "yyyy-M-d";
    public static final String FORMAT_FULL_TIME_SQL = "yyyy-M-d H:mm:ss";
    public static final String FORMAT_ONLY_TIME = "H:mm";
    
    public static class Table {
        public static final String INT = "INTEGER";
        public static final String STR = "CHAR(250)";
        public static final String DBL = "DOUBLE";
        public static final String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
    }
    
    public abstract void create(SQLiteDatabase db);
    public abstract int add(Database db);
    
    public static String toString(java.sql.Time time) {
        if (time == null) return null;
        
        SimpleDateFormat format = simpleDateFormat(FORMAT_ONLY_TIME); // TODO: sprememba iz kom. metode: FORMAT_SECONDS
        return format.format(new java.util.Date(time.getTime()));
    }
    
    public static String toString(java.sql.Timestamp time) {
        if (time == null) return null;
        
        SimpleDateFormat format = simpleDateFormat(FORMAT_FULL_TIME_SQL);
        return format.format(new java.util.Date(time.getTime()));
    }
    
    public static SimpleDateFormat simpleDateFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        return sdf;
    }
    
    public void trancute(SQLiteDatabase db) {
        create(db);
    }
    
    public String toString(java.sql.Date date) {
        if (date == null) return null;
        
        SimpleDateFormat format = simpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }
    public static Timestamp toTimestamp(String time) {
        return Database.toTimestamp(time);
    }
    
    public static Timestamp time() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}
