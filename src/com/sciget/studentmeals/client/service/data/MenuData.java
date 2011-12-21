package com.sciget.studentmeals.client.service.data;

import java.sql.Date;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class MenuData {
	public int restaurantId;
	public String date;
	public String menu;
	
	public MenuData(SoapObject obj) {
		try {
			restaurantId = Integer.parseInt(obj.getProperty("restaurantId").toString());
			date = obj.getPropertyAsString("date");
			menu = obj.getPropertyAsString("menu");
		} catch (Exception e) {
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
