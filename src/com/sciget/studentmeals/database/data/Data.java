package com.sciget.studentmeals.database.data;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public abstract class Data {
    public static class Table {
        public static final String INT = "INTEGER";
        public static final String STR = "CHAR(250)";
        public static final String DBL = "DOUBLE";
        public static final String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
    }
    
    public abstract void create(SQLiteDatabase db);
    public abstract int add(Database db);
    
    public static String toString(java.sql.Time val) {
        return val.toString();
    }
    
    public void trancute(SQLiteDatabase db) {
        create(db);
    }
}
