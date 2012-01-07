package com.sciget.studentmeals.database.data;

import java.sql.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.database.Database;

public class RestaurantMenuData extends Data {
	public static final String NAME = "restaurants_menues";
	
    public static class Menu {
        private Vector<String> menuList = new Vector<String>();
        
        public Menu(String json) {
            JSONArray object = (JSONArray) JSONValue.parse(json);
            for (int i = 0; i < object.size(); i++) {
                menuList.add((String) object.get(i));
            }
        }
        
        public boolean isEmpty() {
            return menuList.isEmpty();
        }
        
        public Vector<String> parseJson() {
            return menuList;
        }
        
        public String toJson() {
            JSONArray list = new JSONArray();
            for (String element : menuList) {
                list.add(element);
            }
            return list.toString();
        }
    }
	
	public int id;
	public int restaurantId;
	public Date date;
	public String menu;
	
	public Vector<String> getMenu() {
		return new Menu(menu).parseJson();
	}
	
	public RestaurantMenuData() {}
	
	public RestaurantMenuData(int id, int restaurantId, Date date, String menu) {
		this.id = id;
		this.restaurantId = restaurantId;
		this.date = date;
		this.menu = menu;
	}
	
	public RestaurantMenuData(Date date, String menu) {
		this.date = date;
		this.menu = menu;
	}

	public RestaurantMenuData(int restaurantId, Date date, String menu) {
		this.restaurantId = restaurantId;
		this.date = date;
		this.menu = menu;
	}
	
    public RestaurantMenuData(int restaurantId, String date, String menu) {
        this.restaurantId = restaurantId;
        this.date = Database.toDate(date);
        this.menu = menu;
    }

	public int add(Database db) {
		return db.update("INSERT INTO " + NAME + " (restaurantId, date, menu) VALUES (?, ?, ?)", new Object[] { restaurantId, date, menu });
	}

    @Override
    public void create(SQLiteDatabase db) {
        //if (Database.tableExists(db, NAME)) return;
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        db.execSQL(
            "CREATE TABLE `" + NAME + "` (" +
            "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  `restaurantId` INTEGER," +
            "  `date` DATE," +
            "  `menu` TEXT" +
            ")"
        );
    }

    public String getFood1() {
        if (date == null) {
            return "Stalna ponudba";
        } else {
            return toString(date);
        }
    }
    
    public String getFood2() {
        return menu; // TODO popravi
    }
}
