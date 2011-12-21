package si.feri.projekt.studentskaprehrana;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.sciget.studentmeals.database.data.RestaurantData;

import android.content.SharedPreferences;
import android.location.Location;


public class Settings {
	public static Main main;
	public static final String SETTINGS = "SETTINGS";
	
	public static String username;
	public static String password;
	
	public static double subsidy = 2.62; // cena subvencije
	
	public static int openTimeType;
	
	public static final int WORKDAY = 1;
	public static final int SATURDAY = 2;
	public static final int SUNDAY = 3;
	
	public static Boolean firstUpdate;
	
	public static int CURRENT_LIST_ACTIVITY;
	
	public static ArrayList<RestaurantData> arrayListProviders;
	
	public static double locationLat = 46.5575721;
	public static double locationLon = 15.6375547;
	
	public static boolean startLoadingProviders = false;
	public static boolean startLoadingMenus = false;
	
	public static void setCurrentListActivity(int type) {
		CURRENT_LIST_ACTIVITY = type;
	}
	
	public static int getOpenTimeType() {
		if (openTimeType == 0) {
			setOpenTimeType();
		}
		return openTimeType;
	}
	
	public static void setOpenTimeType() {
		GregorianCalendar newCal = new GregorianCalendar();
		int day = newCal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SUNDAY) {
			openTimeType = SUNDAY;
		} else if (day == Calendar.SATURDAY) {
			openTimeType = SATURDAY;
		} else {
			openTimeType = WORKDAY;
		}
	}
	
	/*
	 * Nastavi glavno aktivnost
	 */
	public static void setMain(Main m) {
		main = m;
	}
	
	public static String getUsername() {
		if (username == null) {
			SharedPreferences settings = main.getSharedPreferences(SETTINGS, 0);
			username = settings.getString("username", "");
		}
		return username;
	}
	
	public static boolean getFirstUpdate(ListApplication app) {
		if (firstUpdate == null) {
			SharedPreferences settings = app.getSharedPreferences(SETTINGS, 0);
			firstUpdate = settings.getBoolean("firstUpdate", true);
		}
		return firstUpdate;
	}
	
	public static void firstUpdateDone(ListApplication app) {
		SharedPreferences settings = app.getSharedPreferences(SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("firstUpdate", false);
		editor.commit();
	}
	
	public static String getPassword() {
		if (password == null) {
			SharedPreferences settings = main.getSharedPreferences(SETTINGS, 0);
			username = settings.getString("password", "");
		}
		return password;
	}
	
	public static void setUsername(String s) {
		SharedPreferences settings = main.getSharedPreferences(SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("username", s);
		editor.commit();
	}
	
	public static void setPassword(String s) {
		SharedPreferences settings = main.getSharedPreferences(SETTINGS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("password", s);
		editor.commit();
	}

	public static double getSubsidy() {
		return subsidy;
	}
	
	public static void setLocation(double lat, double lon) {
		Settings.locationLat = lat;
		Settings.locationLon = lon;
	}

}