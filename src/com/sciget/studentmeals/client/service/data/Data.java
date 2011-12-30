package com.sciget.studentmeals.client.service.data;

import java.sql.Time;

import org.ksoap2.serialization.SoapObject;

public class Data {
	private SoapObject obj;
	
	public Data() {}
	
	public Data(SoapObject obj) {
		this.obj = obj;
	}
	
	public String get(String name) {
		try {
			return obj.getPropertyAsString(name);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean getBoolean(String name) {
        try {
            return Boolean.parseBoolean(obj.getProperty(name).toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
	}
	
	public int getInt(String name) {
		return parseInt(obj.getProperty(name));
	}
	
	public double getDouble(String name) {
		return parseDouble(obj.getProperty(name));
	}

	public static int parseInt(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double parseDouble(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Time getTime(String name) {
		try {
			String value = get(name);
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
