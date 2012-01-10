package com.sciget.studentmeals.client.service.data;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.ksoap2.serialization.SoapObject;

import android.util.Log;

public class Data {
	private SoapObject obj;
	
	public Data() {}
	
	public Data(SoapObject obj) {
		this.obj = obj;
	}
	
	public String get(String name) {
		try {
			String value = obj.getPropertyAsString(name);
			if (value.equals("anyType{}")) {
			    return "";
			} else {
			    return value;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean getBoolean(String name) {
        try {
            return Boolean.parseBoolean(obj.getProperty(name).toString());
        } catch (Exception e) {
            Log.e("Data getBoolean", e.toString());
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

			DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			java.util.Date date = sdf.parse(value);
			
			return new Time(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}
}
