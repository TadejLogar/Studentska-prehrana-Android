package com.sciget.studentmeals.database.data;

import java.sql.Timestamp;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public class StudentMealCommentData extends Data {
    public static final String NAME = "students_meals_comments";

    public int id;
    public int userId;
    public int restaurantId;
    public String name;
    public int rate;
    public String comment;
    public Timestamp time;

    public StudentMealCommentData(int id, int userId, int restaurantId, String name, int rate, String comment, Timestamp time) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.rate = rate;
        this.comment = comment;
        this.time = time;
    }

    public StudentMealCommentData(int userId, int restaurantId, String name, int rate, String comment, Timestamp time) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.rate = rate;
        this.comment = comment;
        this.time = time;
    }

    public int add(Database db) {
        return db.update("INSERT INTO " + NAME + " (userId, restaurantId, name, rate, comment, time) VALUES (?, ?, ?, ?, ?, ?)", new Object[] { userId, restaurantId, name, rate, comment, time });
    }

    @Override
    public void create(SQLiteDatabase db) {   
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        String sql = new StringBuilder().append("CREATE TABLE `" + NAME + "` (\n").
        append("  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n").
        append("  `userId` INTEGER,\n").
        append("  `restaurantId` INTEGER,\n").
        append("  `name` text,\n").
        append("  `rate` INTEGER,\n").
        append("  `comment` text,\n").
        append("  `time` datetime\n").
        append(")").toString();
        db.execSQL(sql);
    }
    
}
