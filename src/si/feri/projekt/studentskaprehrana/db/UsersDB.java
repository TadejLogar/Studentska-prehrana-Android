package si.feri.projekt.studentskaprehrana.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

@Deprecated
public class UsersDB implements BaseColumns {
	public static String INT = "INTEGER";
	public static String STR = "CHAR(250)";
	public static String DBL = "DOUBLE";
	public static String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
	
	public static String TABLE_NAME = "users";
	
	public static String ID = "id";
	public static String ID_TYPE = INT;
	
	public static String FIRSTNAME = "firstname";
	public static String FIRSTNAME_TYPE = STR;
	
	public static String LASTNAME = "lastname";
	public static String LASTNAME_TYPE = STR;
	
	public static String EMAIL = "email";
	public static String EMAIL_TYPE = STR;	
	
	public static String PASSWORD = "password";
	public static String PASSWORD_TYPE = STR;
	
	public static String REMAINING_SUBSIDIES  = "remaining_subsidies"; // preostalo število subvencij
	public static String REMAINING_SUBSIDIES_TYPE = INT;
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
										ID + " " + PRIMERY_ID + ", " +
										FIRSTNAME + " " + FIRSTNAME_TYPE + ", " +
										LASTNAME + " " + LASTNAME_TYPE + ", " +
										EMAIL + " " + EMAIL_TYPE + ", " +
										PASSWORD + " " + PASSWORD_TYPE + ", " +
										REMAINING_SUBSIDIES + " " + REMAINING_SUBSIDIES_TYPE +
										")";
	
	private SQLiteDatabase db;
	private DatabaseHelperOld dbHelper;
	private Context context;
	
	
	public UsersDB(Context ctx) throws Exception {
	    if (true) throw new Exception("Deprecated");
		context = ctx;
		dbHelper = new DatabaseHelperOld(context);
	}

	public UsersDB open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public long addUser(String email, String password) {
        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        return db.insert(TABLE_NAME, null, values);
	}
	
	public void updateUser(String firstname, String lastname, int remainingSubsidies) {
		db.execSQL("UPDATE " + TABLE_NAME + " SET " + FIRSTNAME + " = ?, " + LASTNAME + " = ?, " +
			REMAINING_SUBSIDIES + " = " + remainingSubsidies, new String[]{firstname, lastname});
	}
	
}
