package com.sciget.studentmeals.client.service.data;

import java.sql.Date;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import android.util.Log;

public class MenuData {
	public int restaurantId;
	public String date;
	public String menu;
	
	public MenuData(SoapObject obj) {
		try {
			restaurantId = Integer.parseInt(obj.getProperty("restaurantId").toString());
			date = toString(obj.getProperty("date"));
			menu = obj.getPropertyAsString("menu");
		} catch (Exception e) {
		    Log.e("MenuData", e.toString());
		}
	}
	
	public String toString(Object obj) {
	    if (obj == null) {
	        return null;
	    } else {
	        return obj.toString();
	    }
	}
	
	public MenuData(int restaurantId, String date, String menu) {
		super();
		this.restaurantId = restaurantId;
		this.date = date;
		this.menu = menu;
	}
	
	public int getRestaurantId() {
		return restaurantId;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getMenu() {
		return menu;
	}

	@Override
	public String toString() {
		return "MenuData [restaurantId=" + restaurantId + ", date=" + date + ", menu=" + menu + "]";
	}
	
}
