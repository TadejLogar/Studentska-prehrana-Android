package si.feri.projekt.studentskaprehrana.db;

import android.provider.BaseColumns;
import android.text.format.Time;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Hashtable;
import java.util.Vector;

import si.feri.projekt.studentskaprehrana.Provider;

@Deprecated
public class ProvidersDB implements BaseColumns {
	public static final String INT = "INTEGER";
	public static final String STR = "CHAR(250)";
	public static final String DBL = "DOUBLE";
    public static final String TIME = "TIME";
	public static final String PRIMERY_ID = INT + " PRIMARY KEY AUTOINCREMENT";
	
	public static String TABLE_NAME = "providers";
	
	public static String ID = "id";
	public static String ID_TYPE = INT;
	public static int ID_ID = 0;
        
	public static String HASH = "hash";
	public static String HASH_TYPE = STR;
    public static int HASH_ID = 1;
	
	public static String NAME = "name";
	public static String NAME_TYPE = STR;
    public static int NAME_ID = 2;
	
	public static String ADDRESS = "address";
	public static String ADDRESS_TYPE = STR;
    public static int ADDRESS_ID = 3;
        
	public static String POST = "post";
	public static String POST_TYPE = STR;
    public static int POST_ID = 4;
        
	public static String COUNTRY = "country";
	public static String COUNTRY_TYPE = STR;
    public static int COUNTRY_ID = 5;
        
	public static String PRICE = "price";
	public static String PRICE_TYPE = DBL;
    public static int PRICE_ID = 6;
	
	public static String PHONE = "phone";
	public static String PHONE_TYPE = STR;
    public static int PHONE_ID = 7;
	
	public static String OPEN_WORKDAY_FROM = "open_workday_from";
	public static String OPEN_WORKDAY_FROM_TYPE = TIME;
    public static int OPEN_WORKDAY_FROM_ID = 8;
        
	public static String OPEN_WORKDAY_TO = "open_workday_to";
	public static String OPEN_WORKDAY_TO_TYPE = TIME;
    public static int OPEN_WORKDAY_TO_ID = 9;
	
	public static String OPEN_SATURDAY_FROM = "open_saturday_from";
	public static String OPEN_SATURDAY_FROM_TYPE = TIME;
    public static int OPEN_SATURDAY_FROM_ID = 10;
        
	public static String OPEN_SATURDAY_TO = "open_saturday_to";
	public static String OPEN_SATURDAY_TO_TYPE = TIME;
    public static int OPEN_SATURDAY_TO_ID = 11;
	
	public static String OPEN_SUNDAY_FROM = "open_sunday_from";
	public static String OPEN_SUNDAY_FROM_TYPE = TIME;
    public static int OPEN_SUNDAY_FROM_ID = 12;
        
	public static String OPEN_SUNDAY_TO = "open_sunday_to";
	public static String OPEN_SUNDAY_TO_TYPE = TIME;
    public static int OPEN_SUNDAY_TO_ID = 13;
        
	public static String LOCATION_LATITUDE = "location_latitude";
	public static String LOCATION_LATITUDE_TYPE = DBL;
    public static int LOCATION_LATITUDE_ID = 14;
        
	public static String LOCATION_LONGITUDE = "location_longitude";
	public static String LOCATION_LONGITUDE_TYPE = DBL;
    public static int LOCATION_LONGITUDE_ID = 15;
    
	public static String FAVORITED = "favorited";
	public static String FAVORITED_TYPE = INT;
    public static int FAVORITED_ID = 16;
        
	
	public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " " + PRIMERY_ID + ", " +
            HASH + " " + HASH_TYPE + ", " +
            NAME + " " + NAME_TYPE + ", " +
            ADDRESS + " " + ADDRESS_TYPE + ", " +
            POST + " " + POST_TYPE + ", " +
            COUNTRY + " " + COUNTRY_TYPE + ", " +
            PRICE + " " + PRICE_TYPE + ", " +
            PHONE + " " + PHONE_TYPE + ", " +
            OPEN_WORKDAY_FROM + " " + OPEN_WORKDAY_FROM_TYPE + ", " +
            OPEN_WORKDAY_TO + " " + OPEN_WORKDAY_TO_TYPE + ", " +
            OPEN_SATURDAY_FROM + " " + OPEN_SATURDAY_FROM_TYPE + ", " +
            OPEN_SATURDAY_TO + " " + OPEN_SATURDAY_TO_TYPE + ", " +
            OPEN_SUNDAY_FROM + " " + OPEN_SUNDAY_FROM_TYPE + ", " +
            OPEN_SUNDAY_TO + " " + OPEN_SUNDAY_TO_TYPE + ", " +
            LOCATION_LATITUDE + " " + LOCATION_LATITUDE_TYPE + ", " +
            LOCATION_LONGITUDE + " " + LOCATION_LONGITUDE_TYPE + ", " +
            FAVORITED + " " + FAVORITED_TYPE +
        ")";
	
	private SQLiteDatabase db;
	private DatabaseHelperOld dbHelper;
	private Context context;
	
	
	public ProvidersDB(Context ctx) throws Exception {
	    if (true) throw new Exception("Deprecated");
		context = ctx;
		dbHelper = new DatabaseHelperOld(context);
	}

	public ProvidersDB open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public void truncateTable() {
		dbHelper.dropTable(db, TABLE_NAME);
		db.execSQL(ProvidersDB.CREATE_TABLE);
	}
        
