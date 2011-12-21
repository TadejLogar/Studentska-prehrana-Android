package com.sciget.studentmeals.database.data;

import java.sql.Time;

import si.feri.projekt.studentskaprehrana.Settings;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.client.service.data.RestaurantData.Features;
import com.sciget.studentmeals.database.Database;

public class RestaurantData extends Data {
	public static final String NAME = "restaurants";

	public int id;
	public String hash;
	public String name;
	public String address;
	public String post;
	public String country;
	public double price;
	public String phone;
	public Time openWorkdayFrom;
	public Time openWorkdayTo;
	public Time openSaturdayFrom;
	public Time openSaturdayTo;
	public Time openSundayFrom;
	public Time openSundayTo;
	public Double locationLatitude;
	public Double locationLongitude;
	public String features;
	public String message;
	
	public static final double SUBSIDY = 2.62;
	
	public int getId() { return id; }
	public String getHash() { return hash; }
	public String getName() { return name; }
	public String getAddress() { return address; }
	public String getPost() { return post; }
	public String getCountry() { return country; }
	public double getPrice() { return price - SUBSIDY; }
	public String getPhone() { return phone; }
	public String getOpenWorkdayFrom() { return toString(openWorkdayFrom); }
	public String getOpenWorkdayTo() { return toString(openWorkdayTo); }
	public String getOpenSaturdayFrom() { return toString(openSaturdayFrom); }
	public String getOpenSaturdayTo() { return toString(openSaturdayTo); }
	public String getOpenSundayFrom() { return toString(openSundayFrom); }
	public String getOpenSundayTo() { return toString(openSundayTo); }
	public double getLocationLatitude() { return locationLatitude; }
	public double getLocationLongitude() { return locationLongitude; }
	public String getLocation() {
		if (post == null || post.length() == 0) return null;
		int i = 0;
		for (i = 0; i < post.length(); i++) {
			char c = post.charAt(i);
			if (!(Character.isDigit(c) || c == ' ')) break;
		}
		try {
			return post.substring(i);
		} catch (Exception e) {
			return null;
		}
	}
	public String getFullName() {
		String location = getLocation();
		if (location == null) {
			return name;
		} else {
			return name + ", " + location;
		}
	}
	
	public Features getFeatures() {
		Features f = new Features();
		f.parse(features);
		return f;
	}
	
	public RestaurantData() {}
	
	public RestaurantData(int id, String hash) {
		this.id = id;
		this.hash = hash;
	}
	
	public RestaurantData(int id, String hash, String name, String address, String post, String country, double price, String phone, Time openWorkdayFrom, Time openWorkdayTo, Time openSaturdayFrom, Time openSaturdayTo, Time openSundayFrom, Time openSundayTo, double locationLatitude, double locationLongitude, String features, String message) {
		this.id = id;
		this.hash = hash;
		this.name = name;
		this.address = address;
		this.post = post;
		this.country = country;
		this.price = price;
		this.phone = phone;
		this.openWorkdayFrom = openWorkdayFrom;
		this.openWorkdayTo = openWorkdayTo;
		this.openSaturdayFrom = openSaturdayFrom;
		this.openSaturdayTo = openSaturdayTo;
		this.openSundayFrom = openSundayFrom;
		this.openSundayTo = openSundayTo;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
		this.features = features;
		this.message = message;
	}
	
	public RestaurantData(String hash, String name, String address, String post, String country, double price, String phone, Time openWorkdayFrom, Time openWorkdayTo, Time openSaturdayFrom, Time openSaturdayTo, Time openSundayFrom, Time openSundayTo, Double locationLatitude, Double locationLongitude, String features, String message) {
		this.hash = hash;
		this.name = name;
		this.address = address;
		this.post = post;
		this.country = country;
		this.price = price;
		this.phone = phone;
		this.openWorkdayFrom = openWorkdayFrom;
		this.openWorkdayTo = openWorkdayTo;
		this.openSaturdayFrom = openSaturdayFrom;
		this.openSaturdayTo = openSaturdayTo;
		this.openSundayFrom = openSundayFrom;
		this.openSundayTo = openSundayTo;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
		this.features = features;
		this.message = message;
	}

	public int add(Database db) {
		int id = db.getValue("SELECT id FROM " + NAME + " WHERE hash = ?", hash);
		if (id == -1) {
			return db.update("INSERT INTO " + NAME + " (hash, name, address, post, country, price, phone, openWorkdayFrom, openWorkdayTo, openSaturdayFrom, openSaturdayTo, openSundayFrom, openSundayTo, locationLatitude, locationLongitude, features, message) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { hash, name, address, post, country, price, phone, openWorkdayFrom, openWorkdayTo, openSaturdayFrom, openSaturdayTo, openSundayFrom, openSundayTo, locationLatitude, locationLongitude, features, message });
		} else {
			db.update("UPDATE " + NAME + " SET name = ?, address = ?, post = ?, price = ?, features = ?, message = ? WHERE id = " + id, new Object[] { name, address, post, price, features, message });
			return id;
		}
	}
	
    @Override
    public void create(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        String sql = new StringBuilder().append("CREATE TABLE `" + NAME + "` (\n").
        append("  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n").
        append("  `hash` varchar(50),\n").
        append("  `name` varchar(250),\n").
        append("  `address` varchar(250),\n").
        append("  `post` varchar(100),\n").
        append("  `country` varchar(100),\n").
        append("  `price` double,\n").
        append("  `phone` varchar(50),\n").
        append("  `openWorkdayFrom` time,\n").
        append("  `openWorkdayTo` time,\n").
        append("  `openSaturdayFrom` time,\n").
        append("  `openSaturdayTo` time,\n").
        append("  `openSundayFrom` time,\n").
        append("  `openSundayTo` time,\n").
        append("  `locationLatitude` double,\n").
        append("  `locationLongitude` double,\n").
        append("  `features` varchar(20),\n").
        append("  `message` text\n").
        append(")").toString();
        db.execSQL(sql);
    }
    public String getFullAddress() {
        return address;
    }
    public boolean getFavorited() {
        return false;
    }
    
    public String getEuroFee() {
        String fee = getFee() + "";
        fee = fee.replace('.', ',');
        return fee + " €";
    }
    
    public double getFee() {
        return round2(price - Settings.getSubsidy());
    }
    
    private double round2(double value) {
        double result = value * 100;
        result = Math.round(result);
        result = result / 100;
        return result;
    }
    
    public boolean contains(String str) {
        if (str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5") || str.contains("6")) {
            try {
                double d = Double.parseDouble(str);
                if (d < 10) {
                    return getFee() <= d;
                }
            } catch (Exception ex) {
            }
        }
        
        String lowerStr = str.toLowerCase();
        
        if (name != null) {
            if (name.toLowerCase().contains(lowerStr)) return true;
        }
        
        if (address != null) {
            if (address.toLowerCase().contains(lowerStr)) return true;
        }
        
        if (post != null) {
            if (post.toLowerCase().contains(lowerStr)) return true;
        }
        
        return false;
    }
    
    public boolean isNear(double lat, double lon) {
        if (Math.abs(locationLatitude - lat) < 0.01 && Math.abs(locationLongitude - lon) < 0.01) {
            return true;
        }
        return false;
    }
    
    public Double howNear(double lat, double lon) {
        double distance = Math.sqrt(Math.pow(lat - locationLatitude, 2.0) + Math.pow(lon - locationLongitude, 2.0));
        return Math.abs(locationLatitude - lat);
    }
}