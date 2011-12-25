package com.sciget.studentmeals.database.data;

import java.sql.Timestamp;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public class StudentMealCommentData extends Data {
    public static final String NAME = "students_meals_comments";

    public int id;
    public int userId;
    public int restaurantId;
    public String comment;
    public Timestamp time;

    public StudentMealCommentData(int id, int userId, int restaurantId, String comment, Timestamp time) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.time = time;
    }

    public StudentMealCommentData(int userId, int restaurantId, String comment, Timestamp time) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.time = time;
    }

    public int add(Database db) {
        return db.update("INSERT INTO " + NAME + " (userId, restaurantId, comment, time) VALUES (?, ?, ?, ?)", new Object[] { userId, restaurantId, comment, time });
    }

    @Override
    public void create(SQLiteDatabase db) {   
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        String sql = new StringBuilder().append("CREATE TABLE `" + NAME + "` (\n").
        append("  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n").
        append("  `userId` INTEGER,\n").
        append("  `restaurantId` INTEGER,\n").
        append("  `comment` text,\n").
        append("  `time` datetime\n").
        append(")").toString();
        db.execSQL(sql);
    }

    @Override
    public String toString() {
        return "StudentMealCommentData [id=" + id + ", userId=" + userId
                + ", restaurantId=" + restaurantId + ", comment=" + comment
                + ", time=" + time + "]";
    }
}
