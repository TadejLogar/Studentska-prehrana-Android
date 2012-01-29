package com.sciget.studentmeals.database;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sciget.studentmeals.MyPerferences;
import com.sciget.studentmeals.database.data.FavoritedRestaurantData;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealFileData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.data.StudentMealUserData;

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
            //database = databaseHelper.getWritableDatabase();

            database = SQLiteDatabase.openOrCreateDatabase(new File(MyPerferences.getInstance().getDatabasePath()), null);
            if (!tableExists(database, RestaurantData.NAME)) {
                databaseHelper.onCreate(database);
            }
        }
        return this;
    }

    public void close() {
        try {
            databaseHelper.close();
        } catch (Exception e) {
        }

        try {
            database.close();
        } catch (Exception e) {
        }

        databaseHelper = null;
        database = null;
        context = null;
    }

    public int getValue(String sql, String param) {
        int id = -1;
        String[] params;
        if (param == null) {
            params = new String[] {};
        } else {
            params = new String[] { param };
        }
        Cursor cursor = rawQuery(sql, params);
        if (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public int update(String sql, Object[] params) {
        database.execSQL(sql, params);
        //return getValue("select last_insert_rowid()");
        return 0; // TODO vrne naj zadnji vstavljeni oz. spremenjeni id
    }

    public int update(String sql) {
        return update(sql, new Object[] {});
    }

    public int getValue(String sql) {
        return getValue(sql, null);
    }

    public Cursor rawQuery(String sql, String[] obj) {
        return database.rawQuery(sql, obj);
    }

    public Cursor rawQuery(String sql) {
        // System.out.println(sql);
        return database.rawQuery(sql, new String[] {});
    }

    public Cursor rawQuery(String sql, String param) {
        return rawQuery(sql, new String[] { param });
    }

    public Cursor rawQuery(String sql, int param) {
        return rawQuery(sql, new String[] { Integer.toString(param) });
    }

    private static long stringToLongTime(String timeStr, String format)
            throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        java.util.Date parsedDate = dateFormat.parse(timeStr);
        long time = parsedDate.getTime();
        return time;
    }

    public static java.sql.Timestamp toTimestamp(String timeStr) {
        try {
            return new java.sql.Timestamp(stringToLongTime(timeStr,
                    "yyyy-MM-dd hh:mm:ss.SSS"));
        } catch (Exception e) {
        }
        try {
            return new java.sql.Timestamp(stringToLongTime(timeStr,
                    "yyyy-MM-dd hh:mm:ss"));
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

    public static java.sql.Time toTime(String timeStr) {
        if (timeStr == null) return null;
        try {
            int n = countOccurrences(timeStr, ':');
            long longTime = -1;
            if (n == 1) {
                longTime = stringToLongTime(timeStr, "hh:mm");
            } else {
                longTime = stringToLongTime(timeStr, "hh:mm:ss");
            }
            if (longTime == -1) return null;
            java.sql.Time time = new java.sql.Time(longTime);
            return time;
        } catch (Exception e) {
            //System.out.println(e.toString());
        }
        return null;
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }
    
    public static boolean tableExists(SQLiteDatabase database, String tableName) {
        String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + tableName + "'";
        Cursor cursor0 = database.rawQuery(sql, new String[] {});
        boolean exists = false;
        if (cursor0.moveToNext()) {
            exists = true;
        }
        cursor0.close();
        return exists;
    }

}
