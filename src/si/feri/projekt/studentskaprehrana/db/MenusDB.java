package si.feri.projekt.studentskaprehrana.db;

import android.provider.BaseColumns;
import android.text.format.Time;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.Menu;

@Deprecated
public class MenusDB implements BaseColumns {
	public static String INT = "INTEGER";
	public static String BOOL = "BOOLEAN";
	public static String STR = "CHAR(250)";
	public static String DBL = "DOUBLE";
    public static String TIME = "TIME";
	public static String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
	
	public static String TABLE_NAME = "menus";
	
	public static String ID = "id";
	public static String ID_TYPE = INT;
	public static int ID_ID = 0;
        
	public static String HASH = "restaurant_hash";
	public static String HASH_TYPE = STR;
    public static int HASH_ID = 1;
	
	public static String CONTENTS = "contents";
	public static String CONTENTS_TYPE = STR;
    public static int CONTENTS_ID = 2;
	
	public static String VEGETARIAN = "vegetarian";
	public static String VEGETARIAN_TYPE = BOOL;
    public static int VEGETARIAN_ID = 3;
 
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        ID + " " + PRIMERY_ID + ", " +
        HASH + " " + HASH_TYPE + ", " +
        CONTENTS + " " + CONTENTS_TYPE + ", " +
        VEGETARIAN + " " + VEGETARIAN_TYPE +
    ")";
	
	private SQLiteDatabase db;
	private DatabaseHelperOld dbHelper;
	private Context context;
	
	
	public MenusDB(Context ctx) throws Exception {
	    if (true) throw new Exception("Deprecated");
		context = ctx;
		dbHelper = new DatabaseHelperOld(context);
	}

	public MenusDB open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public void truncateTable() {
		dbHelper.dropTable(db, TABLE_NAME);
		db.execSQL(MenusDB.CREATE_TABLE);
	}
        
    public void insertMenus(Vector<Menu> menus) {
    	truncateTable();
        for (Menu menu : menus) {
            insert(menu);
        }
    }
        
        public long insert(Menu menu) {
            ContentValues values = new ContentValues();
            values.put(HASH, menu.getRestaurantHash());
            values.put(CONTENTS, menu.getContents());
            values.put(VEGETARIAN, menu.getVegetarian());
            return db.insert(TABLE_NAME, null, values);
        }
        
        public Vector<Menu> getMenusByHash(String hash) {
            Vector<Menu> menus = new Vector<Menu>();
            Cursor cursor = db.rawQuery("SELECT " +
				ID + ", " +
				HASH + ", " +
				CONTENTS + ", " +
				VEGETARIAN +
		    " FROM " + TABLE_NAME + " WHERE " + HASH + " = ?", new String[] {hash});
            while (cursor.moveToNext()) {
            	Menu menu = new Menu(
        			cursor.getLong(ID_ID),
                    cursor.getString(HASH_ID),
                    cursor.getString(CONTENTS_ID),
                    cursor.getInt(VEGETARIAN_ID)
                );
            	menus.add(menu);
            }
            return menus;
        }
        
        private static java.sql.Time strToTime(String t) {
        	try {
        		long l = java.sql.Time.parse(t);
        		java.sql.Time time = new java.sql.Time(l);
        		return time;
        	} catch (Exception ex) {
        		return null;
        	}
        }

		public boolean menuExists(String hash) {
			open();
			boolean result = false;
            Cursor cursor = db.rawQuery("SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + HASH + " = ? LIMIT 1", new String[] {hash});
            if (cursor.moveToNext()) {
            	result = true;
            }
			close();
			return result;
		}
		
		public boolean dataExists() {
            Cursor cursor = db.rawQuery("SELECT " + ID + " FROM " + TABLE_NAME + " LIMIT 1", new String[] {});
            if (cursor.moveToNext()) {
            	return true;
            }
			return false;
		}
}
