package com.sciget.studentmeals.database;

import com.sciget.studentmeals.database.data.FavoritedRestaurantData;
import com.sciget.studentmeals.database.data.RestaurantData;
import com.sciget.studentmeals.database.data.RestaurantMenuData;
import com.sciget.studentmeals.database.data.StudentMealFileData;
import com.sciget.studentmeals.database.data.StudentMealHistoryData;
import com.sciget.studentmeals.database.data.StudentMealUserData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentmeals";
    private SQLiteDatabase db;
    private Context context;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        createAll();
    }
    
    public void createAll() {
        new RestaurantData().create(db);
        new FavoritedRestaurantData().create(db);
        new StudentMealUserData().create(db);
        new RestaurantMenuData().create(db);
        new StudentMealHistoryData().create(db);
        new StudentMealFileData().create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        createAll();
    }
}
