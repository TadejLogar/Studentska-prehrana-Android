package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class RecaptchaData extends Data {
	private String id;
	private String imageUrl;
	
	public RecaptchaData(SoapObject obj) {
		super(obj);
		this.id = get("id");
		this.imageUrl = get("imageUrl");
	}
	
	public RecaptchaData(String id, String imageUrl) {
		this.id = id;
		this.imageUrl = imageUrl;
	}
	
	public String getId() {
		return id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
