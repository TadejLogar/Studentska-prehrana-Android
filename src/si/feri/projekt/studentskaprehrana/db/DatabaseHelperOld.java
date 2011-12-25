package si.feri.projekt.studentskaprehrana.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@Deprecated
public class DatabaseHelperOld extends SQLiteOpenHelper {
	public static final String TAG = "DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "studentskaprehrana";

	DatabaseHelperOld(Context context) throws Exception {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		if (true) throw new Exception("Deprecated");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropTable(db, ProvidersDB.TABLE_NAME);
		dropTable(db, FavoritesDB.TABLE_NAME);
		dropTable(db, UsersDB.TABLE_NAME);
		dropTable(db, MenusDB.TABLE_NAME);
		
		db.execSQL(ProvidersDB.CREATE_TABLE);
		db.execSQL(FavoritesDB.CREATE_TABLE);
		db.execSQL(UsersDB.CREATE_TABLE);
		db.execSQL(MenusDB.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		
		dropTable(db, ProvidersDB.TABLE_NAME);
		dropTable(db, FavoritesDB.TABLE_NAME);
		dropTable(db, UsersDB.TABLE_NAME);
		
		onCreate(db);
	}
	
	public void dropTable(SQLiteDatabase db, String name) {
		db.execSQL("DROP TABLE IF EXISTS " + name);
	}
}
