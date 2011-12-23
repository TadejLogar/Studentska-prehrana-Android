package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class HistoryData extends Data {
	public int userId;
	public String time;
	public String provider;
	public String company;
	public double fee;
	public double fullPrice;
	public String note;
	
	public HistoryData(SoapObject obj) {
		super(obj);
		
		this.userId = getInt("userId");
		this.time = get("time");
		this.provider = get("provider");
		this.company = get("company");
		this.fee = getDouble("fee");
		this.fullPrice = getDouble("fullPrice");
		this.note = get("note");
	}
	
	public HistoryData(int userId, String time, String provider, String company, double fee, double fullPrice, String note) {
		this.userId = userId;
		this.time = time;
		this.provider = provider;
		this.company = company;
		this.fee = fee;
		this.fullPrice = fullPrice;
		this.note = note;
	}

	public int getUserId() {
		return userId;
	}

	public String getTime() {
		return time;
	}

	public String getProvider() {
		return provider;
	}

	public String getCompany() {
		return company;
	}

	public double getFee() {
		return fee;
	}

	public double getFullPrice() {
		return fullPrice;
	}

	public String getNote() {
		return note;
	}
}
