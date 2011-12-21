package si.feri.projekt.studentskaprehrana.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

@Deprecated
public class FavoritesDB implements BaseColumns {
	// TODO: prestavi
	public static String INT = "INTEGER";
	public static String STR = "CHAR(250)";
	public static String DBL = "DOUBLE";
	public static String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
	
	public static String TABLE_NAME = "favorites";
	
	public static String ID = "id";
	public static String ID_TYPE = INT;
	
	public static String USER_ID = "user_id";
	public static String USER_ID_TYPE = INT;
	
	public static String PROVIDER_ID = "provider_id";
	public static String PROVIDER_ID_TYPE = INT;
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
										ID + " " + PRIMERY_ID + ", " +
										USER_ID + " " + USER_ID_TYPE + ", " +
										PROVIDER_ID + " " + PROVIDER_ID_TYPE +
										")";
	
	private SQLiteDatabase db;
	private DatabaseHelperOld dbHelper;
	private Context context;
	
	
	public FavoritesDB(Context ctx) throws Exception {
	    if (true) throw new Exception("Deprecated");
		context = ctx;
		dbHelper = new DatabaseHelperOld(context);
	}

	public FavoritesDB open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
}
