package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class FavoritedRestaurantData extends Data {
	private int userId;
	private int restaurantId;
	
	public FavoritedRestaurantData(SoapObject obj) {
        super(obj);
        
        userId = getInt("userId");
        restaurantId = getInt("restaurantId");
    }

    public int getUserId() {
		return userId;
	}
	
	public int getRestaurantId() {
		return restaurantId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
}
