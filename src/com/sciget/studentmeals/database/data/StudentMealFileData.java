package com.sciget.studentmeals.database.data;

import com.sciget.studentmeals.database.Database;

import android.database.sqlite.SQLiteDatabase;

public class StudentMealFileData {
    public class FileType {
        public static final int IMAGE = 1;
        public static final int AUDIO = 2;
        public static final int VIDEO = 3;
    }
    
    public class Size {
        public static final int SMALL = 1;
        public static final int ORIGINAL = 2;
    }
    
    public static final String NAME = "students_meals_files";
    
    public int id;
    public int restaurantId;
    public int userId;
    public int type;
    public String smallHash;
    public String hash;
    public boolean smallDone;
    public boolean done;
    public String fileKey;

    public StudentMealFileData(int restaurantId, int userId, int type, String smallHash, String hash, boolean smallDone, boolean done, String fileKey) {
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.type = type;
        this.smallHash = smallHash;
        this.hash = hash;
        this.smallDone = smallDone;
        this.done = done;
        this.fileKey = fileKey;
    }

    public StudentMealFileData(int id, int restaurantId, int userId, int type, String smallHash, String hash, boolean smallDone, boolean done, String fileKey) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.type = type;
        this.smallHash = smallHash;
        this.hash = hash;
        this.smallDone = smallDone;
        this.done = done;
        this.fileKey = fileKey;
    }

    public StudentMealFileData() { }

    public StudentMealFileData(int id, int restaurantId, int userId, int type, String smallHash, String hash, int smallDone, int done, String fileKey) {
        this(id, restaurantId, userId, type, smallHash, hash, smallDone == 1 ? true : false, done == 1 ? true : false, fileKey);
    }

    public int add(Database db) {
        return db.update("INSERT INTO " + NAME + " (restaurantId, userId, type, smallHash, hash, smallDone, done, fileKey) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { restaurantId, userId, type, smallHash, hash, smallDone, done, fileKey });
    }
    
    public void create(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        db.execSQL(
            "CREATE TABLE `" + NAME + "` (" +
            "    `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    `restaurantId` INTEGER," +
            "    `userId` INTEGER," +
            "    `type` INTEGER," +
            "    `smallHash` varchar(50)," +
            "    `hash` varchar(50)," +
            "    `smallDone` boolean," +
            "    `done` boolean," +
            "    `fileKey` varchar(50)" +
            ")"
        );
    }

}