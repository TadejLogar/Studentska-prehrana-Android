package com.sciget.studentmeals.database.data;

import java.sql.Timestamp;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public class FavoritedRestaurantData extends Data {
    public static final String NAME = "favorited_restaurants";

    public int id;
    public int level;
    public int userId;
    public int restaurantId;
    public Timestamp time;
    
    public FavoritedRestaurantData() {}
    
    public FavoritedRestaurantData(int id, int level, int userId, int restaurantId, Timestamp time) {
        this.id = id;
        this.level = level;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.time = time;
    }
    
    public FavoritedRestaurantData(int level, int userId, int restaurantId, Timestamp time) {
        this.level = level;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.time = time;
    }
    
    public int add(Database db) {
        return db.update("INSERT INTO " + NAME + " (level, userId, restaurantId, time) VALUES (?, ?, ?, ?)", new Object[] { level, userId, restaurantId, time });
    }
    
    @Override
    public void create(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        String sql = new StringBuilder().append("CREATE TABLE `" + NAME + "` (\n").
        append("  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n").
        append("  `userId` integer,\n").
        append("  `restaurantId` integer,\n").
        append("  `time` datetime\n").
        append(")").toString();
        db.execSQL(sql);
    }
}
