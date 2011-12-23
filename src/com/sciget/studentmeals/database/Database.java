package com.sciget.studentmeals.database;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import si.feri.projekt.studentskaprehrana.db.DatabaseHelperOld;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;
    
    public Database(Context context) {
        this(context, true);
    }
    
    public Database(Context context, boolean open) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        if (open) {
            open();
        }
    }
    
    public SQLiteDatabase getDatabase() {
        return database;
    }

    public Database open() throws SQLException {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return this;
    }

    public void close() {
        try {
            databaseHelper.close();
        } catch (Exception e) {}
        
        try {
            database.close();
        } catch (Exception e) {}
        
        databaseHelper = null;
        database = null;
        context = null;
    }
    
    public int getValue(String sql, String param) {
        int id = -1;
        Cursor cursor = rawQuery(sql, new String[] { param });
        if (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public int update(String sql, Object[] params) {
        database.execSQL(sql, params);
        return 0; // TODO vrne naj zadnji vstavljeni oz. spremenjeni id
    }
    
    public int update(String sql) {
        return update(sql, new Object[] {});
    }

    public int getValue(String sql) {
        return 0;
    }
    
    public Cursor rawQuery(String sql, String[] obj) {
        return database.rawQuery(sql, obj);
    }
    
    public Cursor rawQuery(String sql) {
        //System.out.println(sql);
        return database.rawQuery(sql, new String[] {});
    }
    
    public Cursor rawQuery(String sql, String param) {
        return rawQuery(sql, new String[] { param });
    }
    
    public Cursor rawQuery(String sql, int param) {
        return rawQuery(sql, new String[] { Integer.toString(param) });
    }
    
    private static long stringToLongTime(String timeStr, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        java.util.Date parsedDate = dateFormat.parse(timeStr);
        return parsedDate.getTime();
    }
    
    public static java.sql.Timestamp toTimestamp(String timeStr) {
        try {
            return new java.sql.Timestamp(stringToLongTime(timeStr, "yyyy-MM-dd hh:mm:ss.SSS"));
        } catch (Exception e) {
        }
        try {
            return new java.sql.Timestamp(stringToLongTime(timeStr, "yyyy-MM-dd hh:mm:ss"));
        } catch (Exception ex) {
        }
        return null;
    }
    
    public static Date toDate(String timeStr) {
        try {
            return new java.sql.Date(stringToLongTime(timeStr, "yyyy-MM-dd"));
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Time toTime(String timeStr) {
        try {
            return new java.sql.Time(stringToLongTime(timeStr, "hh:mm:ss"));
        } catch (Exception e) {
            return null;
        }
    }

}
