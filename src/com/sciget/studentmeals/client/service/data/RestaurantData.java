package com.sciget.studentmeals.client.service.data;

import java.sql.Time;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

public class RestaurantData extends Data {
	public static class Features {
		public static final int CELIAC = 1; // celiakiji prijazni obroki
		public static final int DELIVERY = 2; // dostava
		public static final int MEAL_ASSEMBLY = 3; // možnost sestavljanja obrokov  
		public static final int SALAD = 4; // solatni bar
		public static final int INVALID = 5; // dostop za invalide
		public static final int INVALID_WC = 6; // dostop za invalide (WC)
		public static final int VEGETARIAN = 7; // vegetarijanska prehrana
		public static final int WEEKEND = 8; // odprto ob vikendih
		
		public boolean celiac; // celiakiji prijazni obroki
		public boolean delivery; // dostava
		public boolean mealAssembly; // možnost sestavljanja obrokov
		public boolean salad; // solatni bar
		public boolean invalid; // dostop za invalide
		public boolean invalidWC; // dostop za invalide (WC)
		public boolean vegetarian; // vegetarijanska prehrana
		public boolean weekend; // odprto ob vikendih
		
		public String values;
		
		public Features() {}
		
		public void parse(String values) {
			this.values = values;
			if (values != null && values.length() == 8) {
				celiac = booleanValue(values.charAt(0));
				delivery = booleanValue(values.charAt(1));
				mealAssembly = booleanValue(values.charAt(2));
				salad = booleanValue(values.charAt(3));
				invalid = booleanValue(values.charAt(4));
				invalidWC = booleanValue(values.charAt(5));
				vegetarian = booleanValue(values.charAt(6));
				weekend = booleanValue(values.charAt(7));
			}
		}
		
		public Vector<String> toList() {
		    Vector<String> list = new Vector<String>();
	        if (celiac) list.add("celiakiji prijazni obroki");
	        if (delivery) list.add("dostava");
	        if (mealAssembly) list.add("možnost sestavljanja obrokov");
	        if (salad) list.add("solatni bar");
	        if (invalid) list.add("dostop za invalide");
	        if (invalidWC) list.add("dostop za invalide (WC)");
	        if (vegetarian) list.add("vegetarijanska prehrana");
	        if (weekend) list.add("odprto ob vikendih");
	        return list;
		}
		
		public String toString() {
		    StringBuilder str = new StringBuilder();
		    Vector<String> list = toList();
		    for (String element : list) {
		        if (str.length() > 0) str.append(", ");
		        str.append(element);
		    }
		    return str.toString();
		}
		
		/*public String toString() {
		    return values;
		}*/
		
		public boolean booleanValue(char b) {
			return b == '1';
		}
	}
	
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
	public String imageSha1;
	
	public RestaurantData(SoapObject obj) {
		super(obj);
		
		this.id = getInt("id");
		this.hash = get("hash");
		this.name = get("name");
		this.address = get("address");
		this.post = get("post");
		this.country = get("country");
		this.price = getDouble("price");
		this.phone = get("phone");
		this.openWorkdayFrom = getTime("openWorkdayFrom");
		this.openWorkdayTo = getTime("openWorkdayTo");
		this.openSaturdayFrom = getTime("openSaturdayFrom");
		this.openSaturdayTo = getTime("openSaturdayTo");
		this.openSundayFrom = getTime("openSundayFrom");
		this.openSundayTo = getTime("openSundayTo");
		this.locationLatitude = getDouble("locationLatitude");
		this.locationLongitude = getDouble("locationLongitude");
		this.features = get("features");
		this.message = get("message");
		this.imageSha1 = get("imageSha1");
	}
	
	public int getId() {
	    return id;
	}
	
	public String getHash() {
		return hash;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPost() {
		return post;
	}
	public String getCountry() {
		return country;
	}
	public double getPrice() {
		return price;
	}
	public String getPhone() {
		return phone;
	}
	public Time getOpenWorkdayFrom() {
		return openWorkdayFrom;
	}
	public Time getOpenWorkdayTo() {
		return openWorkdayTo;
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
	public Double getLocationLatitude() {
		return locationLatitude;
	}
	public Double getLocationLongitude() {
		return locationLongitude;
	}
	public String getFeatures() {
		return features;
	}
	public String getMessage() {
		return message;
	}

	/*public void setFeatures(String features) {
		Features f = new Features();
		f.parse(features);
		this.features = f;
	}*/
	
    public String getImageSha1() {
        return imageSha1;
    }
	
}
