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

    public Database open() throws SQLException {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return this;
    }

    public void close() {
        databaseHelper.close();
        database = null;
        context = null;
    }
    
    public int getValue(String sql, String param) {
        Cursor cursor = rawQuery(sql, new String[] { param });
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return -1;
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
        return database.rawQuery(sql, new String[] {});
    }
    
    public Cursor rawQuery(String sql, String param) {
        return rawQuery(sql, new String[] { param });
    }
    
    public Cursor rawQuery(String sql, int param) {
        return rawQuery(sql, new String[] { Integer.toString(param) });
    }
    
    private static long stringToLongTime(String timeStr) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        java.util.Date parsedDate = dateFormat.parse(timeStr);
        return parsedDate.getTime();
    }
    
    public static java.sql.Timestamp toTimestamp(String timeStr) {
        try {
            return new java.sql.Timestamp(stringToLongTime(timeStr));
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date toDate(String timeStr) {
        try {
            return new java.sql.Date(stringToLongTime(timeStr));
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Time toTime(String timeStr) {
        try {
            return new java.sql.Time(stringToLongTime(timeStr));
        } catch (Exception e) {
            return null;
        }
    }

}
