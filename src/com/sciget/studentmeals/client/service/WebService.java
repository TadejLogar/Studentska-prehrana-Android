package com.sciget.studentmeals.client.service;

import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public abstract class WebService {
	private String url;
	private String namespace;
	
	private SoapSerializationEnvelope soapSerializationEnvelope;
	private HttpTransportSE httpTransportSE;
	private String method;
	private SoapObject soapObject;
	
	public WebService(String url, String namespace) {
		this.url = url;
		this.namespace = namespace;
	}

	protected void setMethodName(String method) {
		this.method = method;
	}

	protected void prepare() {
		soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapSerializationEnvelope.dotNet = false;

		soapObject = new SoapObject(namespace, method); 
		soapSerializationEnvelope.setOutputSoapObject(soapObject);
	}
	
	private Object request0() {
		if (soapSerializationEnvelope == null) return null;
		
        try {
            httpTransportSE = new HttpTransportSE(url, 300000);
            httpTransportSE.call(namespace + method, soapSerializationEnvelope);
            Object obj = soapSerializationEnvelope.getResponse();   
            return obj;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
        	this.soapSerializationEnvelope = null;
        	this.httpTransportSE = null;
        	this.method = null;
        	this.soapObject = null;
        }
        
        return null;
	}
	
	protected SoapObject request() {
		return (SoapObject) request0();
	}
	
	protected SoapPrimitive requestPrimitive() {
		return (SoapPrimitive) request0();
	}
	
	protected Vector<SoapObject> requestVector() {
		Object obj = request0();
		if (obj == null) {
			return new Vector<SoapObject>();
		} else if (obj instanceof Vector) {
			return (Vector<SoapObject>) obj; 
		} else {
			Vector<SoapObject> list = new Vector<SoapObject>();
			list.add((SoapObject) obj);
			return list;
		}
	}
	
	protected SoapObject request(String method) {
		setMethodName(method);
		prepare();
		return request();
	}
	
	protected Vector<SoapObject> requestVector(String method) {
		setMethodName(method);
		prepare();
		return requestVector();
	}
	
	protected SoapPrimitive requestPrimitive(String method) {
		setMethodName(method);
		prepare();
		return (SoapPrimitive) request0();
	}
	
	protected void addString(String key, String value) {
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(PropertyInfo.STRING_CLASS);
		propertyInfo.setName(key);
		propertyInfo.setValue(value);
		soapObject.addProperty(propertyInfo);
	}
	
	protected void addInt(String key, int value) {
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(PropertyInfo.INTEGER_CLASS);
		propertyInfo.setName(key);
		propertyInfo.setValue(value);
		soapObject.addProperty(propertyInfo);
	}

}
