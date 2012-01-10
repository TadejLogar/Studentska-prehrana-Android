package com.sciget.studentmeals.database.data;

import java.sql.Time;
import java.util.Calendar;

import si.feri.projekt.studentskaprehrana.Settings;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.studentmeals.MyPerferences.Location;
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
	public String mapImageSha1;
	
	public String imageSha1;

    private boolean favorited;

    private float distance;
	
	public static final double SUBSIDY = 2.62;
	
	public int getId() { return id; }
	public String getHash() { return hash; }
	public String getName() { return name; }
	public String getAddress() { return address; }
	public String getPost() { return post; }
	public String getCountry() { return country; }
	public double getPrice() { return price - SUBSIDY; }
	public String getPhone() { return phone; }
	public String getOpenWorkdayFrom() {
	    return toString(openWorkdayFrom);
    }
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
	
	public RestaurantData(int id, String hash, String name, String address, String post, String country, double price, String phone, Time openWorkdayFrom, Time openWorkdayTo, Time openSaturdayFrom, Time openSaturdayTo, Time openSundayFrom, Time openSundayTo, double locationLatitude, double locationLongitude, String features, String message, String imageSha1, String mapImageSha1) {
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
		this.imageSha1 = imageSha1;
		this.mapImageSha1 = mapImageSha1;
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
        //if (Database.tableExists(db, NAME)) return;
        db.execSQL("DROP TABLE IF EXISTS " + NAME);
        String sql = new StringBuilder().append("CREATE TABLE `" + NAME + "` (\n").
        append("  `id` INTEGER,\n").
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
        append("  `message` text,\n").
        append("  `imageSha1` varchar(50),\n").
        append("  `mapImageSha1` varchar(50)\n").
        append(")").toString();
        db.execSQL(sql);
    }
    public String getFullAddress() {
        return address + ", " + post;
    }
    
    public String getEuroFee() {
        String fee = getFee() + "";
        fee = fee.replace('.', ',');
        return fee + " €";
    }
    
    public double getFee() {
        double fee = round2(price - Settings.getSubsidy());
        if (fee < 0) {
            return 0;
        } else {
            return fee;
        }
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
        
        String lowerStr = str;
        
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
    
    public boolean isNear(double latitude, double longitude) {
        if (Math.abs(locationLatitude - latitude) < 0.5 && Math.abs(locationLongitude - longitude) < 0.5) {
            return true;
        }
        return false;
    }
    
    public Double howNear(double lat, double lon) {
        double distance = Math.sqrt(Math.pow(lat - locationLatitude, 2.0) + Math.pow(lon - locationLongitude, 2.0));
        return distance;
        //return Math.abs(locationLatitude - lat);
    }
    
    public static float distanceInMeters(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Float(dist * meterConversion).floatValue();
    }
    
    public boolean isOpen() {
        int currentTimeInMinutes = getTimeInMinutes(null);
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        
        Time timeFrom = null;
        Time timeTo = null;
        if (dayOfWeek == Calendar.SUNDAY) {
            timeFrom = openSundayFrom;
            timeTo = openSundayTo;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            timeFrom = openSaturdayFrom;
            timeTo = openSaturdayTo;
        } else {
            timeFrom = openWorkdayFrom;
            timeTo = openWorkdayTo;
        }
        
        int timeFromInMinutes = getTimeInMinutes(timeFrom);
        int timeToInMinutes = getTimeInMinutes(timeTo);
        
        if (timeFromInMinutes < timeToInMinutes) {
            return currentTimeInMinutes >= timeFromInMinutes && currentTimeInMinutes < timeToInMinutes;
        } else {
            return currentTimeInMinutes >= timeFromInMinutes && currentTimeInMinutes < timeToInMinutes; // TODO: popravi (čas po polnoči)
        }
    }
    
    public static int getTimeInMinutes(Time time) {
        Calendar calendar = Calendar.getInstance();
        if (time != null) calendar.setTimeInMillis(time.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int currentTime = hour * 60 + minute;
        return currentTime;
    }
    
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
    
    public boolean getFavorited() {
        return favorited;
    }
    public String getOpenTime() {
        if (getOpenWorkdayFrom() != null) {
            return getOpenWorkdayFrom() + " - " + getOpenWorkdayTo();
        } else {
            return "";
        }
    }
    
    public String getOpenTimes() {
        StringBuilder str = new StringBuilder();
        
        if (getOpenWorkdayFrom() != null) {
            str.append("Delovnik: " + getOpenWorkdayFrom() + " - " + getOpenWorkdayTo()).append("\n");
        }
        
        if (getOpenSaturdayFrom() != null) {
            str.append("Sobota: " + getOpenSaturdayFrom() + " - " + getOpenSaturdayTo()).append("\n");
        }
        
        if (getOpenSundayFrom() != null) {
            str.append("Nedelja: " + getOpenSundayFrom() + " - " + getOpenSundayTo());
        }
        
        return str.toString();
    }
    
    public String getOpenTimeWorkday() {
        return generateTime("Delovnik", getOpenWorkdayFrom(), getOpenWorkdayTo());
    }
    
    public String getOpenTimeSaturday() {
        return generateTime("Sobota", getOpenSaturdayFrom(), getOpenSaturdayTo());
    }
    
    public String getOpenTimeSunday() {
        return generateTime("Nedelja", getOpenSundayFrom(), getOpenSundayTo());
    }
    
    private String generateTime(String day, String from, String to) {
        StringBuilder str = new StringBuilder();
        str.append(day);
        str.append(": ");
        if (from == null) {
            str.append("zaprto");
        } else {
            str.append(from + " - " + to);
        }
        return str.toString();
    }
    
    public String getFullPhone() {
        if (phone != null && phone.length() > 0) {
            return "Telefon: " + phone;
        } else {
            return null;
        }
    }
    
    public void setDistance(Location location) {
        distance = distanceInMeters(new Float(locationLatitude), new Float(locationLongitude), new Float(location.latitude), new Float(location.longitude));
        distance = Math.round(distance * 100) / 100;
    }
    
    public float getDistance() {
        return distance;
    }

}