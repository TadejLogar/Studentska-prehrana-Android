package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapPrimitive;

public class DataPrimitive {
	private SoapPrimitive primitive;
	
	public DataPrimitive(SoapPrimitive primitive) {
		this.primitive = primitive;
	}
	
	public double getDouble() {
		return Data.parseDouble(primitive);
	}

	public int getInt() {
		return Data.parseInt(primitive);
	}

	public String getString() {
		return primitive.toString();
	}
}
