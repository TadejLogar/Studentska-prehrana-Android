package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class CommentData extends Data {
    public int userId;
    public int restaurantId;
    public String comment;
    public String time;

    public CommentData(int userId, int restaurantId, String comment, String time) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.time = time;
    }

    public CommentData(SoapObject soapObject) {
        super(soapObject);
        
        this.userId = getInt("userId");
        this.restaurantId = getInt("restaurantId");
        this.comment = get("comment");
        this.time = get("time");
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