        public void insertProviders(Vector<Provider> providers) {
            //db.execSQL("TRUNCATE TABLE " + TABLE_NAME);
        	truncateTable();
            for (Provider provider : providers) {
                insert(provider);
            }
        }
        
        public long insert(Provider provider) {
            ContentValues values = new ContentValues();
            values.put(HASH, provider.getHash());
            values.put(NAME, provider.getName());
            values.put(ADDRESS, provider.getAddress());
            values.put(POST, provider.getPost());
            values.put(COUNTRY, provider.getCountry());
            values.put(PRICE, provider.getPrice());
            values.put(PHONE, provider.getPhone());
            values.put(OPEN_WORKDAY_FROM, provider.getOpenWorkdayFrom() == null ? null : provider.getOpenWorkdayFrom().toString());
            values.put(OPEN_WORKDAY_TO, provider.getOpenWorkdayTo() == null ? null : provider.getOpenWorkdayTo().toString());
            values.put(OPEN_SATURDAY_FROM, provider.getOpenWorkdayFrom() == null ? null : provider.getOpenWorkdayFrom().toString());
            values.put(OPEN_SATURDAY_TO, provider.getOpenWorkdayTo() == null ? null : provider.getOpenWorkdayTo().toString());
            values.put(OPEN_SUNDAY_FROM, provider.getOpenSundayFrom() == null ? null : provider.getOpenSundayFrom().toString());
            values.put(OPEN_SUNDAY_TO, provider.getOpenSundayTo() == null ? null : provider.getOpenSundayTo().toString());
            values.put(LOCATION_LATITUDE, provider.getLocationLatitude());
            values.put(LOCATION_LONGITUDE, provider.getLocationLongitude());
            values.put(FAVORITED, 0);
            return db.insert(TABLE_NAME, null, values);
        }
        
        public Vector<Provider> getAll() {
            Vector<Provider> providers = new Vector<Provider>();
            Cursor cursor = db.rawQuery("SELECT " +
				TABLE_NAME + "." + ID + ", " +
				TABLE_NAME + "." + HASH + ", " +
				TABLE_NAME + "." + NAME + ", " +
				TABLE_NAME + "." + ADDRESS + ", " +
				TABLE_NAME + "." + POST + ", " +
				TABLE_NAME + "." + COUNTRY + ", " +
				TABLE_NAME + "." + PRICE + ", " +
				TABLE_NAME + "." + PHONE + ", " +
				TABLE_NAME + "." + OPEN_WORKDAY_FROM + ", " +
				TABLE_NAME + "." + OPEN_WORKDAY_TO + ", " +
				TABLE_NAME + "." + OPEN_SATURDAY_FROM + ", " +
				TABLE_NAME + "." + OPEN_SATURDAY_TO + ", " +
				TABLE_NAME + "." + OPEN_SUNDAY_FROM + ", " +
				TABLE_NAME + "." + OPEN_SUNDAY_TO + ", " +
				TABLE_NAME + "." + LOCATION_LATITUDE + ", " +
				TABLE_NAME + "." + LOCATION_LONGITUDE + ", " +
				TABLE_NAME + "." + FAVORITED +
		    " FROM " + TABLE_NAME,
		    new String[]{});
            while (cursor.moveToNext()) {
            	Provider provider = new Provider(
        			cursor.getLong(ID_ID),	
                    cursor.getString(HASH_ID),
                    cursor.getString(NAME_ID),
                    cursor.getString(ADDRESS_ID),
                    cursor.getString(POST_ID),
                    cursor.getString(COUNTRY_ID),
                    cursor.getDouble(PRICE_ID),
                    cursor.getString(PHONE_ID),
                    strToTime(cursor.getString(OPEN_WORKDAY_FROM_ID)),
                    strToTime(cursor.getString(OPEN_WORKDAY_TO_ID)),
                    strToTime(cursor.getString(OPEN_SATURDAY_FROM_ID)),
                    strToTime(cursor.getString(OPEN_SATURDAY_TO_ID)),
                    strToTime(cursor.getString(OPEN_SUNDAY_FROM_ID)),
                    strToTime(cursor.getString(OPEN_SUNDAY_TO_ID)),
                    cursor.getDouble(LOCATION_LATITUDE_ID),
                    cursor.getDouble(LOCATION_LONGITUDE_ID),
                    cursor.getInt(FAVORITED_ID)
                );
            	providers.add(provider);
            }
            return providers;
        }
        
        private static java.sql.Time strToTime(String t) {
        	if (t != null && t.length() > 0) {
        		try {
		        	String[] str = t.split(":");
		        	java.sql.Time time = new java.sql.Time(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
		        	return time;
        		} catch (Exception ex) { }
        	}
        	return null;
        }

		public void setFavorited(String hash, boolean favorite) {
			open();
			int f = favorite ? 1 : 0;
			db.execSQL("UPDATE " + TABLE_NAME + " SET " + FAVORITED + " = " + f + " WHERE " + HASH + " = \"" + hash + "\"");
			close();
		}

		public boolean dataExists() {
            Cursor cursor = db.rawQuery("SELECT " + ID + " FROM " + TABLE_NAME + " LIMIT 1", new String[] {});
            if (cursor.moveToNext()) {
            	return true;
            }
			return false;
		}
}

