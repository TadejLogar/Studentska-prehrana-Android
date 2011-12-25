package si.feri.projekt.studentskaprehrana;

import java.sql.Time;

import si.feri.projekt.studentskaprehrana.db.MenusDB;

import android.location.Location;

/*
 * Ponudnik - restavracija
 */
@Deprecated
public class Provider {
    private String hash;
    private String name;
    private String address;
    private String post;
    private String country;
    private double price;
    private String phone;
    private Time openWorkdayFrom;
    private Time openWorkdayTo;
    private Time openSaturdayFrom;
    private Time openSaturdayTo;
    private Time openSundayFrom;
    private Time openSundayTo;
    private double locationLatitude;
    private double locationLongitude;
    
    private boolean favorited;
    private long id; // id elementa v bazi telefona
    private Boolean menu = null; // Ali obstajajo podatki o menujih (null - podatki še niso znani, true - obstajajo, false - ne obstajajo)
    
    public Provider() {
    }

    public Provider(long id, String hash, String name, String address, String post, String country, double price, String phone, Time openWorkdayFrom, Time openWorkdayTo, Time openSaturdayFrom, Time openSaturdayTo, Time openSundayFrom, Time openSundayTo, double locationLatitude, double locationLongitude, int favorited) {
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
        this.favorited = favorited == 1;
    }
    
    public void setId(long id) {
    	this.id = id;
    }
    
    public long getId() {
    	return id;
    }

	public String getAddress() {
        return address;
    }
	
	public String getFullAddress() {
		return address + ", " + post;
	}

    public String getCountry() {
        return country;
    }

    public String getHash() {
        return hash;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public String getName() {
        return name;
    }

    public Time getOpenSaturdayFrom() {
        return openSaturdayFrom;
    }

    public Time getOpenSaturdayTo() {
        return openSaturdayTo;
    }

    public Time getOpenSundayFrom() {
        return openSundayFrom;
    }

    public Time getOpenSundayTo() {
        return openSundayTo;
    }

    public Time getOpenWorkdayFrom() {
        return openWorkdayFrom;
    }

    public Time getOpenWorkdayTo() {
        return openWorkdayTo;
    }

    public String getPhone() {
        return phone;
    }

    public String getPost() {
        return post;
    }

    public double getPrice() {
        return price;
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

    public String getOpenTime() {
        int type = Settings.openTimeType;
        if (type == Settings.WORKDAY) {
        		if (getOpenWorkdayFrom() != null && getOpenWorkdayTo() != null) {
        			return shortTime(getOpenWorkdayFrom().toString()) + " - " + shortTime(getOpenWorkdayTo().toString());
        		}
        } else if (type == Settings.SATURDAY) {
                return getOpenSaturdayFrom() + " - " + getOpenSaturdayTo();
        } else if (type == Settings.SUNDAY) {
                return getOpenSundayFrom() + " - " + getOpenSundayTo();
        }
		if (getOpenWorkdayFrom() != null && getOpenWorkdayTo() != null) {
			return shortTime(getOpenWorkdayFrom().toString()) + " - " + shortTime(getOpenWorkdayTo().toString());
		}
		return "";
    }
    
    private String shortTime(String time) {
    	int last = time.lastIndexOf(':');
    	if (last == -1) {
    		return "";
    	} else {
    		return time.substring(0, last);
    	}
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
    
    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = Double.parseDouble(locationLatitude);
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = Double.parseDouble(locationLongitude);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpenSaturdayFrom(Time openSaturdayFrom) {
        this.openSaturdayFrom = openSaturdayFrom;
    }

    public void setOpenSaturdayTo(Time openSaturdayTo) {
        this.openSaturdayTo = openSaturdayTo;
    }

    public void setOpenSundayFrom(Time openSundayFrom) {
        this.openSundayFrom = openSundayFrom;
    }

    public void setOpenSundayTo(Time openSundayTo) {
        this.openSundayTo = openSundayTo;
    }

    public void setOpenWorkdayFrom(Time openWorkdayFrom) {
        this.openWorkdayFrom = openWorkdayFrom;
    }

    public void setOpenWorkdayTo(Time openWorkdayTo) {
        this.openWorkdayTo = openWorkdayTo;
    }
    
    public void setOpenSaturdayFrom(String openSaturdayFrom) {
        this.openSaturdayFrom = parseTime(openSaturdayFrom);
    }

    public void setOpenSaturdayTo(String openSaturdayTo) {
        this.openSaturdayTo = parseTime(openSaturdayTo);
    }

    public void setOpenSundayFrom(String openSundayFrom) {
        this.openSundayFrom = parseTime(openSundayFrom);
    }

    public void setOpenSundayTo(String openSundayTo) {
        this.openSundayTo = parseTime(openSundayTo);
    }

    public void setOpenWorkdayFrom(String openWorkdayFrom) {
        this.openWorkdayFrom = parseTime(openWorkdayFrom);
    }

    public void setOpenWorkdayTo(String openWorkdayTo) {
        this.openWorkdayTo = parseTime(openWorkdayTo);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }
    
    private Time parseTime(String s) {
        try {
        	java.sql.Time myTime = java.sql.Time.valueOf(s);
        	return myTime;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

	public String getEuroFee() {
		String fee = getFee() + "";
		fee = fee.replace('.', ',');
		return fee + " €";
	}

	public void setFavorited(boolean b) {
		favorited = b;
	}
	
	public boolean getFavorited() {
		return favorited;
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
	
	public Boolean getMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}
    
}
