package com.sciget.studentmeals.database.data;

import java.sql.Timestamp;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public class StudentMealHistoryData extends Data {
    public static final String NAME = "students_meals_history";

    public int id;
    public int userId;
    public Timestamp time;
    public String provider;
    public String company;
    public double fee;
    public double fullPrice;
    public String note;
    
    public StudentMealHistoryData(int id, int userId, Timestamp time, String provider, String company, double fee, double fullPrice, String note) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.provider = provider;
        this.company = company;
        this.fee = fee;
        this.fullPrice = fullPrice;
        this.note = note;
    }
    
    public StudentMealHistoryData(int userId, Timestamp time, String provider, String company, double fee, double fullPrice, String note) {
        this(0, userId, time, provider, company, fee, fullPrice, note);
    }

    public StudentMealHistoryData() { }

    public int add(Database db) {
        int id = db.getValue("SELECT id FROM " + NAME + " WHERE userId = " + userId + " AND time = ?", toString(time));
        if (id == -1) {
            return db.update("INSERT INTO " + NAME + " (userId, time, provider, company, fee, fullPrice, note) VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[] { userId, time, provider, company, fee, fullPrice, note });
        }
        return id;
    }

    @Override
    public void create(SQLiteDatabase db) {
        //if (Database.tableExists(db, NAME)) return;
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        db.execSQL(
            "CREATE TABLE `students_meals_history` (" +
            "    `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    `userId` INTEGER," +
            "   `time` datetime," +
            "    `provider` varchar(250)," +
            "    `company` varchar(250)," +
            "    `fee` double," +
            "    `fullPrice` double," +
            "    `note` text" +
            ")"
        );
    }
}